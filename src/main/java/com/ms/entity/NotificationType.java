package com.ms.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

public enum NotificationType {

    MIN("MIN"),
    HOUR("HOUR"),
    DAY("DAY"),
    WEEK("WEEK");

    private String type;

    NotificationType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

    @JsonCreator
    public static NotificationType parsing(String inputValue) {
        return Stream.of(NotificationType.values())
                .filter(type -> type.toString().equals(inputValue.toUpperCase()))
                .findFirst()
                .orElse(null);
    }
}
