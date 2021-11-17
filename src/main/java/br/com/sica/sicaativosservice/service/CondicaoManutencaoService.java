package br.com.sica.sicaativosservice.service;

import br.com.sica.sicaativosservice.enums.CondicaoManutencao;
import br.com.sica.sicaativosservice.models.Ativo;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CondicaoManutencaoService {

    private final Logger LOGGER = LoggerFactory.getLogger(CondicaoManutencaoService.class);

    public CondicaoManutencao calcularCondicaoManutencao(final Ativo ativo, final DateTime ultimaManutencao) {
        if (ativo.getIntervaloManutencao() != null) {
            DateTime dataManutencao;
            if (ultimaManutencao == null) {
                dataManutencao = ativo.getDataCadastro();
            } else {
                dataManutencao = ultimaManutencao;
            }
            LocalDate ultimoDataValida;
            switch (ativo.getIntervaloManutencao()) {
                case DIARIO:
                    ultimoDataValida = new LocalDate().minusDays(1);
                    LOGGER.info("Comparando condicao de manutencao Diaria: [Ultima manutencao: {}] [Data limite: {}]", dataManutencao.toLocalDate(), ultimoDataValida);
                    if (dataManutencao.toLocalDate().isBefore(ultimoDataValida)) {
                        LOGGER.info("A manutencao do ativo {} esta ATRASADA", ativo.getId());
                        return CondicaoManutencao.ATRASADA;
                    }
                    break;
                case MENSAL:
                    ultimoDataValida = new LocalDate().minusMonths(1);
                    LOGGER.info("Comparando condicao de manutencao Mensal: [Ultima manutencao: {}] [Data limite: {}]", dataManutencao.toLocalDate(), ultimoDataValida);
                    if (dataManutencao.toLocalDate().isBefore(ultimoDataValida)) {
                        LOGGER.info("A manutencao do ativo {} esta ATRASADA", ativo.getId());
                        return CondicaoManutencao.ATRASADA;
                    }
                    break;

                case ANUAL:
                    ultimoDataValida = new LocalDate().minusYears(1);
                    LOGGER.info("Comparando condicao de manutencao Anual: [Ultima manutencao: {}] [Data limite: {}]", dataManutencao.toLocalDate(), ultimoDataValida);
                    if (dataManutencao.toLocalDate().isBefore(ultimoDataValida)) {
                        LOGGER.info("A manutencao do ativo {} esta ATRASADA", ativo.getId());
                        return CondicaoManutencao.ATRASADA;
                    }
                    break;
            }
        }
        LOGGER.info("A manutencao do ativo {} esta EM DIA", ativo.getId());
        return CondicaoManutencao.EM_DIA;
    }
}
