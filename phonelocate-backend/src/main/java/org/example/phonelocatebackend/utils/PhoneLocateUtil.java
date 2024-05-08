package org.example.phonelocatebackend.utils;

import com.baidubce.http.ApiExplorerClient;
import com.baidubce.http.AppSigner;
import com.baidubce.http.HttpMethodName;
import com.baidubce.model.ApiExplorerRequest;
import com.baidubce.model.ApiExplorerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
public class PhoneLocateUtil {

    private static final Logger logger = LoggerFactory.getLogger(PhoneLocateUtil.class);

    @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public static String getPhoneLocation(String phoneNum) {
        String path = "http://qrytel.api.bdymkt.com/lundear/qrytel";
        ApiExplorerRequest request = new ApiExplorerRequest(HttpMethodName.GET, path);
        request.setCredentials("c7e2a156ecba47bf813090cb43dc8e3d", "f88ece0b0c524fc3a1beb7bf4add77a2");

        // 设置header参数
        request.addHeaderParameter("Content-Type", "application/json; charset=utf-8");

        // 设置query参数
        request.addQueryParameter("mobile", phoneNum);

        ApiExplorerClient client = new ApiExplorerClient(new AppSigner());

        try {
            ApiExplorerResponse response = client.sendRequest(request);
            // 返回结果格式为Json字符串
            return response.getResult();
        } catch (Exception e) {
            logger.error("Failed to get phone location for phone number: {}", phoneNum, e);
            return "";
        }
    }
}
