package com.programmer.dmaker.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DeveloperLevel {
    NEW("newbie"),
    JUNIOR("junior"),
    JUNGNIOR("jungnior"),
    SENIOR("sinior")
    ;

    private final String description;
}
