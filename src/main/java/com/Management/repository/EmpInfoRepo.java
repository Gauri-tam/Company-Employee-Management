package com.Management.repository;

import com.Management.entity.EmpInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpInfoRepo  extends JpaRepository<EmpInfo, Integer > {
}
