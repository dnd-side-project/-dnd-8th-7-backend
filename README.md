<div align="center">
    <img src="https://user-images.githubusercontent.com/86864534/222874803-77f9a765-bd9c-44d9-8478-62699ed19294.png">
</div>

# 하루 블럭 [![codecov](https://codecov.io/gh/dnd-side-project/dnd-8th-7-backend/branch/main/graph/badge.svg?token=A0EO0JG772)](https://codecov.io/gh/dnd-side-project/dnd-8th-7-backend)

> ⏰ 하루를 블럭으로 나누어 복잡한 일상을 심플하게 만들어주는, 하루블럭

> DND 8기 7조
>
> 프로젝트 기간: 2023.01.08 ~ 2023.04

## 📚 Skill Stack

- **Front-end** : TypeScript, Next, React Native, Zustand, Tailwind, Storybook
- **Back-end** : Java 11, Spring boot, Spring Data Jpa, QueryDSL, JUnit, RestAssured,
  TestContainers, Jacoco
- **DB** : PostgreSql, Redis
- **Infra** : AWS Services(EC2, S3, RDS, Route53, CloudFront, ALB), Docker

## 📋 API Docs

[API Docs](https://www.notion.so/eunseong/API-ad7cd23937574300ad77c5ac7307919d)

## 🛠️ Architecture

### 백엔드

![backend](https://user-images.githubusercontent.com/86864534/229432488-f87568b2-21b0-45c5-b2e5-48df3aca2a43.png)

### CI/CD

![cicd](https://user-images.githubusercontent.com/86864534/229435221-cf4376e1-7024-4071-99c1-05a0f05174cc.png)

## 📈 ERD

![ERD](https://user-images.githubusercontent.com/86864534/229415245-d9a88a78-733d-45e7-a609-e2b90525dd83.png)

## ✍️ Process Docs

<details>
  <summary><b>User Login Process</b></summary>
  <img src="https://user-images.githubusercontent.com/86864534/222876245-a21fb974-dc5e-4dc2-8512-2bf93cc5b485.png" />

### 최초 구글 로그인 시

- redirect url을 통해 클라이언트 사이드에서 구글 로그인을 시도합니다.
- 로그인 성공 시, 서버의 successful 핸들러가 응답을 받습니다. 이에 따라 회원가입된 유저가 아닌 경우, 회원가입을 진행합니다.
- 로그인 성공 시, refresh token을 redis 세션 서버에 저장하고 클라이언트에 jwt token, 신규 유저 여부를 url 파라미터에 실어나서 반환합니다.

<br>

### 정상 API 호출 시

- header에 access token을 정상적으로 포함하고, 만료되지 않고 유효한 access token인 경우 정상적으로 api가 동작합니다.

<br>

### Access Token 재발급

- Access Token 만료 전에 Refresh 요청 시에, Refresh Token을 기반으로 token을 갱신하여 새로운 token을 반환합니다.

</details>
