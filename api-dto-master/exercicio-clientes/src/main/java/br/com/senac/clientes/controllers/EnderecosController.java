package br.com.senac.clientes.controllers;

import br.com.senac.clientes.entitys.Enderecos;
import br.com.senac.clientes.services.EnderecosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/enderecos")
public class EnderecosController {
    @Autowired
    private EnderecosService enderecosService;

    @GetMapping("/listar")
    public ResponseEntity<List<Enderecos>> carregarEnderecos() {
        try {
            return ResponseEntity.ok(enderecosService.carregarEnderecos());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/listar/cliente/{clienteId}")
    public ResponseEntity<List<Enderecos>> carregarEnderecosByClienteId(@PathVariable Long clienteId) {
        try {
            return ResponseEntity.ok(enderecosService.carregarEnderecosByClienteId(clienteId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/criar")
    public ResponseEntity<Enderecos> cadastrarEndereco(@RequestBody Enderecos endereco) {
        try {
            return ResponseEntity.ok(enderecosService.criarEndereco(endereco));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Enderecos> atualizarEndereco(@PathVariable Long id , @RequestBody Enderecos endereco) {
        try {
            return ResponseEntity.ok(enderecosService.atualizarEndereco(id, endereco));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<Void> excluirEndereco(@PathVariable Long id) {
        try {
            enderecosService.excluirEndereco(id);

            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
