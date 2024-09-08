package br.com.fiap.IaFuture.interacaoCliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InteracaoClienteService {

    @Autowired
    private InteracaoClienteRepository interacaoClienteRepository;

    public InteracaoCliente cadastrarInteracao(InteracaoCliente interacaoCliente) {
        return interacaoClienteRepository.save(interacaoCliente);
    }
}
