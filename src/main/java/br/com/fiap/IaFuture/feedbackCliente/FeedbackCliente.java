package br.com.fiap.IaFuture.feedbackCliente;

import java.time.LocalDate;

import br.com.fiap.IaFuture.clientes.Cliente;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "tb_java_iafuture_feedbacks")
public class FeedbackCliente {
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Long id_feedback;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate data_feedback;
    
    @NotBlank
    @Size(min = 3, max = 350, message = "O comentário deve ter entre 3 à 350 caracteres")
    String comentario;

    @Min(value = 1, message = "A avaliação deve ter o valor mínimo de 1")
    @Max(value = 5, message = "A avaliação deve ter o valor máximo de 5")
    int avaliacao;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    Cliente cliente;

}
