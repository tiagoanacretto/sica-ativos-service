package br.com.sica.sicaativosservice.models;

import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
public class Manutencao implements Serializable {

    private static final long serialVersionUID = 265613040120318L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "manutencao_seq_gen")
    @SequenceGenerator(name = "manutencao_seq_gen", sequenceName = "manutencao_id_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ativo_id", nullable = false)
    private Ativo ativo;

    @Column(name = "data_realizada", nullable = false)
    @NotBlank
    private DateTime dataRealizada;

    @OneToOne
    @JoinColumn(name = "agendamento_id")
    private AgendamentoManutencaoAtivo agendamento;

    private String responsavel;

    private String observacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ativo getAtivo() {
        return ativo;
    }

    public void setAtivo(Ativo ativo) {
        this.ativo = ativo;
    }

    public DateTime getDataRealizada() {
        return dataRealizada;
    }

    public void setDataRealizada(DateTime dataRealizada) {
        this.dataRealizada = dataRealizada;
    }

    public AgendamentoManutencaoAtivo getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(AgendamentoManutencaoAtivo agendamento) {
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

    @Override
    public String toString() {
        return "Manutencao{" +
                "id=" + id +
                ", ativo=" + ativo.getId() +
                ", dataRealizada=" + dataRealizada +
                ", agendamento=" + agendamento +
                ", responsavel='" + responsavel + '\'' +
                ", observacao='" + observacao + '\'' +
                '}';
    }
}
