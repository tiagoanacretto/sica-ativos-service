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

    public static ParametroAtivo parse(ParametroAtivoDto parametroAtivoDto, boolean inclusao) {
        ParametroAtivo parametroAtivo = new ParametroAtivo();
        if (!inclusao) {
            parametroAtivo.setId(parametroAtivoDto.getId());
        }
        parametroAtivo.setNome(parametroAtivoDto.getNome());
        parametroAtivo.setDescricao(parametroAtivoDto.getDescricao());
        parametroAtivo.setValor(parametroAtivoDto.getValor());
        return parametroAtivo;
    }

    public static AgendamentoManutencaoAtivo parse(AgendamentoManutencaoAtivoDto agendamentoDto, boolean inclusao) {
        AgendamentoManutencaoAtivo agendamento = new AgendamentoManutencaoAtivo();
        if (!inclusao) {
            agendamento.setId(agendamentoDto.getId());
        }
        agendamento.setDataAgendada(FormatUtils.stringToLocalDate(agendamentoDto.getDataAgendada()));
        agendamento.setStatus(agendamentoDto.getStatus());
        agendamento.setObservacao(agendamentoDto.getObservacao());
        return agendamento;
    }

    public static Ativo parse(AtivoDto ativoDto, boolean inclusao) {
        Ativo ativo = new Ativo();
        if (!inclusao) {
            ativo.setId(ativoDto.getId());
        }
        ativo.setCodigo(ativoDto.getCodigo());
        ativo.setDescricao(ativoDto.getDescricao());
        ativo.setCategoria(ativoDto.getCategoria());
        ativo.setStatusManutencao(ativoDto.getStatusManutencao());
        ativo.setIntervaloManutencao(ativoDto.getIntervaloManutencao());
        parseAgendamentos(ativoDto.getAgendamentos(), ativo, inclusao);
        ativo.setValorCompra(ativoDto.getValorCompra());
        parseParametros(ativoDto.getParametros(), ativo, inclusao);
        ativo.setAtivo(ativoDto.getAtivo());
        ativo.setDataCadastro(FormatUtils.stringToDateTime(ativoDto.getDataCadastro()));
        return ativo;
    }

    private static void parseAgendamentos(List<AgendamentoManutencaoAtivoDto> agendamentosDto, Ativo ativo, boolean inclusao) {
        if (agendamentosDto != null) {
            agendamentosDto.parallelStream().forEach(dto -> ativo.addAgendamento(parse(dto, inclusao)));
        }
    }

    private static void parseParametros(List<ParametroAtivoDto> parametrosDto, Ativo ativo, boolean inclusao) {
        if (parametrosDto != null) {
            parametrosDto.parallelStream().forEach(dto -> ativo.addParametros(parse(dto, inclusao)));
        }
    }
}
