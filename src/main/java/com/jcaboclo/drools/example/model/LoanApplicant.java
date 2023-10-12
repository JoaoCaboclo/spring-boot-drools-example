package com.jcaboclo.drools.example.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanApplicant {

    private String name;
    private int age;
    private int creditScore;
    private long annualSalary;
    private long existingDebt;
    private long loanAmount;
}
