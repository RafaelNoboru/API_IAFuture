package br.com.fiap.IaFuture.clientes;

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
    Long id_cliente;
    
    @NotBlank
    String nome;
    
    @NotBlank
    String email;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate data_nascimento;

    @CPF(message = "Cpf deve ser válido")
    @NotBlank
    String cpf;

    @NotBlank
    String telefone;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    List<InteracaoCliente> interacoes;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    List<FeedbackCliente> feedbacks;
}
