package com.mybank.facility.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mybank.facility.dto.LoanDto;
import com.mybank.facility.service.FacilityService;

@RequestMapping("/facility")
@RestController
@CrossOrigin(origins = { "*" }, allowCredentials = "false", allowedHeaders = { "*" }, exposedHeaders = {
        "*" }, maxAge = 60
                * 30, methods = { RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT })
public class FacilityController {

    @Autowired
    private FacilityService facilityService;

    @PostMapping
    public ResponseEntity<Void> createLoanForNonExistingFacility( @RequestBody LoanDto loanDto) {
        try {
            facilityService.createLoanForNonExistingFacility(loanDto);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PostMapping("/{facilityId}/loan")
    public ResponseEntity<Void> createLoansForExistingFacility(@PathVariable("facilityId") long facilityId,
            @RequestBody LoanDto loanDto) {
        
        try {
            facilityService.createLoanByFacilityId(facilityId, loanDto);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Void>(HttpStatus.CREATED);
        
    }

    @GetMapping("/applicant/{applicantId}/loans")
    public ResponseEntity<List<LoanDto>> getAllLoansForApplicant(@PathVariable("applicantId") long applicantId) {

        try {
            return new ResponseEntity<List<LoanDto>>(facilityService.getAllLoansByApplicantId(applicantId),
                    HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<List<LoanDto>>(HttpStatus.NO_CONTENT);
        }

    }
}
