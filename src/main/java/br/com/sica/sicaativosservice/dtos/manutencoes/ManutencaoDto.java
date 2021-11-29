package br.com.sica.sicaativosservice.dtos.manutencoes;

import br.com.sica.sicaativosservice.dtos.ativos.AgendamentoManutencaoAtivoDto;
import br.com.sica.sicaativosservice.dtos.ativos.AtivoDto;
import org.joda.time.DateTime;


public class ManutencaoDto {

    private Long id;

    private AtivoDto ativo;

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

    public AtivoDto getAtivo() {
        return ativo;
    }

    public void setAtivo(AtivoDto ativo) {
        this.ativo = ativo;
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
