package br.com.fiap.IaFuture.resultados;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(name = "tb_java_iafuture_resultados")
public class ResultadoIa {
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Long id_resultadoIa;

    @NotBlank
    String tipo_analise;
    
    @NotBlank
    String detalhes;

    
}
