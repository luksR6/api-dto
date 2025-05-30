package br.com.senac.clientes.exceptions;

public class SenacEnderecoNaoExisteException extends Exception {
    public SenacEnderecoNaoExisteException() {
        super("Endereco não encontrado!");
    }
}
