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

	@Column(name = "auth_code", nullable = false, unique = true)
	private String authCode;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "profile_image")
	private String profileImage;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "education")
	private String education;

	@Builder
	public User(String authCode, String email, String profileImage, String name, String education) {
		this.authCode = authCode;
		this.email = email;
		this.profileImage = profileImage;
		this.name = name;
		this.education = education;
	}
}