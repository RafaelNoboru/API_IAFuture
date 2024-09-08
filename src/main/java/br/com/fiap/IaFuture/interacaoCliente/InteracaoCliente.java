package br.com.fiap.IaFuture.interacaoCliente;

import java.time.LocalDate;

import br.com.fiap.IaFuture.clientes.Cliente;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Data
@Table(name = "tb_java_iafuture_interacoes")
public class InteracaoCliente {
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Long id_interacao;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate data_interacao;
    
    @Column(length = 20)
    
    @Pattern(
        regexp = "^(visita_site|interacao_social|compra_site|envio_email|clique_anuncio)$",
        message = "Tipo deve ser visita_site, interacao_social, compra_site, envio_email, clique_anuncio"
    )    
    String tipo;

    @Column(length = 20)

    @Pattern(
        regexp = "^(facebook|instagram|twitter|site|email)$",
        message = "Canal deve ser facebook, instagram, twitter, site, email"
    )
    String canal;
    
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    Cliente cliente;
}

