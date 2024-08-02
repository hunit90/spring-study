package com.programmer.dmaker.service;

import com.programmer.dmaker.code.StatusCode;
import com.programmer.dmaker.dto.CreateDeveloper;
import com.programmer.dmaker.dto.DeveloperDetailDto;
import com.programmer.dmaker.dto.DeveloperDto;
import com.programmer.dmaker.entity.Developer;
import com.programmer.dmaker.repository.DeveloperRepository;
import com.programmer.dmaker.repository.RetiredDeveloperRepository;
import com.programmer.dmaker.type.DeveloperLevel;
import com.programmer.dmaker.type.DeveloperSkillType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.programmer.dmaker.type.DeveloperLevel.JUNIOR;
import static com.programmer.dmaker.type.DeveloperLevel.SENIOR;
import static com.programmer.dmaker.type.DeveloperSkillType.FRONT_END;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class DMakerServiceTest {
    @Mock
    private DeveloperRepository developerRepository;

    @Mock
    private RetiredDeveloperRepository retiredDeveloperRepository;

    @InjectMocks
    private DMakerService dMakerService;

    @Test
    public void testSomething() {
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(Developer.builder()
                        .developerLevel(SENIOR)
                        .developerSkillType(FRONT_END)
                        .experienceYears(12)
                        .statusCode(StatusCode.EMPLOYED)
                        .name("name")
                        .age(12)
                        .build()));

        DeveloperDetailDto developerDetail = dMakerService.getDeveloperDetail("memberId");

        assertEquals(SENIOR, developerDetail.getDeveloperLevel());
        assertEquals(12, developerDetail.getExperienceYears());
        assertEquals(FRONT_END, developerDetail.getDeveloperSkillType());
    }
}