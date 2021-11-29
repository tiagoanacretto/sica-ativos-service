package br.com.sica.sicaativosservice.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import br.com.sica.sicaativosservice.enums.CategoriaAtivo;
import br.com.sica.sicaativosservice.enums.TipoAgendaManutencao;
import br.com.sica.sicaativosservice.models.DisponibilidadeManutencao;
import br.com.sica.sicaativosservice.repositories.DisponibilidadeManutencaoRepository;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ManutencaoServiceTest {

    @Spy
    @InjectMocks
    private ManutencaoService manutencaoService;

    @Mock
    private DisponibilidadeManutencaoRepository disponibilidadeManutencaoRepository;

    @Test
    public void testManutencoesDisponiveisIntervaloDias_cenario1() {
        // Cenario 1
        // os dias disponiveis sao menores que o dia atual
        // esperado: devem ser retornados os dias do proximo mes
        DateTime diaCorrente = new DateTime("2022-01-20T13:00:00.000-03:00");
        doReturn(diaCorrente).when(manutencaoService).getDiaCorrente();

        CategoriaAtivo categoriaAtivo = CategoriaAtivo.VEICULO;
        when(disponibilidadeManutencaoRepository.findByCategoria(categoriaAtivo)).thenReturn(listaDisponibilidadeIntervalo(1, 3));

        List<String> diasDisponibilidade = manutencaoService.listarManutencoesDisponiveis(categoriaAtivo.name());

        assertEquals("01/02/2022", diasDisponibilidade.get(0));
        assertEquals("02/02/2022", diasDisponibilidade.get(1));
        assertEquals("03/02/2022", diasDisponibilidade.get(2));
        assertEquals("01/03/2022", diasDisponibilidade.get(3));
        assertEquals("02/03/2022", diasDisponibilidade.get(4));

        verify(disponibilidadeManutencaoRepository, times(1)).findByCategoria(categoriaAtivo);
    }

    @Test
    public void testManutencoesDisponiveisIntervaloDias_cenario2() {
        // Cenario 2
        // os dias disponiveis sao maiores que o dia atual
        // esperado: devem ser retornados os dias do mesmo mes

        DateTime diaCorrente = new DateTime("2022-01-10T13:00:00.000-03:00");
        doReturn(diaCorrente).when(manutencaoService).getDiaCorrente();

        CategoriaAtivo categoriaAtivo = CategoriaAtivo.VEICULO;
        when(disponibilidadeManutencaoRepository.findByCategoria(categoriaAtivo)).thenReturn(listaDisponibilidadeIntervalo(12, 20));

        List<String> diasDisponibilidade = manutencaoService.listarManutencoesDisponiveis(categoriaAtivo.name());

        assertEquals("12/01/2022", diasDisponibilidade.get(0));
        assertEquals("13/01/2022", diasDisponibilidade.get(1));
        assertEquals("14/01/2022", diasDisponibilidade.get(2));
        assertEquals("15/01/2022", diasDisponibilidade.get(3));
        assertEquals("16/01/2022", diasDisponibilidade.get(4));

        verify(disponibilidadeManutencaoRepository, times(1)).findByCategoria(categoriaAtivo);
    }

    @Test
    public void testManutencoesDisponiveisIntervaloDias_cenario3() {
        // Cenario 3
        // os dias disponiveis sao entre o dia atual
        // esperado: primeiro dia disponivel deve ser o dia corrente

        DateTime diaCorrente = new DateTime("2022-01-10T13:00:00.000-03:00");
        doReturn(diaCorrente).when(manutencaoService).getDiaCorrente();

        CategoriaAtivo categoriaAtivo = CategoriaAtivo.VEICULO;
        when(disponibilidadeManutencaoRepository.findByCategoria(categoriaAtivo)).thenReturn(listaDisponibilidadeIntervalo(8, 11));

        List<String> diasDisponibilidade = manutencaoService.listarManutencoesDisponiveis(categoriaAtivo.name());

        assertEquals("10/01/2022", diasDisponibilidade.get(0));
        assertEquals("11/01/2022", diasDisponibilidade.get(1));
        assertEquals("08/02/2022", diasDisponibilidade.get(2));
        assertEquals("09/02/2022", diasDisponibilidade.get(3));
        assertEquals("10/02/2022", diasDisponibilidade.get(4));

        verify(disponibilidadeManutencaoRepository, times(1)).findByCategoria(categoriaAtivo);
    }

    @Test
    public void testManutencoesDisponiveisDiaSemana_cenario1() {
        // Cenario 1
        // corrente e o mesmo dia da semana
        // esperado: primeiro dia disponivel deve ser o dia corrente

        DateTime diaCorrente = new DateTime("2022-01-10T13:00:00.000-03:00");
        doReturn(diaCorrente).when(manutencaoService).getDiaCorrente();

        CategoriaAtivo categoriaAtivo = CategoriaAtivo.VEICULO;
        when(disponibilidadeManutencaoRepository.findByCategoria(categoriaAtivo)).thenReturn(listaDisponibilidadeDiaSemana(DateTimeConstants.MONDAY));

        List<String> diasDisponibilidade = manutencaoService.listarManutencoesDisponiveis(categoriaAtivo.name());

        assertEquals("10/01/2022", diasDisponibilidade.get(0));
        assertEquals("17/01/2022", diasDisponibilidade.get(1));
        assertEquals("24/01/2022", diasDisponibilidade.get(2));
        assertEquals("31/01/2022", diasDisponibilidade.get(3));
        assertEquals("07/02/2022", diasDisponibilidade.get(4));

        verify(disponibilidadeManutencaoRepository, times(1)).findByCategoria(categoriaAtivo);
    }

    @Test
    public void testManutencoesDisponiveisDiaSemana_cenario2() {
        // Cenario 2
        // corrente e anterior ao dia da semana
        // esperado: primeiro dia disponivel deve ser na mesma semana

        DateTime diaCorrente = new DateTime("2022-01-10T13:00:00.000-03:00");
        doReturn(diaCorrente).when(manutencaoService).getDiaCorrente();

        CategoriaAtivo categoriaAtivo = CategoriaAtivo.VEICULO;
        when(disponibilidadeManutencaoRepository.findByCategoria(categoriaAtivo)).thenReturn(listaDisponibilidadeDiaSemana(DateTimeConstants.WEDNESDAY));

        List<String> diasDisponibilidade = manutencaoService.listarManutencoesDisponiveis(categoriaAtivo.name());

        assertEquals("12/01/2022", diasDisponibilidade.get(0));
        assertEquals("19/01/2022", diasDisponibilidade.get(1));
        assertEquals("26/01/2022", diasDisponibilidade.get(2));
        assertEquals("02/02/2022", diasDisponibilidade.get(3));
        assertEquals("09/02/2022", diasDisponibilidade.get(4));

        verify(disponibilidadeManutencaoRepository, times(1)).findByCategoria(categoriaAtivo);
    }

    @Test
    public void testManutencoesDisponiveisDiaSemana_cenario3() {
        // Cenario 3
        // corrente e posterior ao dia da semana
        // esperado: primeiro dia disponivel deve ser na proxima semana
        DateTime diaCorrente = new DateTime("2022-01-05T13:00:00.000-03:00");
        doReturn(diaCorrente).when(manutencaoService).getDiaCorrente();

        CategoriaAtivo categoriaAtivo = CategoriaAtivo.VEICULO;
        when(disponibilidadeManutencaoRepository.findByCategoria(categoriaAtivo)).thenReturn(listaDisponibilidadeDiaSemana(DateTimeConstants.MONDAY));

        List<String> diasDisponibilidade = manutencaoService.listarManutencoesDisponiveis(categoriaAtivo.name());

        assertEquals("10/01/2022", diasDisponibilidade.get(0));
        assertEquals("17/01/2022", diasDisponibilidade.get(1));
        assertEquals("24/01/2022", diasDisponibilidade.get(2));
        assertEquals("31/01/2022", diasDisponibilidade.get(3));
        assertEquals("07/02/2022", diasDisponibilidade.get(4));

        verify(disponibilidadeManutencaoRepository, times(1)).findByCategoria(categoriaAtivo);
    }

    @Test
    public void testManutencoesDisponiveisDiaMes_cenario1() {
        // Cenario 1
        // corrente e o mesmo dia do mes
        // esperado: primeiro dia disponivel deve ser o dia corrente

        DateTime diaCorrente = new DateTime("2021-11-05T13:00:00.000-03:00");
        doReturn(diaCorrente).when(manutencaoService).getDiaCorrente();

        CategoriaAtivo categoriaAtivo = CategoriaAtivo.VEICULO;
        when(disponibilidadeManutencaoRepository.findByCategoria(categoriaAtivo)).thenReturn(listaDisponibilidadeDiaMes(5));

        List<String> diasDisponibilidade = manutencaoService.listarManutencoesDisponiveis(categoriaAtivo.name());

        assertEquals("05/11/2021", diasDisponibilidade.get(0));
        assertEquals("05/12/2021", diasDisponibilidade.get(1));
        assertEquals("05/01/2022", diasDisponibilidade.get(2));
        assertEquals("05/02/2022", diasDisponibilidade.get(3));
        assertEquals("05/03/2022", diasDisponibilidade.get(4));

        verify(disponibilidadeManutencaoRepository, times(1)).findByCategoria(categoriaAtivo);
    }

    @Test
    public void testManutencoesDisponiveisDiaMes_cenario2() {
        // Cenario 2
        // corrente e anterior ao dia da mes
        // esperado: primeiro dia disponivel deve ser no mesmo mes
        DateTime diaCorrente = new DateTime("2022-01-05T13:00:00.000-03:00");
        doReturn(diaCorrente).when(manutencaoService).getDiaCorrente();

        CategoriaAtivo categoriaAtivo = CategoriaAtivo.VEICULO;
        when(disponibilidadeManutencaoRepository.findByCategoria(categoriaAtivo)).thenReturn(listaDisponibilidadeDiaMes(31));

        List<String> diasDisponibilidade = manutencaoService.listarManutencoesDisponiveis(categoriaAtivo.name());

        assertEquals("31/01/2022", diasDisponibilidade.get(0));
        assertEquals("28/02/2022", diasDisponibilidade.get(1));
        assertEquals("31/03/2022", diasDisponibilidade.get(2));
        assertEquals("30/04/2022", diasDisponibilidade.get(3));
        assertEquals("31/05/2022", diasDisponibilidade.get(4));

        verify(disponibilidadeManutencaoRepository, times(1)).findByCategoria(categoriaAtivo);
    }

    @Test
    public void testManutencoesDisponiveisDiaMes_cenario3() {
        // Cenario 3
        // corrente e posterior ao dia do mes
        // esperado: primeiro dia disponivel deve ser no proxima mes

        DateTime diaCorrente = new DateTime("2022-01-05T13:00:00.000-03:00");
        doReturn(diaCorrente).when(manutencaoService).getDiaCorrente();

        CategoriaAtivo categoriaAtivo = CategoriaAtivo.VEICULO;
        when(disponibilidadeManutencaoRepository.findByCategoria(categoriaAtivo)).thenReturn(listaDisponibilidadeDiaMes(4));

        List<String> diasDisponibilidade = manutencaoService.listarManutencoesDisponiveis(categoriaAtivo.name());

        assertEquals("04/02/2022", diasDisponibilidade.get(0));
        assertEquals("04/03/2022", diasDisponibilidade.get(1));
        assertEquals("04/04/2022", diasDisponibilidade.get(2));
        assertEquals("04/05/2022", diasDisponibilidade.get(3));
        assertEquals("04/06/2022", diasDisponibilidade.get(4));

        verify(disponibilidadeManutencaoRepository, times(1)).findByCategoria(categoriaAtivo);
    }

    @Test
    public void testManutencoesDisponiveisDiaMes_cenario4() {
        // Cenario 4
        // proximo dia 31 quando esta em fevereiro
        // esperado: primeiro dia disponivel deve ser dia 28

        DateTime diaCorrente = new DateTime("2022-02-28T13:00:00.000-03:00");
        doReturn(diaCorrente).when(manutencaoService).getDiaCorrente();

        CategoriaAtivo categoriaAtivo = CategoriaAtivo.VEICULO;
        when(disponibilidadeManutencaoRepository.findByCategoria(categoriaAtivo)).thenReturn(listaDisponibilidadeDiaMes(31));

        List<String> diasDisponibilidade = manutencaoService.listarManutencoesDisponiveis(categoriaAtivo.name());

        assertEquals("28/02/2022", diasDisponibilidade.get(0));
        assertEquals("31/03/2022", diasDisponibilidade.get(1));
        assertEquals("30/04/2022", diasDisponibilidade.get(2));
        assertEquals("31/05/2022", diasDisponibilidade.get(3));
        assertEquals("30/06/2022", diasDisponibilidade.get(4));

        verify(disponibilidadeManutencaoRepository, times(1)).findByCategoria(categoriaAtivo);
    }

    @Test
    public void testManutencoesDisponiveisDiaMes_cenario41() {
        // Cenario 4.1
        // proximo dia 31 quando esta em janeiro
        // esperado: primeiro dia disponivel deve ser dia 28

        DateTime diaCorrente = new DateTime("2022-01-30T13:00:00.000-03:00");
        doReturn(diaCorrente).when(manutencaoService).getDiaCorrente();

        CategoriaAtivo categoriaAtivo = CategoriaAtivo.VEICULO;
        when(disponibilidadeManutencaoRepository.findByCategoria(categoriaAtivo)).thenReturn(listaDisponibilidadeDiaMes(31));

        List<String> diasDisponibilidade = manutencaoService.listarManutencoesDisponiveis(categoriaAtivo.name());

        assertEquals("31/01/2022", diasDisponibilidade.get(0));
        assertEquals("28/02/2022", diasDisponibilidade.get(1));
        assertEquals("31/03/2022", diasDisponibilidade.get(2));
        assertEquals("30/04/2022", diasDisponibilidade.get(3));
        assertEquals("31/05/2022", diasDisponibilidade.get(4));


        verify(disponibilidadeManutencaoRepository, times(1)).findByCategoria(categoriaAtivo);
    }

    private List<DisponibilidadeManutencao> listaDisponibilidadeIntervalo(final Integer inicio, final Integer fim) {
        List<DisponibilidadeManutencao> disponibilidadeManutencoes = new ArrayList<>();
        DisponibilidadeManutencao disponibilidade = new DisponibilidadeManutencao();
        disponibilidade.setTipoAgenda(TipoAgendaManutencao.INTERVALO_DIAS);
        disponibilidade.setDiaInicio(inicio);
        disponibilidade.setDiaFim(fim);
        disponibilidadeManutencoes.add(disponibilidade);
        return disponibilidadeManutencoes;
    }

    private List<DisponibilidadeManutencao> listaDisponibilidadeDiaSemana(final Integer diaSemana) {
        List<DisponibilidadeManutencao> disponibilidadeManutencoes = new ArrayList<>();
        DisponibilidadeManutencao disponibilidade = new DisponibilidadeManutencao();
        disponibilidade.setTipoAgenda(TipoAgendaManutencao.DIA_SEMANA);
        disponibilidade.setDiaEspecifico(diaSemana);
        disponibilidadeManutencoes.add(disponibilidade);
        return disponibilidadeManutencoes;
    }

    private List<DisponibilidadeManutencao> listaDisponibilidadeDiaMes(final Integer diaMes) {
        List<DisponibilidadeManutencao> disponibilidadeManutencoes = new ArrayList<>();
        DisponibilidadeManutencao disponibilidade = new DisponibilidadeManutencao();
        disponibilidade.setTipoAgenda(TipoAgendaManutencao.DIA_MES);
        disponibilidade.setDiaEspecifico(diaMes);
        disponibilidadeManutencoes.add(disponibilidade);
        return disponibilidadeManutencoes;
    }
}
