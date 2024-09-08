package br.com.fiap.IaFuture.conta;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

@Data
@Entity
@Table(name = "tb_java_iafuture_contas")
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id_conta;

    String nome;

    String telefone;

    String cnpj;

    String cep;

    String logradouro;

    String complemento;

    String bairro;

    String cidade;

    String estado;

    String numero;

    @Column(unique = true)
    @NotNull
    String username;

    @NotNull
    String senha;

}
