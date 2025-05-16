package com.devpass.domain.recruitment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RecruitmentDetailResponseDTO {

      private Long recruitmentId;

      private String companyName;

      private String positionName;

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
