package br.com.senac.clientes.services;

import br.com.senac.clientes.controllers.dtos.UsuariosRequest;
import br.com.senac.clientes.controllers.dtos.UsuariosResponse;
import br.com.senac.clientes.entitys.Usuarios;
import br.com.senac.clientes.jwt.TokenService;
import br.com.senac.clientes.repositorys.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    public UsuariosResponse criarUsuarios(UsuariosRequest usuarioRequest) {
        Optional<Usuarios> usuarioResult = usuariosRepository.findByEmail(usuarioRequest.getEmail());

        if (usuarioResult.isPresent()){ // se o usuario ja existir ele entra aqui
            throw new RuntimeException("Usurario já existe na base");
        }

        Usuarios usuario = new Usuarios();
        usuario.setEmail(usuarioRequest.getEmail());
        usuario.setSenha(passwordEncoder.encode(usuarioRequest.getSenha()));
        usuario.setNome(usuarioRequest.getNome());
        usuario.setId(null);

        Usuarios usuariosPersistResult = usuariosRepository.save(usuario);

        UsuariosResponse retorno = new UsuariosResponse();
        retorno.setId(usuariosPersistResult.getId());
        retorno.setEmail(usuariosPersistResult.getEmail());

        return retorno;
    }

    public UsuariosResponse login(UsuariosRequest usuario){

        Optional<Usuarios> usuarioResult = usuariosRepository.findByEmail(usuario.getEmail());

        if (usuarioResult.isEmpty()){
            throw new RuntimeException("Usuario não encontrado!");
        }

        Usuarios usuarioBanco = usuarioResult.get();

        if (passwordEncoder.matches(usuario.getSenha(), usuarioBanco.getSenha())){ // passa a senha da requisicao e a senha do bd vai verificar, se forem iguais retorna true
            UsuariosResponse response = new UsuariosResponse();

            response.setId(usuarioBanco.getId());
            response.setEmail(usuarioBanco.getEmail());
            response.setToken(tokenService.gerarToken(usuarioBanco));

            return response;
        }

        throw new RuntimeException("SENHA INVÁLIDA!");
    }


}
