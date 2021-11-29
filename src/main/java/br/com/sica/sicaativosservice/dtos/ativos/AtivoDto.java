package br.com.sica.sicaativosservice.dtos.ativos;

import br.com.sica.sicaativosservice.enums.CategoriaAtivo;
import br.com.sica.sicaativosservice.enums.CondicaoManutencao;
import br.com.sica.sicaativosservice.enums.IntervaloManutencao;

import java.math.BigDecimal;
import java.util.List;

public class AtivoDto {

    private Long id;

    private String codigo;

    private String descricao;

    private CategoriaAtivo categoria;

    private IntervaloManutencao intervaloManutencao;

    private CondicaoManutencao statusManutencao;

    private List<AgendamentoManutencaoAtivoDto> agendamentos;

    private BigDecimal valorCompra;

    private List<ParametroAtivoDto> parametros;

    private Boolean ativo;

    private String dataCadastro;

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

    public List<AgendamentoManutencaoAtivoDto> getAgendamentos() {
        return agendamentos;
    }

    public void setAgendamentos(List<AgendamentoManutencaoAtivoDto> agendamentos) {
        this.agendamentos = agendamentos;
    }

    public BigDecimal getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(BigDecimal valorCompra) {
        this.valorCompra = valorCompra;
    }

    public List<ParametroAtivoDto> getParametros() {
        return parametros;
    }

    public void setParametros(List<ParametroAtivoDto> parametros) {
        this.parametros = parametros;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

}
