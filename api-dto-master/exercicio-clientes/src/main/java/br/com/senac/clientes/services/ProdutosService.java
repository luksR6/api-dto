package br.com.senac.clientes.services;

import br.com.senac.clientes.entitys.Produtos;
import br.com.senac.clientes.exceptions.SenacException;
import br.com.senac.clientes.repositorys.ProdutosRepository;
import br.com.senac.clientes.utils.ValidacoesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.senac.clientes.utils.ValidacoesUtils.validarSeRegistroExiste;

@Service
public class ProdutosService {

    @Autowired
    private ProdutosRepository produtosRepository;

    public Produtos criar(Produtos produto) throws SenacException {

        // zera id para garantir que o banco gere o ID
        produto.setId(null);

        return produtosRepository.save(produto);
    }

    public Produtos atualizar(Long id, Produtos produto) {
        ValidacoesUtils.validarSeRegistroExiste(produtosRepository, id);

        // adicionado id passado por parametro no objeto
        produto.setId(id);

        return produtosRepository.save(produto);
    }

    public List<Produtos> carregar() {
        List<Produtos> produtosResult = produtosRepository.findAll();

        return produtosResult;
    }

    public void excluir(Long id) {
        validarSeRegistroExiste(produtosRepository, id);

        produtosRepository.deleteById(id);
    }
}
