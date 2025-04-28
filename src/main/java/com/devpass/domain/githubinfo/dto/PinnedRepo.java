package com.devpass.domain.githubinfo.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class PinnedRepo {
    private final String name;
    private final String description;
    private final String readme;
}