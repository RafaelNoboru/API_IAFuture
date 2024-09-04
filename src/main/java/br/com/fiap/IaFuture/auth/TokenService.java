package br.com.fiap.IaFuture.auth;

import br.com.fiap.IaFuture.conta.Conta;
import br.com.fiap.IaFuture.conta.ContaRepository;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private final ContaRepository contaRepository;
    private Algorithm algorithm = Algorithm.HMAC256("assinatura");

    public TokenService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    public Token create(Conta conta){
        var expires = LocalDateTime.now().plusMinutes(10).toInstant(ZoneOffset.ofHours(-3));

        var token = JWT.create()
                .withSubject(conta.getUsername())
                .withClaim("role", "USER")
                .withExpiresAt(expires)
                .sign(algorithm);

        return new Token(token, conta.getUsername());
    }

    public Conta getUserFromToken(String token) {
        var username = JWT.require(algorithm)
                .build()
                .verify(token)
                .getSubject();

        return contaRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }
}
