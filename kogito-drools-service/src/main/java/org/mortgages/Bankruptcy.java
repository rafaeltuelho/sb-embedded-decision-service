package org.mortgages;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Bankruptcy {
    private String name;
    private Integer yearOfOccurrence;
    private BigDecimal amountOwed;
}
