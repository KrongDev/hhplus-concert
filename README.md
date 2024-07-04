### 애니메이션 OST를 위한 콘서트 예약 서비스입니다.

### 사용자에게 제공되는 기능

1. 공연 목록 조회
2. 공연 날짜 및 좌석 조회
3. 공연 예약
4. 포인트 충전/사용/조회
5. 결제

### 오류 발생 케이스

1. 공연 날짜 및 좌석 조회
    1. 공연 날짜별로 생성이 안된 경우
2. 공연 예약
    1. 좌석이 이미 예약 된 경우
    2. 예약할 수 없는 Token을 가지고 있는 경우
       ex) 만료된 토큰 or 유효하지 않은 토큰
3. 포인트 충전/사용
    1. 동시에 여러번의 요청이 들어온 경우
    2. 사용금액이 보유 금액보다 많을 경우
    3. 0원을 충전/사용 하려 하는 경우
4. 결제
    1. 포인트가 결제 비용보다 적을 경우
    2. 예약한 좌석이 결제시간(5분)을 넘겨 해지된 경우

### 테스트 시 주의 사항

1. 대기열
    1. 몇명이 들어올 것인가
    2. 몇명이 대기할 것인가
    3. 몇명을 입장 시킬 것인가
2. 콘서트 예약
    1. 여러명이 동일한 좌석을 요청할 경우
3. 결제
    1. 포인트 사용시 오류가 발생하였으면 임시예약한 공연은 어떻게 처리할 것인가.
    2. 결제 요청한 임시예약 공연은 유효한가?

## Sequence Diagram
### 1. 대기열 토큰 발급

```mermaid
sequenceDiagram

	actor User
	participant ConcertToken
	participant ConcertQueue
	
	Note over User,ConcertToken: 토큰발급
	User->>+ConcertToken: 1. 대기열 입장을 위한 토큰 발급 요청
	ConcertToken->>+ConcertQueue: 2. 현재 대기열 조회
	ConcertQueue-->>-ConcertToken: 3. 현재 대기열 상황 반환
	ConcertToken->>ConcertToken: 4. 유저 정보를 통해 토큰 생성
	ConcertToken-->User: 5. 대기를 위한 토큰 발행
```

### 2. 토큰을 통한 사이트 입장 대기열 체크

Tip:   
특정시간동안 N명에게만 권한을 부여한다 - 신청가능 권한   
한번에 활성화된 최대 유저를 N으로 유지한다.   

```mermaid
sequenceDiagram

	actor User
	participant ConcertQueue
	participant ConcertToken

	Note over User,ConcertToken: token 갱신
	loop 사용자의 토큰 Health Check Polling방식
		User->>+ConcertQueue: 1. 대기열 진입 ( polling 방식)
		ConcertQueue->>+ConcertToken: 2. Health Check
		break 토큰 만료로 인한 종료
			ConcertToken-->>ConcertQueue: 3. 토큰 만료로 인한 종료 Exception 발행
			ConcertQueue-->>User:4. Token만료로 인한 종료
		end
		ConcertToken->>ConcertToken: 5. Token 만료시간 및 마지막 Health Check시간 수정
		ConcertToken-->>ConcertQueue: 6. 생존 신고
		ConcertQueue->>ConcertQueue: 7. 대기열 체크
		
		alt 입장 순위의 경우
			ConcertQueue->>ConcertQueue: 8. 해당 토큰의 대기열 상태 입장으로 변경
			break 입상순위로 인한 Loop 탈출
				ConcertQueue-->>-User: 9-1. 대기 종료로 인한 콘서트 신청 페이지로 Redirect요청 반환
			end
		else
			ConcertQueue-->>User: 9-2.현재 대기 상황 반환
		end
	end
```

### 3. 예약 가능 날짜/좌석 조회 API

Tip: 좌석 정보는 1 ~ 50 까지의 좌석 번호를 관리합니다.

