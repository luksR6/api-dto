package br.com.senac.clientes.repositorys;

import br.com.senac.clientes.entitys.PedidosItens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidosItensRepository extends JpaRepository<PedidosItens, Long> {
    Optional<PedidosItens> findByPedidoIdAndProdutoId(Long pedidoId, Long produtoId);
}
