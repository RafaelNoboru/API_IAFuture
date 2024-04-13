package br.com.fiap.IaFuture.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.IaFuture.model.Cadastro;

public interface CadastroRepository extends JpaRepository<Cadastro, Long> {
    
}