package br.com.sica.sicaativosservice.controllers;

import br.com.sica.sicaativosservice.dtos.manutencoes.ListagemManutencao;
import br.com.sica.sicaativosservice.dtos.manutencoes.ManutencaoDto;
import br.com.sica.sicaativosservice.service.ManutencaoService;
import br.com.sica.sicaativosservice.validators.RequestAuthValidator;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/manutencoes")
public class ManutencaoController {

    private final Logger LOGGER = LoggerFactory.getLogger(ManutencaoController.class);

    @Autowired
    private ManutencaoService manutencaoService;

    @Operation(summary = "Listagem de datas de manutenção", description = "Dada uma categoria, lista as próximas datas disponíveis para agendamento de uma manutenção")
    @GetMapping(value = "/disponiveis/{categoria}")
    public ResponseEntity<List<String>> listarManutencoesDisponiveis(@PathVariable String categoria) {
        LOGGER.info("Chamando listagem de datas de manutencoes disponiveis...");
        return new ResponseEntity<>(manutencaoService.listarManutencoesDisponiveis(categoria), HttpStatus.OK);
    }

    @Operation(summary = "Listagem de manutenções", description = "Lista todas as manutenções cadastradas na base de dados")
    @GetMapping
    public ResponseEntity<List<ListagemManutencao>> listarTodasManutencoes() {
        LOGGER.info("Chamando listagem de manutencoes...");
        return new ResponseEntity<>(manutencaoService.listarTodas(), HttpStatus.OK);
    }

    @Operation(summary = "Consulta de manutenção", description = "Exibe todos os dados de uma manutenção a partir de seu ID")
    @GetMapping("/{id}")
    public ResponseEntity<ManutencaoDto> buscarManutencaoPorId(@PathVariable Long id) {
        LOGGER.info("Buscando manutencao {}...", id);
        ManutencaoDto manutencao = manutencaoService.buscarPorId(id);
        if (manutencao == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(manutencao, HttpStatus.OK);
        }
    }

    @Operation(summary = "Inclusão de manutenção", description = "Inclui uma nova manutenção na base de dados")
    @PostMapping
    public ResponseEntity<ManutencaoDto> salvarManutencao(@RequestBody ManutencaoDto ativo) {
        LOGGER.info("Chamando salvar manutencao...");
        return new ResponseEntity<>(manutencaoService.salvar(ativo), HttpStatus.CREATED);
    }

    @Operation(summary = "Alteração de manutenção", description = "Sobrescreve os atributos de uma manutenção já cadastrado")
    @PutMapping("/{id}")
    public ResponseEntity<ManutencaoDto> alterar(@RequestBody ManutencaoDto manutencao, @PathVariable Long id) {
        LOGGER.info("Alterando manutencao {}...", id);
        ManutencaoDto manutencaoAlterada = manutencaoService.alterar(manutencao, id);
        if (manutencaoAlterada == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(manutencaoAlterada, HttpStatus.OK);
        }
    }

    @Operation(summary = "Remoção de manutenção", description = "Remove uma manutenção da base de dados")
    @DeleteMapping("/{id}")
    public ResponseEntity apagarManutencao(@PathVariable Long id) {
        LOGGER.info("Apagando manutencao {}...", id);
        boolean confirmacao = manutencaoService.apagar(id);
        if (confirmacao) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
