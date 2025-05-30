package br.com.senac.clientes.controllers.dtos;

import java.time.LocalDate;
import java.util.List;

public class PedidoItensRequest {
    private LocalDate dataCriacao;
    private Double valorTotal;
    private Long clienteId;
    private Long enderecoId;
    private List<ItemPedidoRequest> pedidoItens;

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(Long enderecoId) {
        this.enderecoId = enderecoId;
    }

    public List<ItemPedidoRequest> getPedidoItens() {
        return pedidoItens;
    }

    public void setPedidoItens(List<ItemPedidoRequest> pedidoItens) {
        this.pedidoItens = pedidoItens;
    }
}
