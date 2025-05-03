# MemberService 테스트 가이드 📘

## 개요 🌟
이 문서는 `MemberService`의 테스트 코드 실행 방법과 각 테스트의 목적을 설명합니다.

## 테스트 종류 🧪

### 1. 단위 테스트 (MemberServiceTest)
- JWT 토큰을 사용한 인증 상태에서 `MemberService`의 각 메서드를 단위 테스트합니다.
- Mockito를 사용하여 외부 의존성을 모킹하여 격리된 환경에서 테스트합니다.

### 2. 통합 테스트 (MemberServiceIntegrationTest)
- 실제 데이터베이스와 연동하여 `MemberService`의 각 메서드를 통합 테스트합니다.
- Spring Security 인증 컨텍스트와 JWT 토큰을 사용합니다.
- `@Transactional` 어노테이션을 사용하여 테스트 후 롤백합니다.

## 테스트 실행 방법 🚀

### 단위 테스트 실행
```bash
# Windows
./gradlew.bat test --tests "com.samsungnomads.wheretogo.domain.member.service.MemberServiceTest"

# Linux/MacOS
./gradlew test --tests "com.samsungnomads.wheretogo.domain.member.service.MemberServiceTest"
```

### 통합 테스트 실행
```bash
# Windows
./gradlew.bat test --tests "com.samsungnomads.wheretogo.domain.member.service.MemberServiceIntegrationTest"

# Linux/MacOS
./gradlew test --tests "com.samsungnomads.wheretogo.domain.member.service.MemberServiceIntegrationTest"
```

### 모든 테스트 실행
```bash
# Windows
./gradlew.bat test

# Linux/MacOS
./gradlew test
```

## 테스트 케이스 설명 📝

### 단위 테스트 케이스
1. `joinSuccess` - 회원 가입 성공 테스트
2. `joinFailWithDuplicateLoginId` - 중복 아이디 회원 가입 실패 테스트
3. `joinFailWithDuplicateEmail` - 중복 이메일 회원 가입 실패 테스트
4. `findMembersTest` - 모든 회원 조회 테스트
5. `findOneByIdSuccess` - ID로 회원 조회 성공 테스트
6. `findOneByIdFail` - ID로 회원 조회 실패 테스트
7. `findByLoginIdSuccess` - 로그인 아이디로 회원 조회 성공 테스트
8. `findByLoginIdFail` - 로그인 아이디로 회원 조회 실패 테스트
9. `findByEmailSuccess` - 이메일로 회원 조회 성공 테스트
10. `findByEmailFail` - 이메일로 회원 조회 실패 테스트
11. `updateMemberTest` - 회원 정보 업데이트 테스트
12. `deleteMemberSuccess` - 회원 삭제 성공 테스트
13. `deleteMemberFail` - 회원 삭제 실패 테스트
14. `isLoginIdAvailableTest` - 아이디 중복 검사 테스트
15. `isEmailAvailableTest` - 이메일 중복 검사 테스트

### 통합 테스트 케이스
1. `joinIntegrationTest` - 회원 가입 통합 테스트
2. `duplicateMemberJoinIntegrationTest` - 중복 회원 가입 통합 테스트
3. `findMemberIntegrationTest` - 회원 조회 통합 테스트
4. `findByLoginIdIntegrationTest` - 로그인 아이디로 회원 조회 통합 테스트
5. `findByEmailIntegrationTest` - 이메일로 회원 조회 통합 테스트
6. `findAllMembersIntegrationTest` - 모든 회원 조회 통합 테스트
7. `updateMemberIntegrationTest` - 회원 정보 업데이트 통합 테스트
8. `deleteMemberIntegrationTest` - 회원 삭제 통합 테스트
9. `findNonExistentMemberIntegrationTest` - 존재하지 않는 회원 조회 통합 테스트
10. `isLoginIdAvailableIntegrationTest` - 아이디 중복 검사 통합 테스트
11. `isEmailAvailableIntegrationTest` - 이메일 중복 검사 통합 테스트
12. `authenticatedMemberOperationsTest` - JWT 토큰으로 인증된 상태에서 멤버 조회 테스트

## 참고 사항 📌
- 테스트를 실행하기 전에 프로젝트의 의존성이 모두 설치되어 있어야 합니다.
- 통합 테스트는 테스트용 데이터베이스를 사용하므로 `application-test.yml` 설정을 확인하세요.
- 테스트 실행 중 JWT 관련 설정이 올바르게 되어 있어야 합니다. 