package br.com.senac.clientes.repositorys;

import br.com.senac.clientes.entitys.Enderecos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecosRepository extends JpaRepository<Enderecos, Long> {
    List<Enderecos> findByClienteId(Long clienteId);
}
