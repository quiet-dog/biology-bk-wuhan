package com.biology.domain.manage.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.domain.manage.door.command.AddDoorCommand;
import com.biology.domain.manage.door.db.DoorEntity;
import com.biology.domain.manage.door.db.DoorService;
import com.biology.domain.manage.door.model.DoorFactory;
import com.biology.domain.manage.door.model.DoorModel;
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
import com.biology.domain.manage.task.db.MaterialsTaskEntity;
import com.biology.domain.manage.task.db.MaterialsTaskService;
import com.biology.domain.manage.websocket.db.WebsocketService;

import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TaskApplicationService {

    private final MaterialsTaskService materialsTaskService;

    private final MaterialsService materialsService;

    private final WebsocketService websocketService;

    private final GosipClientService gosipClientService;

    private final PersonnelService personnelService;

    private final PersonnelFactory personnelFactory;

    private final KoalaService koalaService;

    private final DoorFactory doorFactory;

    private final DoorService doorService;

    private static Boolean isDoorRunning = false;

    private static Integer page = 1;

    private static Integer size = 10000;

    private static Boolean isSubjectRunning = false;

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

}
