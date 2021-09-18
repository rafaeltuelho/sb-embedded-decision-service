package org.mortgages;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanApplication {
    private int id;
    private Applicant applicant;
    private boolean approved;
    private String explanation;
    private BigDecimal amount;
    private BigDecimal deposit;
}
