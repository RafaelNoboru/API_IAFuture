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
import br.com.fiap.IaFuture.model.FeedbackCliente;
import br.com.fiap.IaFuture.repository.ClienteRepository;
import br.com.fiap.IaFuture.repository.FeedbackClienteRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("feedbackcliente")
@Slf4j
@CacheConfig(cacheNames = "feedbacks")
@Tag(name = "feedbacks")
public class FeedbackClienteController {
    
    @Autowired 
    FeedbackClienteRepository repository;
    
    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    @Operation(
        summary = "Listar todos os feedbacks.",
        description = "Retorna um array com todos os feedbacks no formato do objeto."
    )
    public List<FeedbackCliente> index() {
        return repository.findAll();
    }

    // @PostMapping
    // @ResponseStatus(CREATED)
    // public FeedbackCliente create(@RequestBody @Valid FeedbackCliente feedbackCliente) {
    //     log.info("Cadastrando feedback {}", feedbackCliente);
    //     return repository.save(feedbackCliente);
    // }
    
    @PostMapping("{id_cliente}")
    @Operation(
        summary = "Cadastrar um feedback em um ID de cliente específico.",
        description = "Cria um novo feedback com os dados enviados no corpo da requisição."
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201", description = "Cadastro de feedback efetuado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados enviados são inválidos. Verifique o corpo da requisição.", useReturnTypeSchema = false)
        }
    )
    public ResponseEntity<FeedbackCliente> criarFeedback(@PathVariable Long id_cliente, @RequestBody FeedbackCliente feedbackCliente) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id_cliente);
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            feedbackCliente.setCliente(cliente); 
            FeedbackCliente novoFeedback = repository.save(feedbackCliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoFeedback);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // @GetMapping("{id_feedback}")
    // public ResponseEntity<FeedbackCliente> show(@PathVariable Long id_feedback) {
    //     log.info("buscando feedback com id {}", id_feedback);

    //     return repository
    //             .findById(id_feedback)
    //             .map(ResponseEntity::ok)
    //             .orElse(ResponseEntity.notFound().build());

    // }
    
    @GetMapping("{id_cliente}")
    @Operation(
        summary = "Buscar um feedback pelo ID.",
        description = "Retorna os detalhes do feedback através do `id` informado como parâmetro de path."
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Os dados do feedback foram retornados com sucesso."),
            @ApiResponse(responseCode = "404", description = "Não existe feedback com o `id` informado.", useReturnTypeSchema = false)
        }
    )
    public ResponseEntity<List<FeedbackCliente>> obterFeedbacksCliente(@PathVariable Long id_cliente) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id_cliente);
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            List<FeedbackCliente> feedbacks = cliente.getFeedbacks();
            return ResponseEntity.ok(feedbacks);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id_feedback}")
    @ResponseStatus(NO_CONTENT)
    @Operation(
        summary = "Deletar um feedback pelo ID.",
        description = "Deleta todos os dados de um feedback através do ID especificado no parâmetro path."
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "204", description = "Feedback apagado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Não existe feedback com o `id` informado.", useReturnTypeSchema = false)
        }
    )
    public void destroy(@PathVariable Long id_feedback) {
        log.info("apagando feedback {}", id_feedback);
        verificarSeFeedbackExiste(id_feedback);
        repository.deleteById(id_feedback);
    }

    @PutMapping("{id_feedback}")
    @Operation(
        summary = "Atualizar os dados de um feedback pelo ID.",
        description = "Altera os dados do feedback especificado no `id`, utilizando as informações enviadas no corpo da requisição."
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Feedback alterado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados enviados são inválidos. Verifique o corpo da requisição.", useReturnTypeSchema = false),
            @ApiResponse(responseCode = "404", description = "Não existe feedback com o `id` informado.", useReturnTypeSchema = false)
        }
    )
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
