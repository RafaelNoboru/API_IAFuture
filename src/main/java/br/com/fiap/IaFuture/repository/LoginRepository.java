package br.com.fiap.IaFuture.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fiap.IaFuture.model.Login;
import io.swagger.v3.oas.annotations.Hidden;

@Hidden
public interface LoginRepository extends JpaRepository<Login, Long> {
    
    @Query(value = "SELECT * FROM Login ORDER BY id_login DESC", nativeQuery = true)
    List<Login> findLast();
}