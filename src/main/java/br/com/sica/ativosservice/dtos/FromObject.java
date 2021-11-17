package br.com.sica.ativosservice.dtos;

import br.com.sica.ativosservice.dtos.ativos.AgendamentoManutencaoAtivoDto;
import br.com.sica.ativosservice.dtos.ativos.AtivoDto;
import br.com.sica.ativosservice.dtos.ativos.ParametroAtivoDto;
import br.com.sica.ativosservice.models.AgendamentoManutencaoAtivo;
import br.com.sica.ativosservice.models.Ativo;
import br.com.sica.ativosservice.models.ParametroAtivo;
import br.com.sica.ativosservice.utils.FormatUtils;

import java.util.List;
import java.util.stream.Collectors;

public class FromObject {

    private FromObject() {}

    public static ParametroAtivoDto from(ParametroAtivo parametroAtivo) {
        ParametroAtivoDto parametroAtivoDto = new ParametroAtivoDto();
        parametroAtivoDto.setId(parametroAtivo.getId());
        parametroAtivoDto.setNome(parametroAtivo.getNome());
        parametroAtivoDto.setDescricao(parametroAtivo.getDescricao());
        parametroAtivoDto.setValor(parametroAtivo.getValor());
        return parametroAtivoDto;
    }

    public static AgendamentoManutencaoAtivoDto from (AgendamentoManutencaoAtivo agendamento) {
        AgendamentoManutencaoAtivoDto agendamentoDto = new AgendamentoManutencaoAtivoDto();
        agendamentoDto.setId(agendamento.getId());
        agendamentoDto.setDataAgendada(FormatUtils.localDateToString(agendamento.getDataAgendada()));
        agendamentoDto.setDataRealizada(FormatUtils.dateTimeToString(agendamento.getDataRealizada()));
        agendamentoDto.setStatus(agendamento.getStatus());
        agendamentoDto.setObservacao(agendamento.getObservacao());
        return agendamentoDto;
    }

    public static AtivoDto from(Ativo ativo) {
        AtivoDto ativoDto = new AtivoDto();
        ativoDto.setId(ativo.getId());
        ativoDto.setCodigo(ativo.getCodigo());
        ativoDto.setDescricao(ativo.getDescricao());
        ativoDto.setCategoria(ativo.getCategoria());
        ativoDto.setIntervaloManutencao(ativo.getIntervaloManutencao());
        ativoDto.setAgendamentos(fromAgendamentos(ativo.getAgendamentos()));
        ativoDto.setValorInicial(ativo.getValorInicial());
        ativoDto.setValorAtual(ativo.getValorAtual());
        ativoDto.setParametros(fromParametros(ativo.getParametros()));
        ativoDto.setAtivo(ativo.getAtivo());
        ativoDto.setDataCadastro(FormatUtils.dateTimeToString(ativo.getDataCadastro()));
        return ativoDto;
    }

    private static List<AgendamentoManutencaoAtivoDto> fromAgendamentos(List<AgendamentoManutencaoAtivo> agendamentos) {
        if (agendamentos != null) {
            return agendamentos.parallelStream().map(FromObject::from).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    private static List<ParametroAtivoDto> fromParametros(List<ParametroAtivo> parametros) {
        if (parametros != null) {
            return parametros.parallelStream().map(FromObject::from).collect(Collectors.toList());
        } else {
            return null;
        }
    }
}
