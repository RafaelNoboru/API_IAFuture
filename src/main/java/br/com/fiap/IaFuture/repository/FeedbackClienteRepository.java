package br.com.fiap.IaFuture.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.IaFuture.model.FeedbackCliente;

public interface FeedbackClienteRepository extends JpaRepository<FeedbackCliente, Long> {
    
}
