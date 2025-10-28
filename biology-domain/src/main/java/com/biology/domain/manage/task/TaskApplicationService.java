package com.biology.domain.manage.task;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.biology.common.utils.time.DatePickUtil;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.detection.command.AddDetectionCommand;
import com.biology.domain.manage.detection.db.DetectionService;
import com.biology.domain.manage.dianShui.db.DianShuiEntity;
import com.biology.domain.manage.dianShui.db.DianShuiService;
import com.biology.domain.manage.dianShui.model.DianShuiFactory;
import com.biology.domain.manage.dianShui.model.DianShuiModel;
import com.biology.domain.manage.door.command.AddDoorCommand;
import com.biology.domain.manage.door.db.DoorEntity;
import com.biology.domain.manage.door.db.DoorService;
import com.biology.domain.manage.door.model.DoorFactory;
import com.biology.domain.manage.door.model.DoorModel;
import com.biology.domain.manage.environment.db.EnvironmentService;
import com.biology.domain.manage.equipment.db.EquipmentDataService;
import com.biology.domain.manage.equipment.db.EquipmentEntity;
import com.biology.domain.manage.equipment.db.EquipmentService;
import com.biology.domain.manage.gosip.db.GosipClientService;
import com.biology.domain.manage.gosip.dto.channels.ChannaelListDTO;
import com.biology.domain.manage.gosip.dto.channels.ChannelQuery;
import com.biology.domain.manage.koala.db.KoalaService;
import com.biology.domain.manage.koala.dto.KoalaResDTO;
import com.biology.domain.manage.koala.dto.event.KoalaEventDTO;
import com.biology.domain.manage.koala.dto.event.KoalaEventQuery;
import com.biology.domain.manage.koala.dto.records.KoalaRecordDTO;
import com.biology.domain.manage.koala.dto.records.KoalaRecordResponseDTO;
import com.biology.domain.manage.koala.dto.records.KoalaRecordsQuery;
import com.biology.domain.manage.koala.dto.subject.SubjectDTO;
import com.biology.domain.manage.koala.dto.subject.SubjectQuery;
import com.biology.domain.manage.materials.db.MaterialsEntity;
import com.biology.domain.manage.materials.db.MaterialsService;
import com.biology.domain.manage.personnel.command.AddPersonnelCommand;
import com.biology.domain.manage.personnel.command.UpdatePersonnelCommand;
import com.biology.domain.manage.personnel.db.PersonnelEntity;
import com.biology.domain.manage.personnel.db.PersonnelService;
import com.biology.domain.manage.personnel.model.PersonnelFactory;
import com.biology.domain.manage.personnel.model.PersonnelModel;
import com.biology.domain.manage.runTime.command.AddRunTimeCommand;
import com.biology.domain.manage.runTime.db.RunTimeEntity;
import com.biology.domain.manage.runTime.db.RunTimeService;
import com.biology.domain.manage.runTime.model.RunTimeFactory;
import com.biology.domain.manage.runTime.model.RunTimeModel;
import com.biology.domain.manage.task.db.MaterialsTaskEntity;
import com.biology.domain.manage.task.db.MaterialsTaskService;
import com.biology.domain.manage.task.dto.ShengOnelineResDTO;
import com.biology.domain.manage.websocket.db.WebsocketService;
import com.biology.domain.manage.websocket.dto.OnlineDTO;
import com.biology.domain.manage.xunJian.db.XunJianEntity;
import com.biology.domain.manage.xunJian.db.XunJianService;
import com.biology.domain.manage.xunJian.model.XunJianFactory;
import com.biology.domain.manage.xunJianHistory.db.XunJianHistoryEntity;

