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

import br.com.fiap.IaFuture.model.Login;
import br.com.fiap.IaFuture.repository.LoginRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("login")
@Slf4j

public class LoginController {
    
    @Autowired 
    LoginRepository repository;

    @GetMapping
    public List<Login> index() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Login create(@RequestBody @Valid Login login) {
        log.info("Cadastrando login {}", login);
        return repository.save(login);
    }

    @GetMapping("{id_login}")
    public ResponseEntity<Login> show(@PathVariable Long id_login) {
        log.info("buscando login com id {}", id_login);

        return repository
                .findById(id_login)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @DeleteMapping("{id_login}")
    @ResponseStatus(NO_CONTENT)
    public void destroy(@PathVariable Long id_login) {
        log.info("apagando login {}", id_login);
        verificarSeLoginExiste(id_login);
        repository.deleteById(id_login);
    }

    @PutMapping("{id_login}")
    public Login update(@PathVariable Long id_login, @Valid @RequestBody Login login) {
        log.info("atualizar login {} para {}", id_login, login);

        verificarSeLoginExiste(id_login);
        login.setId_login(id_login);
        return repository.save(login);
    }

    private void verificarSeLoginExiste(Long id_login) {
        repository
                .findById(id_login)
                .orElseThrow(() -> new ResponseStatusException(
                        NOT_FOUND,
                        "Não existe cadastro com o id informado"));
    }
}
