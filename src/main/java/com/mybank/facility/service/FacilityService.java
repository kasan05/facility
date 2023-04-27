package com.mybank.facility.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mybank.facility.dto.FacilityDto;
import com.mybank.facility.dto.LoanDto;
import com.mybank.facility.enums.LoanType;
import com.mybank.facility.modal.CreditFacility;
import com.mybank.facility.modal.Loan;
import com.mybank.facility.modal.User;
import com.mybank.facility.repository.FacilityRepository;
import com.mybank.facility.repository.LoanRepository;
import com.mybank.facility.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class FacilityService {

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoanRepository loanRepository;

    public CreditFacility save(long bankId, long applicantId) throws Exception {
        CreditFacility creditFacility = new CreditFacility();

        User bank = userRepository.findById(bankId).orElseThrow(() -> new Exception());

        creditFacility.setBank(bank);

        User applicant = userRepository.findById(applicantId).orElseThrow(() -> new Exception());

        creditFacility.setBank(bank);
        creditFacility.setApplicant(applicant);

        return facilityRepository.save(creditFacility);
    }

    @Transactional
    public void createLoanForNonExistingFacility(LoanDto loanDto) throws Exception {
        CreditFacility creditFacility = save(loanDto.getBankId(), loanDto.getApplicantId());
        Loan loan = new Loan();
        loan.setCreditFacility(creditFacility);
        loan.setAmount(loanDto.getAmount());
        loan.setLoanType(loanDto.getLoanType());
        loanRepository.save(loan);
    }

    @Transactional
    public void createLoanByFacilityId(long FacilityId, LoanDto loanDto) throws Exception {

        CreditFacility creditFacility = facilityRepository.findById(FacilityId).orElseThrow(() -> new Exception());

        Loan loan = new Loan();
        loan.setCreditFacility(creditFacility);
        loan.setAmount(loanDto.getAmount());
        loan.setDueAmount(loanDto.getDueAmount());
        loan.setLoanType(loanDto.getLoanType());
        loan.setPaidAmount(loanDto.getPaidAmount());

        loanRepository.save(loan);
    }

    public List<LoanDto> getAllLoansByApplicantId(long applicantId) throws Exception {

        User user = userRepository.findById(applicantId).orElseThrow(() -> new Exception());

        CreditFacility creditFacility = facilityRepository.findByApplicant(user).orElseThrow(() -> new Exception());

        List<Loan> loans = loanRepository.findByCreditFacility(creditFacility).orElseThrow(() -> new Exception());

        return loans.stream().map((loan) -> {
            LoanDto loanDto = new LoanDto();
            loanDto.setId(loan.getId());
            loanDto.setLoanType(loan.getLoanType());
            loanDto.setAmount(loan.getAmount());
            loanDto.setDueAmount(loan.getDueAmount());
            loanDto.setPaidAmount(loan.getPaidAmount());
            return loanDto;
        }).collect(Collectors.toList());
    }
}
