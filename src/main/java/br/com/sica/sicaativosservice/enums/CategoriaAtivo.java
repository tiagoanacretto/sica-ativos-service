package br.com.sica.sicaativosservice.enums;

public enum CategoriaAtivo {
    MAQUINA("Máquina"),
    EQUIPAMENTO("Equipamento"),
    INSUMO("Insumo"),
    INVESTIMENTO("Investimento"),
    IMOVEL("Imóvel"),
    VEICULO("Veículo");

    private final String descricao;

    CategoriaAtivo(final String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
