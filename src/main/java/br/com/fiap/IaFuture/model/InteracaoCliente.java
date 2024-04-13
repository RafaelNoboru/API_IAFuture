package br.com.fiap.IaFuture.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Data
public class InteracaoCliente {
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_interacao;
    
    @NotNull
    private Long id_cliente;
    
    private LocalDate data_interacao;
    
    @Column(length = 20)
    
    @Pattern(
        regexp = "^(visita_site|interacao_social|compra_site|envio_email|clique_anuncio)$",
        message = "Tipo deve ser visita_site, interacao_social, compra_site, envio_email, clique_anuncio"
    )    
    private String tipo;

    @Column(length = 20)

    @Pattern(
        regexp = "^(facebook|instagram|twitter|site|email)$",
        message = "Canal deve ser facebook, instagram, twitter, site, email"
    )
    private String canal;
}
