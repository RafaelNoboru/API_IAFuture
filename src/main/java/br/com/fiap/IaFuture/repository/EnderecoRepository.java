package br.com.fiap.IaFuture.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.IaFuture.model.Endereco;
import io.swagger.v3.oas.annotations.Hidden;
    
@Hidden
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    
}


