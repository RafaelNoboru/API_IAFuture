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

import br.com.fiap.IaFuture.model.ResultadoIa;
import br.com.fiap.IaFuture.repository.ResultadoIaRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("resultadoia")
@Slf4j

public class ResultadoIaController {
    
    @Autowired 
    ResultadoIaRepository repository;

    @GetMapping
    public List<ResultadoIa> index() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public ResultadoIa create(@RequestBody @Valid ResultadoIa resultadoIa) {
        log.info("Cadastrando resultado {}", resultadoIa);
        return repository.save(resultadoIa);
    }

    @GetMapping("{id_resultadoIa}")
    public ResponseEntity<ResultadoIa> show(@PathVariable Long id_resultadoIa) {
        log.info("buscando resultado com id {}", id_resultadoIa);

        return repository
                .findById(id_resultadoIa)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @DeleteMapping("{id_resultadoIa}")
    @ResponseStatus(NO_CONTENT)
    public void destroy(@PathVariable Long id_resultadoIa) {
        log.info("apagando resultado {}", id_resultadoIa);
        verificarSeResultadoExiste(id_resultadoIa);
        repository.deleteById(id_resultadoIa);
    }

    @PutMapping("{id_resultadoIa}")
    public ResultadoIa update(@PathVariable Long id_resultadoIa, @Valid @RequestBody ResultadoIa resultadoIa) {
        log.info("atualizar resultado {} para {}", id_resultadoIa, resultadoIa);

        verificarSeResultadoExiste(id_resultadoIa);
        resultadoIa.setId_resultadoIa(id_resultadoIa);
        return repository.save(resultadoIa);
    }

    private void verificarSeResultadoExiste(Long id_resultadoIa) {
        repository
                .findById(id_resultadoIa)
                .orElseThrow(() -> new ResponseStatusException(
                        NOT_FOUND,
                        "Não existe resultado com o id informado"));
    }
}
