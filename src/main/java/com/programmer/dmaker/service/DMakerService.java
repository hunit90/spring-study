package com.programmer.dmaker.service;

import com.programmer.dmaker.dto.CreateDeveloper;
import com.programmer.dmaker.entity.Developer;
import com.programmer.dmaker.exception.DMakerErrorCode;
import com.programmer.dmaker.exception.DMakerException;
import com.programmer.dmaker.repository.DeveloperRepository;
import com.programmer.dmaker.type.DeveloperLevel;
import com.programmer.dmaker.type.DeveloperSkillType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.programmer.dmaker.exception.DMakerErrorCode.DUPLICATED_MEMBER_ID;
import static com.programmer.dmaker.exception.DMakerErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATCHED;

@Service
@RequiredArgsConstructor
public class DMakerService {
    private final DeveloperRepository developerRepository;

    @Transactional
    public void createDeveloper(CreateDeveloper.Request request) {
        validateCreateDeveloperRequest(request);

        Developer developer = Developer.builder()
                .developerLevel(DeveloperLevel.JUNIOR)
                .developerSkillType(DeveloperSkillType.FRONT_END)
                .experienceYears(2)
                .name("Olaf")
                .age(5)
                .build();

        developerRepository.save(developer);
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
}
