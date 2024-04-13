package br.com.fiap.IaFuture.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.IaFuture.model.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    
}

