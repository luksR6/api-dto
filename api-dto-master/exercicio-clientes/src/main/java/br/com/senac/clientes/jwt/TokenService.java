package br.com.senac.clientes.jwt;

import br.com.senac.clientes.entitys.Usuarios;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${spring.seguranca.segredo}")
    private String secret;

    @Value("${spring.seguranca.tempo_validade}")
    private Long expirationTime;

    public String gerarToken(Usuarios usuarios) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        String token = JWT.create()
                .withIssuer("exemplo-api-token")
                .withSubject(usuarios.getEmail())// vai pegar o email de quem está fazendo
                .withExpiresAt(this.gerarDataValidadeToken()) // faz a validade de tempo
                .sign(algorithm); // vai garantir a segurança

        return token;
    }

    public String validarToken(String token) { // funcao que vai validar o token
        Algorithm algorithm = Algorithm.HMAC256(secret);

        try {
            return JWT.require(algorithm)
                    .withIssuer("exemplo-api-token") // quem gerou
                    .build()
                    .verify(token)
                    .getSubject(); // se estiver certo vai pegar o subject do email 
        } catch (JWTVerificationException e) {
            return null;
        }
    }
    private Instant gerarDataValidadeToken() {
        return LocalDateTime.now().plusMinutes(1).toInstant(ZoneOffset.of("-03:00"));
    }
}
