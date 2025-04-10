package com.devpass.domain.resume.document;

import com.devpass.domain.resume.dto.response.ResumeResponseDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Document(collection = "resumes")
@Getter
@Setter
@NoArgsConstructor
public class ResumeDocument {
    @Id
    private String id;

    // 생성된 이력서를 객체로 저장
    private ResumeResponseDTO resume;

}
