package br.com.fiap.IaFuture.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Login {
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_login;

    @NotBlank
    private String usuario;

    @NotBlank
    @Size(min = 8, message = "A senha deve conter no mínimo 8 caracteres")
    private String senha;
}
