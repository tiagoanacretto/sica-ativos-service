package br.com.sica.sicaativosservice.controllers;

import br.com.sica.sicaativosservice.enums.CategoriaAtivo;
import br.com.sica.sicaativosservice.enums.CondicaoManutencao;
import br.com.sica.sicaativosservice.enums.IntervaloManutencao;
import br.com.sica.sicaativosservice.enums.TipoAgendaManutencao;
import br.com.sica.sicaativosservice.models.Ativo;
import br.com.sica.sicaativosservice.models.DisponibilidadeManutencao;
import br.com.sica.sicaativosservice.models.Manutencao;
import br.com.sica.sicaativosservice.models.ParametroAtivo;
import br.com.sica.sicaativosservice.repositories.AtivoRepository;
import br.com.sica.sicaativosservice.repositories.DisponibilidadeManutencaoRepository;
import br.com.sica.sicaativosservice.repositories.ManutencaoRepository;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/utils")
public class UtilsController {

    private final Logger LOGGER = LoggerFactory.getLogger(UtilsController.class);

    @Autowired
    private DisponibilidadeManutencaoRepository disponibilidadeManutencaoRepository;

    @Autowired
    private AtivoRepository ativoRepository;

    @Autowired
    private ManutencaoRepository manutencaoRepository;

