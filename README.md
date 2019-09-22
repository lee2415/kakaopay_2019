# kakaoTest
카카오페이 경력 과제

# 개발 프레임워크 
구분|제품명|Version
--|--|--
|개발언어|Java|1.8
| DBMS | H2 |1.4.197   
| Framework | Spring Boot | 2.1.8.RELEASE 

# 문제해결 전략
1. JWT 인증
- 재발급 요청 시 기존에 발급한 Token을 입력해야 하는데, 해당 Token이 만료될 경우 내부 정보 조회가 불가하여 만료될 경우에는 재 로그인으로 기준 설정

2. json return의 경우 모두 200 으로 return하고, return 같이 보내는 code값으로 판단하여 에러 처리 진행

3. api return형태를 맞추기 위해서 쿼리가 복잡해지고 모든 쿼리를 반영하는것보다는, 조회 후 return형식에 맞게 변경하도록 하는 방향이 효율적으로 판단되어 return을 위한 유틸을 생성하여 결과값에 반영

4. JPA 만으로 결과값 조회가 어려운 케이스에 QueryDSL을 사용하여 진행함.

5. 요구사항에 있는 Json return 형식을 맞추기 위해 JsonProperty, JsonView, JsonAnyGetter를 사용

# 빌드 및 실행 방법
### 빌드 방법
Maven을 사용하여 빌드 진행

    mvn clean package

위 결과로 생성된 파일 실행

    java -jar target/test-0.0.1-SNAPSHOT.jar

기본 접속 URL : http://localhost:8080

H2 DB Console 접속 URL : http://localhost:8080/h2-console/

    Driver Class : org.h2.Driver
    JDBC URL : jdbc:h2:~/pay
    USER NAME : pay
    Passworld : 



# API 정의
API 입력/출력은 모두 json 형태로 진행.

#### 공통 output sample
    정상일 경우 아래와 같이 공통 return 메시지와 나머지 정보를 같이 return 한다.
    {
        "code": "00",
        "message": ""
    }

    실패일 경우 code와 메시지를 아래와 같이 출력
    {
        "error": {
            "code": "ERROR.MEMBER",
            "message": "회원 가입을 위한 필수값이 부족합니다."
        }
    }

* * *

## 회원 가입 관련 API
1. **회원 가입**

구분|값
--|--
URL|/member/signup
method|POST
API 설명|userID와 userPass를 입력 받아 회원 가입을 진행하는 API 회원 가입 후 인증에 대한 Token을 헤더에 담아 발행해 준다. 

#### input example
    {
	    "userId":"leel2415",
	    "userPass":"12341234"
    }

#### output exampel
    {
        "code": "00",
        "message": ""
    }
#### output header example
    Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhcGlBdXRoIjp0cnVlLCJleHAiOjE1NTQzNjc0OTcsInVzZXJJZCI6ImxlZWwyNDE1IiwiaWF0IjoxNTU0MzYzODk3fQ.I0fb4JSWeX6F5QzptoaN6Uq-vq1-uCHS_9SEQ8XwtUY
    Content-Type: application/json;charset=UTF-8
    Transfer-Encoding: chunked
    Date: Thu, 04 Apr 2019 07:44:57 GMT

* * *

2. **로그인**

구분|값
--|--
URL|/member/signin
method|POST
API 설명|userID와 userPass를 입력 받아 로그인 처리를 진행하는 API, 로그인 후 인증을 위한 Token을 헤더에 담아 return 함.

#### input example
    {
	    "userId":"leel2415",
	    "userPass":"12341234"
    }

#### output exampel
    {
        "code": "00",
        "message": ""
    }
#### output header 
    Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhcGlBdXRoIjp0cnVlLCJleHAiOjE1NTQ2MjM3NzMsInVzZXJJZCI6ImxlZWwyNDE1IiwiaWF0IjoxNTU0NjIwMTczfQ.XbYF1pAjwPIbCHEB78vC2HSxrmg63wVH6LaP-Vp8fU4
    Content-Type: application/json;charset=UTF-8
    Transfer-Encoding: chunked
    Date: Sun, 07 Apr 2019 06:56:14 GMT

* * *

3. **Token 재발급 요청 API**

구분|값
--|--
URL|/member/refresh
method|POST
API 설명|기존 토큰을 Header에 담아 요청 하게되면, 해당 Token에 대한 정보를 이용하여 Token 을 재발행, 만료된 토큰의 경우에는 로그인을 다시 해야함. 

#### input example
    별도의 input 불필요
    {
    }

