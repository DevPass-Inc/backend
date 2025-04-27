package com.devpass.domain.user.entity;

import com.devpass.global.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "provider", nullable = false)
	private String provider;

	@Column(name = "provider_id", nullable = false, unique = true)
	private String providerId;

	@Column(name = "profile_image")
	private String profileImage;

	@Column(name = "education")
	private String education;

	@Column(name = "github_login", unique = true)
	private String githubLogin;

	@Column(name = "github_oauth_token")
	private String githubOAuthToken;

	@Column(name = "phone")
	private String phone;

	@Column(name = "blog_url")
	private String blogUrl;

	@Builder
	public User(String name,
				String email,
				String provider,
				String providerId,
				String profileImage,
				String education,
				String githubLogin,
				String githubOAuthToken,
				String phone,
				String blogUrl) {
		this.name = name;
		this.email = email;
		this.provider = provider;
		this.providerId = providerId;
		this.profileImage = profileImage;
		this.education = education;
		this.githubLogin = githubLogin;
		this.githubOAuthToken = githubOAuthToken;
		this.phone = phone;
		this.blogUrl = blogUrl;
	}

	public void updateName(String name) {
		this.name = name;
	}

	public void updateEmail(String email) {
		this.email = email;
	}
}
