package com.biology.domain.manage.runTime.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.runTime.db.RunTimeEntity;
import com.biology.domain.manage.runTime.model.RunTimeModel;
import com.biology.domain.manage.runTime.db.RunTimeService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RunTimeFactory {

    private RunTimeService runTimeService;

    public RunTimeModel create() {
        return new RunTimeModel(runTimeService);
    }

    public RunTimeModel loadById(Long runTimeId) {
        RunTimeEntity byId = runTimeService.getById(runTimeId);
        if (byId == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, runTimeId, "报告");
        }
        return new RunTimeModel(byId, runTimeService);
    }
}
