package br.com.senac.clientes.exceptions;

public class SenacEnderecoNaoRelacionadoClienteException extends Exception{
    public SenacEnderecoNaoRelacionadoClienteException() {
        super("Endereço não pertence ao cliente do pedido!");
    }
}
