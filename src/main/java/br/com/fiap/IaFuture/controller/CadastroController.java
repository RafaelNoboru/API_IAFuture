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

import br.com.fiap.IaFuture.model.Cadastro;
import br.com.fiap.IaFuture.repository.CadastroRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("cadastro")
@Slf4j

public class CadastroController {
    
    @Autowired 
    CadastroRepository repository;

    @GetMapping
    public List<Cadastro> index() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Cadastro create(@RequestBody @Valid Cadastro cadastro) {
        log.info("Cadastrando cadastro {}", cadastro);
        return repository.save(cadastro);
    }

    @GetMapping("{id_cadastro}")
    public ResponseEntity<Cadastro> show(@PathVariable Long id_cadastro) {
        log.info("buscando cadastro com id {}", id_cadastro);

        return repository
                .findById(id_cadastro)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @DeleteMapping("{id_cadastro}")
    @ResponseStatus(NO_CONTENT)
    public void destroy(@PathVariable Long id_cadastro) {
        log.info("apagando cadastro {}", id_cadastro);
        verificarSeCadastroExiste(id_cadastro);
        repository.deleteById(id_cadastro);
    }

    @PutMapping("{id_cadastro}")
    public Cadastro update(@PathVariable Long id_cadastro, @Valid @RequestBody Cadastro cadastro) {
        log.info("atualizar cadastro {} para {}", id_cadastro, cadastro);

        verificarSeCadastroExiste(id_cadastro);
        cadastro.setId_cadastro(id_cadastro);
        return repository.save(cadastro);
    }

    private void verificarSeCadastroExiste(Long id_cadastro) {
        repository
                .findById(id_cadastro)
                .orElseThrow(() -> new ResponseStatusException(
                        NOT_FOUND,
                        "Não existe cadastro com o id informado"));
    }
}
