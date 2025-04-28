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
import com.devpass.domain.resume.util.ResumePrompt;
import com.devpass.domain.user.entity.User;
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

	/**
	 * 이력서 생성 및 저장
	 *
	 * @param providerId    OAuth 로그인 시 발급된 providerId (실제 GitHub 사용자 ID)
	 * @param devExpId      DevExperience PK
	 * @param recStackId    RecruitmentStack PK
	 * @param includeGitHub true면 GitHub 프로필·핀된 레포 정보를, false면 빈 컨텍스트로 생성
	 */
	@Transactional
	public ResumeDocument generateAndSaveResume(
			String providerId,
			Long devExpId,
			Long recStackId,
			boolean includeGitHub
	) {
		// 1) providerId 로 User 조회
		User user = userRepository.findByProviderId(providerId)
				.orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

		// 2) Dev 경험 집계
		DevExperienceAggregateResponseDTO aggregate =
				devExperienceAggregateService.getAggregateByDevExperienceId(user.getId(), devExpId);

		// 3) 채용 공고 조회
		RecruitmentDetailResponseDTO recruitment =
				recruitmentService.getRecruitmentById(recStackId);
		if (recruitment == null) {
			throw new GeneralException(ErrorStatus.NOT_FOUND);
		}

		// 4) GitHub 컨텍스트 구성 (선택적으로)
		String githubContext = "";
		if (includeGitHub) {
			GitHubDetailResponseDTO info =
					githubInfoService.getGitHubDetails(user.getGithubOAuthToken(), 6);

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

		// 5) 프롬프트 생성
		String prompt = buildPrompt(aggregate, recruitment, githubContext);

		// 6) Function Calling 으로 GPT 호출 → 순수 JSON 인자 받아오기
		String argsJson = openAIConfig.callGenerateResumeFunction(prompt);

		// 7) JSON → DTO 파싱
		ResumeResponseDTO dto;
		try {
			dto = objectMapper.readValue(argsJson, ResumeResponseDTO.class);
		} catch (Exception e) {
			throw new GeneralException(ErrorStatus.GPT_RESPONSE_PARSE_ERROR);
		}

		// 8) 사용자 정보 채워 최종 DTO 생성
		ResumeResponseDTO filled = ResumeResponseDTO.builder()
				.name(user.getName())
				.title("") // 필요 시 설정
				.phone(user.getPhone())
				.email(user.getEmail())
				.github(user.getGithubLogin())
				.blog(user.getBlogUrl())
				.summary(dto.getSummary())
				.experience(dto.getExperience())
				.activities(dto.getActivities())
				.skills(dto.getSkills())
				.education(dto.getEducation())
				.build();

		// 9) 저장 후 반환
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

			ResumePromptDTO p = ResumePromptDTO.builder()
					.header(ResumePrompt.HEADER)
					.jsonTemplate(ResumePrompt.JSON_TEMPLATE)
					.notes(ResumePrompt.NOTES
							+ "\n\n## 채용 공고\n" + recJson
							+ "\n\n## GitHub Context\n" + githubContext)
					.aggregateData(aggJson)
					.build();

			return objectMapper.writeValueAsString(p);
		} catch (Exception e) {
			throw new GeneralException(ErrorStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Transactional(readOnly = true)
	public List<ResumeDocument> getResumesByUserId(Long userId) {
		return resumePersistenceService.findByUserId(userId);
	}
}