```mermaid
sequenceDiagram

	actor User
	participant ConcertSeries
	participant ConcertSheet
	
	Note over User, ConcertSheet: 콘서트 예약 가능 좌석 조회
	User->>+ConcertSeries: 1. 현재 예약 가능한 날짜 요청
	ConcertSeries-->>-User: 2. 예약 가능한 날짜 반환
	User->>+ConcertSeries: 3. 선택한 날짜에 예약가능한 좌석 요청
	ConcertSeries->>+ConcertSheet: 4. 예약가능한 좌석 요청
	ConcertSheet-->>-ConcertSeries: 5. 예약가능한 좌석 반환
	ConcertSeries-->>-User: 6. 해당 콘서트 예약 가능한 좌석 반환
```

### 4. 좌석 예약 요청 API

Tip: 임시 배정 시간은 5분입니다.

```mermaid
sequenceDiagram

	actor User
	participant ConcertSheet
	
	Note over User, TemporaryReservation: 선택한 좌석 예약 신청
	User->>+ConcertSheet: 1. 예약가능한 좌석 요청
	alt 좌석이 있을경우
		ConcertSheet->>+TemporaryReservation: 2-1. 선택한 좌석 신청
	else 좌석이 이미 예약된 경우
		ConcertSheet->>User: 2-2. 예약된 좌석이므로 Exception
	end
	TemporaryReservation->>TemporaryReservation: 3. 좌석 임시 예약
	TemporaryReservation-->>-ConcertSheet: 4. 임시예약 신청 여부 반환
	ConcertSheet-->>-User: 5. 임시예약 신청 여부 반환
```

### 5. 잔액 충전/조회 API

```mermaid
sequenceDiagram

	actor User
	participant Point
	participant PointHistory
	
	Note over User,PointHistory: 1. 잔액충전/조회 API
	User->>+Point: 2. 현재 잔액 정보 요청
	Point-->>-User: 3. 정보 반환
	User->>+Point: 4. 잔액 충전 요청
	Point->>Point: 5. 잔액 충전
	Point->>PointHistory: 6. 잔액 충전 History 생성 요청
	PointHistory-->>Point: 7. History 생성완료 
	Point-->>-User: 8. 잔액 충전 여부 반환
```

### 6. 결제 API

```mermaid
sequenceDiagram

	actor User
	participant Payment
	participant Point
	participant PointHistory
	participant TemporaryReservation
	participant Reservation
	participant ConcertToken
	participant ConcertQueue
	
	Note over User,PointHistory: 1. 콘서트 결제
	User->>+Payment: 2. 임시 에약한 좌석 결제 요청
	Payment->>+TemporaryReservation: 3. 해당 좌석 유저가 임시예약 여부 요청
	TemporaryReservation-->>-Payment: 4. 임시예약 여부 반환
	Payment->>Payment: 5. 결제 정보 생성
	Payment->>+Point: 6. 포인트 사용 요청
	Point->>+PointHistory: 7. 포인트 사용 History 생성 요청
	PointHistory->>-Point: 8. history 생성 응답
	Point-->>-Payment: 9. 포인트 사용 여부 반환
	Payment->>+TemporaryReservation: 10. 결제 완료로 인해 콘서트 좌석 확정 Process 진행
	TemporaryReservation->>+Reservation: 11. 임시예약 좌석 확정 Process 진행
	Reservation->>Reservation: 12. 예약 자리 확정
	Reservation->>ConcertToken: 13.  좌석 구매로 인해 대기열에 사용한 자원 정리
	ConcertToken->>ConcertToken: 14. 토큰 만료로 삭제 or 만료 처리
	ConcertToken->>ConcertQueue: 15. 토큰 만료 or 삭제시  대기열 자원 삭제 요청 
	ConcertQueue->>ConcertQueue: 16. 해당 토큰에 대한 자원 정리
	ConcertQueue-->>Payment: 17. 결제 완료 후속처리 완료
	Payment-->>-User: 18. 결제 완료 여부 반환
```
## ERD

TemporaryReservation: 임시예약 테이블   
Reservation: 예약 테이블 

위 두테이블은 Concert와 ConcertSeries, ConcertSheet의 데이터들을 가질 수 있습니다.   
이를 위해 반정규화를 진행하려 하였으나 개발하면서 계속 수정이 이뤄질거 같아 참조 관계를 중점으로 ERD작성하였습니다.

