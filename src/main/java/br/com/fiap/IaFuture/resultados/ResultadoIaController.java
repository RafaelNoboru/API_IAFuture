package br.com.fiap.IaFuture.resultados;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/resultados")
public class ResultadoIaController {

    @Autowired
    private ResultadoIaRepository resultadoIaRepository;

    @GetMapping
    public List<ResultadoIa> listarResultados() {
        return resultadoIaRepository.findAll();
    }
}
