package com.eypery.lts.aio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eypery.lts.aio.entities.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

}
