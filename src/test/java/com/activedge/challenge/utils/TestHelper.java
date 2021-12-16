package com.activedge.challenge.utils;

import java.util.Date;

import com.activedge.challenge.model.Stock;
import com.activedge.challenge.pojos.StockRequestDto;

public class TestHelper {

    public static Stock createStock() {
	return Stock.builder().amount(40.0).name("Tesla").createdDate(new Date()).updatedDate(new Date()).build();
    }

    public static StockRequestDto stockRequest() {
	return StockRequestDto.builder().id(2l).amount(34.7).name("Facebook").build();
    }

}
