package org.mortgages;

import java.math.BigDecimal;

import org.kie.kogito.rules.DataSource;
import org.kie.kogito.rules.DataStore;
import org.kie.kogito.rules.RuleUnitData;

public class MortgageRules implements RuleUnitData {
    private DataStore<Bankruptcy> bankruptcy = DataSource.createStore();
    private DataStore<Applicant> applicant = DataSource.createStore();
    private DataStore<LoanApplication> loanApplications = DataSource.createStore();
    private DataStore<AllAmounts> allAmounts = DataSource.createStore();

    private BigDecimal maxAmount;
    
    public MortgageRules() {
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }

    public DataStore<Bankruptcy> getBankruptcy() {
        return bankruptcy;
    }
    public void setBankruptcy(DataStore<Bankruptcy> bankruptcy) {
        this.bankruptcy = bankruptcy;
    }
    public DataStore<Applicant> getApplicant() {
        return applicant;
    }
    public void setApplicant(DataStore<Applicant> applicant) {
        this.applicant = applicant;
    }
    public DataStore<LoanApplication> getLoanApplications() {
        return loanApplications;
    }
    public void setLoanApplications(DataStore<LoanApplication> application) {
        this.loanApplications = application;
    }

    public DataStore<AllAmounts> getAllAmounts() {
        return allAmounts;
    }

    public void setAllAmounts(DataStore<AllAmounts> allAmounts) {
        this.allAmounts = allAmounts;
    }
    
}
