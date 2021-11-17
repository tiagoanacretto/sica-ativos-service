package br.com.sica.ativosservice.dtos.ativos;

public class ParametroAtivoDto {

    private Long id;

    private String nome;

    private String descricao;

    private String valor;

    public ParametroAtivoDto() {
    }

    public ParametroAtivoDto(Long id, String nome, String descricao, String valor) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
