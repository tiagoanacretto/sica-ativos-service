package br.com.sica.sicaativosservice.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "parametro_ativo")
public class ParametroAtivo implements Serializable {

    private static final long serialVersionUID = 4267508873666501780L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prmatv_seq_gen")
    @SequenceGenerator(name = "prmatv_seq_gen", sequenceName = "prmatv_id_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ativo_id", nullable = false)
    private Ativo ativo;

    @Column(length = 30)
    @NotBlank
    @Size(min = 1, max = 30)
    private String nome;

    @Column(length = 80)
    @Size(max = 80)
    private String descricao;

    @Column(length = 80)
    @NotBlank
    @Size(min = 1, max = 80)
    private String valor;

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
