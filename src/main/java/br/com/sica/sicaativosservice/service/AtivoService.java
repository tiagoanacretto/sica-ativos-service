package br.com.sica.sicaativosservice.service;

import br.com.sica.sicaativosservice.dtos.FromObject;
import br.com.sica.sicaativosservice.dtos.ParserFromDto;
import br.com.sica.sicaativosservice.dtos.ativos.AtivoDto;
import br.com.sica.sicaativosservice.dtos.ativos.ListagemAtivo;
import br.com.sica.sicaativosservice.enums.CondicaoManutencao;
import br.com.sica.sicaativosservice.enums.StatusAgendamento;
import br.com.sica.sicaativosservice.models.AgendamentoManutencaoAtivo;
import br.com.sica.sicaativosservice.models.Ativo;
import br.com.sica.sicaativosservice.models.Manutencao;
import br.com.sica.sicaativosservice.repositories.AgendamentoManutencaoAtivoRepository;
import br.com.sica.sicaativosservice.repositories.AtivoRepository;
import br.com.sica.sicaativosservice.repositories.ManutencaoRepository;
import br.com.sica.sicaativosservice.utils.FormatUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AtivoService {

    private final Logger LOGGER = LoggerFactory.getLogger(AtivoService.class);

    @Autowired
    private AtivoRepository ativoRepository;

    @Autowired
    private ManutencaoRepository manutencaoRepository;

    @Autowired
    private AgendamentoManutencaoAtivoRepository agendamentoManutencaoAtivoRepository;

    @Autowired
    private CondicaoManutencaoService condicaoManutencaoService;

    public AtivoService(final AtivoRepository ativoRepository, final AgendamentoManutencaoAtivoRepository agendamentoManutencaoAtivoRepository,
                        final CondicaoManutencaoService condicaoManutencaoService) {
        this.ativoRepository = ativoRepository;
        this.agendamentoManutencaoAtivoRepository = agendamentoManutencaoAtivoRepository;
        this.condicaoManutencaoService = condicaoManutencaoService;
    }

    public List<ListagemAtivo> listarTodosAtivos() {
        List<Ativo> ativos = ativoRepository.findAll();
        List<ListagemAtivo> listagemAtivos = new ArrayList<>(ativos.size());
        ativos.forEach(ativo -> {
            ListagemAtivo listagemAtivo = criarListagemAtivo(ativo);
            DateTime ultimaManutencao = pesquisarUltimaManutencao(ativo.getId());
            listagemAtivo.setUltimaManutencao(FormatUtils.dateTimeToString(ultimaManutencao));

            listagemAtivo.setProximaManutencao(FormatUtils.localDateToString(pesquisarProximaManutencao(ativo.getId())));
            listagemAtivos.add(listagemAtivo);
        });
        return listagemAtivos;
    }

    public AtivoDto salvar(AtivoDto ativoDto) {
        Ativo ativo = ParserFromDto.parse(ativoDto, true);
        ativo.setDataCadastro(DateTime.now());
        ativo.setAtivo(true);
        ativo.setStatusManutencao(CondicaoManutencao.EM_DIA);
        ativo = ativoRepository.save(ativo);
        LOGGER.info("Ativo {} criado com sucesso", ativo.getId());
        return FromObject.from(ativo);
    }

    public AtivoDto buscarPorId(Long id) {
        Ativo ativo = ativoRepository.findById(id).orElse(null);
        if (ativo != null) {
            LOGGER.info("Ativo {} encontrado com sucesso", id);
            return FromObject.from(ativo);
        } else {
            LOGGER.info("Ativo {} não encontrado", id);
            return null;
        }
    }

    public boolean apagarAtivo(Long id) {
        Ativo ativo = ativoRepository.findById(id).orElse(null);
        if (ativo != null) {
            LOGGER.info("Apagando ativo {}", id);
            ativoRepository.delete(ativo);
        } else {
            LOGGER.info("Ativo {} não encontrado", id);
        }
        return ativo != null;
    }

    public AtivoDto alterarAtivo(AtivoDto ativoDto, Long id) {
        Ativo ativoOrigem = ativoRepository.findById(id).orElse(null);
        if (ativoOrigem != null) {
            LOGGER.info("Alterando ativo {}", id);
            Ativo ativo = ParserFromDto.parse(ativoDto, false);

            // Voltar dados que nao deve ser editados
            ativo.setId(ativoOrigem.getId());
            ativo.setDataCadastro(ativoOrigem.getDataCadastro());

            LOGGER.info("Ativo {} alterado com sucesso", ativo.getId());
            ativo = ativoRepository.save(ativo);
            return FromObject.from(ativo);
        } else {
            LOGGER.info("Ativo {} não encontrado", id);
            return null;
        }
    }

    private DateTime pesquisarUltimaManutencao(Long ativoId) {
        Manutencao manutencao = manutencaoRepository.findTopByAtivoIdOrderByDataRealizadaDesc(ativoId);
        if (manutencao != null) {
            return manutencao.getDataRealizada();
        }
        return null;
    }

    private LocalDate pesquisarProximaManutencao(Long ativoId) {
        AgendamentoManutencaoAtivo agendamentoManutencaoAtivo = agendamentoManutencaoAtivoRepository.findTopByAtivoIdAndStatusOrderByDataAgendadaAsc(ativoId, StatusAgendamento.AGENDADA);
        if (agendamentoManutencaoAtivo != null) {
            return agendamentoManutencaoAtivo.getDataAgendada();
        }
        return null;
    }

    private CondicaoManutencao verificarCondicaoManutencao(Ativo ativo) {
        if (ativo.getIntervaloManutencao() != null) {
            //TODO
            return CondicaoManutencao.ATRASADA;
        }
        return CondicaoManutencao.EM_DIA;
    }

    private ListagemAtivo criarListagemAtivo(Ativo ativo) {
        ListagemAtivo listagemAtivo = new ListagemAtivo();
        listagemAtivo.setId(ativo.getId());
        listagemAtivo.setCodigo(ativo.getCodigo());
        listagemAtivo.setDescricao(ativo.getDescricao());
        listagemAtivo.setCategoria(ativo.getCategoria().getDescricao());
        listagemAtivo.setCondicao(ativo.getStatusManutencao());
        return listagemAtivo;
    }
}
