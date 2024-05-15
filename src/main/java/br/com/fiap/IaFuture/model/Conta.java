package br.com.fiap.IaFuture.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Conta {
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_conta;
    
    @OneToOne
    @JoinColumn(name = "id_cadastro")
    private Cadastro cadastro;
}
