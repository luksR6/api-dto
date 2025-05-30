package br.com.senac.clientes.controllers;

import br.com.senac.clientes.controllers.dtos.ClientesRequest;
import br.com.senac.clientes.entitys.Clientes;
import br.com.senac.clientes.exceptions.SenacException;
import br.com.senac.clientes.exceptions.SenacNovaException;
import br.com.senac.clientes.services.ClientesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/clientes")
public class ClientesController {

    @Autowired
    private ClientesService clientesService;

    @GetMapping("/carregar")
    public ResponseEntity<List<Clientes>> carregarClientes() {
        try {
            return ResponseEntity.ok(clientesService.carregarClientes());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/criar")
    public ResponseEntity<?> cadastrarCliente(@RequestBody Clientes cliente) {
        try {
            return ResponseEntity.ok(clientesService.criarCliente(cliente));
        } catch (SenacException | SenacNovaException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Clientes> atualizarCliente(@PathVariable Long id , @RequestBody Clientes cliente) {
        try {
            return ResponseEntity.ok(clientesService.atualizarCliente(id, cliente));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<Void> excluirCliente(@PathVariable Long id) {
        try {
            clientesService.excluirCliente(id);

            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/completo/criar")
    public ResponseEntity<?> criarClienteCompleto(@RequestBody ClientesRequest cliente) {
        try {
            return ResponseEntity
                    .created(null)
                    .body(clientesService.criarClienteCompleto(cliente));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
