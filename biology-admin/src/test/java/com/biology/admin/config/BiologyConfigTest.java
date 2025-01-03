package com.biology.admin.config;

import com.biology.admin.BiologyAdminApplication;
import com.biology.common.config.BiologyConfig;
import com.biology.common.constant.Constants.UploadSubDir;
import java.io.File;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = BiologyAdminApplication.class)
@RunWith(SpringRunner.class)
public class BiologyConfigTest {

    @Resource
    private BiologyConfig config;

    @Test
    public void testConfig() {
        String fileBaseDir = "D:\\biology\\profile";

        Assertions.assertEquals("Biology", config.getName());
        Assertions.assertEquals("1.0.0", config.getVersion());
        Assertions.assertEquals("2022", config.getCopyrightYear());
        Assertions.assertTrue(config.isDemoEnabled());
        Assertions.assertEquals(fileBaseDir, BiologyConfig.getFileBaseDir());
        Assertions.assertFalse(BiologyConfig.isAddressEnabled());
        Assertions.assertEquals("math", BiologyConfig.getCaptchaType());
        Assertions.assertEquals("math", BiologyConfig.getCaptchaType());
        Assertions.assertEquals(fileBaseDir + "\\import",
                BiologyConfig.getFileBaseDir() + File.separator + UploadSubDir.IMPORT_PATH);
        Assertions.assertEquals(fileBaseDir + "\\avatar",
                BiologyConfig.getFileBaseDir() + File.separator + UploadSubDir.AVATAR_PATH);
        Assertions.assertEquals(fileBaseDir + "\\download",
                BiologyConfig.getFileBaseDir() + File.separator + UploadSubDir.DOWNLOAD_PATH);
        Assertions.assertEquals(fileBaseDir + "\\upload",
                BiologyConfig.getFileBaseDir() + File.separator + UploadSubDir.UPLOAD_PATH);
    }

}
