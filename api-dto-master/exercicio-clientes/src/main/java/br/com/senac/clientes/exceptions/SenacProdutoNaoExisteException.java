package br.com.senac.clientes.exceptions;

public class SenacProdutoNaoExisteException extends Exception{
    public SenacProdutoNaoExisteException() {
        super("Produto não existe na base de dados!");
    }
}
