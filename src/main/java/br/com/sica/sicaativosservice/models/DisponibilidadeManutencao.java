package br.com.sica.sicaativosservice.models;

import br.com.sica.sicaativosservice.enums.CategoriaAtivo;
import br.com.sica.sicaativosservice.enums.TipoAgendaManutencao;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;

// Mapeia os intervalos de manutencao disponiveis pela engenharia
@Entity
@Table(name = "disponibilidade_manutencao")
public class DisponibilidadeManutencao implements Serializable {

    private static final long serialVersionUID = 418352681330877268L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agdmnt_seq_gen")
    @SequenceGenerator(name = "agdmnt_seq_gen", sequenceName = "agdmnt_id_seq")
    private Long id;

    @Column(name = "tipo_agenda")
    @Enumerated(EnumType.STRING)
    private TipoAgendaManutencao tipoAgenda;

    @Column(name = "categoria")
    @Enumerated(EnumType.STRING)
    private CategoriaAtivo categoria;

    // usado com INTERVALO_DIAS
    @Column(name = "dia_inicio")
    private Integer diaInicio;

    // usado com INTERVALO_DIAS
    @Column(name = "dia_fim")
    private Integer diaFim;

    // 1-7 se DIA_SEMANA, onde 1 e domingo, ou 1-31 se DIA_MES
    @Column(name = "dia_especifico")
    private Integer diaEspecifico;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoAgendaManutencao getTipoAgenda() {
        return tipoAgenda;
    }

    public void setTipoAgenda(TipoAgendaManutencao tipoAgenda) {
        this.tipoAgenda = tipoAgenda;
    }

    public CategoriaAtivo getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaAtivo categoria) {
        this.categoria = categoria;
    }

    public Integer getDiaInicio() {
        return diaInicio;
    }

    public void setDiaInicio(Integer diaInicio) {
        this.diaInicio = diaInicio;
    }

    public Integer getDiaFim() {
        return diaFim;
    }

    public void setDiaFim(Integer diaFim) {
        this.diaFim = diaFim;
    }

    public Integer getDiaEspecifico() {
        return diaEspecifico;
    }

    public void setDiaEspecifico(Integer diaEspecifico) {
        this.diaEspecifico = diaEspecifico;
    }
}
