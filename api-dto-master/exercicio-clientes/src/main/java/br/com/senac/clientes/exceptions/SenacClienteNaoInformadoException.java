package br.com.senac.clientes.exceptions;

public class SenacClienteNaoInformadoException extends Exception{
    public SenacClienteNaoInformadoException() {
        super("Cliente não informado!");
    }
}
