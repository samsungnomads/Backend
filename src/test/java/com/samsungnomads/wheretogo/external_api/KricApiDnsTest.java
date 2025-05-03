package com.samsungnomads.wheretogo.external_api;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 철도공사 API 호스트 DNS 해석 및 HTTP 연결 테스트
 * 🔍 openapi.kric.go.kr 도메인의 DNS 해석 및 API 호출 테스트
 */
public class KricApiDnsTest {
    private static final String API_HOST = "openapi.kric.go.kr";
    private static final String API_URL = "https://openapi.kric.go.kr/openapi/trainUseInfo/subwayRouteInfo?serviceKey=$2a$10$.P03IDI0V3/IewJhrarByeBimfbBi.doplIJyt9zJ/Ta5h0j9HQPi&format=json&mreaWideCd=01&lnCd=1";

    public static void main(String[] args) {
        System.out.println("TEST_START");
        
        try {
            // DNS 해석 테스트
            long dnsStartTime = System.currentTimeMillis();
            try {
                InetAddress address = InetAddress.getByName(API_HOST);
                long dnsEndTime = System.currentTimeMillis();
                System.out.println("DNS_SUCCESS:" + address.getHostAddress() + ":" + (dnsEndTime - dnsStartTime));
            } catch (UnknownHostException e) {
                System.out.println("DNS_FAILURE:" + e.getMessage());
                System.out.println("TEST_END:DNS_FAILURE");
                System.exit(1);
            }
            
            // HTTP 연결 테스트
            long httpStartTime = System.currentTimeMillis();
            try {
                URL url = new URL(API_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.setRequestMethod("GET");
                
                int responseCode = connection.getResponseCode();
                long httpEndTime = System.currentTimeMillis();
                
                if (responseCode >= 200 && responseCode < 300) {
                    System.out.println("HTTP_SUCCESS:" + responseCode + ":" + (httpEndTime - httpStartTime));
                    
                    // 응답 내용 첫 줄만 출력
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    String line = reader.readLine();
                    reader.close();
                    
                    System.out.println("RESPONSE:" + (line != null ? line.substring(0, Math.min(line.length(), 100)) : "empty"));
                    System.out.println("TEST_END:SUCCESS");
                } else {
                    System.out.println("HTTP_FAILURE:" + responseCode + ":" + (httpEndTime - httpStartTime));
                    System.out.println("TEST_END:HTTP_FAILURE");
                }
                
                connection.disconnect();
            } catch (IOException e) {
                System.out.println("HTTP_ERROR:" + e.getMessage());
                System.out.println("TEST_END:HTTP_ERROR");
            }
            
        } catch (Exception e) {
            System.out.println("GENERAL_ERROR:" + e.getMessage());
            System.out.println("TEST_END:GENERAL_ERROR");
        }
    }
} 