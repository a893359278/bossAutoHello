package com.csp.boss.hello.model.request;

import lombok.Data;

@Data
public class EmployeeInfoRequest {
    private String age = "16,-1";
    private int gender = 0;
    private int exchangeResumeWithColleague = 0;
    private int switchJobFrequency = 0;
    private int activation = 0;
    private int recentNotView = 0;
    private int school = 0;
    private int major;
    private String experience;
    private String degree;
    private String salary;
    private String intention;
    private String jobId;
    // 从 1 开始
    private int page;
}
