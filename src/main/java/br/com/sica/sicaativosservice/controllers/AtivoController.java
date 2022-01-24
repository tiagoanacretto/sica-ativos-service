package br.com.sica.sicaativosservice.controllers;

import br.com.sica.sicaativosservice.dtos.ativos.AgendamentoManutencaoAtivoDto;
import br.com.sica.sicaativosservice.dtos.ativos.AtivoDto;
import br.com.sica.sicaativosservice.dtos.ativos.ListagemAtivo;
import br.com.sica.sicaativosservice.service.AtivoService;
import br.com.sica.sicaativosservice.validators.RequestAuthValidator;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@OpenAPIDefinition(
        info = @Info(
                title = "sica-ativos-service",
                description = "Serviços de gerenciamento de ativos",
                version = "1.0.0",
                contact = @Contact(name = "Tiago Anacretto", email = "tiago.anacretto@gmail.com")
        ))
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

    @Operation(summary = "Listagem de ativos", description = "Lista todos os ativos cadastrados na base de dados")
    @GetMapping
    public ResponseEntity<List<ListagemAtivo>> listarTodosAtivos() {
        LOGGER.info("Chamando listagem de ativos...");
        List<ListagemAtivo> todosAtivos = ativosService.listarTodosAtivos();
        return new ResponseEntity<>(todosAtivos, HttpStatus.OK);
    }

    @Operation(summary = "Consulta de ativos", description = "Exibe todos os dados de um ativo a partir de seu ID")
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

    @Operation(summary = "Inclusão de ativo", description = "Inclui um novo ativo na base de dados")
    @PostMapping
    public ResponseEntity<AtivoDto> salvarAtivo(@RequestBody AtivoDto ativo) {
        LOGGER.info("Chamando salvar ativo...");
        AtivoDto ativoDto = ativosService.salvar(ativo);
        return new ResponseEntity<>(ativoDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Alteração de ativo", description = "Sobrescreve os atributos de um ativo já cadastrado")
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

    @Operation(summary = "Remoção de ativos", description = "Remove um ativo da base de dados")
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

    @Operation(summary = "Listagem de agendamentos", description = "Lista todos os agendamentos de um determinado ativo")
    @GetMapping("/{id}/agendamentos")
    public ResponseEntity<List<AgendamentoManutencaoAtivoDto>> getAgendamentosDoAtivo(@PathVariable Long id) {
        LOGGER.info("Buscando agendamentos do ativo {}...", id);
        return new ResponseEntity<>(ativosService.getAgendamentosDoAtivo(id), HttpStatus.OK);
    }
}
