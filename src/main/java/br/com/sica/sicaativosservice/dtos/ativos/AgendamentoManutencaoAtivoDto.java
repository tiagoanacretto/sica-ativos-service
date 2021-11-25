package br.com.sica.sicaativosservice.dtos.ativos;

import br.com.sica.sicaativosservice.enums.StatusAgendamento;

public class AgendamentoManutencaoAtivoDto {

    private Long id;

    private String dataAgendada;

    private StatusAgendamento status;

    private String observacao;

    public AgendamentoManutencaoAtivoDto() {
    }

    public AgendamentoManutencaoAtivoDto(Long id, String dataAgendada, StatusAgendamento status, String observacao) {
        this.id = id;
        this.dataAgendada = dataAgendada;
        this.status = status;
        this.observacao = observacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataAgendada() {
        return dataAgendada;
    }

    public void setDataAgendada(String dataAgendada) {
        this.dataAgendada = dataAgendada;
    }

    public StatusAgendamento getStatus() {
        return status;
    }

    public void setStatus(StatusAgendamento status) {
        this.status = status;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

}
