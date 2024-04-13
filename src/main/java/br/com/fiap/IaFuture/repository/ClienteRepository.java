package br.com.fiap.IaFuture.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.IaFuture.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
}
