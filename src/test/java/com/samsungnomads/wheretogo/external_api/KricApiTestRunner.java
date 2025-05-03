package com.samsungnomads.wheretogo.external_api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 철도공사 API 연결 테스트 러너
 * 🧪 DNS 캐시 초기화 및 JVM 재시작을 통한 API 연결 안정성 테스트
 */
public class KricApiTestRunner {
    private static final int TEST_COUNT = 20;
    private static final int DELAY_SECONDS = 3;
    
    // 테스트 결과 카운터
    private static int dnsFailures = 0;
    private static int httpFailures = 0;
    private static int successCount = 0;
    private static int otherErrors = 0;
    
    public static void main(String[] args) {
        System.out.println("===== 철도공사 API 연결 테스트 시작 (JVM 재시작 포함) =====");
        System.out.println("총 " + TEST_COUNT + "회 테스트 실행, 각 테스트 간 " + DELAY_SECONDS + "초 지연");
        System.out.println("========================================\n");
        
        // 현재 클래스패스 가져오기
        String classpath = System.getProperty("java.class.path");
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
        
        for (int i = 1; i <= TEST_COUNT; i++) {
            System.out.println("\n----- 테스트 #" + i + " 시작 -----");
            
            try {
                // DNS 캐시 초기화
                clearDnsCache();
                
                // 새 JVM에서 API 테스트 실행
                ProcessBuilder pb = new ProcessBuilder(
                        javaBin, 
                        "-cp", 
                        classpath, 
                        "com.samsungnomads.wheretogo.external_api.KricApiDnsTest");
                
                Process process = pb.start();
                
                // 출력 스트림 읽기
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));
                
                String line;
                String testResult = "";
                List<String> outputLines = new ArrayList<>();
                
                while ((line = reader.readLine()) != null) {
                    outputLines.add(line);
                    if (line.startsWith("TEST_END:")) {
                        testResult = line.substring(9);
                    }
                }
                
                // 오류 스트림 읽기
                BufferedReader errorReader = new BufferedReader(
                        new InputStreamReader(process.getErrorStream()));
                
                List<String> errorLines = new ArrayList<>();
                while ((line = errorReader.readLine()) != null) {
                    errorLines.add(line);
                }
                
                // 프로세스 종료 대기
                int exitCode = process.waitFor();
                
                // 결과 출력
                for (String outLine : outputLines) {
                    System.out.println("  " + outLine);
                }
                
                if (!errorLines.isEmpty()) {
                    System.out.println("  오류 출력:");
                    for (String errLine : errorLines) {
                        System.out.println("  " + errLine);
                    }
                }
                
                // 결과 분석
                System.out.println("  테스트 종료 코드: " + exitCode);
                System.out.println("  테스트 결과: " + (testResult.isEmpty() ? "알 수 없음" : testResult));
                
                // 결과 카운트
                if ("SUCCESS".equals(testResult)) {
                    successCount++;
                } else if ("DNS_FAILURE".equals(testResult)) {
                    dnsFailures++;
                } else if ("HTTP_FAILURE".equals(testResult) || "HTTP_ERROR".equals(testResult)) {
                    httpFailures++;
                } else {
                    otherErrors++;
                }
                
            } catch (Exception e) {
                System.out.println("  테스트 실행 중 오류: " + e.getMessage());
                e.printStackTrace();
                otherErrors++;
            }
            
            // 다음 테스트 전 대기
            if (i < TEST_COUNT) {
                System.out.println("  다음 테스트까지 " + DELAY_SECONDS + "초 대기 중...");
                try {
                    TimeUnit.SECONDS.sleep(DELAY_SECONDS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        
        // 최종 결과 요약
        System.out.println("\n===== 테스트 결과 요약 =====");
        System.out.println("총 테스트 횟수: " + TEST_COUNT);
        System.out.println("DNS 해석 실패: " + dnsFailures + " (" + formatPercent(dnsFailures) + "%)");
        System.out.println("HTTP 연결 실패: " + httpFailures + " (" + formatPercent(httpFailures) + "%)");
        System.out.println("성공 횟수: " + successCount + " (" + formatPercent(successCount) + "%)");
        System.out.println("기타 오류: " + otherErrors + " (" + formatPercent(otherErrors) + "%)");
    }
    
    private static String formatPercent(int count) {
        return String.format("%.1f", (count * 100.0 / TEST_COUNT));
    }
    
    private static void clearDnsCache() {
        try {
            String osName = System.getProperty("os.name").toLowerCase();
            Process process;
            
            if (osName.contains("win")) {
                // Windows
                process = Runtime.getRuntime().exec("ipconfig /flushdns");
                System.out.println("  Windows DNS 캐시 초기화 명령 실행");
            } else if (osName.contains("mac")) {
                // macOS
                process = Runtime.getRuntime().exec("sudo killall -HUP mDNSResponder");
                System.out.println("  macOS DNS 캐시 초기화 명령 실행");
            } else if (osName.contains("nix") || osName.contains("nux")) {
                // Linux (다양한 시스템에 따라 다름)
                process = Runtime.getRuntime().exec("sudo systemd-resolve --flush-caches");
                System.out.println("  Linux DNS 캐시 초기화 명령 실행");
            } else {
                System.out.println("  지원하지 않는 운영체제: " + osName);
                return;
            }
            
            int exitCode = process.waitFor();
            System.out.println("  DNS 캐시 초기화 종료 코드: " + exitCode);
            
        } catch (Exception e) {
            System.out.println("  DNS 캐시 초기화 중 오류: " + e.getMessage());
        }
    }
} 