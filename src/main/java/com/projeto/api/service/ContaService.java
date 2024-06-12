package com.projeto.api.service;

import com.projeto.api.domain.Conta;
import com.projeto.api.domain.enumeration.ContaSituacao;
import com.projeto.api.repository.ContaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    public Optional<Conta> buscarPorId(Long id){
        return contaRepository.findById(id);
    }

    public Conta cadastrarConta(Conta dto){
        log.info("Nova conta para ser cadastrada: {}",dto);
        dto.setSituacao(ContaSituacao.PENDENTE);
        return contaRepository.save(dto);
    }

    public Optional<Conta> atualizarConta(Conta dto){
        log.info("Atualizar conta para : {}",dto);
        return contaRepository.findById(dto.getId())
                .map( fromDataBase -> {
                    if(dto.getDescricao() != null){
                        fromDataBase.setDescricao(dto.getDescricao());
                    }
                    if(dto.getValor()!= null){
                        fromDataBase.setValor(dto.getValor());
                    }
                    if(dto.getDataPagamento() != null){
                        fromDataBase.setDataPagamento(dto.getDataPagamento());
                    }
                    if(dto.getDataVencimento()!= null){
                        fromDataBase.setDataVencimento(dto.getDataVencimento());
                    }
                    if(dto.getSituacao() != null){
                        fromDataBase.setSituacao(dto.getSituacao());
                    }
                    return fromDataBase;
                }).map(contaRepository::save);
    }

    public Page<Conta> buscaPaginada(LocalDate dateVencimento, String descricao, ContaSituacao status, Pageable pageable) {
        if(status == null){
            status = ContaSituacao.PENDENTE;
        }
        log.info("Listar contas com os filtros: Data={}, descricao={}, status={}",dateVencimento,descricao,status);
        if(dateVencimento!= null){
            return contaRepository.findByFilters(status, dateToString(dateVencimento), descricao,  pageable);
        }
        return contaRepository.findByFilters(status, descricao,  pageable);
    }

    private String dateToString(LocalDate date){
        if(date == null){
            return null;
        }
        return date != null ? date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
    }
}
