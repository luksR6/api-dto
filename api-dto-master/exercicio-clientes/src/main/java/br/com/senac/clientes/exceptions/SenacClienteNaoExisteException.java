package br.com.senac.clientes.exceptions;

public class SenacClienteNaoExisteException extends Exception{
    public SenacClienteNaoExisteException() {
        super("Cliente não encontrado na base de dados!");
    }
}
