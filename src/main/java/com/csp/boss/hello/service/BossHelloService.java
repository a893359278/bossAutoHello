package com.csp.boss.hello.service;

import com.csp.boss.hello.model.request.EmployeeInfoRequest;
import com.csp.boss.hello.model.request.PostHelloRequest;
import com.csp.boss.hello.model.response.AccountJobInfoModel;
import com.csp.boss.hello.model.response.CommonResponse;
import com.csp.boss.hello.model.response.EmployeeInfoModel;
import com.csp.boss.hello.model.response.PostHelloModel;

import java.util.List;

public interface BossHelloService {

    void setCookie(String cookie);

    /**
     * 发生 get 请求 获取工作列表
     * @param currentTimeStamp
     * @return
     */
    List<AccountJobInfoModel> getJobInfos(Long currentTimeStamp);

    /**
     * 发送 get 请求 获取 boss 直聘雇员信息.
     * @param request
     * @return
     */
    CommonResponse<EmployeeInfoModel> getEmployeeInfos(EmployeeInfoRequest request);


    /**
     * 发送 post 请求 向 牛人打招呼.
     * @return
     */
    CommonResponse<PostHelloModel> postHello(PostHelloRequest request);
}
