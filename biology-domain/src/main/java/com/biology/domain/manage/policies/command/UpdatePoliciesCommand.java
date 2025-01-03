package com.biology.domain.manage.policies.command;

import lombok.Data;

@Data
public class UpdatePoliciesCommand extends AddPoliciesCommand {
    private Long policiesId;
}
