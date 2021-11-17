package br.com.sica.sicaativosservice.models;

import br.com.sica.sicaativosservice.enums.CategoriaAtivo;
import br.com.sica.sicaativosservice.enums.TipoAgendaManutencao;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;

// Mapeia os intervalos de manutencao disponiveis pela engenharia
@Entity
@Table(name = "agenda_manutencao")
public class AgendaManutencao implements Serializable {

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

    // usado com INTERVALO_DATA
    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    // usado com INTERVALO_DATA
    @Column(name = "data_fim")
    private LocalDate dataFim;

    // 1-7 se DIA_SEMANA ou 1-31 se DIA_MES
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

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public Integer getDiaEspecifico() {
        return diaEspecifico;
    }

    public void setDiaEspecifico(Integer diaEspecifico) {
        this.diaEspecifico = diaEspecifico;
    }
}
