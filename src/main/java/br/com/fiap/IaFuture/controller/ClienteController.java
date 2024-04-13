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

import br.com.fiap.IaFuture.model.Cliente;
import br.com.fiap.IaFuture.repository.ClienteRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("cliente")
@Slf4j

public class ClienteController {

    @Autowired 
    ClienteRepository repository;


    @GetMapping
    public List<Cliente> index() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Cliente create(@RequestBody @Valid Cliente cliente) {
        log.info("Cadastrando cliente {}", cliente);
        return repository.save(cliente);
    }

    @GetMapping("{id_cliente}")
    public ResponseEntity<Cliente> show(@PathVariable Long id_cliente) {
        log.info("buscando cliente com id {}", id_cliente);

        return repository
                .findById(id_cliente)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @DeleteMapping("{id_cliente}")
    @ResponseStatus(NO_CONTENT)
    public void destroy(@PathVariable Long id_cliente) {
        log.info("apagando cliente {}", id_cliente);
        verificarSeClienteExiste(id_cliente);
        repository.deleteById(id_cliente);
    }

    @PutMapping("{id_cliente}")
    public Cliente update(@PathVariable Long id_cliente, @Valid @RequestBody Cliente cliente) {
        log.info("atualizar cliente {} para {}", id_cliente, cliente);

        verificarSeClienteExiste(id_cliente);
        cliente.setId_cliente(id_cliente);
        return repository.save(cliente);
    }

    private void verificarSeClienteExiste(Long id_cliente) {
        repository
                .findById(id_cliente)
                .orElseThrow(() -> new ResponseStatusException(
                        NOT_FOUND,
                        "Não existe cliente com o id informado"));
    }

}
