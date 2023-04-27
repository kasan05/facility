package com.mybank.facility.modal;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.mybank.facility.enums.LoanType;


@Table(name = "loan")
@Entity
public class Loan implements Serializable {

    private static final long serialVersionUID = 5896349558988395500L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "loan_type")
    private LoanType loanType;

    @Column(name = "amount")
    private String amount;

    @Column(name = "paid_amount")
    private String paidAmount;

    @Column(name = "due_amount")
    private String dueAmount;

    @ManyToOne
    @JoinColumn(name = "facility_id", nullable = false)
    private CreditFacility creditFacility;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LoanType getLoanType() {
        return loanType;
    }

    public void setLoanType(LoanType loanType) {
        this.loanType = loanType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(String dueAmount) {
        this.dueAmount = dueAmount;
    }

    public CreditFacility getCreditFacility() {
        return creditFacility;
    }

    public void setCreditFacility(CreditFacility creditFacility) {
        this.creditFacility = creditFacility;
    }

}
