package com.vienna.jaray;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.vienna.jaray.alibaba.dysms.SmsUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class SmsApplicationTests {
    @Autowired
    private SmsUtil smsUtil;

    @Test
    void contextLoads() {
    }

    @Test
    public void sendSms(){
        //发短信
        SendSmsResponse response = smsUtil.sendSms();
        log.info("短信接口返回的数据----------------");
        log.info("Code=" + response.getCode());
        log.info("Message=" + response.getMessage());
        log.info("RequestId=" + response.getRequestId());
        log.info("BizId=" + response.getBizId());

        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            log.error("异常", e);

        }

        //查明细
        if(response.getCode() != null && response.getCode().equals("OK")) {
            QuerySendDetailsResponse querySendDetailsResponse = null;
            try {
                querySendDetailsResponse = smsUtil.querySendDetails(response.getBizId());
            } catch (ClientException e) {
                log.error("客户端异常", e);
            }
            log.info("短信明细查询接口返回数据----------------");
            log.info("Code=" + querySendDetailsResponse.getCode());
            log.info("Message=" + querySendDetailsResponse.getMessage());
            int i = 0;
            for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
            {
                log.info("SmsSendDetailDTO["+i+"]:");
                log.info("Content=" + smsSendDetailDTO.getContent());
                log.info("ErrCode=" + smsSendDetailDTO.getErrCode());
                log.info("OutId=" + smsSendDetailDTO.getOutId());
                log.info("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
                log.info("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
                log.info("SendDate=" + smsSendDetailDTO.getSendDate());
                log.info("SendStatus=" + smsSendDetailDTO.getSendStatus());
                log.info("Template=" + smsSendDetailDTO.getTemplateCode());
            }
            log.info("TotalCount=" + querySendDetailsResponse.getTotalCount());
            log.info("RequestId=" + querySendDetailsResponse.getRequestId());
        }
    }

}
