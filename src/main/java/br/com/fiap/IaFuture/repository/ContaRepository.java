package br.com.fiap.IaFuture.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fiap.IaFuture.model.Conta;
import io.swagger.v3.oas.annotations.Hidden;

@Hidden
public interface ContaRepository extends JpaRepository<Conta, Long> {
    
    @Query("SELECT c FROM Conta c JOIN FETCH c.cadastro")
    List<Conta> findAllWithCadastro();

    @Query(value = "SELECT * FROM Conta ORDER BY id_conta DESC LIMIT 10", nativeQuery = true)
    List<Conta> findLast10();

    @Query("SELECT c FROM Conta c ORDER BY c.cadastro.nome ASC")
    List<Conta> findAllOrderedByName();
}

