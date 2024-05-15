package br.com.fiap.IaFuture.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fiap.IaFuture.model.ResultadoIa;
import io.swagger.v3.oas.annotations.Hidden;

@Hidden
public interface ResultadoIaRepository extends JpaRepository<ResultadoIa, Long> {
 
    @Query(value = "SELECT * FROM ResultadoIa ORDER BY id_resultadoIa DESC", nativeQuery = true)
    List<ResultadoIa> findLast();

}