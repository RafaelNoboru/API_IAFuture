package br.com.fiap.IaFuture.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

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

import br.com.fiap.IaFuture.model.FeedbackCliente;
import br.com.fiap.IaFuture.repository.FeedbackClienteRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("feedbackcliente")
@Slf4j

public class FeedbackClienteController {
    
    @Autowired 
    FeedbackClienteRepository repository;


    @GetMapping
    public List<FeedbackCliente> index() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public FeedbackCliente create(@RequestBody @Valid FeedbackCliente feedbackCliente) {
        log.info("Cadastrando feedback {}", feedbackCliente);
        return repository.save(feedbackCliente);
    }

    @GetMapping("{id_feedback}")
    public ResponseEntity<FeedbackCliente> show(@PathVariable Long id_feedback) {
        log.info("buscando feedback com id {}", id_feedback);

        return repository
                .findById(id_feedback)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @DeleteMapping("{id_feedback}")
    @ResponseStatus(NO_CONTENT)
    public void destroy(@PathVariable Long id_feedback) {
        log.info("apagando feedback {}", id_feedback);
        verificarSeFeedbackExiste(id_feedback);
        repository.deleteById(id_feedback);
    }

    @PutMapping("{id_feedback}")
    public FeedbackCliente update(@PathVariable Long id_feedback, @Valid @RequestBody FeedbackCliente feedbackCliente) {
        log.info("atualizar feedback {} para {}", id_feedback, feedbackCliente);

        verificarSeFeedbackExiste(id_feedback);
        feedbackCliente.setId_feedback(id_feedback);
        return repository.save(feedbackCliente);
    }

    private void verificarSeFeedbackExiste(Long id_feedback) {
        repository
                .findById(id_feedback)
                .orElseThrow(() -> new ResponseStatusException(
                        NOT_FOUND,
                        "Não existe feedback com o id informado"));
    }

}
