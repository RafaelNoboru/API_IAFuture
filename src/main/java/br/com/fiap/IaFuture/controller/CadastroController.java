package br.com.fiap.IaFuture.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

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

import br.com.fiap.IaFuture.model.Cadastro;
import br.com.fiap.IaFuture.model.Conta;
import br.com.fiap.IaFuture.repository.CadastroRepository;
import br.com.fiap.IaFuture.repository.ContaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("cadastro")
@Slf4j
@CacheConfig(cacheNames = "cadastros")
@Tag(name = "cadastros")
public class CadastroController {
    
    @Autowired 
    CadastroRepository repository;

    @Autowired
    ContaRepository contaRepository;

    @GetMapping
    @Operation(
        summary = "Listar todos os cadastros",
        description = "Retorna um array com todos os cadastros no formato do objeto"
    )
    public List<Cadastro> index() {
        return repository.findAll();
    }

    @PostMapping("registro")
    @ResponseStatus(CREATED)
    @Operation(
        summary = "Realizar um cadastro",
        description = "Cria um novo cadastro com os dados enviados no corpo da requisição"
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201", description = "Cadastro efetuado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados enviados são inválidos. Verifique o corpo da requisição", useReturnTypeSchema = false)
        }
    )
    public void register(@RequestBody @Valid Cadastro cadastro) {
        log.info("Fazendo um cadastro {}", cadastro);
    
        Cadastro novoCadastro = repository.save(cadastro);

        Conta conta = new Conta();
        conta.setCadastro(novoCadastro);
        contaRepository.save(conta);
    }

    @GetMapping("{id_cadastro}")
    public ResponseEntity<Cadastro> show(@PathVariable Long id_cadastro) {
        log.info("buscando cadastro com id {}", id_cadastro);

        return repository
                .findById(id_cadastro)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("ultimos")
    @Operation(
        summary = "Listar os últimos 10 cadastros",
        description = "Retorna um array com todos os últimos 10 cadastros no formato do objeto"
    )
    public List<Cadastro> getLast10() {
        return repository.findLast10();
    }
    @GetMapping("ordemalfabetica")
    @Operation(
        summary = "Listar todos os cadastros em ordem alfabética",
        description = "Retorna um array com todos os cadastros em ordem alfabética e no formato do objeto"
    )
    public List<Cadastro> getOrderedByName() {
        return repository.findAllOrderedByName();
    }

    @DeleteMapping("{id_cadastro}")
    @ResponseStatus(NO_CONTENT)
    @Operation(
        summary = "Deletar um cadastro pelo ID",
        description = "Deleta todos os dados de um cadastro através do ID especificado no parâmetro path"
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "204", description = "Cadastro apagado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não existe cadastro com o `id` informado", useReturnTypeSchema = false)
        }
    )
    public void destroy(@PathVariable Long id_cadastro) {
        log.info("apagando cadastro {}", id_cadastro);
        verificarSeCadastroExiste(id_cadastro);
        repository.deleteById(id_cadastro);
    }

    @PutMapping("{id_cadastro}")
    @Operation(
        summary = "Atualiza os dados de um cadastro pelo ID",
        description = "Altera os dados do cadastro especificado no `id`, utilizando as informações enviadas no corpo da requisição."
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Cadastro alterado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados enviados são inválidos. Verifique o corpo da requisição", useReturnTypeSchema = false),
            @ApiResponse(responseCode = "404", description = "Não existe cadastro com o `id` informado", useReturnTypeSchema = false)
        }
    )
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
