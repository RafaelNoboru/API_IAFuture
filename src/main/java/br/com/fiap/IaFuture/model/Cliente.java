package br.com.fiap.IaFuture.model;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@JsonIgnoreProperties({"interacoes", "feedbacks"})
public class Cliente {
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_cliente;
    
    @NotBlank
    private String nome;
    
    @NotBlank
    private String email;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data_nascimento;

    @CPF(message = "Cpf deve ser válido")
    @NotBlank
    private String cpf;

    @NotBlank
    private String telefone;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<InteracaoCliente> interacoes;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<FeedbackCliente> feedbacks;
}
