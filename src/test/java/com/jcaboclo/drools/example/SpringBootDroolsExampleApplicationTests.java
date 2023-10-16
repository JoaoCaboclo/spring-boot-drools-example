package com.jcaboclo.drools.example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.jcaboclo.drools.example.model.LoanApplicant;
import com.jcaboclo.drools.example.model.LoanRate;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SpringBootDroolsExampleApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private KieContainer kieContainer;
	@Test
	public void Checking_Credit_Score_less_than_650() {

		LoanRate loanRate = new LoanRate();
		LoanApplicant loanApplicant = new LoanApplicant();
		loanApplicant.setCreditScore(550);
		loanApplicant.setAnnualSalary(90000);
		loanApplicant.setExistingDebt(1000);
		loanApplicant.setLoanAmount(400);

		// Cria a sessão
		KieSession kieSession = kieContainer.newKieSession();

		// Passa o objeto de retorno como global
		kieSession.setGlobal("loanRate", loanRate);

		// Insert facts and execute rules
		kieSession.insert(loanApplicant);
		int firedRules = kieSession.fireAllRules();

		// Verify that the rule fired
		assertEquals(1, firedRules);

		// Check the result
		assertEquals("Approved", loanRate.getLoanStatus());
		assertEquals(7.25, loanRate.getLoanRate(), 0.01); // Using a delta for floating-point comparison

	}
}
