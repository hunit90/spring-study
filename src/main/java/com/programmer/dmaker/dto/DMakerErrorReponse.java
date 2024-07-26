package com.programmer.dmaker.dto;

import com.programmer.dmaker.exception.DMakerErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DMakerErrorReponse {
    private DMakerErrorCode errorCode;
    private String errorMessage;
}
