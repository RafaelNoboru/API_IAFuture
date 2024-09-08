package br.com.fiap.IaFuture.conta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contas")
@CrossOrigin(origins = "http://localhost:3030")
public class ContaController {

    @Autowired
    private ContaService contaService;
    @Autowired
    private ContaRepository contaRepository;

    @PostMapping
    public ResponseEntity<Conta> cadastrar(@RequestBody Conta conta) {
        Conta novaConta = contaService.cadastrarUsuario(conta);
        return ResponseEntity.ok(novaConta);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Conta> getContaByUsername(@PathVariable String username) {
        Optional<Conta> conta = contaRepository.findByUsername(username);
        return conta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());}

    @GetMapping
    public List<Conta> listarContas() {
        return contaRepository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Conta> atualizarConta(@PathVariable Long id, @RequestBody @Validated Conta contaAtualizada) {
        Optional<Conta> contaExistente = contaRepository.findById(id);
        if (contaExistente.isPresent()) {
            contaAtualizada.setId_conta(id);
            Conta contaSalva = contaRepository.save(contaAtualizada);
            return ResponseEntity.ok(contaSalva);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarConta(@PathVariable Long id) {
        Optional<Conta> contaExistente = contaRepository.findById(id);
        if (contaExistente.isPresent()) {
            contaRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
