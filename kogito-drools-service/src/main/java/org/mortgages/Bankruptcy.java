package org.mortgages;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bankruptcy {
    private String name;
    private int yearOfOccurrence;
    private BigDecimal amountOwed;
}
