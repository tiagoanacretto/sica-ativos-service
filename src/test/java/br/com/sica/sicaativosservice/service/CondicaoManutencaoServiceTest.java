package br.com.sica.sicaativosservice.service;

import br.com.sica.sicaativosservice.enums.CondicaoManutencao;
import br.com.sica.sicaativosservice.enums.IntervaloManutencao;
import br.com.sica.sicaativosservice.models.Ativo;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CondicaoManutencaoServiceTest {

    private final DateTime UM_DIA_ATRAS = new DateTime().minusDays(1);
    private final DateTime DOIS_DIAS_ATRAS = new DateTime().minusDays(2);
    private final DateTime TRES_DIAS_ATRAS = new DateTime().minusDays(3);
    private final DateTime UM_MES_ATRAS = new DateTime().minusMonths(1);
    private final DateTime TRINTA_DOIS_DIAS_ATRAS = new DateTime().minusDays(32);
    private final DateTime TREZENDO_SETENTA_DIAS_ATRAS = new DateTime().minusDays(370);

    @InjectMocks
    CondicaoManutencaoService condicaoManutencaoService;

    @Test
    public void testCalcularCondicaoManutencao_cenario1() {
        // Ativo sem intervalo de manutencao
        Ativo ativo = new Ativo();

        CondicaoManutencao condicao = condicaoManutencaoService.calcularCondicaoManutencao(ativo, null);

        assertEquals(CondicaoManutencao.EM_DIA, condicao);
    }

    @Test
    public void testCalcularCondicaoManutencao_cenario2() {
        // Ativo com intervalo Diario, sem manutencao anterior
        // Deve considerar a data de cadastro
        Ativo ativo = new Ativo();
        ativo.setIntervaloManutencao(IntervaloManutencao.DIARIO);
        ativo.setDataCadastro(TRES_DIAS_ATRAS);

        CondicaoManutencao condicao = condicaoManutencaoService.calcularCondicaoManutencao(ativo, null);

        assertEquals(CondicaoManutencao.ATRASADA, condicao);
    }

    @Test
    public void testCalcularCondicaoManutencao_cenario3() {
        // Intervalo Diario - Em dia
        Ativo ativo = new Ativo();
        ativo.setIntervaloManutencao(IntervaloManutencao.DIARIO);

        CondicaoManutencao condicao = condicaoManutencaoService.calcularCondicaoManutencao(ativo, UM_DIA_ATRAS);

        assertEquals(CondicaoManutencao.EM_DIA, condicao);
    }

    @Test
    public void testCalcularCondicaoManutencao_cenario4() {
        // Intervalo Diario - Atrasada
        Ativo ativo = new Ativo();
        ativo.setIntervaloManutencao(IntervaloManutencao.DIARIO);

        CondicaoManutencao condicao = condicaoManutencaoService.calcularCondicaoManutencao(ativo, DOIS_DIAS_ATRAS);

        assertEquals(CondicaoManutencao.ATRASADA, condicao);
    }

    @Test
    public void testCalcularCondicaoManutencao_cenario5() {
        // Intervalo Mensal - Em dia
        Ativo ativo = new Ativo();
        ativo.setIntervaloManutencao(IntervaloManutencao.MENSAL);

        CondicaoManutencao condicao = condicaoManutencaoService.calcularCondicaoManutencao(ativo, UM_MES_ATRAS);

        assertEquals(CondicaoManutencao.EM_DIA, condicao);
    }

    @Test
    public void testCalcularCondicaoManutencao_cenario6() {
        // Intervalo Mensal - Atrasada
        Ativo ativo = new Ativo();
        ativo.setIntervaloManutencao(IntervaloManutencao.MENSAL);

        CondicaoManutencao condicao = condicaoManutencaoService.calcularCondicaoManutencao(ativo, TRINTA_DOIS_DIAS_ATRAS);

        assertEquals(CondicaoManutencao.ATRASADA, condicao);
    }

    @Test
    public void testCalcularCondicaoManutencao_cenario7() {
        // Intervalo Anual - Em dia
        Ativo ativo = new Ativo();
        ativo.setIntervaloManutencao(IntervaloManutencao.ANUAL);

        CondicaoManutencao condicao = condicaoManutencaoService.calcularCondicaoManutencao(ativo, TRINTA_DOIS_DIAS_ATRAS);

        assertEquals(CondicaoManutencao.EM_DIA, condicao);
    }

    @Test
    public void testCalcularCondicaoManutencao_cenario8() {
        // Intervalo Anual - Atrasada
        Ativo ativo = new Ativo();
        ativo.setIntervaloManutencao(IntervaloManutencao.ANUAL);

        CondicaoManutencao condicao = condicaoManutencaoService.calcularCondicaoManutencao(ativo, TREZENDO_SETENTA_DIAS_ATRAS);

        assertEquals(CondicaoManutencao.ATRASADA, condicao);
    }
}
