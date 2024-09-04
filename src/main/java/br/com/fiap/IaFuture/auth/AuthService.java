package br.com.fiap.IaFuture.auth;

import br.com.fiap.IaFuture.conta.ContaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final ContaRepository contaRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthService(ContaRepository contaRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.contaRepository = contaRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public Token login(Credentials credentials){
        var conta = contaRepository.findByUsername(credentials.username())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        System.out.println("Senha recebida: " + credentials.senha());
        System.out.println("Senha armazenada: " + conta.getSenha());

        if (!passwordEncoder.matches(credentials.senha(), conta.getSenha())) {
            throw new RuntimeException("Senha incorreta");
        }

        return tokenService.create(conta);
    }
}
