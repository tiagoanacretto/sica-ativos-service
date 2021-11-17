package br.com.sica.sicaativosservice.dtos;

import br.com.sica.sicaativosservice.dtos.ativos.AgendamentoManutencaoAtivoDto;
import br.com.sica.sicaativosservice.dtos.ativos.AtivoDto;
import br.com.sica.sicaativosservice.dtos.ativos.ParametroAtivoDto;
import br.com.sica.sicaativosservice.models.AgendamentoManutencaoAtivo;
import br.com.sica.sicaativosservice.models.Ativo;
import br.com.sica.sicaativosservice.models.ParametroAtivo;
import br.com.sica.sicaativosservice.utils.FormatUtils;

import java.util.List;

public class ParserFromDto {

    private ParserFromDto() {}

    public static ParametroAtivo parse(ParametroAtivoDto parametroAtivoDto) {
        ParametroAtivo parametroAtivo = new ParametroAtivo();
        parametroAtivo.setId(parametroAtivoDto.getId());
        parametroAtivo.setNome(parametroAtivoDto.getNome());
        parametroAtivo.setDescricao(parametroAtivoDto.getDescricao());
        parametroAtivo.setValor(parametroAtivoDto.getValor());
        return parametroAtivo;
    }

    public static AgendamentoManutencaoAtivo parse(AgendamentoManutencaoAtivoDto agendamentoDto) {
        AgendamentoManutencaoAtivo agendamento = new AgendamentoManutencaoAtivo();
        agendamento.setId(agendamentoDto.getId());
        agendamento.setDataAgendada(FormatUtils.stringToLocalDate(agendamentoDto.getDataAgendada()));
        agendamento.setDataRealizada(FormatUtils.stringToDateTime(agendamentoDto.getDataRealizada()));
        agendamento.setStatus(agendamentoDto.getStatus());
        agendamento.setObservacao(agendamentoDto.getObservacao());
        return agendamento;
    }

    public static Ativo parse(AtivoDto ativoDto) {
        Ativo ativo = new Ativo();
        ativo.setId(ativoDto.getId());
        ativo.setCodigo(ativoDto.getCodigo());
        ativo.setDescricao(ativoDto.getDescricao());
        ativo.setCategoria(ativoDto.getCategoria());
        ativo.setIntervaloManutencao(ativoDto.getIntervaloManutencao());
        parseAgendamentos(ativoDto.getAgendamentos(), ativo);
        ativo.setValorInicial(ativoDto.getValorInicial());
        ativo.setValorAtual(ativoDto.getValorAtual());
        parseParametros(ativoDto.getParametros(), ativo);
        ativo.setAtivo(ativoDto.getAtivo());
        ativo.setDataCadastro(FormatUtils.stringToDateTime(ativoDto.getDataCadastro()));
        return ativo;
    }

    private static void parseAgendamentos(List<AgendamentoManutencaoAtivoDto> agendamentosDto, Ativo ativo) {
        if (agendamentosDto != null) {
            agendamentosDto.parallelStream().forEach(dto -> ativo.addAgendamento(parse(dto)));
        }
    }

    private static void parseParametros(List<ParametroAtivoDto> parametrosDto, Ativo ativo) {
        if (parametrosDto != null) {
            parametrosDto.parallelStream().forEach(dto -> ativo.addParametros(parse(dto)));
        }
    }
}