![ERD](./images/erd.png)

# API 정의서

현재 개발이 이뤄지지 않아 대기열 체크에 사용하는 Token과 토큰 정보가 필요한 경우 TokenId를 사용했습니다.   
추후 개발하며 Token에서 필요한 데이터를 추출하는 방향으로 작업할 예정입니다.

## Authentication
API 호출시 필요한 토큰입니다.
서비스를 이용하려면 Token을 발급받고 대기후 서비스를 이용하실 수 있습니다.
- **Type**: Bearer Token
- **Header**: `Authorization: Bearer <your-token>`
- **Data**:
 ```json
{
  "tokenId": "test_token_id",
  "userId": "test_user_id",
  "name": "Geon Lee"
}
```

## 토큰
### 1. 토큰 정보 저장 및 발급
**Request**
- **Method**: POST
- **Endpoint**: `/token`
- **Body**:
```json  
{
  "userId": "test_userId",
  "seriesId": "test_seriesId"
}
```
**Response**
- **Status Code**: 200 OK
- **Body**:
```json  
"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbklkIjoidGVzdF90b2tlbl9pZCIsInVzZXJJZCI6InRlc3RfdXNlcl9pZCIsIm5hbWUiOiJHZW9uIExlZSJ9.mx7kU1He_L2GXNH1wWidt_BytwRwPF64SI-e9Wh1HbY"  
```
---
### 2. 토큰 정보 조회
**Request**
- **Method**: GET
- **Endpoint**: `/token/{tokenId}`
- **Headers**:
    - `Authorization: Bearer <your-token>`
- **Parameters**:
    - `tokenId` (path): 토큰 아이디

**Response**
- **Status Code**: 200 OK
- **Body**:
```json  
{
  "tokenId": "test_tokenId",
  "userId": "test_userId",
  "payload": "test_payload",
  "lastUpdateAt": "2024-07-05T00:45:09.389071",
  "expiredAt": "2024-07-05T01:45:09.389071",
  "createAt": "2024-07-05T00:45:09.389071"
}  
```
---
### 3. 토큰 생존 확인 (health check)
**Request**
- **Method**: PATCH
- **Endpoint**: `/token/{tokenId}`
- **Headers**:
    - `Authorization: Bearer <your-token>`
- **Parameters**:
    - `tokenId` (path): 토큰 ID

**Response**
- **Status Code**: 200 OK
---
## 대기열
### 1. 대기열 생성
**Request**
- **Method**: POST
- **Endpoint**: `/concert-queue`
- **Headers**:
    - `Authorization: Bearer <your-token>`
- **Body**:
```json  
{
  "tokenId": "test_tokenId",
  "seriesId": "test_seriesId"
}
```  

**Response**
- **Status Code**: 200 OK
- **Body**: 대기열 아이디 반환
```json 
"3f17fb2c-62a5-4e0e-80e2-289f38b296cb"  
```
---
### 2. 대기열 순번 조회
**Request**
- **Method**: GET
- **Endpoint**: `/concert-queue`
- **Headers**:
    - `Authorization: Bearer <your-token>`
    - `tokenId: <your-token-id>`

**Response**
- **Status Code**: 200 OK
- **Body**:
```json  
{
  "queueId": "test_queueId",
  "tokenId": "test_tokenId",
  "seriesId": "test_seriesId",
  "count": 1,
  "status": "PROCESSING",
  "createAt": "2024-07-05T00:30:19.735231"
}  
```
---
## 콘서트 API
### 1. 콘서트 생성 API
**Request**
- **Method**: POST
- **Endpoint**: `/concert`
- **Headers**:
    - `Authorization: Bearer <your-token>`
- **Body**:
```json
{
  "userId": "test_userId",
  "title": "test_title"
}
```
**Response**
- **Status Code**: 200 OK
---
### 2. 콘서트 시리즈 생성
**Request**
- **Method**: POST
- **Endpoint**: `/concert/series`
- **Headers**:
    - `Authorization: Bearer <your-token>`
