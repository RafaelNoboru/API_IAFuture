package br.com.fiap.IaFuture.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class FeedbackCliente {
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_feedback;
    
    @NotNull
    private Long id_cliente;
    
    private LocalDate data_feedback;
    
    @NotBlank
    @Size(min = 3, max = 350, message = "O comentário deve ter entre 3 à 350 caracteres")
    private String comentario;

    @Min(value = 1, message = "A avaliação deve ter o valor mínimo de 1")
    @Max(value = 5, message = "A avaliação deve ter o valor máximo de 5")
    private int avaliacao;

    
}
