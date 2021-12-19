package br.com.sica.sicaativosservice.models;

import br.com.sica.sicaativosservice.enums.CategoriaAtivo;
import br.com.sica.sicaativosservice.enums.CondicaoManutencao;
import br.com.sica.sicaativosservice.enums.IntervaloManutencao;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

@Entity
public class Ativo implements Serializable {

    private static final long serialVersionUID = -5408173195907102288L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ativo_seq_gen")
    @SequenceGenerator(name = "ativo_seq_gen", sequenceName = "ativo_id_seq")
    private Long id;

    @Column(length = 20)
    @NotBlank
    @Size(min = 1, max = 20)
    private String codigo;

    @Column(length = 250)
    @Size(max = 250)
    private String descricao;

    @Column(name = "categoria", nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoriaAtivo categoria;

    @Column(name = "intervalo_manutencao", nullable = false)
    @NotBlank
    @Enumerated(EnumType.STRING)
    private IntervaloManutencao intervaloManutencao;

    @Column(name = "status_manutencao", nullable = false)
    @NotBlank
    @Enumerated(EnumType.STRING)
    private CondicaoManutencao statusManutencao;

    @OneToMany(mappedBy = "ativo", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AgendamentoManutencaoAtivo> agendamentos;

    @Column(name = "valor_compra")
    @Digits(integer=9, fraction=2)
    private BigDecimal valorCompra;

    // para carro: marca, chevrolet / modelo, s10
    // para investimento: banco, itau / tipo, CDB
    @OneToMany(mappedBy = "ativo", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ParametroAtivo> parametros;

    private Boolean statusAtivo = true;

    @Column(name = "data_cadastro", nullable = false)
    @NotBlank
    private DateTime dataCadastro;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public CategoriaAtivo getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaAtivo categoria) {
        this.categoria = categoria;
    }

    public IntervaloManutencao getIntervaloManutencao() {
        return intervaloManutencao;
    }

    public void setIntervaloManutencao(IntervaloManutencao intervaloManutencao) {
        this.intervaloManutencao = intervaloManutencao;
    }

    public CondicaoManutencao getStatusManutencao() {
        return statusManutencao;
    }

    public void setStatusManutencao(CondicaoManutencao statusManutencao) {
        this.statusManutencao = statusManutencao;
    }

    public List<AgendamentoManutencaoAtivo> getAgendamentos() {
        if (agendamentos == null) {
            agendamentos = new ArrayList<>();
        }
        return agendamentos;
    }

    public void setAgendamentos(List<AgendamentoManutencaoAtivo> agendamentos) {
        this.agendamentos = agendamentos;
    }

    public void addAgendamento(AgendamentoManutencaoAtivo agendamento) {
        getAgendamentos().add(agendamento);
        agendamento.setAtivo(this);
    }

    public BigDecimal getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(BigDecimal valorCompra) {
        this.valorCompra = valorCompra;
    }

    public List<ParametroAtivo> getParametros() {
        if (parametros == null) {
            parametros = new ArrayList<>();
        }
        return parametros;
    }

    public void setParametros(List<ParametroAtivo> parametros) {
        this.parametros = parametros;
    }

    public void addParametros(ParametroAtivo parametro) {
        getParametros().add(parametro);
        parametro.setAtivo(this);
    }

    public Boolean getStatusAtivo() {
        return statusAtivo;
    }

    public void setStatusAtivo(Boolean statusAtivo) {
        this.statusAtivo = statusAtivo;
    }

    public DateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(DateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
