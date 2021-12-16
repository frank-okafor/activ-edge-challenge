package com.activedge.challenge.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import com.activedge.challenge.exception.ServiceException;
import com.activedge.challenge.model.Stock;
import com.activedge.challenge.pojos.ServiceResponse;
import com.activedge.challenge.pojos.StockRequestDto;
import com.activedge.challenge.repository.StockRepository;
import com.activedge.challenge.utils.TestHelper;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class StockServiceTest {

    @Mock
    private StockRepository stockRepository;

    private StockService service;

    @BeforeEach
    void setUp() throws Exception {
	service = new StockService(stockRepository);
    }

    @Test
    void testAddNewStock() {
	Stock stock = TestHelper.createStock();
	when(stockRepository.save(any(Stock.class))).thenReturn(stock);
	ServiceResponse response = service.addNewStock(TestHelper.stockRequest());
	assertThat(response.getMessage()).isEqualTo("Stock saved successfully");
    }

    @Test
    void testUpdateStockDetails() {
	Stock stock = TestHelper.createStock();
	when(stockRepository.findById(anyLong())).thenReturn(Optional.of(stock));
	when(stockRepository.save(any(Stock.class))).thenReturn(stock);
	ServiceResponse response = service.updateStockDetails(TestHelper.stockRequest());
	assertThat(response.getMessage()).isEqualTo("stock updated successfully");
    }

    @Test
    void testGetStockById() {
	Stock stock = TestHelper.createStock();
	when(stockRepository.findById(anyLong())).thenReturn(Optional.of(stock));
	ServiceResponse response = service.getStockById(3L);
	assertThat(response.getMessage()).isEqualTo("stock found successfully");
	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testDeleteStockById() {
	Stock stock = TestHelper.createStock();
	when(stockRepository.findById(anyLong())).thenReturn(Optional.of(stock));
	service.deleteStockById(3L);
	verify(stockRepository, times(1)).delete(any());
    }

    @Test
    void testGetAllStocks() {
	Stock stock = TestHelper.createStock();
	List<Stock> stocks = List.of(stock);
	Page<Stock> StockPage = new PageImpl<>(stocks);
	when(stockRepository.findAll(any(Pageable.class))).thenReturn(StockPage);
	ServiceResponse response = service.getAllStocks(1, 10);
	assertThat(response.getMessage()).isEqualTo("Stocks found successfully");
	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testStockExistError() {
	Stock stock = TestHelper.createStock();
	StockRequestDto dto = TestHelper.stockRequest();
	when(stockRepository.findByName(anyString())).thenReturn(Optional.of(stock));
	assertThatThrownBy(() -> service.addNewStock(dto)).isInstanceOf(ServiceException.class)
		.hasMessageContaining("stock with name " + dto.getName() + " already exists");
	verify(stockRepository, never()).save(any());
    }

    @Test
    void testNameBelongToAnotherStockError() {
	StockRequestDto dto = TestHelper.stockRequest();
	when(stockRepository.checkIfNameExists(anyString(), anyLong())).thenReturn(1);
	assertThatThrownBy(() -> service.updateStockDetails(dto)).isInstanceOf(ServiceException.class)
		.hasMessageContaining("stock with name " + dto.getName() + " already exists");
	verify(stockRepository, never()).save(any());
    }

}
