package br.com.senac.clientes.controllers;

import br.com.senac.clientes.entitys.Enderecos;
import br.com.senac.clientes.entitys.Pedidos;
import br.com.senac.clientes.services.PedidosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/pedidos")
public class PedidosController {

    @Autowired
    private PedidosService pedidosService;

    @GetMapping("/listar")
    public ResponseEntity<?> carregar() {
        try {
            return ResponseEntity.ok(pedidosService.carregar());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() != null ? e.getMessage() : e.getCause().getMessage());
        }
    }

    @PostMapping("/criar")
    public ResponseEntity<?> cadastrar(@RequestBody Pedidos pedido) {
        try {
            return ResponseEntity.created(null).body(pedidosService.criar(pedido));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage() != null ? e.getMessage() : e.getCause().getMessage());
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id , @RequestBody Pedidos pedido) {
        try {
            return ResponseEntity.ok(pedidosService.atualizar(id, pedido));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage() != null ? e.getMessage() : e.getCause().getMessage());
        }
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        try {
            pedidosService.excluir(id);

            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
