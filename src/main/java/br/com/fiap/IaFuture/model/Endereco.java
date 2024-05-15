package br.com.fiap.IaFuture.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Endereco {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_endereco;

    @NotBlank
    private String cep;
    
    @NotBlank
    private String logradouro;
    
    private String complemento;
    
    @NotBlank
    private String bairro;
    
    @NotBlank
    private String cidade;
    
    @NotBlank
    private String estado;

    private int numero;

    @JsonIgnore
    @OneToOne(mappedBy = "endereco")
    private Cadastro cadastro;

}

