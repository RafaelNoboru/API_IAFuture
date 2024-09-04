package br.com.fiap.IaFuture.conta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    String username;

    String senha;

}
