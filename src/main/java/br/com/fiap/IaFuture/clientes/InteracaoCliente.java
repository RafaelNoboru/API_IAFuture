package br.com.fiap.IaFuture.clientes;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Data
public class InteracaoCliente {
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_interacao;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
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
    
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
}

