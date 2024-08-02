package com.programmer.dmaker.controller;

import com.programmer.dmaker.dto.DeveloperDto;
import com.programmer.dmaker.service.DMakerService;
import com.programmer.dmaker.type.DeveloperLevel;
import com.programmer.dmaker.type.DeveloperSkillType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

@WebMvcTest(DMakerController.class)
class DMakerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DMakerService dMakerService;

    protected MediaType contentType =
            new MediaType(MediaType.APPLICATION_JSON.getType(),
                    MediaType.APPLICATION_JSON.getSubtype(),
                    StandardCharsets.UTF_8);

    @Test
    void getAllDevelopers() throws Exception {
        DeveloperDto juniorDeveloperDto = DeveloperDto.builder()
                .developerSkillType(DeveloperSkillType.BACK_END)
                .developerLevel(DeveloperLevel.JUNIOR)
                .memberId("memberId1").build();
        DeveloperDto seniorDeveloperDto = DeveloperDto.builder()
                .developerSkillType(DeveloperSkillType.BACK_END)
                .developerLevel(DeveloperLevel.SENIOR)
                .memberId("memberId2").build();

        given(dMakerService.getAllEmployedDeveloper())
                .willReturn(Arrays.asList(juniorDeveloperDto, seniorDeveloperDto));

        mockMvc.perform(get("/developers").contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].developerSkillType", is(DeveloperSkillType.BACK_END.name())))
                .andExpect(jsonPath("$.[1].developerSkillType", is(DeveloperSkillType.BACK_END.name())))
                .andDo(print());
    }
}