import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TaskApplicationService {

    private final MaterialsTaskService materialsTaskService;

    private final DetectionService detectionService;

    private final MaterialsService materialsService;

    private final WebsocketService websocketService;

    private final GosipClientService gosipClientService;

    private final PersonnelService personnelService;

    private final PersonnelFactory personnelFactory;

    private final DianShuiFactory dianShuiFactory;

    private final DianShuiService dianShuiService;

    private final KoalaService koalaService;

    private final DoorFactory doorFactory;

    private final DoorService doorService;

    private final RunTimeService runTimeService;

    private final RunTimeFactory runTimeFactory;

    private final EquipmentService equipmentService;

    private final EnvironmentService environmentService;

    private static Boolean isDoorRunning = false;

    private static Integer page = 1;

    private static Integer size = 10000;

    private static Boolean isSubjectRunning = false;

    private final WebClient renTiClient;

    private final EquipmentDataService equipmentDataService;

    private final XunJianService xunJianService;

    private final XunJianFactory xunJianFactory;

    // @Scheduled(cron = "30 0 0 1 * ?")
    // public void stock() {
    // QueryWrapper<MaterialsEntity> queryWrapper = new
    // QueryWrapper<MaterialsEntity>();
    // List<MaterialsEntity> list = materialsService.list(queryWrapper);
    // for (MaterialsEntity MaterialsEntity : list) {
    // MaterialsTaskEntity result = new MaterialsTaskEntity();
    // result.setMaterialsId(MaterialsEntity.getMaterialsId());
    // result.setStock(MaterialsEntity.getStock());
    // result.setType("month");
    // materialsTaskService.save(result);
    // }
    // }

    // 每天统计一次库存
    @Scheduled(cron = "0 50 23 * * ?")
    public void statistics() {
        QueryWrapper<MaterialsEntity> queryWrapper = new QueryWrapper<MaterialsEntity>();
        List<MaterialsEntity> list = materialsService.list(queryWrapper);
        for (MaterialsEntity MaterialsEntity : list) {
            MaterialsTaskEntity result = new MaterialsTaskEntity();
            result.setMaterialsId(MaterialsEntity.getMaterialsId());
            result.setStock(MaterialsEntity.getStock());
            materialsTaskService.save(result);
        }
    }

    // 1小时执行一次
    // @Scheduled(cron = "0 0/1 * * * ?")
    public void test() {

        if (isDoorRunning) {
            return;
        }
        isDoorRunning = true;

        if (koalaService.getAuthToken() == null || koalaService.getAuthToken() == "") {
            try {
                koalaService.auth();
            } catch (Exception e) {
                isDoorRunning = false;
                return;
            }

            koalaService.auth();
        }

        try {
            KoalaRecordsQuery query = new KoalaRecordsQuery();
            LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
            // 转为时间戳（秒级）
            long startTimestamp = startOfDay.toEpochSecond(ZoneOffset.ofHours(0));

            // 获取今天的结束时间
            LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);
            // 转为时间戳（秒级）
            long endTimestamp = endOfDay.toEpochSecond(ZoneOffset.ofHours(0));
            System.out.println("startTimestamp:" + startTimestamp);
            query.setStartTime(new Integer((int) startTimestamp));
            query.setEndTime(new Integer((int) endTimestamp));
            query.setPage(page);
            query.setSize(size);
            KoalaRecordResponseDTO res = koalaService.getRecords(query);
            if (res != null) {
                System.out.println(res.getData().size());
                for (KoalaRecordDTO record : res.getData()) {
                    AddDoorCommand command = new AddDoorCommand();
                    if (record.getSubject() == null) {
                        continue;
                    }
                    command.setOutId(new Long(record.getSubject().getId()));
                    command.setDoorDate(record.getDate());
                    command.setAvatar(record.getSubject().getAvatar());
                    command.setClockIn(record.getClock_in());
                    command.setClockOut(record.getClock_out());
                    command.setCheckInTime(record.getCheck_in_time());
                    command.setCheckOutTime(record.getCheck_out_time());
                    command.setDepartment(record.getSubject().getDepartment());
                    command.setDescription(record.getSubject().getDescription());
                    command.setExtraId(record.getSubject().getExtra_id());
                    command.setJobNumber(record.getSubject().getJob_number());
                    command.setName(record.getSubject().getName());

                    QueryWrapper<DoorEntity> queryWrapper = new QueryWrapper<DoorEntity>();
                    queryWrapper
                            .eq("out_id", record.getSubject().getId())
                            .eq("door_date", record.getDate());
                    DoorEntity one = doorService.getOne(queryWrapper);
                    if (one == null) {
                        DoorModel doorModel = doorFactory.create();
                        doorModel.loadAddDoorCommand(command);
                        doorModel.insert();
                    }

                }
            }
            isDoorRunning = false;
        } catch (Exception e) {
            isDoorRunning = false;
            return;
        }
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void getEvent() {
        if (isDoorRunning) {
            return;
        }
        isDoorRunning = true;

        if (koalaService.getAuthToken() == null || koalaService.getAuthToken() == "") {
            try {
                koalaService.auth();
            } catch (Exception e) {
                isDoorRunning = false;
                return;
            }

        }

        KoalaEventQuery query = new KoalaEventQuery();

        LocalDate today = LocalDate.now(); // 获取当前日期
        LocalDateTime startOfDay = today.atStartOfDay(); // 转换为当天的开始时间

        // 转换为时间戳（秒）
        long timestamp = startOfDay.toEpochSecond(ZoneOffset.ofHours(8));
        // 转为时间戳（秒级）
        // long endTimestamp = endOfDay.toEpochSecond(ZoneOffset.ofHours(0));
        // System.out.println("startTimestamp:" + startTimestamp);
        System.out.println("timestamp:" + timestamp);
        query.setStart(new Integer((int) timestamp));
        // query.setEnd(new Integer((int) endTimestamp));
        query.setPage(page);
        query.setSize(size);

        try {
            KoalaResDTO<List<KoalaEventDTO>> res = koalaService.getEvents(query);
            if (res != null && res.getData() != null && res.getData().size() > 0) {
                for (KoalaEventDTO event : res.getData()) {
                    QueryWrapper<DoorEntity> queryWrapper = new QueryWrapper<DoorEntity>();
                    queryWrapper.eq("out_id", event.getId());
                    DoorEntity one = doorService.getOne(queryWrapper);
                    if (one != null) {
                        continue;
                    }

                    QueryWrapper<PersonnelEntity> queryWrapper2 = new QueryWrapper<>();
                    if (event.getSubject() == null) {
                        continue;
                    }
                    queryWrapper2
                            .eq("out_id", event.getSubject().getId());
                    PersonnelEntity pEntity = personnelService.getOne(queryWrapper2);
                    if (pEntity == null) {
                        continue;
                    }
                    DoorModel doorModel = doorFactory.create();
                    AddDoorCommand command = new AddDoorCommand();
                    command.setPersonnelId(pEntity.getPersonnelId());
                    command.setOutId(new Long(event.getId()));
                    // command.setDoorCode(String.valueOf(event.getId()));
                    command.setAvatar(event.getPhoto());
                    if (event.getSubject() != null) {
                        command.setName(event.getSubject().getName());
                        command.setDepartment(event.getSubject().getDepartment());
                    }
                    if (event.getScreen() != null) {
                        command.setDoorPlace(event.getScreen().getCameraPosition());
                    }
                    if (event.getEventType() != null) {
                        if (event.getEventType() == 0) {
                            command.setEventType("识别");
                        }
                        if (event.getEventType() == 1) {
                            command.setEventType("未识别");
                        }
                        if (event.getEventType() == 2) {
                            command.setEventType("密码开门");
                        }
                        if (event.getEventType() == 3) {
                            command.setEventType("远程开门");
                        }
                    }
                    if (event.getPassType() != null) {
                        if (event.getPassType() == 0) {
                            command.setEnterStatus("未通行");
                        }
                        if (event.getPassType() == 1) {
                            command.setEnterStatus("通行");
                        }
                    }

                    command.setDoorDate(event.getTimestamp());

                    doorModel.loadAddDoorCommand(command);
                    doorModel.insert();
                }
            }
            isDoorRunning = false;
        } catch (Exception e) {
            isDoorRunning = false;
            throw e;
        }

    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void getSubjects() {
        if (isSubjectRunning) {
            return;
        }
        isSubjectRunning = true;

        if (koalaService.getAuthToken() == null || koalaService.getAuthToken() == "") {
            try {
                koalaService.auth();
            } catch (Exception e) {
                isSubjectRunning = false;
                return;
            }

        }

        try {
            SubjectQuery query = new SubjectQuery();
            query.setPage(page);
            query.setSize(size);
            query.setCategory("employee");

            KoalaResDTO<List<SubjectDTO>> res = koalaService.getSubjectList(query);

            if (res != null && res.getData() != null && res.getData().size() > 0) {
                List<Integer> extraIds = new ArrayList<>();
                for (SubjectDTO sDto : res.getData()) {
                    extraIds.add(sDto.getId());
                    QueryWrapper<PersonnelEntity> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("out_id", sDto.getId());
                    PersonnelEntity pEntity = personnelService.getOne(queryWrapper);
                    if (pEntity == null) {
                        PersonnelModel pModel = personnelFactory.create();
                        AddPersonnelCommand command = new AddPersonnelCommand();
                        command.setCode(sDto.getJobNumber());
                        command.setContact(sDto.getPhone());
                        command.setDepartment(sDto.getDepartment());
                        // command.setEntryTime(sDto.getEntryDate());
                        command.setJobCode(sDto.getJobNumber());
                        // command.setLeaveTime(sDto.le);
                        command.setName(sDto.getName());
                        command.setOutId(sDto.getId());
                        command.setPost(sDto.getTitle());
                        if (sDto.getGender() == 0) {
                            command.setSex("未知");
                        }
                        if (sDto.getGender() == 1) {
                            command.setSex("男");
                        }
                        if (sDto.getGender() == 2) {
                            command.setSex("女");
                        }
                        pModel.loadAddPersonnelCommand(command);
                        pModel.insert();
                    }
                    /**
                     * else {
                     * PersonnelModel pModel = personnelFactory.create();
                     * UpdatePersonnelCommand command = new UpdatePersonnelCommand();
                     * // 将one的属性值复制到command
                     * BeanUtil.copyProperties(pEntity, command);
                     * command.setPersonnelId(pEntity.getPersonnelId());
                     * command.setCode(sDto.getJobNumber());
                     * command.setContact(sDto.getPhone());
                     * command.setDepartment(sDto.getDepartment());
                     * // command.setEntryTime(sDto.getEntryDate());
                     * command.setJobCode(sDto.getJobNumber());
                     * // command.setLeaveTime(sDto.le);
                     * command.setName(sDto.getName());
                     * command.setOutId(sDto.getId());
                     * command.setPost(sDto.getTitle());
                     * if (sDto.getGender() == 0) {
                     * command.setSex("未知");
                     * }
                     * if (sDto.getGender() == 1) {
                     * command.setSex("男");
                     * }
                     * if (sDto.getGender() == 2) {
                     * command.setSex("女");
                     * }
                     * pModel.loadAddPersonnelCommand(command);
                     * pModel.updateById();
                     * }
                     */
                }
                if (extraIds.size() > 0) {
                    // 删除不在这里面的人员
                    QueryWrapper queryWrapper = new QueryWrapper<>();
                    queryWrapper.notIn("out_id", extraIds);
                    List<PersonnelEntity> pList = personnelService.list(queryWrapper);
                    if (pList != null && pList.size() > 0) {
                        for (PersonnelEntity pEntity : pList) {
                            PersonnelModel pModel = personnelFactory.loadById(pEntity.getPersonnelId());
                            pModel.deleteById();
                        }
                    }
                }
            }
            isSubjectRunning = false;
        } catch (Exception e) {
            isSubjectRunning = false;
            return;
        }
    }

    // 每天的0点0分0秒执行
    @Scheduled(cron = "0 0 0 * * ?")
    public void createDoor() {
        // 查询未删除的人员
        QueryWrapper<PersonnelEntity> queryWrapper = new QueryWrapper<>();
        // queryWrapper.inSql("personnel_id",
        // "select personnel_id from manage_door where DATE(created_at) = CURDATE() and
        // deleted = 0");
        List<PersonnelEntity> pList = personnelService.list(queryWrapper);
        if (pList != null && pList.size() > 0) {
            for (PersonnelEntity pEntity : pList) {
                DoorModel doorModel = doorFactory.create();
                AddDoorCommand command = new AddDoorCommand();
                command.setClockIn(0);
                command.setClockOut(0);
                command.setCheckInTime(0);
                command.setCheckOutTime(0);
                command.setEnterStatus("不通行");
                command.setDepartment(pEntity.getDepartment());
                command.setName(pEntity.getName());
                command.setPersonnelId(pEntity.getPersonnelId());
                doorModel.loadAddDoorCommand(command);
                doorModel.insert();
            }
        }
    }

    // 每天统计一次库存
    @Scheduled(cron = "0 50 23 * * ?")
    public void cleanDoor() {
        QueryWrapper<PersonnelEntity> queryWrapper = new QueryWrapper<>();
        // queryWrapper.eq("deleted", 0);
        List<PersonnelEntity> pList = personnelService.list(queryWrapper);
        if (pList != null && pList.size() > 0) {
        }
    }

    @Scheduled(fixedRate = 3000)
    public void renTi() {
        ShengOnelineResDTO result = renTiClient.get()
                .uri("/api/getDevicesLastSendInfo") // 注意：你文档里是 getDevicesLastSendInfo
                .retrieve()
                .bodyToMono(ShengOnelineResDTO.class)
                .block(); // 阻塞直到返回结果

        if (result != null && result.getCode() == 0) {
            result.getData().forEach(device -> {
                if (device.getOnline()) {
                    CacheCenter.smDeviceOnlineCache.set(device.getSn(), device);
                } else {
                    CacheCenter.smDeviceOnlineCache.set(device.getSn(), device);
                }
            });
        }

    }

    @Scheduled(cron = "*/1 * * * * ?")
    public void taskPerSecond() {
        equipmentDataService.createNowTable();
    }

    @Scheduled(cron = "*/10 * * * * ?")
    public void tongJiDianShui() {
        LocalDate today = LocalDate.now();
        String suffix = today.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        System.out.println("水电统计任务开始");

        Double shui = detectionService.getAllWaterAndShui(suffix, "水");
        Double dian = detectionService.getAllWaterAndShui(suffix, "电");

        // // === 处理“水” ===
        QueryWrapper<DianShuiEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", "水")
                .apply("DATE(create_time) = CURDATE()");

        DianShuiEntity waterEntity = dianShuiService.getBaseMapper().selectOne(queryWrapper);

        if (waterEntity == null) {
            DianShuiModel model = dianShuiFactory.create();
            AddDetectionCommand cmd = new AddDetectionCommand();
            cmd.setWaterValue(shui);
            model.loadAddDianShuiCommand(cmd);
            model.setType("水");
            model.insert();
            System.out.println("插入水数据");
        } else {
            waterEntity.setWaterValue(shui);
            dianShuiService.updateById(waterEntity);
            System.out.println("更新水数据");
        }

        // === 处理“电” ===
        QueryWrapper<DianShuiEntity> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("type", "电")
                .apply("DATE(create_time) = CURDATE()");

        DianShuiEntity electricEntity = dianShuiService.getBaseMapper().selectOne(queryWrapper2);

        System.out.println("水电=====2");
        System.out.println("水电=====1" + electricEntity);
        if (electricEntity == null) {
            DianShuiModel model2 = dianShuiFactory.create();
            AddDetectionCommand cmd2 = new AddDetectionCommand();
            cmd2.setElectricityValue(dian);
            model2.loadAddDianShuiCommand(cmd2);
            model2.setType("电");
            model2.insert();
            System.out.println("插入电数据");
        } else {
            electricEntity.setElectricityValue(dian);
            dianShuiService.updateById(electricEntity);
            System.out.println("更新电数据");
        }
    }

    // @Scheduled(cron = "0 0/10 * * * ?")
    @Scheduled(cron = "*/10 * * * * ?")
    public void tongJiEquipmentRunTime() {
        List<Long> equipmentIds = equipmentService.getAllIds();

        for (Long id : equipmentIds) {
            String redisId = String.format("equipment-%d", id);

            OnlineDTO onlineDTO = CacheCenter.onlineCache.getObjectById(redisId);
            if (onlineDTO != null && onlineDTO.getIsOnline()) {
                QueryWrapper<RunTimeEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper
                        .eq("equipment_id", id)
                        .eq("is_stop", false)
                        .orderByDesc("run_time_id")
                        .last("LIMIT 1");
                RunTimeEntity runTimeEntity = runTimeService.getOne(queryWrapper);
                // 已经停止或者没有开始的,重新开始计时
                if (runTimeEntity == null) {
                    RunTimeModel runTimeModel = runTimeFactory.create();
                    AddRunTimeCommand addRunTimeCommand = new AddRunTimeCommand();
                    addRunTimeCommand.setEquipmentId(id);
                    addRunTimeCommand.setIsStop(false);
                    runTimeModel.loadAddRunTimeCommand(addRunTimeCommand);
                    runTimeModel.insert();
                } else {
                    runTimeEntity.updateById();
                }
            } else {
                QueryWrapper<RunTimeEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper
                        .eq("equipment_id", id)
                        .eq("is_stop", false)
                        .orderByDesc("run_time_id")
                        .last("LIMIT 1");
                RunTimeEntity runTimeEntity = runTimeService.getOne(queryWrapper);
                if (runTimeEntity != null) {
                    runTimeEntity.setIsStop(true);
                    runTimeEntity.updateById();
                }
            }

        }
    }

    @Scheduled(cron = "0 0/10 * * * ?")
    public void tongJiEnvironmentRunTime() {
        List<Long> environmentIds = environmentService.getAllIds();
        for (Long id : environmentIds) {
            String redisId = String.format("environment-%d", id);

            OnlineDTO onlineDTO = CacheCenter.onlineCache.getObjectById(redisId);
            if (onlineDTO != null && onlineDTO.getIsOnline()) {
                QueryWrapper<RunTimeEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper
                        .eq("environment_id", id)
                        .eq("is_stop", false)
                        .orderByDesc("run_time_id")
                        .last("LIMIT 1");
                RunTimeEntity runTimeEntity = runTimeService.getOne(queryWrapper);
                // 已经停止或者没有开始的,重新开始计时
                if (runTimeEntity == null) {
                    RunTimeModel runTimeModel = runTimeFactory.create();
                    AddRunTimeCommand addRunTimeCommand = new AddRunTimeCommand();
                    addRunTimeCommand.setEnvironmentId(id);
                    addRunTimeCommand.setIsStop(false);
                    runTimeModel.loadAddRunTimeCommand(addRunTimeCommand);
                    runTimeModel.insert();
                } else {
                    runTimeEntity.updateById();
                }
            } else {
                QueryWrapper<RunTimeEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper
                        .eq("environment_id", id)
                        .eq("is_stop", false)
                        .orderByDesc("run_time_id")
                        .last("LIMIT 1");
                RunTimeEntity runTimeEntity = runTimeService.getOne(queryWrapper);
                if (runTimeEntity != null) {
                    runTimeEntity.setEnvironmentId(id);
                    runTimeEntity.setIsStop(true);
                    runTimeEntity.updateById();
                }
            }

        }
    }

    // 任务调用执行巡检是否创建表
    @Scheduled(cron = "0 0/15 * * * ?")
    public void createXunJianTable() {
        QueryWrapper<XunJianEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("enable", true).eq("xun_jian_lei_xing", "持续巡检");
        List<XunJianEntity> list = xunJianService.list(queryWrapper);

        LocalDate today = LocalDate.now();
        int currentDayOfWeek = today.getDayOfWeek().getValue() % 7; // Java 周一=1，周日=7，转成0-6
        int currentDayOfMonth = today.getDayOfMonth() - 1; // 假设 dayRange 的每月天数从0开始

        for (XunJianEntity x : list) {
            Boolean isInsert = false;
            Boolean isStop = false;
            if (x.getXunJianPinLu().equals("每周")) {
                // 本周周一
                LocalDate weekStart = today.with(DayOfWeek.MONDAY);
                // 本周周日
                LocalDate weekEnd = today.with(DayOfWeek.SUNDAY);
                if (x.getDayRange().contains(currentDayOfWeek - 1)) {

                    // 判断今天是否已有记录
                    QueryWrapper<XunJianHistoryEntity> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.eq("xun_jian_id", x.getXunJianId())
                            .eq("status", "巡检中")
                            .ge("create_time", weekStart.atStartOfDay()) // create_time >= 本周周一 00:00
                            .lt("create_time", weekEnd.plusDays(1).atStartOfDay()); // create_time < 下周一 00:00;
                    XunJianHistoryEntity xunJianHistoryEntity = new XunJianHistoryEntity().selectOne(queryWrapper2);
                    if (xunJianHistoryEntity == null) {
                        isInsert = true;
                    }
                } else {
                    int max = x.getDayRange().stream().mapToInt(Integer::intValue).max().orElse(Integer.MIN_VALUE);
                    if (currentDayOfWeek > max) {
                        QueryWrapper<XunJianHistoryEntity> queryWrapper2 = new QueryWrapper<>();
                        queryWrapper2.eq("xun_jian_id", x.getXunJianId())
                                .eq("status", "巡检中")
                                .ge("create_time", weekStart.atStartOfDay()) // create_time >= 本周周一 00:00
                                .lt("create_time", weekEnd.plusDays(1).atStartOfDay()); // create_time < 下周一 00:00;
                        XunJianHistoryEntity xunJianHistoryEntity = new XunJianHistoryEntity().selectOne(queryWrapper2);
                        if (xunJianHistoryEntity != null) {
                            isStop = true;
                        }
                    }
                }

            }

            if (x.getXunJianPinLu().equals("每月")) {
                // 本月第一天
                LocalDate monthStart = today.withDayOfMonth(1);
                // 下个月第一天（作为本月结束边界）
                LocalDate nextMonthStart = monthStart.plusMonths(1);
                if (x.getDayRange().contains(currentDayOfMonth - 1)) {
                    // 判断今天是否已有记录
                    QueryWrapper<XunJianHistoryEntity> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.eq("xun_jian_id", x.getXunJianId())
                            .eq("status", "巡检中")
                            .ge("create_time", monthStart.atStartOfDay()) // create_time >= 本周周一 00:00
                            .lt("create_time", nextMonthStart.atStartOfDay()); // create_time < 下周一 00:00;
                    XunJianHistoryEntity xunJianHistoryEntity = new XunJianHistoryEntity().selectOne(queryWrapper2);
                    if (xunJianHistoryEntity == null) {
                        isInsert = true;
                    }
                } else {
                    // 判断今天是否已有记录
                    QueryWrapper<XunJianHistoryEntity> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.eq("xun_jian_id", x.getXunJianId())
                            .eq("status", "巡检中")
                            .ge("create_time", monthStart.atStartOfDay()) // create_time >= 本周周一 00:00
                            .lt("create_time", nextMonthStart.atStartOfDay()); // create_time < 下周一 00:00;
                    XunJianHistoryEntity xunJianHistoryEntity = new XunJianHistoryEntity().selectOne(queryWrapper2);
                    if (xunJianHistoryEntity != null) {
                        isStop = true;
                    }
                }
            }

            if (isInsert) {
                XunJianHistoryEntity xunJianHistoryEntity = new XunJianHistoryEntity();
                xunJianHistoryEntity.setDayRange(x.getDayRange());
                xunJianHistoryEntity.setFanWei(x.getFanWei());
                xunJianHistoryEntity.setStatus("巡检中");
                xunJianHistoryEntity.setTimeRange(x.getTimeRange());
                xunJianHistoryEntity.setXunJianId(x.getXunJianId());
                xunJianHistoryEntity.setXunJianLeiXing(x.getXunJianLeiXing());
                xunJianHistoryEntity.setXunJianPinLu(x.getXunJianPinLu());
                xunJianHistoryEntity.insert();
            }

            if (isStop) {
                UpdateWrapper<XunJianHistoryEntity> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("status", "巡检中").eq("xun_jian_id", x.getXunJianId()).set("status", "已完成");
                new XunJianHistoryEntity().update(updateWrapper);
            }
        }
    }

}
