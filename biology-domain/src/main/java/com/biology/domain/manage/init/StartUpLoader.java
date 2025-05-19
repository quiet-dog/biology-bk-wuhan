package com.biology.domain.manage.init;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.domain.manage.moni.MoniApplicationService;
import com.biology.domain.manage.moni.db.MoniEntity;
import com.biology.domain.manage.moni.db.MoniService;

@Component
public class StartUpLoader implements ApplicationRunner {

    @Autowired
    private MoniService moniService;

    @Autowired
    private MoniApplicationService moniApplicationService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        QueryWrapper<MoniEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_push", true);
        List<MoniEntity> list = moniService.list(queryWrapper);
        list.forEach(moni -> {
            moniApplicationService.startPush(moni.getMoniId());
        });
        return;
    }

}
