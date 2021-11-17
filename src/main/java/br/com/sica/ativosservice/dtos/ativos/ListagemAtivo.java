package br.com.sica.ativosservice.dtos.ativos;

import br.com.sica.ativosservice.enums.CondicaoManutencao;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

public class ListagemAtivo {

    private Long id;
    private String codigo;
    private String descricao;
    private String categoria;
    private String ultimaManutencao;
    private CondicaoManutencao condicao;
    private String proximaManutencao;

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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getUltimaManutencao() {
        return ultimaManutencao;
    }

    public void setUltimaManutencao(String ultimaManutencao) {
        this.ultimaManutencao = ultimaManutencao;
    }

    public CondicaoManutencao getCondicao() {
        return condicao;
    }

    public void setCondicao(CondicaoManutencao condicao) {
        this.condicao = condicao;
    }

    public String getProximaManutencao() {
        return proximaManutencao;
    }

    public void setProximaManutencao(String proximaManutencao) {
        this.proximaManutencao = proximaManutencao;
    }
}
