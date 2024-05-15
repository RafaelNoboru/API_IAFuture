package br.com.fiap.IaFuture.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.HttpStatus;
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
import br.com.fiap.IaFuture.model.InteracaoCliente;
import br.com.fiap.IaFuture.repository.ClienteRepository;
import br.com.fiap.IaFuture.repository.InteracaoClienteRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("interacaocliente")
@Slf4j
@CacheConfig(cacheNames = "interações")
@Tag(name = "interações")
public class InteracaoClienteController {
    
    @Autowired 
    InteracaoClienteRepository repository;

    @Autowired
    ClienteRepository clienteRepository;

    @GetMapping
    @Operation(
        summary = "Listar todos as interações.",
        description = "Retorna um array com todas as interações no formato do objeto."
    )
    public List<InteracaoCliente> index() {
        return repository.findAll();
    }

    // @PostMapping
    // @ResponseStatus(CREATED)
    // public InteracaoCliente create(@RequestBody @Valid InteracaoCliente interacaoCliente) {
    //     log.info("Cadastrando interação {}", interacaoCliente);
    //     return repository.save(interacaoCliente);
    // }

    @PostMapping("{id_cliente}")
    @Operation(
        summary = "Cadastrar uma nova interação em um ID de cliente específico.",
        description = "Cadastra uma nova interação com os dados enviados no corpo da requisição."
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201", description = "Cadastro da interação efetuada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados enviados são inválidos. Verifique o corpo da requisição.", useReturnTypeSchema = false)
        }
    )
    public ResponseEntity<InteracaoCliente> criarInteracao(@PathVariable Long id_cliente, @RequestBody InteracaoCliente interacaoCliente) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id_cliente);
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            interacaoCliente.setCliente(cliente); 
            InteracaoCliente novaInteracao = repository.save(interacaoCliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaInteracao);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // @GetMapping("{id_interacao}")
    // public ResponseEntity<InteracaoCliente> show(@PathVariable Long id_interacao) {
    //     log.info("buscando interação com id {}", id_interacao);

    //     return repository
    //             .findById(id_interacao)
    //             .map(ResponseEntity::ok)
    //             .orElse(ResponseEntity.notFound().build());

    // }

    @GetMapping("{id_cliente}")
    @Operation(
        summary = "Buscar uma interação pelo ID.",
        description = "Retorna os detalhes da interação através do `id` informado como parâmetro de path."
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Os dados da interação foram retornados com sucesso."),
            @ApiResponse(responseCode = "404", description = "Não existe interação com o `id` informado.", useReturnTypeSchema = false)
        }
    )
    public ResponseEntity<List<InteracaoCliente>> obterInteracoesCliente(@PathVariable Long id_cliente) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id_cliente);
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            List<InteracaoCliente> interacoes = cliente.getInteracoes();
            return ResponseEntity.ok(interacoes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id_interacao}")
    @ResponseStatus(NO_CONTENT)
    @Operation(
        summary = "Deletar uma interação pelo ID.",
        description = "Deleta todos os dados de uma interação através do ID especificado no parâmetro path."
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "204", description = "Interação apagada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Não existe interação com o `id` informado.", useReturnTypeSchema = false)
        }
    )
    public void destroy(@PathVariable Long id_interacao) {
        log.info("apagando interação {}", id_interacao);
        verificarSeInteracaoExiste(id_interacao);
        repository.deleteById(id_interacao);
    }

    @PutMapping("{id_interacao}")
    @Operation(
        summary = "Atualiza os dados de uma interação pelo ID.",
        description = "Altera os dados da interação especificado no `id`, utilizando as informações enviadas no corpo da requisição."
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Interação alterada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados enviados são inválidos. Verifique o corpo da requisição.", useReturnTypeSchema = false),
            @ApiResponse(responseCode = "404", description = "Não existe interação com o `id` informado.", useReturnTypeSchema = false)
        }
    )
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
