package br.com.senac.clientes.services;

import br.com.senac.clientes.controllers.dtos.PedidoItensRequest;
import br.com.senac.clientes.entitys.Pedidos;
import br.com.senac.clientes.entitys.PedidosItens;
import br.com.senac.clientes.entitys.Produtos;
import br.com.senac.clientes.exceptions.SenacException;
import br.com.senac.clientes.exceptions.SenacNovaException;
import br.com.senac.clientes.exceptions.SenacPedidoNaoEncontradoException;
import br.com.senac.clientes.exceptions.SenacProdutoNaoExisteException;
import br.com.senac.clientes.repositorys.PedidosItensRepository;
import br.com.senac.clientes.repositorys.PedidosRespository;
import br.com.senac.clientes.repositorys.ProdutosRepository;
import br.com.senac.clientes.utils.ValidacoesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static br.com.senac.clientes.utils.ValidacoesUtils.validarSeRegistroExiste;

@Service
public class PedidosItensService {

    @Autowired
    private PedidosItensRepository pedidosItensRepository;

    @Autowired
    private ProdutosRepository produtosRepository;

    @Autowired
    private PedidosRespository pedidosRespository;

    public PedidosItens criar(PedidosItens pedidoItem) throws SenacException, SenacProdutoNaoExisteException, SenacPedidoNaoEncontradoException {
        if(!produtosRepository.existsById(pedidoItem.getProduto().getId())){
            throw new SenacProdutoNaoExisteException();
        }

        if(!pedidosRespository.existsById(pedidoItem.getPedido().getId())) {
            throw new SenacPedidoNaoEncontradoException();
        }

        // valida se tem outro item com o mesmo produto
        Optional<PedidosItens> resultBuscaPedidoItem = pedidosItensRepository.findByPedidoIdAndProdutoId(
                pedidoItem.getPedido().getId(),
                pedidoItem.getProduto().getId());
        if(resultBuscaPedidoItem.isPresent()) {
            throw new RuntimeException("Já existe um item para esse produto no pedido!");
        }

        // zera id para garantir que o banco gere o ID
        pedidoItem.setId(null);

        return pedidosItensRepository.save(pedidoItem);
    }

    public PedidosItens atualizar(Long id, PedidosItens pedidoItem) {
        validarSeRegistroExiste(pedidosItensRepository, id);

        return pedidosItensRepository.findById(id).map(record -> {
            record.setQuantidade(10D);
            record.setValorUnitario(200D);
            return pedidosItensRepository.save(record);
        }).get();
    }

    public List<PedidosItens> carregar() {
        List<PedidosItens> pedidoItemsResult = pedidosItensRepository.findAll();

        return pedidoItemsResult;
    }

    public void excluir(Long id) {
        validarSeRegistroExiste(pedidosItensRepository, id);

        pedidosItensRepository.deleteById(id);
    }


}
