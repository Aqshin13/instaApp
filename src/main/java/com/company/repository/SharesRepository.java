package com.company.repository;

import com.company.entities.Shares;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SharesRepository extends JpaRepository<Shares,Long> {
}
