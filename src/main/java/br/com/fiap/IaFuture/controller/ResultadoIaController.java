package br.com.fiap.IaFuture.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("resultadoia")
@Slf4j
@CacheConfig(cacheNames = "resultados")
@Tag(name = "resultados")
public class ResultadoIaController {
    
    @Autowired 
    ResultadoIaRepository repository;

    @GetMapping
    @Operation(
        summary = "Listar todos os resultados.",
        description = "Retorna um array com todos os resultados no formato do objeto."
    )
    public List<ResultadoIa> index() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(
        summary = "Cadastrar um resultado em um ID de cliente específico.",
        description = "Cria um novo resultado com os dados enviados no corpo da requisição."
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201", description = "Cadastro de resultado efetuado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados enviados são inválidos. Verifique o corpo da requisição.", useReturnTypeSchema = false)
        }
    )
    public ResultadoIa create(@RequestBody @Valid ResultadoIa resultadoIa) {
        log.info("Cadastrando resultado {}", resultadoIa);
        return repository.save(resultadoIa);
    }

    @GetMapping("{id_resultadoIa}")
    @Operation(
        summary = "Buscar um resultado pelo ID.",
        description = "Retorna os detalhes do resultado através do `id` informado como parâmetro de path."
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Os dados do resultado foram retornados com sucesso."),
            @ApiResponse(responseCode = "404", description = "Não existe resultado com o `id` informado.", useReturnTypeSchema = false)
        }
    )
    public ResponseEntity<ResultadoIa> show(@PathVariable Long id_resultadoIa) {
        log.info("buscando resultado com id {}", id_resultadoIa);

        return repository
                .findById(id_resultadoIa)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @DeleteMapping("{id_resultadoIa}")
    @ResponseStatus(NO_CONTENT)
    @Operation(
        summary = "Deletar um resultado pelo ID.",
        description = "Deleta todos os dados de um resultado através do ID especificado no parâmetro path."
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "204", description = "Resultado apagado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Não existe resultado com o `id` informado.", useReturnTypeSchema = false)
        }
    )
    public void destroy(@PathVariable Long id_resultadoIa) {
        log.info("apagando resultado {}", id_resultadoIa);
        verificarSeResultadoExiste(id_resultadoIa);
        repository.deleteById(id_resultadoIa);
    }

    @PutMapping("{id_resultadoIa}")
    @Operation(
        summary = "Atualizar os dados de um resultado pelo ID.",
        description = "Altera os dados do resultado especificado no `id`, utilizando as informações enviadas no corpo da requisição."
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Resultado alterado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados enviados são inválidos. Verifique o corpo da requisição.", useReturnTypeSchema = false),
            @ApiResponse(responseCode = "404", description = "Não existe resultado com o `id` informado.", useReturnTypeSchema = false)
        }
    )
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
