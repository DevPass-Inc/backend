package com.devpass.backend.domain.recruitment.domain;

import com.devpass.backend.domain.stack.domain.Stack;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Recruitment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_id")
    private Long id;

    @Column(name = "company_name", columnDefinition = "TEXT")
    private String companyName;

    @Column(name = "position")
    private String position;

    @Column(name = "location")
    private String location;

    @Column(name = "career")
    private String career;

    @Column(name = "main_task", columnDefinition = "TEXT")
    private String mainTask;

    @Column(name = "qualification", columnDefinition = "TEXT")
    private String qualification;

    @Column(name = "preferred", columnDefinition = "TEXT")
    private String preferred;

    @Column(name = "benefit", columnDefinition = "TEXT")
    private String benefit;

    @Column(name = "recruiting", nullable = true, columnDefinition = "TEXT")
    private String recruiting;

    @Column(name = "deadline")
    private String deadline;

    @ManyToMany
    @JoinTable(
        name = "recruitment_stack",
    joinColumns = @JoinColumn(name = "recruitment_id"),
    inverseJoinColumns = @JoinColumn(name = "stack_id")
    )
    private List<Stack> stacks = new ArrayList<>();
}