    @PostMapping("/load")
    public ResponseEntity carregarDatabase() {
        LOGGER.info("Iniciando carragamento inicial do banco...");

        popularDisponibilidadeManutencao();

        popularAtivoAtrasado();

        popularAtivoEmDia();

        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    private void popularAtivoEmDia() {
        Ativo ativo = new Ativo();
        ativo.setStatusAtivo(true);
        ativo.setCodigo("MAQ-002");
        ativo.setCategoria(CategoriaAtivo.MAQUINA);
        ativo.setDescricao("Caminhão Mineração Caterpillar 796 AC");
        ativo.setIntervaloManutencao(IntervaloManutencao.MENSAL);
        ativo.setStatusManutencao(CondicaoManutencao.EM_DIA.EM_DIA);
        ativo.setValorCompra(new BigDecimal(3000000));
        ativo.setDataCadastro(DateTime.now());

        ativoRepository.save(ativo);

        ativo = new Ativo();
        ativo.setStatusAtivo(true);
        ativo.setCodigo("EQP-002");
        ativo.setCategoria(CategoriaAtivo.EQUIPAMENTO);
        ativo.setDescricao("Detonador de explosivo");
        ativo.setIntervaloManutencao(IntervaloManutencao.EVENTO);
        ativo.setStatusManutencao(CondicaoManutencao.EM_DIA);
        ativo.setValorCompra(new BigDecimal(4000));
        ativo.setDataCadastro(new DateTime(2021, 05, 01, 00, 00, 00));

        ativoRepository.save(ativo);
    }

    private void popularAtivoAtrasado() {
        Ativo ativo = new Ativo();
        ativo.setStatusAtivo(true);
        ativo.setCodigo("MAQ-001");
        ativo.setCategoria(CategoriaAtivo.MAQUINA);
        ativo.setDescricao("Dragline Caterpillar 8200");
        ativo.setIntervaloManutencao(IntervaloManutencao.MENSAL);
        ativo.setStatusManutencao(CondicaoManutencao.ATRASADA);
        ativo.setValorCompra(new BigDecimal(3000000));
        ativo.setDataCadastro(new DateTime(2021, 6, 1, 0, 0, 0));

        ativo.addParametros(criarParametro("Caçamba", "Capacidade da Caçamba", "46 - 61 m3"));
        ativo.addParametros(criarParametro("Peso", "Peso Operacional", "3800 - 4100 Tons"));

        ativo = ativoRepository.save(ativo);

        Manutencao manutencao = new Manutencao();
        manutencao.setDataRealizada(new DateTime(2021, 8, 1, 0, 0, 0));
        manutencao.setResponsavel("Leonel Cardoso");
        manutencao.setAtivo(ativo);
        manutencaoRepository.save(manutencao);

        ativo = new Ativo();
        ativo.setStatusAtivo(true);
        ativo.setCodigo("EQP-001");
        ativo.setCategoria(CategoriaAtivo.EQUIPAMENTO);
        ativo.setDescricao("Peneira vibratória fBC MC-1024");
        ativo.setIntervaloManutencao(IntervaloManutencao.EVENTO);
        ativo.setStatusManutencao(CondicaoManutencao.ATRASADA);
        ativo.setValorCompra(new BigDecimal(580000));
        ativo.setDataCadastro(new DateTime(2020, 05, 01, 00, 00, 00));

        ativo.addParametros(criarParametro("Rotação", null, "880 rpm"));
        ativo.addParametros(criarParametro("Lubrificação", null, "graxa"));

        ativoRepository.save(ativo);

        ativo = new Ativo();
        ativo.setStatusAtivo(true);
        ativo.setCodigo("IMV-001");
        ativo.setCategoria(CategoriaAtivo.IMOVEL);
        ativo.setDescricao("Refeitório do campo de extração");
        ativo.setIntervaloManutencao(IntervaloManutencao.ANUAL);
        ativo.setStatusManutencao(CondicaoManutencao.ATRASADA);
        ativo.setValorCompra(new BigDecimal(2700000));
        ativo.setDataCadastro(new DateTime(2019, 10, 01, 00, 00, 00));

        ativo.addParametros(criarParametro("Cidade", null, "Montes Belos"));

        ativoRepository.save(ativo);
    }

    private void popularDisponibilidadeManutencao() {
        // maquina as segundas e quartas
        criarDisponibilidadeManutencao(CategoriaAtivo.MAQUINA, TipoAgendaManutencao.DIA_SEMANA, null ,null, DateTimeConstants.MONDAY);
        criarDisponibilidadeManutencao(CategoriaAtivo.MAQUINA, TipoAgendaManutencao.DIA_SEMANA, null ,null, DateTimeConstants.WEDNESDAY);
        // equipamento de 15 a 31 de todo mes
        criarDisponibilidadeManutencao(CategoriaAtivo.EQUIPAMENTO, TipoAgendaManutencao.INTERVALO_DIAS, 15 ,31, null);
        // imovel todo dia 10
        criarDisponibilidadeManutencao(CategoriaAtivo.IMOVEL, TipoAgendaManutencao.DIA_MES, null ,null, 10);
        // veiculos as quintas
        criarDisponibilidadeManutencao(CategoriaAtivo.VEICULO, TipoAgendaManutencao.DIA_SEMANA, null ,null, DateTimeConstants.THURSDAY);
    }

    private DisponibilidadeManutencao criarDisponibilidadeManutencao(CategoriaAtivo categoria,
                    TipoAgendaManutencao tipoAgenda, Integer diaInicio, Integer diaFim, Integer diaEspecifico) {
        DisponibilidadeManutencao disponibilidadeManutencao = new DisponibilidadeManutencao();
        disponibilidadeManutencao.setCategoria(categoria);
        disponibilidadeManutencao.setTipoAgenda(tipoAgenda);
        disponibilidadeManutencao.setDiaInicio(diaInicio);
        disponibilidadeManutencao.setDiaFim(diaFim);
        disponibilidadeManutencao.setDiaEspecifico(diaEspecifico);
        return disponibilidadeManutencaoRepository.save(disponibilidadeManutencao);
    }

    private ParametroAtivo criarParametro(String nome, String descricao, String valor) {
        ParametroAtivo parametroAtivo = new ParametroAtivo();
        parametroAtivo.setNome(nome);
        parametroAtivo.setDescricao(descricao);
        parametroAtivo.setValor(valor);
        return parametroAtivo;
    }
}


