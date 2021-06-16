package com.challenge.scraper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.scraper.model.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long>{

}