- **Body**:
```json  
{
  "concertId": "test_concertId",
  "startAt": "2024-07-12T12:00:00",
  "endAt": "2024-07-12T15:00:00",
  "reserveStartAt": "2024-07-01T12:00:00",
  "reserveEndAt": "2024-07-12T12:00:00"
}
```
**Response**
- **Status Code**: 200 OK
---
### 3. 콘서트 목록 조회
**Request**
- **Method**: GET
- **Endpoint**: `/concert`
- **Headers**:
    - `Authorization: Bearer <your-token>`

**Response**
- **Status Code**: 200 OK
- **Body**:
```json  
[
  {
    "concertId": "test_concertId",
    "userId": "krong",
    "title": "진격의 거인 OST 콘서트",
    "createAt": "2024-07-05T00:06:42.325059"
  }
]  
```
---
### 4. 콘서트 예약 가능 날짜 조회
**Request**
- **Method**: GET
- **Endpoint**: `/concert/series/{concertId}`
- **Headers**:
    - `Authorization: Bearer <your-token>`
- **Parameters**:
    - `concertId` (path): 콘서트 ID

**Response**
- **Status Code**: 200 OK
- **Body**:
```json  
[
  {
    "seriesId": "test_seriesId",
    "concertId": "test_concertId",
    "startAt": "2024-07-12T12:00:00",
    "endAt": "2024-07-12T15:00:00",
    "reserveStartAt": "2024-07-04T12:00:00",
    "reserveEndAt": "2024-07-07T12:00:00",
    "status": "PROCESSING",
    "createAt": "2024-05-12T12:00:00"
  }
]  
```
---
### 5. 콘서트 예약 가능 좌석 조회
**Request**
- **Method**: POST
- **Endpoint**: `/concert/sheet/{seriesId}`
- **Headers**:
    - `Authorization: Bearer <your-token>`
- **Parameters**:
    - `seriesId` (path): 콘서트 날짜 ID

**Response**
- **Status Code**: 200 OK
- **Body**:
```json  
[
  {
    "sheetId": "test_sheetId",
    "concertId": "test_concertId",
    "seriesId": "test_seriesId",
    "row": 1,
    "col": 1,
    "price": 10000,
    "reserved": false
  }
]  
```
---
## 결제
### 1. 결제요청
**Request**
- **Method**: POST
- **Endpoint**: `/payment`
- **Headers**:
    - `Authorization: Bearer <your-token>`
- **Body**:
```json  
{
  "userId": "test_userId",
  "amount": 10000
}
```  

**Response**
- **Status Code**: 200 OK
---
### 2. 결제 내역 조회
**Request**
- **Method**: GET
- **Endpoint**: `/payment?userId={userId}`
- **Headers**:
    - `Authorization: Bearer <your-token>`
- **Parameters**:
    - `userId` : 사용자 ID

**Response**
- **Status Code**: 200 OK
- **Body**:
```json  
[
  {
    "paymentId": "test_paymentId",
    "userId": "test_userId",
    "amount": 10000,
    "status": "COMPLETE",
    "createAt": "2024-07-05T00:14:42.186414"
  }
]  
```
---
### 3. 결제 조회
**Request**
- **Method**: GET
- **Endpoint**: `/payment/{paymentId}`
- **Headers**:
    - `Authorization: Bearer <your-token>`
- **Parameters**:
    - `paymentId` (path): 결제 아이디

**Response**
- **Status Code**: 200 OK
- **Body**:
```json  
{
  "paymentId": "test_paymentId",
  "userId": "test_userId",
  "amount": 10000,
  "status": "COMPLETE",
  "createAt": "2024-07-05T00:16:33.830471"
}  
```
---
## 포인트
### 1. 포인트 조회
**Request**
- **Method**: GET
- **Endpoint**: `/point/{userId}`
- **Headers**:
    - `Authorization: Bearer <your-token>`
- **Parameters**:
    - `userId` (path): 사용자 ID
  
