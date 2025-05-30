package br.com.senac.clientes.config;

import br.com.senac.clientes.entitys.Usuarios;
import br.com.senac.clientes.jwt.TokenService;
import br.com.senac.clientes.repositorys.UsuariosRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = this.recuperarToken(request);
        String login = tokenService.validarToken(token);

        if (login != null){
            Optional<Usuarios> usuariosResult = usuariosRepository.findByEmail(login);
            if (usuariosResult.isPresent()){
                UsernamePasswordAuthenticationToken autorizacao =
                        new UsernamePasswordAuthenticationToken(usuariosResult.get(), null, null);
                SecurityContextHolder.getContext().setAuthentication(autorizacao);
            } else {
                throw new RuntimeException("Usuario não encontrado");
            }
        }

        filterChain.doFilter(request, response); // ele vai executar mas nao passa a
                                                // diante a request pra frente e nao devolve a requisicao

    }

    private String recuperarToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");

        if (token == null) return null;

        return token.replace("Bearer ", "");

    }
}
