package com.projeto.api.web.rest.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ViewValorTotalPeriodoDTO {
    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private BigDecimal valorTotal;
}

