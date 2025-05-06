package com.biology.domain.manage.koala.dto.records;

import lombok.Data;

@Data
public class KoalaRecordDTO {
    private Integer clock_in;
    private String worktime;
    private Integer id;
    private Integer check_in_time;
    private Integer clock_out;
    private Long date;
    private Integer check_out_time;
    private KoalaSubjectDTO subject;
}
