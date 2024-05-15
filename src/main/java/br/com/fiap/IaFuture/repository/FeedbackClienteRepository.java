package br.com.fiap.IaFuture.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fiap.IaFuture.model.Cliente;
import br.com.fiap.IaFuture.model.FeedbackCliente;
import io.swagger.v3.oas.annotations.Hidden;

@Hidden
public interface FeedbackClienteRepository extends JpaRepository<FeedbackCliente, Long> {
    
    @Query(value = "SELECT * FROM FeedbackCliente ORDER BY id_feedback DESC", nativeQuery = true)
    List<FeedbackCliente> findLast();

    List<FeedbackCliente> findByCliente(Cliente cliente);
}
