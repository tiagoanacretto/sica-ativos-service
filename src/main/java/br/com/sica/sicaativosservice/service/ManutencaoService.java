package br.com.sica.sicaativosservice.service;

import br.com.sica.sicaativosservice.dtos.manutencoes.ManutencaoDto;
import br.com.sica.sicaativosservice.enums.CategoriaAtivo;
import br.com.sica.sicaativosservice.enums.TipoAgendaManutencao;
import br.com.sica.sicaativosservice.models.DisponibilidadeManutencao;
import br.com.sica.sicaativosservice.repositories.DisponibilidadeManutencaoRepository;
import br.com.sica.sicaativosservice.utils.FormatUtils;
import org.apache.tomcat.jni.Local;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ManutencaoService {

    @Autowired
    private DisponibilidadeManutencaoRepository disponibilidadeManutencaoRepository;

    public List<ManutencaoDto> listarTodas() {
        return null;
    }

    public ManutencaoDto buscarPorId(Long id) {
        return null;
    }

    public ManutencaoDto salvar(ManutencaoDto manutencaoDto) {
        return  null;
    }

    public List<String> listarManutencoesDisponiveis(String categoria) {
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

}

