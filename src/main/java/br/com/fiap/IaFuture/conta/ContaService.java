package br.com.fiap.IaFuture.conta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Conta cadastrarUsuario(Conta conta) {
        String senhaCodificada = passwordEncoder.encode(conta.getSenha());
        conta.setSenha(senhaCodificada);
        return contaRepository.save(conta);
    }

    public Conta findByUsername(String username) {
        Optional<Conta> contaOptional = contaRepository.findByUsername(username);
        return contaOptional.orElse(null);
    }
}

