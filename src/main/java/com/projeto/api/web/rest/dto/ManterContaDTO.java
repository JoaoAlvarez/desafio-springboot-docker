package com.projeto.api.web.rest.dto;

import com.projeto.api.domain.enumeration.ContaSituacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ManterContaDTO {

    private Long id;

    private LocalDate dataVencimento;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    //@NotNull(message = "Campo data de pagamento é obrigatorio")
    private LocalDate dataPagamento;

    @NotNull(message = "Campo data de descricao é obrigatorio")
    private String descricao;

    private BigDecimal valor;
    private ContaSituacao situacao;

}
