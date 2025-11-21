package com.biology.infrastructure.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Mybatis支持*匹配扫描包
 *
 * @author valarchie
 */
@Configuration
@EnableTransactionManagement
public class MyBatisConfig {

    public static ThreadLocal<String> equipmentData = new ThreadLocal<>();

    public static ThreadLocal<String> environmentData = new ThreadLocal<>();

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // ✅ 3. 动态表名插件
        DynamicTableNameInnerInterceptor dynamicInterceptor = new DynamicTableNameInnerInterceptor();
        dynamicInterceptor.setTableNameHandler((sql, tableName) -> {
            System.out.println("原表名: " + tableName + ", SQL: " + sql);
            if ("manage_equipment_data".equals(tableName)) {
                String dateSuffix = null;
                dateSuffix = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

                if (sql.startsWith("SELECT") && equipmentData.get() != null && equipmentData.get() != null) {
                    dateSuffix = equipmentData.get();
                }
                return "manage_equipment_data_" + dateSuffix;
            }

            if ("manage_environment_detection".equals(tableName)) {
                String dateSuffix = null;
                dateSuffix = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
                if (sql.startsWith("SELECT") && environmentData.get() != null && environmentData.get() != null) {
                    if (environmentData.get().equals("now")) {
                        return "manage_environment_detection";
                    }
                    dateSuffix = environmentData.get();
                }

                return "manage_environment_detection_" + dateSuffix;
            }

            return tableName;
        });
        interceptor.addInnerInterceptor(dynamicInterceptor);

        // ✅ 1. 分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));

        // ✅ 2. 防止全表更新或删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return interceptor;
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

}
