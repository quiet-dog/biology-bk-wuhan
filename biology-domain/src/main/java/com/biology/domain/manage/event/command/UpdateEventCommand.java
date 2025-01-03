package com.biology.domain.manage.event.command;

import lombok.Data;

@Data
public class UpdateEventCommand extends AddEventCommand {
    private Long eventId;
}
