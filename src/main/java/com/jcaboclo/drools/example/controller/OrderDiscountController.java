package com.jcaboclo.drools.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jcaboclo.drools.example.model.OrderDiscount;
import com.jcaboclo.drools.example.model.OrderRequest;
import com.jcaboclo.drools.example.service.OrderDiscountService;

@RestController
public class OrderDiscountController {

    @Autowired
    private OrderDiscountService orderDiscountService;
    @PostMapping("/get-discount")
    public ResponseEntity<OrderDiscount> getDiscount(@RequestBody OrderRequest orderRequest) {
        OrderDiscount discount = orderDiscountService.getDiscount(orderRequest);
        return new ResponseEntity<>(discount, HttpStatus.OK);
    }
}



/*
       {
           "customerNumber": "1235",
           "age": 8,
           "amount": 50000,
           "customerType": "LOYAL"
        }

        {
           "customerNumber": "1235",
           "age": 65,
           "amount": 500,
           "customerType": "NEW"
        }
*/
