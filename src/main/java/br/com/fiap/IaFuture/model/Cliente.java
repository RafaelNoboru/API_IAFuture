package br.com.fiap.IaFuture.model;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Cliente {
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_cliente;
    
    @NotBlank
    private String nome;
    
    @NotBlank
    private String email;
    
    private LocalDate data_nascimento;

    @CPF(message = "Cpf deve ser válido")
    @NotBlank
    private String cpf;

    @NotBlank
    private String telefone;




    
}
