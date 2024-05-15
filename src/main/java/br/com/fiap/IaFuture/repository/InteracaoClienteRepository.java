package br.com.fiap.IaFuture.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fiap.IaFuture.model.Cliente;
import br.com.fiap.IaFuture.model.InteracaoCliente;
import io.swagger.v3.oas.annotations.Hidden;

@Hidden
public interface InteracaoClienteRepository extends JpaRepository<InteracaoCliente, Long> {
    
    @Query(value = "SELECT * FROM InteracaoCliente ORDER BY id_interacao DESC", nativeQuery = true)
    List<InteracaoCliente> findLast();

    List<InteracaoCliente> findByCliente(Cliente cliente);
}