#### input header example
    Authorization : Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhcGlBdXRoIjp0cnVlLCJleHAiOjE1NTQ2MjM3NzMsInVzZXJJZCI6ImxlZWwyNDE1IiwiaWF0IjoxNTU0NjIwMTczfQ.XbYF1pAjwPIbCHEB78vC2HSxrmg63wVH6LaP-Vp8fU4
    Content-Type : application/json

#### output example
    {
        "code": "00",
        "message": ""
    }
#### output header example
    Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhcGlBdXRoIjp0cnVlLCJleHAiOjE1NTQ2MjM5ODgsInVzZXJJZCI6ImxlZWwyNDE1IiwiaWF0IjoxNTU0NjIwMzg4fQ.C35xu6jK3JVSZpnj4uZQNUgDNB1gn_fDxpFW0vNfhmM
    Content-Type: application/json;charset=UTF-8
    Transfer-Encoding: chunked
    Date: Sun, 07 Apr 2019 06:59:48 GMT


* * *
* * *

## 서비스 API

서비스 API는 위에 가입하여 발급되는 JWT Token을 사용해야지만 서비스가 가능하며, 발급한 Token을 Header에 추가하여 요청이 필요함.

Token에 대한 만료시간은 **1800s** 로 설정함. 

#### input header example
    Authorization : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhcGlBdXRoIjp0cnVlLCJleHAiOjE1NTQ2MjM5ODgsInVzZXJJZCI6ImxlZWwyNDE1IiwiaWF0IjoxNTU0NjIwMzg4fQ.C35xu6jK3JVSZpnj4uZQNUgDNB1gn_fDxpFW0vNfhmM
    Content-Type : application/json

* * *

1. **데이터 파일에서 각 레코드를 데이터베이스에 저장 API**

구분|값
--|--
URL|/api/save
method|POST
API 설명|csv의 지자체 관련 정보 데이터 파일을 DB에 초기 데이터를 저장하는 API. 호출 시, csv 파일을 읽어와 DB에 저장한다.

#### input example
    {
    }

#### output example
    {
       "code": "00",
        "message": ""
    }

* * *

2. **인터넷뱅킹 서비스 접속 기기 목록을 출력하는 API 를 개발**

구분|값
--|--
URL|/api/device/list
mehtod|GET
API 설명|인터넷 뱅킹 서비스 접속 기기 목록을 출력하는 API

#### input example
    {
    }

#### output example
    {
      "devices": [
        {
          "device_id": "DIS0001",
          "device_name": "스마트폰"
        },
        {
          "device_id": "DIS0002",
          "device_name": "데스크탑 컴퓨터"
        },
        {
          "device_id": "DIS0003",
          "device_name": "노트북 컴퓨터"
        },

        ...
        ]
    }

* * *

3. **각 년도별로 인터넷뱅킹을 가장 많이 이용하는 접속기기를 출력하는 API 를 개발**

구분|값
--|--
URL|/api/device/list/max
mehtod|GET
API 설명|각 년도별로 인터넷 뱅킹을 가장 많이 이용하는 접속 기기 정보를 출력 

#### input example
    {
    }

#### output example
    {
      "devices": [
        {
          "year": "2011",
          "rate": "95.1",
          "device_name": "데스크탑 컴퓨터",
          "device_id": "DIS0002"
        },
        {
          "year": "2012",
          "rate": "93.9",
          "device_name": "데스크탑 컴퓨터",
          "device_id": "DIS0002"
        },
        {
          "year": "2013",
          "rate": "67.1",
          "device_name": "데스크탑 컴퓨터",
          "device_id": "DIS0002"
        },
        ...
      ]
    }
* * *

4. **특정 년도를 입력받아 그 해에 인터넷뱅킹에 가장 많이 접속하는 기기 이름을 출력**

구분|값
--|--
URL|/api/device/top/{year}
mehtod|GET
API 설명|특정 년도에 인터넷뱅킹에 가장 많이 접속하는 기기 출력 

#### input example
    /api/device/top/2011
#### output example
    {
      "result": {
        "year": "2011",
        "rate": "95.1",
        "device_name": "데스크탑 컴퓨터"
      }
    }

* * *

5. **디바이스 아이디를 입력받아 인터넷뱅킹에 접속 비율이 가장 많은 해를 출력**

구분|값
--|--
URL|/api/rate/top/{deviceId}
mehtod|GET
API 설명|디바이스 아이디를 입력받아 인터넷뱅킹에 접속 비율이 가능 많은 해를 출력

#### input example
    api/rate/top/DIS0001
#### output example
    {
      "result": {
        "year": "2017",
        "rate": "90.6",
        "device_name": "스마트폰"
      }
    }

* * *
