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

	@Column(name = "email", nullable = true, unique = true)
	private String email;

	@Column(name = "provider")
	private String provider;

	@Column(name = "provider_id", nullable = false)
	private String providerId;

	@Column(name = "profile_image")
	private String profileImage;

	@Column(name = "education")
	private String education;

	@Builder
	public User(String name, String email, String provider, String providerId, String profileImage, String education){
		this.name =name;
		this.email = email;
		this.provider = provider;
		this.providerId = providerId;
		this.profileImage = profileImage;
		this.education = education;
	}
}