package com.devpass.domain.resume.service;

import com.devpass.domain.devexperience.dto.response.DevExperienceAggregateResponseDTO;
import com.devpass.domain.devexperience.service.DevExperienceAggregateService;
import com.devpass.domain.githubinfo.dto.GitHubDetailResponseDTO;
import com.devpass.domain.githubinfo.dto.PinnedRepo;
import com.devpass.domain.githubinfo.service.GitHubInfoService;
import com.devpass.domain.recruitment.dto.response.RecruitmentDetailResponseDTO;
import com.devpass.domain.recruitment.service.RecruitmentService;
import com.devpass.domain.resume.document.ResumeDocument;
import com.devpass.domain.resume.dto.ResumePromptDTO;
import com.devpass.domain.resume.dto.response.ResumeResponseDTO;
import com.devpass.domain.resume.repository.ResumeRepository;
import com.devpass.domain.user.entity.User;
import com.devpass.domain.resume.util.ResumePrompt;
import com.devpass.domain.user.repository.UserRepository;
import com.devpass.global.config.OpenAIConfig;
import com.devpass.global.payload.apicode.ErrorStatus;
import com.devpass.global.payload.error.exception.GeneralException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ResumeService {

	private final DevExperienceAggregateService devExperienceAggregateService;
	private final RecruitmentService recruitmentService;
	private final GitHubInfoService githubInfoService;
	private final OpenAIConfig openAIConfig;
	private final ObjectMapper objectMapper;
	private final ResumePersistenceService resumePersistenceService;
	private final UserRepository userRepository;
  	private final ResumeRepository resumeRepository;

	@Transactional
	public ResumeDocument generateAndSaveResume(
			String githubToken,
			String providerId,
			Long devExpId,
			Long recStackId,
			boolean includeGitHub
	) {
		User user = userRepository.findByProviderId(providerId)
				.orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

		DevExperienceAggregateResponseDTO aggregate =
				devExperienceAggregateService.getAggregateByDevExperienceId(user.getId(), devExpId);

		RecruitmentDetailResponseDTO recruitment =
				recruitmentService.getRecruitmentById(recStackId);
		if (recruitment == null) {
			throw new GeneralException(ErrorStatus.NOT_FOUND);
		}
		GitHubDetailResponseDTO info = null;

		String githubContext = "";
		if (includeGitHub) {
			 info = githubInfoService.getGitHubDetails(githubToken, 6);

			StringBuilder ctx = new StringBuilder();
			ctx.append("## Profile README\n")
					.append(info.getProfileReadme()).append("\n\n")
					.append("## Pinned Repositories\n");

			List<PinnedRepo> pinned = info.getPinnedRepos();
			for (PinnedRepo pr : pinned) {
				ctx.append("### ").append(pr.getName()).append("\n")
						.append(pr.getDescription()).append("\n")
						.append("README:\n").append(pr.getReadme()).append("\n---\n");
			}
			githubContext = ctx.toString();
		}

		String prompt = buildPrompt(aggregate, recruitment, githubContext);

		String argsJson = openAIConfig.callGenerateResumeFunction(prompt);

		ResumeResponseDTO dto;
		try {
			dto = objectMapper.readValue(argsJson, ResumeResponseDTO.class);
		} catch (Exception e) {
			throw new GeneralException(ErrorStatus.GPT_RESPONSE_PARSE_ERROR);
		}

		ResumeResponseDTO filled = ResumeResponseDTO.builder()
				.name(user.getName())
				.title("")
				.phone(user.getPhone())
				.email(user.getEmail())
				.github(info.getProfileUrl())
				.blog(user.getBlogUrl())
				.summary(dto.getSummary())
				.experience(dto.getExperience())
				.activities(dto.getActivities())
				.skills(dto.getSkills())
				.education(dto.getEducation())
				.build();

		return resumePersistenceService.saveResume(filled, user.getId());
	}

	@Transactional(readOnly = true)
	public ResumeDocument getResumeById(String resumeId) {
		return resumePersistenceService.findById(resumeId)
				.orElseThrow(() -> new GeneralException(ErrorStatus.RESUME_NOT_FOUND));
	}

	private String buildPrompt(Object agg,
							   RecruitmentDetailResponseDTO rec,
							   String githubContext) {
		try {
			String aggJson = objectMapper.writeValueAsString(agg);
			String recJson = objectMapper.writeValueAsString(rec);

			String qualification = rec.getQualification();
			String preferred = rec.getPreferred();
			String benefit = rec.getBenefit();

			StringBuilder notes = new StringBuilder(ResumePrompt.NOTES)
					.append("\n\n## 채용 공고 정보\n")
					.append("요구 자격:\n").append(qualification).append("\n\n")
					.append("우대 사항:\n").append(preferred).append("\n\n")
					.append("복리후생:\n").append(benefit).append("\n\n")
					.append("전체 공고 JSON:\n").append(recJson)
					.append("\n\n## GitHub Context\n").append(githubContext);

			ResumePromptDTO p = ResumePromptDTO.builder()
					.header(ResumePrompt.HEADER)
					.jsonTemplate(ResumePrompt.JSON_TEMPLATE)
					.notes(notes.toString())
					.aggregateData(aggJson)
					.build();

			return objectMapper.writeValueAsString(p);
		} catch (Exception e) {
			throw new GeneralException(ErrorStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Transactional(readOnly = true)
    public List<ResumeDocument> getResumesByUserId(Long userId) {
        return resumeRepository.findAllByUserId(userId);
    }
}
