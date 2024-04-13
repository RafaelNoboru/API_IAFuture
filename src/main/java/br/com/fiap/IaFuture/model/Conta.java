package br.com.fiap.IaFuture.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Conta {
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_conta;

    @NotNull
    private Long id_cadastro;

    @NotBlank
    private String nome;

    @NotBlank
    private String telefone;

    @NotBlank
    private String endereco;

    @NotBlank
    private String CNPJ;

}
