package com.csp.boss.hello.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.csp.boss.hello.model.request.EmployeeInfoRequest;
import com.csp.boss.hello.model.request.PostHelloRequest;
import com.csp.boss.hello.model.response.*;
import com.csp.boss.hello.service.AbstractBossHelloService;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基于 hutool 实现的 boss 自动打招呼
 */
@Slf4j
public class HutoolBossHelloService extends AbstractBossHelloService {

    private static final String USER_AGENT = "PostmanRuntime/7.26.8";

    private TypeReference<CommonResponse<EmployeeInfoModel>> employeeInfoTypeReference = new TypeReference<CommonResponse<EmployeeInfoModel>>() {
    };

    private TypeReference<CommonResponse<PostHelloModel>> postHelloTypeReference = new TypeReference<CommonResponse<PostHelloModel>>() {
    };

    private TypeReference<ListResponse<AccountJobInfoModel>> jobInfoTypeReference = new TypeReference<ListResponse<AccountJobInfoModel>>() {
    };

    @Override
    protected List<AccountJobInfoModel> doGetJobInfos(String requestUrl) {
        log.info("url [{}]", requestUrl);
        String result = HttpRequest.get(requestUrl)
                .header(Header.COOKIE, cookie)
                .header(Header.USER_AGENT, USER_AGENT)
                .execute().body();
        ListResponse<AccountJobInfoModel> response = JSON.parseObject(result, jobInfoTypeReference);
        return response.getZpData();
    }

    @Override
    protected CommonResponse<PostHelloModel> doPostHello(String url, PostHelloRequest request) {
        log.info("url [{}], param [{}]", url, JSON.toJSONString(request));
        Map<String, Object> param = new HashMap<>();
        BeanUtil.copyProperties(request, param);
        String result = HttpRequest.post(url)
                .form(param)
//                .body(JSON.toJSONString(request))
                .header(Header.COOKIE, cookie)
                .header(Header.USER_AGENT, USER_AGENT)
                .execute().body();
        return JSON.parseObject(result, postHelloTypeReference);
    }

    @Override
    public CommonResponse<EmployeeInfoModel> getEmployeeInfos(EmployeeInfoRequest request) {
        log.info("url [{}], param [{}]", EMPLOYEE_INFO_URL, JSON.toJSONString(request));

        Map<String, Object> param = new HashMap<>();
        BeanUtil.copyProperties(request, param);

        String result = HttpRequest.get(EMPLOYEE_INFO_URL).body(HttpUtil.toParams(param))
                .header(Header.COOKIE, cookie)
                .header(Header.USER_AGENT, USER_AGENT)
                .execute().body();
        return JSON.parseObject(result, employeeInfoTypeReference);
    }

}
