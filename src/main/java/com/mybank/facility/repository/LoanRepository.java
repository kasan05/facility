package com.mybank.facility.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mybank.facility.modal.CreditFacility;
import com.mybank.facility.modal.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    Optional<List<Loan>> findByCreditFacility(CreditFacility creditFacility);
}
