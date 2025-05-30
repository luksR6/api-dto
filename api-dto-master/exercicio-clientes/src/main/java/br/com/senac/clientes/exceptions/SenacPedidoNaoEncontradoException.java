package br.com.senac.clientes.exceptions;

public class SenacPedidoNaoEncontradoException extends Exception{
    public SenacPedidoNaoEncontradoException() {
        super("Pedido não encontrado!");
    }
}
