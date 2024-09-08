package br.com.fiap.IaFuture.resultados;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultadoIaService {

    @Autowired
    ResultadoIaRepository resultadoIaRepository;

    public ResultadoIa cadastrarResultadoIa(ResultadoIa resultadoIa) {
        return resultadoIaRepository.save(resultadoIa);
    }
}
