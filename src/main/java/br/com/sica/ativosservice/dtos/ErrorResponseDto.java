package br.com.sica.ativosservice.dtos;

public class ErrorResponseDto {

    final String codigo;
    final String mensagem;

    public ErrorResponseDto(String codigo, String mensagem) {
        this.codigo = codigo;
        this.mensagem = mensagem;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getMensagem() {
        return mensagem;
    }
}
