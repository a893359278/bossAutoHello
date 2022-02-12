package com.csp.boss.hello.service;

import com.csp.boss.hello.model.request.PostHelloRequest;
import com.csp.boss.hello.model.response.AccountJobInfoModel;
import com.csp.boss.hello.model.response.CommonResponse;
import com.csp.boss.hello.model.response.PostHelloModel;

import java.util.List;

public abstract class AbstractBossHelloService implements BossHelloService {
    protected String cookie;

    protected static final String EMPLOYEE_INFO_URL = "https://www.zhipin.com/wapi/zpjob/rec/geek/list";

    protected static final String JOB_INFO_URL = "https://www.zhipin.com/wapi/zpjob/job/search/job/list";

    protected static final String HELLO_URL = "https://www.zhipin.com/wapi/zpboss/h5/chat/start?_";


    @Override
    public List<AccountJobInfoModel> getJobInfos(Long currentTimeStamp) {
        return doGetJobInfos(JOB_INFO_URL + "?_"+ currentTimeStamp);
    }

    protected abstract List<AccountJobInfoModel> doGetJobInfos(String requestUrl);


    @Override
    public CommonResponse<PostHelloModel> postHello(PostHelloRequest request) {

        return doPostHello(HELLO_URL + System.currentTimeMillis(), request);
    }

    protected abstract CommonResponse<PostHelloModel> doPostHello(String url, PostHelloRequest request);

    @Override
    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}
