package br.com.senac.clientes.controllers;

import br.com.senac.clientes.controllers.dtos.PedidoItensRequest;
import br.com.senac.clientes.entitys.PedidosItens;
import br.com.senac.clientes.services.PedidosItensService;
import br.com.senac.clientes.services.PedidosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pedidosItens")
public class PedidosItensController {
    @Autowired
    private PedidosItensService pedidosItensService;

    @Autowired
    private PedidosService pedidosService;

    @GetMapping("/listar")
    public ResponseEntity<?> carregar() {
        try {
            return ResponseEntity.ok(pedidosItensService.carregar());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() != null ? e.getMessage() : e.getCause().getMessage());
        }
    }

    @PostMapping("/criar")
    public ResponseEntity<?> cadastrar(@RequestBody PedidosItens pedido) {
        try {
            return ResponseEntity.created(null).body(pedidosItensService.criar(pedido));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() != null ? e.getMessage() : e.getCause().getMessage());
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizar(@PathVariable Long id , @RequestBody PedidosItens pedido) {
        try {
            return ResponseEntity.ok(pedidosItensService.atualizar(id, pedido));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() != null ? e.getMessage() : e.getCause().getMessage());
        }
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        try {
            pedidosItensService.excluir(id);

            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/completo/criar")
    public ResponseEntity<?> criarCompleto(@RequestBody PedidoItensRequest pedido) {
        try {
            // tenta criar o pedido chamando o service e devolve resposta 201 created
            return ResponseEntity
                    .created(null) // nao tem uri de recurso criado entao fica null mesmo
                    .body(pedidosService.criarPedidoCompleto(pedido)); // chama o metodo que monta tudo
        } catch (Exception e){
            // se der erro imprime no console e devolve resposta 400 com a mensagem de erro
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
