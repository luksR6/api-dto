package br.com.senac.clientes.controllers;

import br.com.senac.clientes.entitys.Produtos;
import br.com.senac.clientes.services.ProdutosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/produtos")
public class ProdutosController {

    @Autowired
    private ProdutosService produtosService;

    @GetMapping("/listar")
    public ResponseEntity<?> carregar() {
        try {
            return ResponseEntity.ok(produtosService.carregar());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() != null ? e.getMessage() : e.getCause().getMessage());
        }
    }

    @PostMapping("/criar")
    public ResponseEntity<?> cadastrar(@RequestBody Produtos produto) {
        try {
            return ResponseEntity.created(null).body(produtosService.criar(produto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() != null ? e.getMessage() : e.getCause().getMessage());
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizar(@PathVariable Long id , @RequestBody Produtos produto) {
        try {
            return ResponseEntity.ok(produtosService.atualizar(id, produto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() != null ? e.getMessage() : e.getCause().getMessage());
        }
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        try {
            produtosService.excluir(id);

            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
