package com.devpass.domain.resume.repository;

import com.devpass.domain.resume.document.ResumeDocument;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepository extends MongoRepository<ResumeDocument, String> {
    List<ResumeDocument> findAllByUserId(Long userId);
}
