package br.com.senac.clientes.services;

import br.com.senac.clientes.controllers.dtos.ClientesRequest;
import br.com.senac.clientes.entitys.Clientes;
import br.com.senac.clientes.entitys.Enderecos;
import br.com.senac.clientes.exceptions.SenacException;
import br.com.senac.clientes.exceptions.SenacNovaException;
import br.com.senac.clientes.repositorys.ClientesRepository;
import br.com.senac.clientes.utils.ValidacoesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static br.com.senac.clientes.utils.ValidacoesUtils.validarSeRegistroExiste;

@Service
public class ClientesService {

    @Autowired
    private ClientesRepository clientesRepository;

    @Autowired
    private EnderecosService enderecosService;

    public Clientes criarCliente(Clientes cliente) throws SenacException, SenacNovaException {
        // valida se o email não está nulo
        if(Objects.isNull(cliente.getEmail())) {
            throw new SenacNovaException("Email não informado!");
        }

        // varifica se já existe algum cliente com o email já cadastrado na base
        Optional<Clientes> clienteResult = clientesRepository.findByEmail(cliente.getEmail());
        if(clienteResult.isPresent()) {
            throw new RuntimeException("Já exite cliente cadastrado no banco com o email informado!");
        }

        // zera id do cliente para garantir que o banco gere o ID
        cliente.setId(null);

        return clientesRepository.save(cliente);
    }

    public Clientes atualizarCliente(Long id, Clientes cliente) {
        ValidacoesUtils.validarSeRegistroExiste(clientesRepository, id);

        // valida se o email não está nulo
        if(Objects.isNull(cliente.getEmail())) {
            throw new RuntimeException("Email não informado!");
        }

        // varifica se já existe algum cliente com o email já cadastrado na base
        Optional<Clientes> clienteResult = clientesRepository.findByEmail(cliente.getEmail());
        if(clienteResult.isPresent() && clienteResult.get().getId().equals(id)) {
            throw new RuntimeException("Já exite cliente cadastrado no banco com o email informado!");
        }

        // adicionado id passado por parametro no objeto
        cliente.setId(id);

        return clientesRepository.save(cliente);
    }

    public List<Clientes> carregarClientes() {
        List<Clientes> clientesResult = clientesRepository.findAll();

        return clientesResult;
    }

    public void excluirCliente(Long id) {
        validarSeRegistroExiste(clientesRepository, id);

        clientesRepository.deleteById(id);
    }

    public Clientes criarClienteCompleto(ClientesRequest cliente) throws SenacNovaException, SenacException {
        // cria um objeto cliente para persistir no banco
        Clientes clientePersist = new Clientes();
        clientePersist.setNome(cliente.getNome());
        clientePersist.setSobrenome(cliente.getSobrenome());
        clientePersist.setDataNascimento(cliente.getDataNascimento());
        clientePersist.setEmail(cliente.getEmail());
        clientePersist.setIdade(cliente.getIdade());
        clientePersist.setDdd(cliente.getDdd());
        clientePersist.setTelefone(cliente.getTelefone());
        clientePersist.setSexo(cliente.getSexo());
        clientePersist.setDocumento(cliente.getDocumento());

        // salva o cliente no banco
        Clientes clienteResult = this.criarCliente(clientePersist);

        // verifica se veio lista de enderecos no request
        if(cliente.getEnderecos() != null &&
                cliente.getEnderecos().isEmpty() == false) {
            // cria os enderecos no banco associando ao cliente salvo
            List<Enderecos> enderecosResult =
                    enderecosService.criarEnderecos(clienteResult, cliente.getEnderecos());

            // associa os enderecos criados ao cliente resultado
            clienteResult.setEnderecos(enderecosResult);
        }

        // retorna o cliente completo com os enderecos
        return clienteResult;
    }

}
