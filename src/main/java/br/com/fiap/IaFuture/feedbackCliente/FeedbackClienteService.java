package br.com.fiap.IaFuture.feedbackCliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackClienteService {

    @Autowired
    private FeedbackClienteRepository feedbackClienteRepository;

    public FeedbackCliente cadastrarFeeback(FeedbackCliente feedbackCliente) {
        return feedbackClienteRepository.save(feedbackCliente);
    }
}
