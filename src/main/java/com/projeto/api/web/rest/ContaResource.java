package com.projeto.api.web.rest;

import com.projeto.api.domain.Conta;
import com.projeto.api.repository.ContaRepository;
import com.projeto.api.service.ContaService;
import com.projeto.api.web.rest.dto.ManterContaDTO;
import com.projeto.api.web.rest.dto.ViewContaDTO;
import com.projeto.api.web.rest.dto.mapper.ContaMapper;
import com.projeto.api.web.rest.util.HeaderUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.projeto.api.web.rest.util.PaginationUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/conta")
@Slf4j
public class ContaResource {
    private static final String ENTITY_NAME = "Conta";
    @Value("${spring.application.name}")
    private String applicationName;

    private final ContaService contaService;

    private final ContaRepository contaRepository;

    public ContaResource(ContaService contaService, ContaRepository contaRepository) {
        this.contaService = contaService;
        this.contaRepository = contaRepository;
    }


    /**
     * {@code POST /conta} : Criar nova conta para pagar.
     *
     * @param conta Nova conta para pagar.
     * @return {@link ResponseEntity} com status {@code 201 (Created)} e no body o a nova conta para pagar, ou status {@code 400 (Bad Request)} se a conta ja tiver ID.
     */
    @PostMapping
    public ResponseEntity<Conta> cadastrarConta(@Valid @RequestBody ManterContaDTO conta) throws URISyntaxException {
        log.debug("REST request para criar nova conta : {}", conta);
        if (conta.getId() != null) {
            return returnError("Uma nova conta não pode ter ID", "idexists");
        }
        Conta result = contaService.cadastrarConta(ContaMapper.fromDto(conta));
        return ResponseEntity
                .created(new URI("/api/v1/conta/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @GetMapping("/listar")
    public ResponseEntity<List> buscaPaginada(
            @RequestParam(required = false, name = "dataVencimento")
                @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dateVencimento,
            @RequestParam(required = false, name = "descricao") String descricao,
            @ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of Pragas");
        Page<ViewContaDTO> page = contaService.buscaPaginada(dateVencimento, descricao, null, pageable)
                .map(ContaMapper::toDto);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conta> buscarPorId(
            @PathVariable final Long id
    ) {
        return contaService.buscarPorId(id)
                .map(response -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /**
     * {@code PUT /conta/:id} : Atualizar uma conta existente.
     *
     * @param id o id da conta para atualizar.
     * @param conta ta conta para atualizar.
     @return {@link ResponseEntity} com status {@code 200 (Ok)} e no body o a nova conta para pagar,
     * ou status {@code 400 (Bad Request)} se a conta nao for valida,
      * ou status {@code 500 (Internal Server Error)} se a conta nao conseguiu ser atualizar.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Conta> atualizarConta(@PathVariable(value = "id", required = false) final Long id,
                                            @RequestBody ManterContaDTO conta) {
        log.info("REST request para atualizarConta : {}, {}", id, conta);
        if (conta.getId() == null) {
            return returnError("ID Invalido", "idnull");
        }
        if (!Objects.equals(id, conta.getId())) {
            return returnError("Id da entidade não confere com o ID para atualizar", "idinvalid");
        }
        if (!contaRepository.existsById(id)) {
            return returnError("Entidade não encontrada", "idnotfound");
        }

        return contaService.atualizarConta( ContaMapper.fromDto(conta))
                .map(response ->
                        ResponseEntity.ok()
                        .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, conta.getId().toString()))
                        .body(response))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private ResponseEntity<Conta> returnError(String message, String keyError) {
        return ResponseEntity
                .badRequest()
                .headers(HeaderUtil.createFailureAlert(applicationName, false, ENTITY_NAME, keyError, message))
                .build();
    }
}
