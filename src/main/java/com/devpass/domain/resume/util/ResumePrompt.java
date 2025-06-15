package com.devpass.domain.resume.util;

public class ResumePrompt {

    public static final String HEADER = """
        아래의 개발 경험 데이터, 채용 공고 정보, (선택 시) GitHub 프로필·프로젝트 컨텍스트(프로필 README, Pinned Repos README, 커밋 히스토리 등)를
        모두 참고하여, 수치와 구체적인 성과를 최대한 반영한 상세하고 설득력 있는 이력서를 생성해주세요.
        오직 제공된 정보만 사용하고, 절대로 임의로 내용을 추가하지 마세요.
        GitHub 컨텍스트가 주어졌다면, 아래 지침을 반드시 따르세요.
        다음 JSON 포맷을 정확히 준수해야 합니다.
        """;

    public static final String JSON_TEMPLATE = """
            {
              "summary": [
                "<채용 공고의 qualification, main_task, preferred를 요약·강조한 문장 3~5개>"
              ],
              "experience": [
                {
                  "project": "<프로젝트 명>",
                  "summary": "<해당 프로젝트의 우대사항(preferred)과 연관된 기여 내용 요약>",
                  "position": "<담당 역할>",
                  "duration": "<YYYY.MM ~ YYYY.MM>",
                  "skills": "<주요 기술 스택 리스트>",
                  "description": [
                    "<구체적인 성과 #1 (숫자, 지표 포함)>",
                    "<구체적인 성과 #2>",
                    "..."
                  ]
                }
              ],
              "activities": [
                {
                  "activity": "<인턴십/활동 명칭 및 회사>",
                  "dates": "<YYYY.MM ~ YYYY.MM>",
                  "details": [
                    "<구체적인 역할 및 성과>"
                  ]
                }
              ],
              "skills": [
                {
                  "skill": "<기술 스택>",
                  "level": "<초급/중급/고급/전문가>"
                }
              ],
              "education": [
                {
                  "degree": "<학위 및 전공>",
                  "institution": "<학교명>",
                  "period": "<YYYY.MM ~ YYYY.MM>",
                  "details": "<GPA, 수상, 동아리 활동 등 추가 사항>"
                }
              ]
            }
            """;

    public static final String NOTES = """
        주의사항:
        - includeGitHub=true일 때, GitHub 컨텍스트는 *선별적*으로 사용해야 합니다.
        - 채용공고의 '우대사항', '요구자격', '복리후생' 키워드와 **매칭되는** 레포만 experience에 반영하세요.
          예: 공고에 “AWS”, “Neo4j”가 있으면, 해당 기술을 사용한 핀된 레포만 선택.
        - Profile README 배지·기술은 summary에 간략히 언급하되, 구체적 수치는 experience.description으로.
        - 선택된 레포 각각에 대해:
            • project = repo.name  
            • summary = repo.description + " (Tech: …)"  
            • skills = README에서 드러난 핵심 스택  
            • description = 해당 레포에서 공고 키워드와 매치되는 성과·기능·수치 나열  
        - 공고와 전혀 연관 없는 레포는 무시합니다.
        - 절대 임의 생성 금지. 제공된 컨텍스트만 활용하세요.
        """;

}
