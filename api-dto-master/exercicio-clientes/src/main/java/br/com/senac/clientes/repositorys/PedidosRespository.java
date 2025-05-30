package br.com.senac.clientes.repositorys;

import br.com.senac.clientes.entitys.Pedidos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidosRespository extends JpaRepository<Pedidos, Long> {
}
