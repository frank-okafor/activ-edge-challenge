package com.activedge.challenge.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.activedge.challenge.model.Stock;
import com.activedge.challenge.pojos.StockRequestDto;
import com.activedge.challenge.repository.StockRepository;
import com.activedge.challenge.utils.TestHelper;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
class StockControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private @Autowired StockRepository stockRepository;

    @AfterEach
    void tearDown() throws Exception {
	stockRepository.deleteAll();
    }

    @Test
    void testAddNewStock() throws Exception {
	StockRequestDto dto = TestHelper.stockRequest();
	mockMvc.perform(post("/api/stocks").contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(dto))).andExpect(status().isOk());
	assertThat(stockRepository.findByName(dto.getName()).isPresent()).isEqualTo(true);
    }

    @Test
    void testUpdateStockDetails() throws Exception {
	Stock stock = TestHelper.createStock();
	stock = stockRepository.save(stock);
	StockRequestDto dto = TestHelper.stockRequest();
	dto.setId(stock.getId());
	mockMvc.perform(put("/api/stocks").contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(dto))).andExpect(status().isOk());
	assertThat(stockRepository.findByName(dto.getName()).isPresent()).isEqualTo(true);
    }

    @Test
    void testGetStockById() throws Exception {
	Stock stock = TestHelper.createStock();
	stock = stockRepository.save(stock);
	mockMvc.perform(get("/api/stocks/" + stock.getId()).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());

    }

    @Test
    void testDeleteStockById() throws Exception {
	Stock stock = TestHelper.createStock();
	stock = stockRepository.save(stock);
	mockMvc.perform(delete("/api/stocks/" + stock.getId()).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
    }

    @Test
    void testGetAllStocks() throws Exception {
	Stock stock = TestHelper.createStock();
	stock = stockRepository.save(stock);
	mockMvc.perform(get("/api/stocks").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	boolean result = stockRepository.findAll().isEmpty();
	assertThat(result).isEqualTo(false);
    }

}
