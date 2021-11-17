package br.com.sica.sicaativosservice.dtos.ativos;

import br.com.sica.sicaativosservice.enums.StatusManutencao;

public class AgendamentoManutencaoAtivoDto {

    private Long id;

    private String dataAgendada;

    private String dataRealizada;

    private StatusManutencao status;

    private String observacao;

    public AgendamentoManutencaoAtivoDto() {
    }

    public AgendamentoManutencaoAtivoDto(Long id, String dataAgendada, String dataRealizada, StatusManutencao status, String observacao) {
        this.id = id;
        this.dataAgendada = dataAgendada;
        this.dataRealizada = dataRealizada;
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

    public String getDataRealizada() {
        return dataRealizada;
    }

    public void setDataRealizada(String dataRealizada) {
        this.dataRealizada = dataRealizada;
    }

    public StatusManutencao getStatus() {
        return status;
    }

    public void setStatus(StatusManutencao status) {
        this.status = status;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

}
