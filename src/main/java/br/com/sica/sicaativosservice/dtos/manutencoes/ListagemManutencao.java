package br.com.sica.sicaativosservice.dtos.manutencoes;

import br.com.sica.sicaativosservice.dtos.ativos.AgendamentoManutencaoAtivoDto;
import org.joda.time.DateTime;

public class ListagemManutencao {

    private Long id;

    private String ativoCodigo;

    private DateTime dataRealizada;

    private AgendamentoManutencaoAtivoDto agendamento;

    private String responsavel;

    private String observacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAtivoCodigo() {
        return ativoCodigo;
    }

    public void setAtivoCodigo(String ativoCodigo) {
        this.ativoCodigo = ativoCodigo;
    }

    public DateTime getDataRealizada() {
        return dataRealizada;
    }

    public void setDataRealizada(DateTime dataRealizada) {
        this.dataRealizada = dataRealizada;
    }

    public AgendamentoManutencaoAtivoDto getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(AgendamentoManutencaoAtivoDto agendamento) {
        this.agendamento = agendamento;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