**Response**
- **Status Code**: 200 OK
- **Body**:
```json  
{
  "pointId": "test_pointId",
  "userId": "test_userId",
  "point": 10000
}  
```
---
### 2. 포인트 사용 내역 조회
**Request**
- **Method**: GET
- **Endpoint**: `/point/history/{userId}`
- **Headers**:
    - `Authorization: Bearer <your-token>`
- **Parameters**:
    - `userId` (path): 사용자 ID
  
**Response**
- **Status Code**: 200 OK
- **Body**:
```json  
[
  {
    "pointHistoryId": "test_pointHistoryId",
    "paymentId": "test_paymentId",
    "pointId": "test_pointId",
    "userId": "test_userId",
    "amount": 10000,
    "status": "CHARGE",
    "createAt": "2024-07-05T00:22:17.962837"
  }
]
```
---
### 3. 포인트 충전
**Request**
- **Method**: PATCH
- **Endpoint**: `/point/charge`
- **Headers**:
    - `Authorization: Bearer <your-token>`
- **Body**:
```json  
{
  "userId": "test_userId",
  "amount": 10000
}
```

**Response**
- **Status Code**: 200 OK
---
### 4. 포인트 사용
**Request**
- **Method**: PATCH
- **Endpoint**: `/point/use`
- **Headers**:
    - `Authorization: Bearer <your-token>`
- **Body**:
```json  
{
  "userId": "test_userId",
  "amount": 10000
}
```  

**Response**
- **Status Code**: 200 OK
---
## 임시 예약
### 1. 임시 콘서트 예약
**Request**
- **Method**: POST
- **Endpoint**: `/temporary-reservation`
- **Headers**:
    - `Authorization: Bearer <your-token>`
    - `tokenId: <your-token-id>`
- **Body**:
```json  
{
  "userId": "test_userId",
  "concertId": "test_concertId",
  "seriesId": "test_seriesId",
  "sheetId": "test_sheetId"
}
```  

**Response**
- **Status Code**: 200 OK
- **Body**: 임시예약 티켓 아이디
```json  
"159fcfd5-3970-4fe6-8e23-ea5817eccc69"  
```
---
### 2. 임시예약한 티켓 조회
**Request**
- **Method**: GET
- **Endpoint**: `/temporary-reservation/{temporaryReservationId}`
- **Headers**:
    - `Authorization: Bearer <your-token>`
- **Parameters**:
    - `temporaryReservationId` (path): 임시예약 티켓 ID

**Response**
- **Status Code**: 200 OK
- **Body**:
```json  
{
  "temporaryReservationId": "test_temporaryReservationId",
  "userId": "test_userId",
  "concertId": "test_concertId",
  "seriesId": "test_seriesId",
  "sheetId": "test_sheetId",
  "createAt": "2024-07-05T00:56:30.331515",
  "deleteAt": null
}  
```
---
## 예약 티켓 보관소
### 1. 예약 티켓 조회
**Request**
- **Method**: GET
- **Endpoint**: `/reservation/{reservationId}`
- **Headers**:
    - `Authorization: Bearer <your-token>`
- **Parameters**:
    - `reservationId` (path): 예약 티켓 ID

**Response**
- **Status Code**: 200 OK
- **Body**:
```json  
{
  "reservationId": "test_reservationId",
  "userId": "test_userId",
  "concertId": "test_concertId",
  "seriesId": "test_seriesId",
  "paymentId": "test_paymentId",
  "sheetId": "test_sheetId",
  "createAt": "2024-07-05T00:49:10.188047"
}  
```
---
### 2. 예약한 티켓들 조회
**Request**
- **Method**: GET
- **Endpoint**: `/reservation?userId={userId}`
- **Headers**:
    - `Authorization: Bearer <your-token>`
- **Parameters**:
    - `userId` : 사용자 ID

**Response**
- **Status Code**: 200 OK
- **Body**:
```json  
[
  {
    "reservationId": "test_reservationId",
    "userId": "test_userId",
    "concertId": "test_concertId",
    "seriesId": "test_seriesId",
    "paymentId": "test_paymentId",
    "sheetId": "test_sheetId",
    "createAt": "2024-07-05T00:50:46.946786"
  }
]  
```
