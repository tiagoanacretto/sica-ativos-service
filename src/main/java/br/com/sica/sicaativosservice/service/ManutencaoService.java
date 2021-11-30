package br.com.sica.sicaativosservice.service;

import br.com.sica.sicaativosservice.dtos.FromObject;
import br.com.sica.sicaativosservice.dtos.ParserFromDto;
import br.com.sica.sicaativosservice.dtos.manutencoes.ListagemManutencao;
import br.com.sica.sicaativosservice.dtos.manutencoes.ManutencaoDto;
import br.com.sica.sicaativosservice.enums.CategoriaAtivo;
import br.com.sica.sicaativosservice.models.Ativo;
import br.com.sica.sicaativosservice.models.DisponibilidadeManutencao;
import br.com.sica.sicaativosservice.models.Manutencao;
import br.com.sica.sicaativosservice.repositories.DisponibilidadeManutencaoRepository;
import br.com.sica.sicaativosservice.repositories.ManutencaoRepository;
import br.com.sica.sicaativosservice.utils.FormatUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ManutencaoService {

    private final Logger LOGGER = LoggerFactory.getLogger(ManutencaoService.class);

    @Autowired
    private DisponibilidadeManutencaoRepository disponibilidadeManutencaoRepository;

    @Autowired
    private ManutencaoRepository manutencaoRepository;

    public List<ListagemManutencao> listarTodas() {
        LOGGER.info("Listando todas manutencoes...");
        return ((List<Manutencao>) manutencaoRepository
                .findAll())
                .stream()
                .map(this::convertManutencaoParaListagemDto)
                .collect(Collectors.toList());
    }

    public ManutencaoDto buscarPorId(Long id) {
        LOGGER.info("Buscando manutencao {}", id);
        Manutencao manutencao = manutencaoRepository.findById(id).orElse(null);
        if (manutencao == null) {
            return null;
        }
        return convertManutencaoParaDto(manutencao);
    }

    public ManutencaoDto salvar(ManutencaoDto manutencaoDto) {
        LOGGER.info("Salvando manutencao...");
        ModelMapper modelMapper = new ModelMapper();
        Manutencao manutencao = modelMapper.map(manutencaoDto, Manutencao.class);
        System.out.println(manutencao.toString());
        manutencao = manutencaoRepository.save(manutencao);
        return convertManutencaoParaDto(manutencao);
    }

    public List<String> listarManutencoesDisponiveis(String categoria) {
        LOGGER.info("Listando proximas datas para manutencao do tipo {}", categoria);
        CategoriaAtivo categoriaEnum = CategoriaAtivo.valueOf(categoria);

        List<DisponibilidadeManutencao> disponibilidades = disponibilidadeManutencaoRepository.findByCategoria(categoriaEnum);
        Set<LocalDate> todasDatas = new HashSet<>();
        disponibilidades.forEach(disp -> todasDatas.addAll(calcularDisponibilidadesPorTipoAgenda(disp)));

        List<LocalDate> datasOrdenadas = new ArrayList<>(todasDatas);
        Collections.sort(datasOrdenadas);
        List<String> datasComoString = new ArrayList<>(5);

        for (int i=0; i < 5; i++) {
            datasComoString.add(FormatUtils.localDateToString(datasOrdenadas.get(i)));
        }

        return datasComoString;
    }

    public boolean apagar(Long id) {
        LOGGER.info("Apagando manutencao {}", id);
        Manutencao manutencao = manutencaoRepository.findById(id).orElse(null);
        if (manutencao != null) {
            manutencaoRepository.delete(manutencao);
        } else {
            LOGGER.info("Manutencao {} não encontrada", id);
        }
        return manutencao != null;
    }

    public ManutencaoDto alterar(ManutencaoDto manutencaoDto, Long id) {
        Manutencao manutencao = manutencaoRepository.findById(id).orElse(null);
        if (manutencao != null) {
            LOGGER.info("Alterando manutencao {}", id);
            ModelMapper modelMapper = new ModelMapper();
            Manutencao manutencaoAlterada = modelMapper.map(manutencaoDto, Manutencao.class);

            // Voltar dados que nao deve ser editados
            manutencaoAlterada.setId(manutencao.getId());

            LOGGER.info("Manutencao {} alterada com sucesso", manutencao.getId());
            manutencao = manutencaoRepository.save(manutencaoAlterada);
            return modelMapper.map(manutencao, ManutencaoDto.class);
        } else {
            LOGGER.info("Manutencao {} não encontrada", id);
            return null;
        }
    }

    protected DateTime getDiaCorrente() {
        return new DateTime();
    }

    private LocalDate proximaDataIntervalo(final LocalDate diaCorrente, DisponibilidadeManutencao disponibilidade) {
        if (diaCorrente.getDayOfMonth() <= disponibilidade.getDiaInicio()) {
            return new LocalDate(diaCorrente.getYear(), diaCorrente.getMonthOfYear(), disponibilidade.getDiaInicio());
        } else if (diaCorrente.getDayOfMonth() <= disponibilidade.getDiaFim()) {
            return diaCorrente;
        } else {
            LocalDate dataFutura = new LocalDate(diaCorrente.getYear(), diaCorrente.getMonthOfYear(), disponibilidade.getDiaInicio());
            return dataFutura.plusMonths(1);
        }
    }

    private Set<LocalDate> calcularDisponibilidadesPorTipoAgenda(DisponibilidadeManutencao disponibilidade) {
        Set<LocalDate> diasDisponiveis = new HashSet<>(5);
        Integer diaDesejado = disponibilidade.getDiaEspecifico();
        switch (disponibilidade.getTipoAgenda()) {
            case INTERVALO_DIAS:
                LocalDate proximaDataMes = getDiaCorrente().toLocalDate();

                do {
                    proximaDataMes = proximaDataIntervalo(proximaDataMes, disponibilidade);
                    diasDisponiveis.add(proximaDataMes);
                    proximaDataMes = proximaDataMes.plusDays(1);
                } while (diasDisponiveis.size() < 6);
                return diasDisponiveis;

            case DIA_SEMANA:
                int diaSemana = getDiaCorrente().getDayOfWeek();
                if (diaDesejado < diaSemana) {
                    diaDesejado += 7;
                }
                LocalDate proximoDia = getDiaCorrente().toLocalDate().plusDays(diaDesejado - diaSemana);
                diasDisponiveis.add(proximoDia);

                for (int i=1; i<5; i++) {
                    diasDisponiveis.add(proximoDia.plusWeeks(i));
                }

                return diasDisponiveis;
            case DIA_MES:
                LocalDate primeiroDia;
                int diaCorrente = getDiaCorrente().getDayOfMonth();
                int diaDoMes;

                if (diaDesejado > getDiaCorrente().dayOfMonth().getMaximumValue()) {
                    diaDoMes = getDiaCorrente().dayOfMonth().getMaximumValue();
                } else {
                    diaDoMes = diaDesejado;
                }

                if (diaDoMes >= diaCorrente) {
                    primeiroDia = new LocalDate(getDiaCorrente().getYear(), getDiaCorrente().getMonthOfYear(), diaDoMes);
                } else {
                    primeiroDia = new LocalDate(getDiaCorrente().getYear(), getDiaCorrente().plusMonths(1).getMonthOfYear(), diaDoMes);
                }
                diasDisponiveis.add(primeiroDia);

                for (int i=1; i<5; i++) {
                    LocalDate proximoDiaMes = primeiroDia.plusMonths(i);
                    if (proximoDiaMes.getDayOfMonth() < diaDesejado) {
                        if (diaDesejado > proximoDiaMes.dayOfMonth().getMaximumValue()) {
                            diaDoMes = proximoDiaMes.dayOfMonth().getMaximumValue();
                        } else {
                            diaDoMes = diaDesejado;
                        }
                        proximoDiaMes = new LocalDate(proximoDiaMes.getYear(),proximoDiaMes.getMonthOfYear(), diaDoMes);
                    }

                    diasDisponiveis.add(proximoDiaMes);
                }
                return diasDisponiveis;
            default:
                throw new RuntimeException("TipoAgenda invalido");
        }
    }

    private ManutencaoDto convertManutencaoParaDto(Manutencao manutencao) {
        ModelMapper modelMapper = new ModelMapper();
        ManutencaoDto manutencaoDto = modelMapper
                .map(manutencao, ManutencaoDto.class);
        return manutencaoDto;
    }

    private ListagemManutencao convertManutencaoParaListagemDto(Manutencao manutencao) {
        ModelMapper modelMapper = new ModelMapper();
        ListagemManutencao manutencaoDto = modelMapper
                .map(manutencao, ListagemManutencao.class);
        return manutencaoDto;
    }

}

