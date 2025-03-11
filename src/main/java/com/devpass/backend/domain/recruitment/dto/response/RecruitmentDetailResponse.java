package com.devpass.backend.domain.recruitment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RecruitmentDetailResponse {

      private Long recruitmentId;

      private String companyName;

      private String position;

      private String location;

      private String career;

      private String mainTask;

      private String qualification;

      private String preferred;

      private String benefit;

      private String deadline;

      private String imageUrl;
}
