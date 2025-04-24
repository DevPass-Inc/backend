package com.devpass.global.constants;

import java.util.List;

public final class Constants {

	private Constants() {
	}

	public static final List<String> NO_NEED_FILTER_URLS = List.of(
		"/v3/api-docs/**",
		"/swagger-ui/**",
		"/swagger-ui.html",
		"/login/oauth/authorize",
		"/api/companies/**",
		"/api/recruitments/**",
		"/api/resumes/**"
	);
}