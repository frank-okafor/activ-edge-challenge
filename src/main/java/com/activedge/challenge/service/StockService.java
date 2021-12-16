package com.activedge.challenge.service;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.activedge.challenge.exception.ServiceException;
import com.activedge.challenge.model.Stock;
import com.activedge.challenge.pojos.ServiceResponse;
import com.activedge.challenge.pojos.StockRequestDto;
import com.activedge.challenge.repository.StockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;

    public ServiceResponse addNewStock(StockRequestDto request) {
	if (request.getAmount() <= 0) {
	    throw new ServiceException(HttpStatus.BAD_REQUEST, "stock amount must be greater than zero (0)");
	}
	if (stockRepository.findByName(request.getName()).isPresent()) {
	    throw new ServiceException(HttpStatus.BAD_REQUEST,
		    "stock with name " + request.getName() + " already exists");
	}
	return ServiceResponse
		.builder().message("Stock saved successfully").data(stockRepository.save(Stock.builder()
			.amount(request.getAmount()).name(request.getName()).createdDate(new Date()).build()))
		.status(HttpStatus.OK).build();
    }

    public ServiceResponse updateStockDetails(StockRequestDto request) {
	if (request.getId() == null || request.getId() <= 0) {
	    throw new ServiceException(HttpStatus.BAD_REQUEST, "provide valid stock id to be updated");
	}
	if (stockRepository.checkIfNameExists(request.getName(), request.getId()) > 0) {
	    throw new ServiceException(HttpStatus.BAD_REQUEST,
		    "stock with name " + request.getName() + " already exists");
	}
	if (request.getAmount() <= 0) {
	    throw new ServiceException(HttpStatus.BAD_REQUEST, "stock amount must be greater than zero (0)");
	}
	return stockRepository.findById(request.getId()).map(stock -> {
	    stock.setAmount(request.getAmount());
	    stock.setName(request.getName());
	    stock.setUpdatedDate(new Date());
	    return ServiceResponse.builder().message("stock updated successfully").data(stockRepository.save(stock))
		    .status(HttpStatus.OK).build();
	}).orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND,
		"stock with id " + request.getId() + " does not exist"));
    }

    public ServiceResponse getStockById(Long id) {
	return stockRepository.findById(id).map(stock -> {
	    return ServiceResponse.builder().message("stock found successfully").data(stock).status(HttpStatus.OK)
		    .build();
	}).orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND, "stock with id " + id + " does not exist"));
    }

    public ServiceResponse deleteStockById(Long id) {
	return stockRepository.findById(id).map(stock -> {
	    stockRepository.delete(stock);
	    return ServiceResponse.builder().message("stock deleted successfully").status(HttpStatus.OK).build();
	}).orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND, "stock with id " + id + " does not exist"));
    }

    public ServiceResponse getAllStocks(Integer pageNumber, Integer pageSize) {
	Sort sort = Sort.by(Sort.Order.desc("createdDate").ignoreCase());
	pageNumber = pageNumber == 0 ? 1 : pageNumber;
	pageSize = pageSize == 0 ? 10 : pageSize;
	Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
	Page<Stock> stocks = stockRepository.findAll(pageable);
	return ServiceResponse.builder().message("Stocks found successfully").data(stocks).status(HttpStatus.OK)
		.build();
    }

}
