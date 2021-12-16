package com.activedge.challenge.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.activedge.challenge.exception.ServiceException;
import com.activedge.challenge.pojos.ServiceResponse;
import com.activedge.challenge.pojos.StockRequestDto;
import com.activedge.challenge.service.StockService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;

    @PostMapping
    @ApiOperation(value = "create a new user")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful", response = ServiceResponse.class),
	    @ApiResponse(code = 500, message = "internal error - critical!", response = ServiceException.class) })
    public ResponseEntity<ServiceResponse> addNewStock(@Valid @RequestBody StockRequestDto request) {
	ServiceResponse response = stockService.addNewStock(request);
	return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping
    @ApiOperation(value = "update existing stock details. The id in the stock request is compulsory")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful", response = ServiceResponse.class),
	    @ApiResponse(code = 500, message = "internal error - critical!", response = ServiceException.class) })
    public ResponseEntity<ServiceResponse> updateStockDetails(@Valid @RequestBody StockRequestDto request) {
	ServiceResponse response = stockService.updateStockDetails(request);
	return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get stock details by id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful", response = ServiceResponse.class),
	    @ApiResponse(code = 500, message = "internal error - critical!", response = ServiceException.class) })
    public ResponseEntity<ServiceResponse> getStockById(@PathVariable("id") Long id) {
	ServiceResponse response = stockService.getStockById(id);
	return new ResponseEntity<>(response, response.getStatus());
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete stock by id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful", response = ServiceResponse.class),
	    @ApiResponse(code = 500, message = "internal error - critical!", response = ServiceException.class) })
    public ResponseEntity<ServiceResponse> deleteStockById(@PathVariable("id") Long id) {
	ServiceResponse response = stockService.deleteStockById(id);
	return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping
    @ApiOperation(value = "get all stock details", notes = "endpoint is paginated with a default page number of 1 and page size of 10.")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful", response = ServiceResponse.class),
	    @ApiResponse(code = 500, message = "internal error - critical!", response = ServiceException.class) })
    public ResponseEntity<ServiceResponse> getAllStocks(
	    @RequestParam(value = "pageNumber", required = true, defaultValue = "1") Integer pageNumber,
	    @RequestParam(value = "pageSize", required = true, defaultValue = "10") Integer pageSize) {
	ServiceResponse response = stockService.getAllStocks(pageNumber, pageSize);
	return new ResponseEntity<>(response, response.getStatus());
    }
}
