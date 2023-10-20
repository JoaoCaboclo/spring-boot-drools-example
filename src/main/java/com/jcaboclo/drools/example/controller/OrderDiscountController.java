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

    /*

        Forma de chamada pelo Postman - definir o tipo de arquivo da regra: "DRL" ou "XLSX"
        customerType pode ser: "LOYAL" ou "NEW"


        http://localhost:8080/get-discount

        {
            "customerNumber": "1235",
                "age": 45,
                "amount": 500,
                "customerType": "NEW",
                "fileRuleType": "DRL"

        }

        ou

        {
            "customerNumber": "1235",
                "age": 45,
                "amount": 500,
                "customerType": "NEW",
                "fileRuleType": "XLSX"

        }
     */

}

