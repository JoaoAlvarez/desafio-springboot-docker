package com.projeto.api.web.rest.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
public class ViewValorTotalPeriodoDTO {
    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private BigDecimal valorTotal;
}

