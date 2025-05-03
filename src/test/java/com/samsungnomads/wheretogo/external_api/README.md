# 철도공사 API 연결 테스트 🚆

## 개요
이 테스트 코드는 철도공사 API(`openapi.kric.go.kr`)에 대한 DNS 해석 및 HTTP 연결 테스트를 수행합니다. 특히 DNS 해석 실패로 인한 간헐적 오류를 검증하기 위해 설계되었습니다.

## 문제 상황 🔍
- 네트워크 환경 변경 시 DNS 캐시 초기화 후 철도공사 API 연결 오류 발생
- 이전 네트워크 DNS 캐시가 남아있을 경우 문제가 발생하지 않음
- 브라우저에서는 접속 가능하지만 애플리케이션에서는 실패하는 현상

## 테스트 파일 설명 📋

### KricApiDnsTest.java
단일 API 연결 테스트를 수행하는 클래스입니다.
- DNS 해석 테스트
- HTTP 연결 테스트
- 응답 데이터 확인

### KricApiTestRunner.java
DNS 캐시를 초기화하고 반복적으로 API 연결 테스트를 수행하는 클래스입니다.
- DNS 캐시 초기화
- 별도 JVM에서 테스트 실행 (JVM DNS 캐시 초기화 효과)
- 테스트 결과 수집 및 통계 제공

## 테스트 실행 방법 🚀

### 전제 조건
- JDK 11 이상 설치
- 관리자 권한 (DNS 캐시 초기화에 필요)

### IntelliJ에서 실행하기
1. KricApiDnsTest 클래스에서 마우스 오른쪽 버튼 클릭 > Run 'KricApiDnsTest.main()'
2. KricApiTestRunner 클래스에서 마우스 오른쪽 버튼 클릭 > Run 'KricApiTestRunner.main()' (관리자 권한 필요)

### 터미널에서 실행하기 🖥️

#### Windows 명령 프롬프트 (CMD)
```cmd
# 프로젝트 루트 디렉토리로 이동
cd C:\Users\사용자명\ 어쩌고~

# 테스트 클래스 컴파일
gradlew.bat compileTestJava

# 단일 DNS 테스트 실행
java -cp build/classes/java/test com.samsungnomads.wheretogo.external_api.KricApiDnsTest

# DNS 캐시 초기화 (관리자 권한 필요)
ipconfig /flushdns

# 반복 테스트 실행 (관리자 권한 필요)
java -cp build/classes/java/test com.samsungnomads.wheretogo.external_api.KricApiTestRunner
```

#### Windows PowerShell
```powershell
# 프로젝트 루트 디렉토리로 이동
cd C:\Users\사용자명\IdeaProjects\Backend (예시)

# 테스트 클래스 컴파일
.\gradlew.bat compileTestJava

# 단일 DNS 테스트 실행
java -cp build/classes/java/test com.samsungnomads.wheretogo.external_api.KricApiDnsTest

# DNS 캐시 초기화 (관리자 권한 필요)
ipconfig /flushdns

# 반복 테스트 실행 (관리자 권한 필요)
java -cp build/classes/java/test com.samsungnomads.wheretogo.external_api.KricApiTestRunner
```

#### Linux / macOS 터미널
```bash
# 프로젝트 루트 디렉토리로 이동
cd /path/to/project

# 테스트 클래스 컴파일
./gradlew compileTestJava

# 단일 DNS 테스트 실행
java -cp build/classes/java/test com.samsungnomads.wheretogo.external_api.KricApiDnsTest

# DNS 캐시 초기화 (관리자 권한 필요)
# Linux
sudo systemd-resolve --flush-caches
# macOS
sudo killall -HUP mDNSResponder

# 반복 테스트 실행 (관리자 권한 필요)
sudo java -cp build/classes/java/test com.samsungnomads.wheretogo.external_api.KricApiTestRunner
```

#### Gradle 사용
```bash
# Windows
gradlew.bat test --tests "com.samsungnomads.wheretogo.external_api.KricApiDnsTest"

# Linux / macOS
./gradlew test --tests "com.samsungnomads.wheretogo.external_api.KricApiDnsTest"
```

## 테스트 시나리오 📝
문제를 재현하기 위한 단계는 다음과 같습니다:

1. DNS 캐시 초기화 (`ipconfig /flushdns`)
2. 네트워크 전환 (다른 WiFi로 연결)
3. 다시 DNS 캐시 초기화
4. 브라우저에서 API URL 접속하여 오류 페이지 확인
5. 애플리케이션 실행 시 DNS 해석 오류 발생

## 테스트 결과 분석 📊
테스트 결과는 다음과 같이 출력됩니다:
- DNS 해석 실패율: DNS 서버가 도메인을 IP로 변환하지 못한 비율
- HTTP 연결 실패율: DNS는 해석되었지만 HTTP 연결에 실패한 비율
- 성공률: API 호출이 성공적으로 완료된 비율

## 해결 방안 💡
1. 애플리케이션 시작 시 DNS 해석 재시도 로직 추가
2. 호스트 파일에 도메인-IP 매핑 추가:
   ```
   211.250.27.207   openapi.kric.go.kr
   ```
3. RestTemplate 설정 개선:
   - 연결 타임아웃 증가
   - 재시도 메커니즘 추가
   
## 주의사항 ⚠️
- DNS 캐시 초기화를 위해 관리자 권한으로 실행해야 합니다.
- 일부 환경에서는 방화벽 설정으로 인해 테스트가 실패할 수 있습니다.
- API 키가 만료되었을 경우 HTTP 연결은 성공하지만 응답 코드가 오류일 수 있습니다.

## 문제 해결 팁 🔧

### 1. DNS 캐시 초기화 권한 문제
- Windows: 관리자 권한으로 명령 프롬프트 또는 PowerShell 실행
- Linux/macOS: sudo 권한으로 명령 실행

### 2. 테스트 컴파일 오류
- Gradle 프로젝트: `./gradlew compileTestJava` 명령어로 테스트 클래스 컴파일
- 클래스패스 확인: `java -cp` 옵션에 올바른 클래스패스 지정

### 3. 네트워크 관련 문제
- 방화벽 설정 확인
- 프록시 서버 설정 확인
- VPN 연결 상태 확인

### 4. Gradle 테스트 실행 오류
- `gradle --info test` 명령어로 상세 로그 확인
- `--debug-jvm` 옵션으로 JVM 디버깅 활성화 