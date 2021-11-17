package br.com.sica.sicaativosservice.controllers;

import br.com.sica.sicaativosservice.dtos.ativos.AtivoDto;
import br.com.sica.sicaativosservice.dtos.ativos.ListagemAtivo;
import br.com.sica.sicaativosservice.service.AtivoService;
import br.com.sica.sicaativosservice.validators.RequestAuthValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestAuthValidator
@RequestMapping("/api/ativos")
public class AtivoController {

    private final Logger LOGGER = LoggerFactory.getLogger(AtivoController.class);

    @Autowired
    private AtivoService ativosService;

    public AtivoController(final AtivoService ativosService) {
        this.ativosService = ativosService;
    }
    
    @GetMapping
    public ResponseEntity<List<ListagemAtivo>> listarTodosAtivos() {
        LOGGER.info("Chamando listagem de ativos...");
        List<ListagemAtivo> todosAtivos = ativosService.listarTodosAtivos();
        return new ResponseEntity<>(todosAtivos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtivoDto> buscarAtivoPorId(@PathVariable Long id) {
        LOGGER.info("Buscando ativo {}...", id);
        AtivoDto ativo = ativosService.buscarPorId(id);
        if (ativo == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(ativo, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<AtivoDto> salvarAtivo(@RequestBody AtivoDto ativo) {
        LOGGER.info("Chamando salvar ativo...");
        AtivoDto ativoDto = ativosService.salvar(ativo);
        return new ResponseEntity<>(ativoDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtivoDto> alterarAtivo(@RequestBody AtivoDto ativo, @PathVariable Long id) {
        LOGGER.info("Alterando ativo {}...", id);
        AtivoDto ativoAlterado = ativosService.alterarAtivo(ativo, id);
        if (ativoAlterado == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(ativoAlterado, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity apagarAtivo(@PathVariable Long id) {
        LOGGER.info("Apagando ativo {}...", id);
        boolean confirmacao = ativosService.apagarAtivo(id);
        if (confirmacao) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
