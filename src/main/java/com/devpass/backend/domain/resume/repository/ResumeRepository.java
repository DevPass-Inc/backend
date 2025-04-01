package com.devpass.backend.domain.resume.repository;

import com.devpass.backend.domain.resume.document.ResumeDocument;
import com.devpass.backend.domain.resume.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepository extends MongoRepository<ResumeDocument, String> {
}
