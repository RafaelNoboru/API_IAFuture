package br.com.fiap.IaFuture.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.IaFuture.model.InteracaoCliente;

public interface InteracaoClienteRepository extends JpaRepository<InteracaoCliente, Long> {
    
}
