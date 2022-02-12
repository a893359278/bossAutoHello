package com.csp.boss.hello.model.response;

import lombok.Data;

@Data
public class AccountJobInfoModel {
    private String jid;
    private String jobName;
    private String cityName;
    private String degreeName;
    private String workExperience;
    private String salary;
    private int jobType;
    private long cityCode;
}
