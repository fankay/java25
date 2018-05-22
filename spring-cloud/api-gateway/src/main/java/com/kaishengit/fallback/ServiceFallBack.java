package com.kaishengit.fallback;

import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class ServiceFallBack implements ZuulFallbackProvider {
    /**
     * 容错的服务名称，* 表示所有的服务
     * @return
     */
    @Override
    public String getRoute() {
        return "*";
    }

    /**
     * 如果发生容错，给客户端返回的结果
     * @return
     */
    @Override
    public ClientHttpResponse fallbackResponse() {
        return new ClientHttpResponse() {
            /**
             * getStatusCode() getRawStatusCode() getStatusText()
             * 这三个方法表示给客户端返回的HTTP状态
             * @return
             * @throws IOException
             */
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return getStatusCode().value(); // 200
            }

            @Override
            public String getStatusText() throws IOException {
                return getStatusCode().getReasonPhrase();//"ok";
            }

            @Override
            public void close() {

            }

            /**
             * 给客户端返回的内容
             * @return
             * @throws IOException
             */
            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream("服务暂时不可用".getBytes());
            }

            /**
             * 给客户端返回的响应头
             * @return
             */
            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.add("Content-Type","application/json;charset=UTF-8");
                return httpHeaders;
            }
        };
    }
}
