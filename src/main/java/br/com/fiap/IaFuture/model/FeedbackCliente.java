package br.com.fiap.IaFuture.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class FeedbackCliente {
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_feedback;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data_feedback;
    
    @NotBlank
    @Size(min = 3, max = 350, message = "O comentário deve ter entre 3 à 350 caracteres")
    private String comentario;

    @Min(value = 1, message = "A avaliação deve ter o valor mínimo de 1")
    @Max(value = 5, message = "A avaliação deve ter o valor máximo de 5")
    private int avaliacao;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

}
