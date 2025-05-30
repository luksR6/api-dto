package br.com.senac.clientes.services;

import br.com.senac.clientes.controllers.dtos.EnderecosRequest;
import br.com.senac.clientes.entitys.Clientes;
import br.com.senac.clientes.exceptions.SenacException;
import br.com.senac.clientes.entitys.Enderecos;
import br.com.senac.clientes.repositorys.ClientesRepository;
import br.com.senac.clientes.repositorys.EnderecosRepository;
import br.com.senac.clientes.utils.ValidacoesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

import static br.com.senac.clientes.utils.ValidacoesUtils.validarSeRegistroExiste;

@Service
public class EnderecosService {

    @Autowired
    private EnderecosRepository enderecosRepository;

    @Autowired
    private ClientesRepository clientesRepository;

    public Enderecos criarEndereco(Enderecos endereco) throws SenacException {
        if(endereco.getCliente() == null) {
            throw new SenacException("Cliente não informado!");
        }

        if(endereco.getCliente().getId() == null) {
            throw new SenacException("Id do cliente não informado!");
        }

        // Valida se o cliente existe na base
        if(!clientesRepository.existsById(endereco.getCliente().getId())) {
            throw new SenacException("Cliente não encontrado!");
        }

        boolean permiteCriacao = this.validarQuantidadeEnderecos(endereco.getCliente().getId());
        if(!permiteCriacao) {
            throw new SenacException("Cliente já possui 3 endereços");
        }

        // zera id para garantir que o banco gere o ID
        endereco.setId(null);

        return enderecosRepository.save(endereco);
    }

    public Enderecos atualizarEndereco(Long id, Enderecos endereco) {
        ValidacoesUtils.validarSeRegistroExiste(enderecosRepository, id);

        // adicionado id passado por parametro no objeto
        endereco.setId(id);

        return enderecosRepository.save(endereco);
    }

    public List<Enderecos> carregarEnderecos() {
        List<Enderecos> enderecosResult = enderecosRepository.findAll();

        return enderecosResult;
    }

    public List<Enderecos> carregarEnderecosByClienteId(Long clienteId) {
        List<Enderecos> enderecosResult = enderecosRepository.findByClienteId(clienteId);

        return enderecosResult;
    }

    public void excluirEndereco(Long id) {
        validarSeRegistroExiste(enderecosRepository, id);

        enderecosRepository.deleteById(id);
    }

    private boolean validarQuantidadeEnderecos(Long clienteId) {
        List<Enderecos> enderecosResult = enderecosRepository.findByClienteId(clienteId);

        if(enderecosResult.isEmpty()) {
            return true;
        }

        return enderecosResult.size() < 3;
    }

    // metodo que pega um cliente e uma lista de enderecos do tipo dto
    // transforma isso em entidades enderecos e salva no banco
    public List<Enderecos> criarEnderecos(
            Clientes cliente, List<EnderecosRequest> enderecos) throws SenacException {

        // cria uma lista vazia pra guardar os enderecos que vamos devolver no final
        List<Enderecos> saida = new ArrayList<>();

        // loop nos enderecos que vieram do request dto coisa da api
        for(EnderecosRequest endereco : enderecos) {
            // cria um novo objeto da entidade enderecos que é o que vai pro banco
            Enderecos enderecoPersit = new Enderecos();

            // agora começa a copiar os dados do dto pro objeto de verdade
            enderecoPersit.setBairro(endereco.getBairro());       // bairro
            enderecoPersit.setCep(endereco.getCep());             // cep
            enderecoPersit.setCidade(endereco.getCidade());       // cidade
            enderecoPersit.setLogradouro(endereco.getLogradouro());// rua
            enderecoPersit.setEstado(endereco.getEstado());       // estado tipo sp rj etc
            enderecoPersit.setNumero(endereco.getNumero());       // numero da casa ou predio

            // aqui a parte importante esse endereco pertence a um cliente
            enderecoPersit.setCliente(cliente);

            // salva no banco usando um metodo que ta em outro lugar e guarda o retorno
            saida.add(this.criarEndereco(enderecoPersit));
        }

        // no fim das contas devolve a lista bonitinha com todos os enderecos salvos
        return saida;
    }

}
