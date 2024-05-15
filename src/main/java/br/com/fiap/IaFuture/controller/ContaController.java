package br.com.fiap.IaFuture.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.IaFuture.model.Conta;
import br.com.fiap.IaFuture.repository.ContaRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("conta")
@Slf4j

public class ContaController {
    
    @Autowired 
    ContaRepository repository;

    @GetMapping
    public List<Conta> index() {
        return repository.findAll();
    }

    @GetMapping("{id_conta}")
    public ResponseEntity<Conta> show(@PathVariable Long id_conta) {
        log.info("buscando conta com id {}", id_conta);

        return repository
                .findById(id_conta)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }
    
    @GetMapping("ultimos")
    public List<Conta> getLast10() {
        return repository.findLast10();
    }
    @GetMapping("ordemalfabetica")
    public List<Conta> getOrderedByName() {
        return repository.findAllOrderedByName();
    }

    @DeleteMapping("{id_conta}")
    @ResponseStatus(NO_CONTENT)
    public void destroy(@PathVariable Long id_conta) {
        log.info("apagando conta {}", id_conta);
        verificarSeContaExiste(id_conta);
        repository.deleteById(id_conta);
    }

    @PutMapping("{id_conta}")
    public Conta update(@PathVariable Long id_conta, @Valid @RequestBody Conta conta) {
        log.info("atualizar conta {} para {}", id_conta, conta);

        verificarSeContaExiste(id_conta);
        conta.setId_conta(id_conta);
        return repository.save(conta);
    }

    private void verificarSeContaExiste(Long id_conta) {
        repository
                .findById(id_conta)
                .orElseThrow(() -> new ResponseStatusException(
                        NOT_FOUND,
                        "Não existe conta com o id informado"));
    }

}
