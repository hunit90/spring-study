package com.programmer.dmaker.service;

import com.programmer.dmaker.dto.CreateDeveloper;
import com.programmer.dmaker.dto.DeveloperDetailDto;
import com.programmer.dmaker.dto.DeveloperDto;
import com.programmer.dmaker.entity.Developer;
import com.programmer.dmaker.exception.DMakerErrorCode;
import com.programmer.dmaker.exception.DMakerException;
import com.programmer.dmaker.repository.DeveloperRepository;
import com.programmer.dmaker.type.DeveloperLevel;
import com.programmer.dmaker.type.DeveloperSkillType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.programmer.dmaker.exception.DMakerErrorCode.*;

@Service
@RequiredArgsConstructor
public class DMakerService {
    private final DeveloperRepository developerRepository;

    @Transactional
    public CreateDeveloper.Response createDeveloper(CreateDeveloper.Request request) {
        validateCreateDeveloperRequest(request);

        Developer developer = Developer.builder()
                .developerLevel(request.getDeveloperLevel())
                .developerSkillType(request.getDeveloperSkillType())
                .experienceYears(request.getExprienceYears())
                .memberId(request.getMemberId())
                .name(request.getName())
                .age(request.getAge())
                .build();

        developerRepository.save(developer);
        return CreateDeveloper.Response.fromEntity(developer);
    }

    private void validateCreateDeveloperRequest(CreateDeveloper.Request request) {
        DeveloperLevel developerLevel = request.getDeveloperLevel();
        Integer exprienceYears = request.getExprienceYears();
        if (developerLevel == DeveloperLevel.SENIOR
         && exprienceYears < 10) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }

        if (developerLevel == DeveloperLevel.JUNGNIOR
        && (exprienceYears < 4 || exprienceYears > 10)) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }

        if (developerLevel == DeveloperLevel.JUNIOR && exprienceYears > 4) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }

        Optional<Developer> developer = developerRepository.findByMemberId(request.getMemberId());
        if (developer.isPresent()) {
            throw new DMakerException(DUPLICATED_MEMBER_ID);
        }
    }

    public List<DeveloperDto> getAllDevelopers() {
        return developerRepository.findAll()
                .stream().map(DeveloperDto::fromEntity)
                .collect(Collectors.toList());
    }

    public DeveloperDetailDto getDeveloperDetail(String memberId) {
        return developerRepository.findByMemberId(memberId)
                .map(DeveloperDetailDto::fromEntity)
                .orElseThrow(() -> new DMakerException(NO_DEVELOPER));
    }
}
