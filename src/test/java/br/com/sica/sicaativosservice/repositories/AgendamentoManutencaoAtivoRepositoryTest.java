package br.com.sica.sicaativosservice.repositories;

import static org.junit.jupiter.api.Assertions.*;

import br.com.sica.sicaativosservice.AtivosApplication;
import br.com.sica.sicaativosservice.enums.CategoriaAtivo;
import br.com.sica.sicaativosservice.enums.StatusManutencao;
import br.com.sica.sicaativosservice.models.AgendamentoManutencaoAtivo;
import br.com.sica.sicaativosservice.models.Ativo;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = AtivosApplication.class)
public class AgendamentoManutencaoAtivoRepositoryTest {

    @Autowired AtivoRepository ativoRepository;
    @Autowired AgendamentoManutencaoAtivoRepository agendamentoManutencaoAtivoRepository;

    private final DateTime UM_DIA_ATRAS = new DateTime().minusDays(1);
    private final DateTime DOIS_DIAS_ATRAS = new DateTime().minusDays(2);
    private final DateTime UM_DIA_FRENTE = new DateTime().plusDays(1);

    @Test
    public void testBuscarUltimoAgendamento_cenario1() {
        // ativo informado nao existe
        AgendamentoManutencaoAtivo ultimoAgendamento = agendamentoManutencaoAtivoRepository.findTopByAtivoIdAndStatusOrderByDataRealizadaDesc(12345L, StatusManutencao.REALIZADA);

        assertEquals(null, ultimoAgendamento);
    }

//    @Test
    public void testBuscarUltimoAgendamento_cenario2() {
        // ativo inforado possui status diferente
        Ativo ativo = mockAtivo_cenario2();

        AgendamentoManutencaoAtivo ultimoAgendamento = agendamentoManutencaoAtivoRepository.findTopByAtivoIdAndStatusOrderByDataRealizadaDesc(ativo.getId(), StatusManutencao.REALIZADA);

        assertEquals(null, ultimoAgendamento);
    }

//    @Test
    public void testBuscarUltimoAgendamento_cenario3() {
        // ativo com um agendamento feito
        Ativo ativo = mockAtivo_cenario3();

        AgendamentoManutencaoAtivo ultimoAgendamento = agendamentoManutencaoAtivoRepository.findTopByAtivoIdAndStatusOrderByDataRealizadaDesc(ativo.getId(), StatusManutencao.REALIZADA);

        assertEquals(DOIS_DIAS_ATRAS, ultimoAgendamento.getDataRealizada());
    }

//    @Test
    public void testBuscarUltimoAgendamento_cenario4() {
        // ativo com multiplos agendamentos
        Ativo ativo = mockAtivo_cenario4();

        AgendamentoManutencaoAtivo ultimoAgendamento = agendamentoManutencaoAtivoRepository.findTopByAtivoIdAndStatusOrderByDataRealizadaDesc(ativo.getId(), StatusManutencao.REALIZADA);

        assertEquals(UM_DIA_ATRAS, ultimoAgendamento.getDataRealizada());
    }

    private Ativo mockAtivo_cenario2() {
        Ativo ativo = new Ativo();
        ativo.setCodigo("AT-Cenario2");
        ativo.setCategoria(CategoriaAtivo.VEICULO);
        ativo.setDataCadastro(new DateTime());
        ativo.addAgendamento(mockAgendamentoManutencaoAtivo(StatusManutencao.AGENDADA, UM_DIA_FRENTE.toLocalDate(), null));
        return ativoRepository.save(ativo);
    }

    private Ativo mockAtivo_cenario3() {
        Ativo ativo = new Ativo();
        ativo.setCodigo("AT-Cenario3");
        ativo.setCategoria(CategoriaAtivo.EQUIPAMENTO);
        ativo.setDataCadastro(new DateTime());
        ativo.addAgendamento(mockAgendamentoManutencaoAtivo(StatusManutencao.REALIZADA, DOIS_DIAS_ATRAS.toLocalDate(), DOIS_DIAS_ATRAS));
        return ativoRepository.save(ativo);
    }

    private Ativo mockAtivo_cenario4() {
        Ativo ativo = new Ativo();
        ativo.setCodigo("AT-Cenario4");
        ativo.setCategoria(CategoriaAtivo.MAQUINA);
        ativo.setDataCadastro(new DateTime());
        ativo.addAgendamento(mockAgendamentoManutencaoAtivo(StatusManutencao.CANCELADA, UM_DIA_FRENTE.toLocalDate(), DOIS_DIAS_ATRAS));
        ativo.addAgendamento(mockAgendamentoManutencaoAtivo(StatusManutencao.REALIZADA, DOIS_DIAS_ATRAS.toLocalDate(), DOIS_DIAS_ATRAS));
        ativo.addAgendamento(mockAgendamentoManutencaoAtivo(StatusManutencao.REALIZADA, UM_DIA_ATRAS.toLocalDate(), UM_DIA_ATRAS));
        ativo.addAgendamento(mockAgendamentoManutencaoAtivo(StatusManutencao.AGENDADA, UM_DIA_FRENTE.toLocalDate(), null));
        ativo.addAgendamento(mockAgendamentoManutencaoAtivo(StatusManutencao.REALIZADA, DOIS_DIAS_ATRAS.toLocalDate(), DOIS_DIAS_ATRAS));
        return ativoRepository.save(ativo);
    }

    private AgendamentoManutencaoAtivo mockAgendamentoManutencaoAtivo(StatusManutencao status, LocalDate dataAgendada, DateTime dataRealizada) {
        AgendamentoManutencaoAtivo agendamentoManutencaoAtivo = new AgendamentoManutencaoAtivo();
        agendamentoManutencaoAtivo.setStatus(status);
        agendamentoManutencaoAtivo.setDataAgendada(dataAgendada);
        agendamentoManutencaoAtivo.setDataRealizada(dataRealizada);
        return agendamentoManutencaoAtivo;
    }
}
