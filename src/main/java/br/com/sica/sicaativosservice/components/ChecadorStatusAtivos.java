package br.com.sica.sicaativosservice.components;

import br.com.sica.sicaativosservice.enums.CondicaoManutencao;
import br.com.sica.sicaativosservice.models.Ativo;
import br.com.sica.sicaativosservice.repositories.AtivoRepository;
import br.com.sica.sicaativosservice.service.AtivoService;
import org.bouncycastle.cms.jcajce.JcaX509CertSelectorConverter;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
public class ChecadorStatusAtivos {

    private final Logger LOGGER = LoggerFactory.getLogger(ChecadorStatusAtivos.class);

    private final long SEGUNDO = 1000;
    private final long MINUTO = SEGUNDO * 60;
    private final long SEIS_HORAS = MINUTO * 60 * 6;

    @Autowired
    private AtivoRepository ativoRepository;

    @Autowired
    private AtivoService ativoService;

    @Scheduled(fixedDelay = MINUTO)
    public void checarStatusAtivos() {
        LOGGER.info("Iniciando checagem de status de ativos...");
        List<Ativo> ativosEmDia = ativoRepository.findByStatusManutencao(CondicaoManutencao.EM_DIA);
        LOGGER.info("{} ativos selecionados", ativosEmDia.size());
        ativosEmDia.parallelStream().forEach(at -> {
            if (isCondicaoManutencaoAtrasada(at)) {
                LOGGER.info("O ativo {} esta atrasado. Alterando status", at.getId());
                at.setStatusManutencao(CondicaoManutencao.ATRASADA);
                ativoRepository.save(at);
            }
        });
        LOGGER.info("Checagem concluida", ativosEmDia.size());
    }

    private Boolean isCondicaoManutencaoAtrasada(Ativo ativo) {
        if (ativo.getIntervaloManutencao() != null) {
            DateTime ultimaManutencao = ativoService.pesquisarUltimaManutencao(ativo.getId());
            if (ultimaManutencao == null) {
                ultimaManutencao = ativo.getDataCadastro();
            }
            switch (ativo.getIntervaloManutencao()) {
                case DIARIO:
                    if (ultimaManutencao.plusDays(2).isBeforeNow()) {
                        return true;
                    }
                case MENSAL:
                    if (ultimaManutencao.plusMonths(2).isBeforeNow()) {
                        return true;
                    }
                case ANUAL:
                    if (ultimaManutencao.plusYears(2).isBeforeNow()) {
                        return true;
                    }
                case EVENTO:
                case NAO_SE_APLICA:
                    break;
                default:
                    throw new RuntimeException("TipoAgenda invalido");
            }
        }
        return false;
    }
}
