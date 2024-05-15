package br.com.fiap.IaFuture.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("conta")
@Slf4j
@CacheConfig(cacheNames = "contas")
@Tag(name = "contas")
public class ContaController {
    
    @Autowired 
    ContaRepository repository;

    @GetMapping
    @Operation(
        summary = "Listar todos as contas.",
        description = "Retorna um array com todas as contas no formato do objeto."
    )
    public List<Conta> index() {
        return repository.findAll();
    }

    @GetMapping("{id_conta}")
    @Operation(
        summary = "Buscar uma conta pelo ID.",
        description = "Retorna os detalhes da conta através do `id` informado como parâmetro de path."
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Os dados da conta foram retornados com sucesso."),
            @ApiResponse(responseCode = "404", description = "Não existe conta com o `id` informado.", useReturnTypeSchema = false)
        }
    )
    public ResponseEntity<Conta> show(@PathVariable Long id_conta) {
        log.info("buscando conta com id {}", id_conta);

        return repository
                .findById(id_conta)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }
    
    @GetMapping("ultimos")
    @Operation(
        summary = "Listar as últimas 10 contas.",
        description = "Retorna um array com todas as últimas 10 contas no formato do objeto."
    )
    public List<Conta> getLast10() {
        return repository.findLast10();
    }
    @GetMapping("ordemalfabetica")
    @Operation(
        summary = "Listar todos as contas em ordem alfabética.",
        description = "Retorna um array com todas as contas em ordem alfabética e no formato do objeto."
    )
    public List<Conta> getOrderedByName() {
        return repository.findAllOrderedByName();
    }

    @DeleteMapping("{id_conta}")
    @Operation(
        summary = "Deletar uma conta pelo ID.",
        description = "Deleta todos os dados de uma conta através do ID especificado no parâmetro path."
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "204", description = "Conta apagada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Não existe conta com o `id` informado.", useReturnTypeSchema = false)
        }
    )
    @ResponseStatus(NO_CONTENT)
    public void destroy(@PathVariable Long id_conta) {
        log.info("apagando conta {}", id_conta);
        verificarSeContaExiste(id_conta);
        repository.deleteById(id_conta);
    }

    @PutMapping("{id_conta}")
    @Operation(
        summary = "Atualizar os dados de uma conta pelo ID.",
        description = "Altera os dados da conta especificada no `id`, utilizando as informações enviadas no corpo da requisição."
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Conta alterada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados enviados são inválidos. Verifique o corpo da requisição.", useReturnTypeSchema = false),
            @ApiResponse(responseCode = "404", description = "Não existe conta com o `id` informado.", useReturnTypeSchema = false)
        }
    )
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
