package br.com.fiap.IaFuture.clientes;

import br.com.fiap.IaFuture.feedbackCliente.FeedbackCliente;
import br.com.fiap.IaFuture.feedbackCliente.FeedbackClienteRepository;
import br.com.fiap.IaFuture.feedbackCliente.FeedbackClienteService;
import br.com.fiap.IaFuture.interacaoCliente.InteracaoCliente;
import br.com.fiap.IaFuture.interacaoCliente.InteracaoClienteRepository;
import br.com.fiap.IaFuture.interacaoCliente.InteracaoClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "http://localhost:3000")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private FeedbackClienteRepository feedbackClienteRepository;

    @Autowired
    private FeedbackClienteService feedbackClienteService;

    @Autowired
    private InteracaoClienteRepository interacaoClienteRepository;

    @Autowired
    private InteracaoClienteService interacaoClienteService;


    //Clientes
    @GetMapping
    public List<Cliente> listaClientes() {
        return clienteRepository.findAll();
    }

    @PostMapping
    public Cliente cadastrarCliente(@RequestBody Cliente cliente) {
        return clienteService.cadastrarCliente(cliente);
    }

    //Feedbacks
    @GetMapping("/feedbacks")
    public List<FeedbackCliente> listaFeedbackClientes() {
        return feedbackClienteRepository.findAll();
    }

    @PostMapping("/feedbacks")
    public FeedbackCliente cadastrarFeedback(@RequestBody FeedbackCliente feedbackCliente) {
        return feedbackClienteService.cadastrarFeeback(feedbackCliente);
    }

    //Interações
    @GetMapping("/interacoes")
    public List<InteracaoCliente> listaInteracaoClientes() {
        return interacaoClienteRepository.findAll();
    }

    @PostMapping("/interacoes")
    public InteracaoCliente cadastrarInteracao(@RequestBody InteracaoCliente interacaoCliente) {
        return interacaoClienteService.cadastrarInteracao(interacaoCliente);
    }

}
