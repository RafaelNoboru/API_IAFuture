package br.com.fiap.IaFuture.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
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
import br.com.fiap.IaFuture.model.FeedbackCliente;
import br.com.fiap.IaFuture.model.InteracaoCliente;
import br.com.fiap.IaFuture.repository.ClienteRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("cliente")
@Slf4j
@CacheConfig(cacheNames = "clientes")
@Tag(name = "clientes")
public class ClienteController {

    @Autowired 
    ClienteRepository repository;

    @GetMapping
    @Operation(
        summary = "Listar todos os clientes.",
        description = "Retorna um array com todos os clientes no formato do objeto."
    )
    public List<Cliente> index() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(
        summary = "Cadastrar um novo cliente.",
        description = "Cadastra um novo cliente com os dados enviados no corpo da requisição."
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201", description = "Cadastro do cliente efetuado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados enviados são inválidos. Verifique o corpo da requisição.", useReturnTypeSchema = false)
        }
    )
    public Cliente create(@RequestBody @Valid Cliente cliente) {
        log.info("Cadastrando cliente {}", cliente);
        return repository.save(cliente);
    }

    @GetMapping("{id_cliente}")
    @Operation(
        summary = "Buscar um cliente pelo ID.",
        description = "Retorna os detalhes do cadastro através do `id` informado como parâmetro de path."
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Os dados do cliente foram retornados com sucesso."),
            @ApiResponse(responseCode = "404", description = "Não existe cliente com o `id` informado.", useReturnTypeSchema = false)
        }
    )
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id_cliente) {
        Cliente cliente = repository.findById(id_cliente)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o id: " + id_cliente));

        List<InteracaoCliente> interacoes = repository.findInteracoesByClienteId(id_cliente);
        cliente.setInteracoes(interacoes);

        List<FeedbackCliente> feedbacks = repository.findFeedbacksByClienteId(id_cliente);
        cliente.setFeedbacks(feedbacks);

        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("{id_cliente}")
    @ResponseStatus(NO_CONTENT)
    @Operation(
        summary = "Deletar um cliente pelo ID.",
        description = "Deleta todos os dados de um cliente através do ID especificado no parâmetro path."
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "204", description = "Cliente apagado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Não existe cliente com o `id` informado.", useReturnTypeSchema = false)
        }
    )
    public void destroy(@PathVariable Long id_cliente) {
        log.info("apagando cliente {}", id_cliente);
        verificarSeClienteExiste(id_cliente);
        repository.deleteById(id_cliente);
    }

    @PutMapping("{id_cliente}")
    @Operation(
        summary = "Atualiza os dados de um cliente pelo ID.",
        description = "Altera os dados do cliente especificado no `id`, utilizando as informações enviadas no corpo da requisição."
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Cliente alterado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados enviados são inválidos. Verifique o corpo da requisição.", useReturnTypeSchema = false),
            @ApiResponse(responseCode = "404", description = "Não existe cliente com o `id` informado.", useReturnTypeSchema = false)
        }
    )
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
