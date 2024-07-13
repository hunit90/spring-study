package com.programmer.dmaker.service;

import com.programmer.dmaker.entity.Developer;
import com.programmer.dmaker.repository.DeveloperRepository;
import com.programmer.dmaker.type.DeveloperLevel;
import com.programmer.dmaker.type.DeveloperSkillType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DMakerService {
    private final DeveloperRepository developerRepository;

    @Transactional
    public void createDeveloper() {
        Developer developer = Developer.builder()
                .developerLevel(DeveloperLevel.JUNIOR)
                .developerSkillType(DeveloperSkillType.FRONT_END)
                .experienceYears(2)
                .name("Olaf")
                .age(5)
                .build();

        developerRepository.save(developer);
    }
}
