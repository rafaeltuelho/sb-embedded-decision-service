package org.mortgages;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class LoanApplication {
    private Applicant applicant;
    private Boolean approved;
    private String explanation;
    private BigDecimal amount;
    private BigDecimal deposit;
}
