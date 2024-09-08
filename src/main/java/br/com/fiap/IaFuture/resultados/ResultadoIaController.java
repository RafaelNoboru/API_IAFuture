package br.com.fiap.IaFuture.resultados;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resultados")
@CrossOrigin(origins = "http://localhost:3000")
public class ResultadoIaController {

    @Autowired
    private ResultadoIaRepository resultadoIaRepository;
    @Autowired
    private ResultadoIaService resultadoIaService;

    @GetMapping
    public List<ResultadoIa> listarResultados() {
        return resultadoIaRepository.findAll();
    }

    @PostMapping
    public ResultadoIa cadastraResultadoIa(@RequestBody ResultadoIa resultadoIa) {
        return resultadoIaService.cadastrarResultadoIa(resultadoIa);
    }
}
