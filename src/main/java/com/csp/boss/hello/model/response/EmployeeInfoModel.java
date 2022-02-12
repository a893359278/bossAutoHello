package com.csp.boss.hello.model.response;

import lombok.Data;

import java.util.List;

@Data

public class EmployeeInfoModel {

    private int displayTraineeStyle;
    private int heightGray;
    private long cityCode;
    private int advantageShow;
    private boolean hasMore;
    private boolean displayAboard;
    private int jobExperience;
    private List<GeekList> geekList;
    private int displayBlueStyle;
    private String encryptJobId;
    private long jobId;
    private int startIndex;
    private boolean partTimeJob;
    private List<String> lstSelectedLabel;
    private int page;
    private boolean recommendABTest;

    @Data
    public static class CompanyHighlight {
        private List<String> indexList;
        private String content;
    }

    @Data
    public static class PositionNameHighlight {
        private List<String> indexList;
        private String content;
    }

    @Data
    public static class GeekLastWork {
        private int certStatus;
        private String endDate;
        private CompanyHighlight companyHighlight;
        private String industry;
        private int industryCode;
        private int customPositionId;
        private String positionName;
        private boolean current;
        private String responsibility;
        private int isPublic;
        private String company;
        private long id;
        private int customIndustryId;
        private boolean blueCollarPosition;
        private PositionNameHighlight positionNameHighlight;
        private int workMonths;
        private List<String> workEmphasisList;
        private String workTime;
        private int positionLv2;
        private String workPerformance;
        private int workType;
        private int position;
        private String positionCategory;
        private int geekId;
        private String startDate;
    }

    @Data
    public static class Feedback {
        private int code;
        private String memo;
        private int showType;
    }

    @Data
    public static class ShowEdus {
        private int eduType;
        private String endDate;
        private int degree;
        private String degreeName;
        private int userId;
        private String major;
        private String school;
        private int schoolId;
        private int id;
        private String startDate;
    }

    @Data
    public static class ShowWorks {
        private int certStatus;
        private String endDate;
        private CompanyHighlight companyHighlight;
        private String industry;
        private int industryCode;
        private int customPositionId;
        private String positionName;
        private boolean current;
        private String responsibility;
        private int isPublic;
        private String company;
        private long id;
        private int customIndustryId;
        private boolean blueCollarPosition;
        private PositionNameHighlight positionNameHighlight;
        private int workMonths;
        private List<String> workEmphasisList;
        private String workTime;
        private int positionLv2;
        private String workPerformance;
        private int workType;
        private int position;
        private String positionCategory;
        private int geekId;
        private String startDate;
    }

    @Data
    public static class GeekWorks {
        private int certStatus;
        private String endDate;
        private CompanyHighlight companyHighlight;
        private String industry;
        private int industryCode;
        private int customPositionId;
        private String positionName;
        private boolean current;
        private String responsibility;
        private int isPublic;
        private String company;
        private long id;
        private int customIndustryId;
        private boolean blueCollarPosition;
        private PositionNameHighlight positionNameHighlight;
        private int workMonths;
        private List<String> workEmphasisList;
        private String workTime;
        private int positionLv2;
        private String workPerformance;
        private int workType;
        private int position;
        private String positionCategory;
        private int geekId;
        private String startDate;
    }

    @Data
    public static class GeekEdus {
        private int eduType;
        private String endDate;
        private int degree;
        private String degreeName;
        private int userId;
        private String major;
        private String school;
        private int schoolId;
        private int id;
        private String startDate;
    }

    @Data
    public static class GeekEdu {
        private int eduType;
        private String endDate;
        private int degree;
        private String degreeName;
        private int userId;
        private String major;
        private String school;
        private int schoolId;
        private int id;
        private String startDate;
    }

    @Data
    public static class MiddleContent {
        private List<String> indexList;
        private String content;
    }

    @Data
    public static class GeekDesc {
        private List<String> indexList;
        private String content;
    }

    @Data
    public static class AgeLight {
        private boolean highlight;
        private String content;
    }

    @Data
    public static class GeekCard {
        private String birthday;
        private long expectLocationCode;
        private String encryptGeekId;
        private String lid;
        private String geekAvatar;
        private String expectPositionName;
        private long expectId;
        private String salary;
        private int lowSalary;
        private String expectPositionNameLv2;
        private String encryptJobId;
        private long expectPositionCode;
        private int expectType;
        private int freshGraduate;
        private String geekDegree;
        private List<GeekWorks> geekWorks;
        private boolean viewed;
        private String expectLocationName;
        private int completeType;
        private int expectSubLocation;
        private String ageDesc;
        private long activeTime;
        private int eliteGeek;
        private String securityId;
        private String geekWorkYear;
        private String applyStatusDesc;
        private int geekSource;
        private int geekGender;
        private List<GeekEdus> geekEdus;
        private GeekEdu geekEdu;
        private long jobId;
        private MiddleContent middleContent;
        private int highSalary;
        private GeekDesc geekDesc;
        private AgeLight ageLight;
        private int expectPositionType;
        private int applyStatus;
        private long geekId;
        private String geekName;
    }

    @Data
    public static class GeekList {
        private String encryptGeekId;
        private int geekCallStatus;
        private boolean friendGeek;
        private int cooperate;
        private int blur;
        private GeekLastWork geekLastWork;
        private int anonymousGeek;
        private List<Feedback> feedback;
        private int searchChatCardCostCount;
        private int shareMessage;
        private int haveChatted;
        private int hasBg;
        private int isFriend;
        private boolean showSelectJob;
        private List<ShowEdus> showEdus;
        private boolean canUseDirectCall;
        private List<ShowWorks> showWorks;
        private int hasAttachmentResume;
        private boolean hasJobCompetitive;
        private boolean blurGeek;
        private String activeTimeDesc;
        private String feedbackTitle;
        private GeekCard geekCard;
    }
}