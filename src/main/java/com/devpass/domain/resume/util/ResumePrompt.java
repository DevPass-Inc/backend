package com.devpass.domain.resume.util;

public class ResumePrompt {

    public static final String HEADER = """
            아래의 개발 경험 데이터, 채용 공고 정보, (선택 시) GitHub 프로필·프로젝트 컨텍스트(프로필 README, Pinned Repos README, 커밋 히스토리 등)를
            모두 참고하여, 수치와 구체적인 성과를 최대한 반영한 상세하고 설득력 있는 이력서를 생성해주세요.
            오직 제공된 정보만 사용하고, 절대로 임의로 내용을 추가하지 마세요.
            다음 JSON 포맷을 정확히 준수해야 합니다.
            """;

    public static final String JSON_TEMPLATE = """
            {
              "summary": [
                "<핵심 성과 및 전문성을 반영한 문장 3~5개>"
              ],
              "experience": [
                {
                  "project": "<프로젝트 명>",
                  "summary": "<프로젝트 개요 및 기여 내용>",
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
            - 'summary'는 채용 공고에 맞춰 필요한 역량과 결과를 강조하는 문장으로 구성하세요.
            - 'experience.description'과 'activities.details'에는 반드시 수치(%, 건수, 사용자 수 등)나 결과(예: 처리량 증가, 오류율 감소 등)를 포함해 구체화해야 합니다.
            - 'skills'는 범주별(언어, 프레임워크, 도구 등)로 그룹화하고, 각 스킬의 숙련도를 레벨로 표기하세요.
            - 'education'에는 학위·전공·기관·기간을 명확히 기재하고, 관련 프로젝트나 수상이 있다면 'details'에 추가하세요.
            - GitHub 컨텍스트를 포함할 때는:
              • 프로필 README에서 강조된 기술이나 주요 성과를 summary나 experience에 반영  
              • 핀된 레포 README 텍스트로부터 프로젝트 목적, 핵심 기능, 사용 기술, 주요 커밋 내용 등을 experience.description에 녹여내세요.  
            - 반드시 제공된 데이터만을 사용하고, 임의의 가공이나 추가는 금지됩니다.
            """;
}
