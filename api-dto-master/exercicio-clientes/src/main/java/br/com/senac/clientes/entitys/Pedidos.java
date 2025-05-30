package br.com.senac.clientes.entitys;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Pedidos extends EntidadeMaster{

    @Column(nullable = false)
    private LocalDate dataCriacao;

    private Double valorTotal;

    @ManyToOne(optional = false)
    private Clientes cliente;

    @ManyToOne(optional = false)
    private Enderecos endereco;

    @OneToMany(mappedBy = "pedido")
    @JsonManagedReference
    private List<PedidosItens> itens;

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public Enderecos getEndereco() {
        return endereco;
    }

    public void setEndereco(Enderecos endereco) {
        this.endereco = endereco;
    }

    public List<PedidosItens> getItens() {
        return itens;
    }

    public void setItens(List<PedidosItens> itens) {
        this.itens = itens;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
