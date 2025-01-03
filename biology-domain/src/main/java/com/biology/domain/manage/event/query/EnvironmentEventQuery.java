package com.biology.domain.manage.event.query;

import lombok.Data;

@Data
public class EnvironmentEventQuery {
    private String description;
    private String dayType;
    private Long environmentId;
}
