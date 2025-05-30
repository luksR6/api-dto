package br.com.senac.clientes.utils;

import org.springframework.data.jpa.repository.JpaRepository;

public class ValidacoesUtils {
    public static void validarSeRegistroExiste(JpaRepository repository, Long id) {
        if(!repository.existsById(id)){
            throw new RuntimeException("Registro não encontrado no banco de dados!");
        }
    }
}
