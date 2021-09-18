package org.mortgages;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class LombokTest {

    @Test
    public void test(){
        AllAmounts aa = new AllAmounts(BigDecimal.valueOf(10_000L));
        System.out.println(aa);

        LoanApplication la = new LoanApplication();
        System.out.println(la);
    }
}
