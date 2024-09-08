package br.com.fiap.IaFuture.clientes;

import java.time.LocalDate;
import java.util.List;

import br.com.fiap.IaFuture.feedbackCliente.FeedbackCliente;
import br.com.fiap.IaFuture.interacaoCliente.InteracaoCliente;
import jakarta.persistence.*;
import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@JsonIgnoreProperties({"interacoes", "feedbacks"})
@Table(name = "tb_java_iafuture_clientes")
public class Cliente {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id_cliente;

    String nome;

    String email;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate data_nascimento;

    @CPF(message = "Cpf deve ser válido")
    String cpf;

    String telefone;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    List<InteracaoCliente> interacoes;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    List<FeedbackCliente> feedbacks;
}
