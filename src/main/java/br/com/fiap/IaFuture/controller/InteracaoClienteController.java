package br.com.fiap.IaFuture.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.IaFuture.model.InteracaoCliente;
import br.com.fiap.IaFuture.repository.InteracaoClienteRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("interacaocliente")
@Slf4j

public class InteracaoClienteController {
    
    @Autowired 
    InteracaoClienteRepository repository;

    @GetMapping
    public List<InteracaoCliente> index() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public InteracaoCliente create(@RequestBody @Valid InteracaoCliente interacaoCliente) {
        log.info("Cadastrando interação {}", interacaoCliente);
        return repository.save(interacaoCliente);
    }

    @GetMapping("{id_interacao}")
    public ResponseEntity<InteracaoCliente> show(@PathVariable Long id_interacao) {
        log.info("buscando interação com id {}", id_interacao);

        return repository
                .findById(id_interacao)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @DeleteMapping("{id_interacao}")
    @ResponseStatus(NO_CONTENT)
    public void destroy(@PathVariable Long id_interacao) {
        log.info("apagando interação {}", id_interacao);
        verificarSeInteracaoExiste(id_interacao);
        repository.deleteById(id_interacao);
    }

    @PutMapping("{id_interacao}")
    public InteracaoCliente update(@PathVariable Long id_interacao, @Valid @RequestBody InteracaoCliente interacaoCliente) {
        log.info("atualizar interação {} para {}", id_interacao, interacaoCliente);

        verificarSeInteracaoExiste(id_interacao);
        interacaoCliente.setId_interacao(id_interacao);
        return repository.save(interacaoCliente);
    }

    private void verificarSeInteracaoExiste(Long id_interacao) {
        repository
                .findById(id_interacao)
                .orElseThrow(() -> new ResponseStatusException(
                        NOT_FOUND,
                        "Não existe interação com o id informado"));
    }
}
