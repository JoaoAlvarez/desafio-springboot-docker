package com.projeto.api.web.rest.dto;

import com.projeto.api.domain.enumeration.ContaSituacao;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ViewContaDTO {
    private Long id;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
    private BigDecimal valor;
    private String descricao;
    private ContaSituacao situacao;

    public String getSitaucao() {
        return situacao.name();
    }
}
