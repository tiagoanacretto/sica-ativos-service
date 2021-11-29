package br.com.sica.sicaativosservice.controllers;

import br.com.sica.sicaativosservice.dtos.ativos.AtivoDto;
import br.com.sica.sicaativosservice.dtos.ativos.ListagemAtivo;
import br.com.sica.sicaativosservice.dtos.manutencoes.ManutencaoDto;
import br.com.sica.sicaativosservice.service.ManutencaoService;
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
@RequestMapping("/api/manutencoes")
public class ManutencaoController {

    private final Logger LOGGER = LoggerFactory.getLogger(ManutencaoController.class);

    @Autowired
    private ManutencaoService manutencaoService;

    @GetMapping(value = "/disponiveis/{categoria}")
    public ResponseEntity<List<String>> listarManutencoesDisponiveis(@PathVariable String categoria) {
        LOGGER.info("Chamando listagem de datas de manutencoes disponiveis...");
        return new ResponseEntity<>(manutencaoService.listarManutencoesDisponiveis(categoria), HttpStatus.OK);
    }

//    @GetMapping
//    public ResponseEntity<List<ManutencaoDto>> listarTodasManutencoes() {
//        LOGGER.info("Chamando listagem de manutencoes...");
//        return new ResponseEntity<>(manutencaoService.listarTodas(), HttpStatus.OK);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ManutencaoDto> buscarManutencaoPorId(@PathVariable Long id) {
//        LOGGER.info("Buscando manutencao {}...", id);
//        ManutencaoDto manutencao = manutencaoService.buscarPorId(id);
//        if (manutencao == null) {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        } else {
//            return new ResponseEntity<>(manutencao, HttpStatus.OK);
//        }
//    }
//
//    @PostMapping
//    public ResponseEntity<ManutencaoDto> salvarManutencao(@RequestBody ManutencaoDto ativo) {
//        LOGGER.info("Chamando salvar manutencao...");
//        return new ResponseEntity<>(manutencaoService.salvar(ativo), HttpStatus.CREATED);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ManutencaoDto> alterarAtivo(@RequestBody ManutencaoDto ativo, @PathVariable Long id) {
//        LOGGER.info("Alterando ativo {}...", id);
//        AtivoDto ativoAlterado = manutencaoService.alterarAtivo(ativo, id);
//        if (ativoAlterado == null) {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        } else {
//            return new ResponseEntity<>(ativoAlterado, HttpStatus.OK);
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity apagarManutencao(@PathVariable Long id) {
//        LOGGER.info("Apagando ativo {}...", id);
//        boolean confirmacao = manutencaoService.apagarAtivo(id);
//        if (confirmacao) {
//            return new ResponseEntity<>(null, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }
}
