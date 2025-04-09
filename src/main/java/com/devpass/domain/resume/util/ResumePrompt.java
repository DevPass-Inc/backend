package com.devpass.domain.resume.util;

public class ResumePrompt {
    public static final String HEADER = "아래 개발 경험 데이터를 참고하여, 오직 해당 내용만 사용해 다음 JSON 포맷의 이력서를 생성해줘.";

    public static final String JSON_TEMPLATE = "{\n" +
            "  \"summary\": [\n" +
            "    \"백엔드 개발자로서 다양한 대규모 서비스의 설계 및 개발을 주도한 경험이 있습니다.\",\n" +
            "    \"다양한 팀과 협업하며 RESTful API 및 마이크로서비스 아키텍처 개발 경험이 풍부합니다.\",\n" +
            "    \"성능 최적화 및 보안 강화를 위한 시스템 개선 프로젝트에 참여한 경험이 있습니다.\",\n" +
            "    \"CI/CD 자동화 파이프라인 구축 및 운영 경험이 있으며, Jenkins, GitHub Actions를 사용하여 배포 효율을 높였습니다.\",\n" +
            "    \"클라우드 환경(AWS, GCP)에서 인프라 구축 및 운영, 비용 최적화와 보안 정책 수립까지 직접 경험했습니다.\"\n" +
            "  ],\n" +
            "  \"experience\": [\n" +
            "    {\n" +
            "      \"project\": \"\",\n" +
            "      \"summary\": \"\",\n" +
            "      \"position\": \"\",\n" +
            "      \"duration\": \"\",\n" +
            "      \"skills\": [],\n" +
            "      \"description\": []\n" +
            "    }\n" +
            "  ],\n" +
            "  \"activities\": [\n" +
            "    {\n" +
            "      \"activity\": \"\",\n" +
            "      \"dates\": \"\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"skills\": [\n" +
            "    {\n" +
            "      \"skill\": \"\",\n" +
            "      \"level\": \"\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    public static final String NOTES = "주의사항:\n" +
            "- 'activities' 필드는 인턴십 데이터를 기반으로 채워줘.\n" +
            "- 'skills' 필드는 프로젝트 및 인턴십에서 사용한 기술 스택 데이터를 모두 포함하여 그룹화해줘.\n" +
            "위 데이터를 참고하여, JSON 구조에 맞게 모든 내용을 devExperience 데이터로 채워서 이력서를 생성해줘.";
}
