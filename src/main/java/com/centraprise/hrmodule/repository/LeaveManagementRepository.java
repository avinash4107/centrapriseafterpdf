package com.centraprise.hrmodule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.centraprise.hrmodule.entity.ManageLeave;

public interface LeaveManagementRepository extends JpaRepository<ManageLeave, Integer> {

	ManageLeave findByEmployeeNumberAndYear(int employeeNumber, int year);

	List<ManageLeave> findByYear(int year);

}
