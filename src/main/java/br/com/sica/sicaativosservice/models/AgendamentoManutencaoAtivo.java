package br.com.sica.sicaativosservice.models;

import br.com.sica.sicaativosservice.enums.StatusManutencao;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "agendamento_manutencao_ativo")
public class AgendamentoManutencaoAtivo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agdmntatv_seq_gen")
    @SequenceGenerator(name = "agdmntatv_seq_gen", sequenceName = "agdmntatv_id_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ativo_id", nullable = false)
    private Ativo ativo;

    @Column(name = "data_agendada", nullable = false)
    @NotBlank
    private LocalDate dataAgendada;

    @Column(name = "data_realizada")
    private DateTime dataRealizada;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusManutencao status;

    @Column(length = 250)
    @Size(max = 250)
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

    public LocalDate getDataAgendada() {
        return dataAgendada;
    }

    public void setDataAgendada(LocalDate dataAgendada) {
        this.dataAgendada = dataAgendada;
    }

    public DateTime getDataRealizada() {
        return dataRealizada;
    }

    public void setDataRealizada(DateTime dataRealizada) {
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
