package com.devpass.domain.resume.util;

public class ResumePrompt {

    public static final String HEADER = """
            아래의 개발 경험 데이터, 채용 공고 정보(qualification, main_task, preferred 포함), 
            (선택 시) GitHub 컨텍스트를 모두 참고하여,
            채용 공고의 **우대사항(preferred)** 과 **자격요건(qualification)**, **주요업무(main_task)** 를 
            이력서에 효과적으로 반영해 주세요.
            오직 제공된 정보만 사용하고, 임의로 내용을 추가하지 마세요.
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
            - 'summary' 섹션은 채용 공고의 qualification(자격요건), main_task(주요업무), preferred(우대사항)을 
              핵심 키워드로 요약·강조하는 문장으로 작성하세요.
            - 'experience.summary' 에 프로젝트 경험 중 채용 공고의 우대사항과 직접 연결되는 기여 내용을 기술하세요.
            - 'experience.description' 과 'activities.details' 에는 반드시 수치(%, 건수, 사용자 수 등)나 결과 지표를 포함해 구체화해야 합니다.
            - 'skills' 는 언어·프레임워크·도구별로 그룹화하고, 각 스킬의 숙련도를 레벨로 표기하세요.
            - 'education' 에는 학위·전공·기관·기간을 기재하고, 관련 수상이나 프로젝트 경험이 있으면 'details' 에 추가하세요.
            - GitHub 컨텍스트를 활용할 경우:
              • 프로필 README와 핀된 레포 README에서 확인된 기술 및 성과를 'summary'와 'experience.description'에 반영  
              • 주요 커밋 히스토리 중 채용 공고 우대사항과 연관된 기능 구현 내역을 강조  
            - 제공된 데이터만 사용하고, 임의로 내용을 추가하지 마세요.
            """;
}
