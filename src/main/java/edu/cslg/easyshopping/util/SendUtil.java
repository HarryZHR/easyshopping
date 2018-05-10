package edu.cslg.easyshopping.util;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;

public class SendUtil {
    public static void send(String tel, String validationCode){
        try {
            SendSmsResponse response = ValidationCodeSend.sendSms(tel,validationCode ,"SMS_102315072");
            System.out.println("短信接口返回的数据----------------");
            System.out.println("Code=" + response.getCode());
            System.out.println("Message=" + response.getMessage());
            System.out.println("RequestId=" + response.getRequestId());
            System.out.println("BizId=" + response.getBizId());
        }catch (ClientException e){
            e.printStackTrace();
        }
    }
}
