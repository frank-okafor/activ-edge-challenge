package com.activedge.challenge.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import com.activedge.challenge.model.Stock;
import com.activedge.challenge.utils.TestHelper;

@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
@TestInstance(Lifecycle.PER_CLASS)
class StockRepositoryTest {
    private @Autowired StockRepository stockRepository;

    @BeforeEach
    void setUp() throws Exception {
	stockRepository.deleteAll();
    }

    @Test
    void testFindByName() {
	Stock stock = TestHelper.createStock();
	stockRepository.save(stock);
	assertThat(stockRepository.findByName(stock.getName()).isPresent()).isTrue();
    }

    @Test
    void testCheckIfNameExists() {
	Stock stock = TestHelper.createStock();
	stockRepository.save(stock);
	assertThat(stockRepository.checkIfNameExists(stock.getName(), 7L) > 0).isTrue();
    }

}
