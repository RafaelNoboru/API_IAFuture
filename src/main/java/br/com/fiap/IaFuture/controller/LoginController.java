package br.com.fiap.IaFuture.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("login")
@Slf4j
@CacheConfig(cacheNames = "logins")
@Tag(name = "logins")
public class LoginController {
    
    @Autowired 
    LoginRepository repository;

    @GetMapping
    @Cacheable
    @Operation(
        summary = "Listar todos os logins.",
        description = "Retorna um array com todos os logins no formato do objeto."
    )
    public List<Login> index() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Cadastrar um novo login.",
        description = "Cadastra um novo login com os dados enviados no corpo da requisição."
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201", description = "Cadastro do login efetuado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados enviados são inválidos. Verifique o corpo da requisição.", useReturnTypeSchema = false)
        }
    )
    public Login create(@RequestBody @Valid Login login) {
        log.info("Cadastrando login {}", login);
        return repository.save(login);
    }

    @GetMapping("{id_login}")
    @Operation(
        summary = "Buscar um login pelo ID.",
        description = "Retorna os detalhes do login através do `id` informado como parâmetro de path."
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Os dados do login foram retornados com sucesso."),
            @ApiResponse(responseCode = "404", description = "Não existe login com o `id` informado.", useReturnTypeSchema = false)
        }
    )
    public ResponseEntity<Login> show(@PathVariable Long id_login) {
        log.info("buscando login com id {}", id_login);

        return repository
                .findById(id_login)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @DeleteMapping("{id_login}")
    @ResponseStatus(NO_CONTENT)
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Deletar um login pelo ID.",
        description = "Deleta todos os dados de um login através do ID especificado no parâmetro path."
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "204", description = "Login apagado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Não existe login com o `id` informado.", useReturnTypeSchema = false)
        }
    )
    public void destroy(@PathVariable Long id_login) {
        log.info("apagando login {}", id_login);
        verificarSeLoginExiste(id_login);
        repository.deleteById(id_login);
    }

    @PutMapping("{id_login}")
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Atualiza os dados de um login pelo ID.",
        description = "Altera os dados do login especificado no `id`, utilizando as informações enviadas no corpo da requisição."
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Login alterado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados enviados são inválidos. Verifique o corpo da requisição.", useReturnTypeSchema = false),
            @ApiResponse(responseCode = "404", description = "Não existe login com o `id` informado.", useReturnTypeSchema = false)
        }
    )
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
