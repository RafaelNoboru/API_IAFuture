package br.com.fiap.IaFuture.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class ResultadoIa {
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_resultadoIa;

    @NotBlank
    private String tipo_analise;
    
    @NotBlank
    private String detalhes;

    
}
