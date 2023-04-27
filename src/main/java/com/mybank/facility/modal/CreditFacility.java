package com.mybank.facility.modal;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Table(name = "credit_facility")
@Entity
public class CreditFacility implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    public long id;

    @OneToOne
    @JoinColumn(name = "applicant_id", referencedColumnName = "id")
    private User applicant;

    @OneToOne
    @JoinColumn(name = "bank_id", referencedColumnName = "id")
    private User bank;

    @OneToMany(mappedBy = "creditFacility")
    private Set<Loan> loans;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getApplicant() {
        return applicant;
    }

    public void setApplicant(User applicant) {
        this.applicant = applicant;
    }

    public User getBank() {
        return bank;
    }

    public void setBank(User bank) {
        this.bank = bank;
    }

    public Set<Loan> getLoans() {
        return loans;
    }

    public void setLoans(Set<Loan> loans) {
        this.loans = loans;
    }

}
