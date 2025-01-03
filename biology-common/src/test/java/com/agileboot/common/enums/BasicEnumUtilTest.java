package com.biology.common.enums;

import com.biology.common.enums.common.YesOrNoEnum;
import org.junit.Assert;
import org.junit.Test;

public class BasicEnumUtilTest {

    @Test
    public void testFromValue() {

        YesOrNoEnum yes = BasicEnumUtil.fromValue(YesOrNoEnum.class, 1);
        YesOrNoEnum no = BasicEnumUtil.fromValue(YesOrNoEnum.class, 0);

        Assert.assertEquals(yes.description(), "是");
        Assert.assertEquals(no.description(), "否");

    }
}
