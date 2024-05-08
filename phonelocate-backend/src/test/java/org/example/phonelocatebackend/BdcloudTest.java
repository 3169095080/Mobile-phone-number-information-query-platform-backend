package org.example.phonelocatebackend;

import com.baidubce.http.ApiExplorerClient;
import com.baidubce.http.AppSigner;
import com.baidubce.http.HttpMethodName;
import com.baidubce.model.ApiExplorerRequest;
import com.baidubce.model.ApiExplorerResponse;

// 手机号归属地查询 示例代码
public class BdcloudTest {
    public static void main(String[] args) {
        String path = "http://qrytel.api.bdymkt.com/lundear/qrytel";
        ApiExplorerRequest request = new ApiExplorerRequest(HttpMethodName.GET, path);
        request.setCredentials("c7e2a156ecba47bf813090cb43dc8e3d", "f88ece0b0c524fc3a1beb7bf4add77a2");

        // 设置header参数
        request.addHeaderParameter("Content-Type", "application/json; charset=utf-8");

        // 设置query参数
        request.addQueryParameter("mobile", "18147592218");


        ApiExplorerClient client = new ApiExplorerClient(new AppSigner());

        try {
            ApiExplorerResponse response = client.sendRequest(request);
            // 返回结果格式为Json字符串
            System.out.println(response.getResult());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
