package br.com.senac.clientes.services;

import br.com.senac.clientes.SenacException;
import br.com.senac.clientes.controllers.dtos.ItemPedidoRequest;
import br.com.senac.clientes.controllers.dtos.PedidoItensRequest;
import br.com.senac.clientes.entitys.*;
import br.com.senac.clientes.exceptions.*;
import br.com.senac.clientes.repositorys.ClientesRepository;
import br.com.senac.clientes.repositorys.EnderecosRepository;
import br.com.senac.clientes.repositorys.PedidosItensRepository;
import br.com.senac.clientes.repositorys.PedidosRespository;
import br.com.senac.clientes.utils.ValidacoesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static br.com.senac.clientes.utils.ValidacoesUtils.validarSeRegistroExiste;

@Service
public class PedidosService {

    @Autowired
    private PedidosRespository pedidosRespository;

    @Autowired
    private ClientesRepository clientesRepository;

    @Autowired
    private EnderecosRepository enderecosRepository;

    @Autowired
    private PedidosItensRepository pedidosItensRepository;

    public Pedidos criar(Pedidos pedido) throws SenacClienteNaoExisteException, SenacClienteNaoInformadoException, SenacEnderecoNaoExisteException {
        if(pedido.getCliente() == null) {
            throw new SenacClienteNaoInformadoException();
        }

        Optional<Enderecos> resultEndereco = enderecosRepository.findById(pedido.getEndereco().getId());
        if(resultEndereco.isEmpty()) {
            throw new SenacEnderecoNaoExisteException();
        }

        Clientes clienteResult = resultEndereco.get().getCliente();

        if(clienteResult.getId() != pedido.getCliente().getId()) {
            throw new SenacClienteNaoExisteException();
        }

        // zera id para garantir que o banco gere o ID
        pedido.setId(null);

        return pedidosRespository.save(pedido);
    }

    public Pedidos atualizar(Long id, Pedidos pedido) throws SenacEnderecoNaoRelacionadoClienteException, SenacEnderecoNaoExisteException {
        Optional<Pedidos> pedidoResult = pedidosRespository.findById(id);
        if(pedidoResult.isEmpty()) {
            throw new RuntimeException("Pedido não encontrado");
        }

        Optional<Enderecos> enderecosResult = enderecosRepository.findById(pedido.getEndereco().getId());
        if(enderecosResult.isEmpty()) {
            throw new SenacEnderecoNaoExisteException();
        }

        if(pedidoResult.get().getCliente().getId() != enderecosResult.get().getCliente().getId()) {
            throw new SenacEnderecoNaoRelacionadoClienteException();
        }


        return pedidosRespository.findById(id).map(record -> {
            record.setEndereco(pedido.getEndereco());
            record.setValorTotal(pedido.getValorTotal());

            return pedidosRespository.save(record);
        }).get();
    }

    public List<Pedidos> carregar() {
        List<Pedidos> pedidosResult = pedidosRespository.findAll();

        return pedidosResult;
    }

    public void excluir(Long id) {
        validarSeRegistroExiste(pedidosRespository, id);

        pedidosRespository.deleteById(id);
    }

    public Pedidos criarPedidoCompleto(PedidoItensRequest pedidos) throws SenacClienteNaoExisteException, SenacEnderecoNaoExisteException, SenacClienteNaoInformadoException {
        // cria um novo objeto pedido que vai ser salvo
        Pedidos pedidoPersist = new Pedidos();

        // cria um cliente com o id vindo do request
        Clientes cliente = new Clientes();
        cliente.setId(pedidos.getClienteId());

        // cria um endereco com o id vindo do request
        Enderecos enderecos = new Enderecos();
        enderecos.setId(pedidos.getEnderecoId());

        // seta os dados principais do pedido
        pedidoPersist.setDataCriacao(LocalDate.now());
        pedidoPersist.setValorTotal(pedidos.getValorTotal());
        pedidoPersist.setCliente(cliente);
        pedidoPersist.setEndereco(enderecos);

        // salva o pedido no banco
        Pedidos pedidoResult = this.criar(pedidoPersist);

        // percorre a lista de itens do pedido
        for (ItemPedidoRequest itemDto : pedidos.getPedidoItens()){
            // cria um novo item do pedido
            PedidosItens item = new PedidosItens();
            item.setPedido(pedidoResult); // associa com o pedido salvo

            // cria um produto com o id vindo do dto
            Produtos produto = new Produtos();
            produto.setId(itemDto.getProdutoId());
            item.setProduto(produto); // associa o produto ao item

            // seta quantidade e valor unitario
            item.setQuantidade(itemDto.getQuantidade());
            item.setValorUnitario(itemDto.getValorUnitario());

            // salva o item no banco
            pedidosItensRepository.save(item);
        }

        // retorna o pedido completo com os itens já salvos
        return pedidoResult;
    }

}
