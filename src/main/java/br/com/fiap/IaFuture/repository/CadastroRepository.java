package br.com.fiap.IaFuture.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fiap.IaFuture.model.Cadastro;
import io.swagger.v3.oas.annotations.Hidden;

@Hidden
public interface CadastroRepository extends JpaRepository<Cadastro, Long> {

    @Query(value = "SELECT * FROM Cadastro ORDER BY id_cadastro DESC LIMIT 10", nativeQuery = true)
    List<Cadastro> findLast10();

    @Query("SELECT c FROM Cadastro c ORDER BY c.nome ASC")
    List<Cadastro> findAllOrderedByName();

}