package com.mybank.facility.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mybank.facility.modal.CreditFacility;
import com.mybank.facility.modal.User;

@Repository
public interface FacilityRepository extends JpaRepository<CreditFacility,Long> {

    Optional<CreditFacility> findByApplicant(User user);
}
