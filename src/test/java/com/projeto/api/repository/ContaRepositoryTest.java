package com.projeto.api.repository;

import static org.assertj.core.api.Assertions.*;

import com.projeto.api.domain.Conta;
import com.projeto.api.domain.enumeration.ContaSituacao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ContaRepositoryTest {


    private static final BigDecimal BIG_DECIMAL_100 = BigDecimal.valueOf(100.00);
    private static final String CONTA_NOME = "a cool bill";
    private static final LocalDate DATA = LocalDate.of(2024, 6,10);

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @AfterEach
    public void afterEach() {
        contaRepository.deleteAll();
    }


    @Test
    public void testPersistence() {
        //given
        Conta conta = new Conta();
        conta.setSituacao(ContaSituacao.PENDENTE);
        conta.setValor(BIG_DECIMAL_100);
        conta.setDataVencimento(DATA);
        conta.setDescricao(CONTA_NOME);

        //when
        contaRepository.save(conta);

        //then
        assertThat(conta.getId()).isNotNull();
        Conta newConta = contaRepository.findById(conta.getId()).orElse(null);
        assertThat(newConta.getId()).isEqualTo((Long) 1L);
        assertThat(newConta.getDescricao()).isEqualTo(CONTA_NOME);
        assertThat(BIG_DECIMAL_100.compareTo(newConta.getValor())).isEqualTo(0);
        assertThat(newConta.getDataVencimento()).isEqualTo(DATA);
    }
}
