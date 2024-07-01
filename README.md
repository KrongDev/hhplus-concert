# 애니메이션 OST 콘서트 예약 서비스
애니메이션 OST 콘서트 예약 서비스입니다.  
전세계 오타쿠들이여 모여라~

## Sequence Diagram
### 1. 대기열 토큰 발급
````mermaid
sequenceDiagram
    actor User
    participant TokenService
    participant QueueService
    
    Note over User,TokenService: 토큰발급
    User->>+TokenService: 1. 대기열 입장을 위한 토큰 발급 요청
    TokenService->>+QueueService: 2. 현재 대기열 조회
    QueueService-->>-TokenService: 3. 현재 대기열 상황 반환
    TokenService->>TokenService: 4. 유저 정보를 통해 토큰 생성
    TokenService-->User: 5. 대기를 위한 토큰 발행
````
### 2. 토큰을 통한 사이트 입장 대기열 체크
Tip:   
특정시간동안 N명에게만 권한을 부여한다 - 신청가능 권한   
한번에 활성화된 최대 유저를 N으로 유지한다.
```mermaid
sequenceDiagram
    actor User
    participant QueueService
    participant TokenService
    
    Note over User,TokenService: token 갱신
    
    User->>+QueueService: 3. 대기열 진입 ( webSocket 연결 )
    loop 사용자의 토큰 Health Check Polling방식
        QueueService->>+TokenService: 1. Health Check
        
        break 토큰 만료로 인한 종료
            TokenService-->>QueueService: 2. 토큰 만료로 인한 종료 Exception 발행 
            QueueService-->>User:3. Token만료로 인한 종료
        end
        TokenService->>TokenService: 4. Token 만료시간 및 마지막 Health Check시간 수정
        TokenService-->>QueueService: 5. 생존 신고
        QueueService->>QueueService: 6. 대기열 체크
        alt 입장 순위의 경우 
            QueueService->>QueueService: 7-1. 해당 토큰의 대기열 상태 입장으로 변경
        break 입상순위로 인한 Loop 탈출
            QueueService-->>-User: 8. 대기 종료로 인한 콘서트 신청 페이지로 Redirect요청 반환
        end
        else
            QueueService-->>User: 7-2.현재 대기 상황 반환
        end
    end
```
### 3. 예약 가능 날짜/좌석 조회 API
Tip: 좌석 정보는 1 ~ 50 까지의 좌석 번호를 관리합니다.
```mermaid
sequenceDiagram
    actor User
    participant ConcertSeriesService
    participant ConcertSheetService
    
    Note over User, ConcertSheetService: 콘서트 예약 가능 좌석 조회
    User->>+ConcertSeriesService: 1. 현재 예약 가능한 날짜 요청
    ConcertSeriesService-->>-User: 2. 예약 가능한 날짜 반환
    User->>+ConcertSeriesService: 3. 선택한 날짜에 예약가능한 좌석 요청
    ConcertSeriesService->>+ConcertSheetService: 4. 예약가능한 좌석 요청
    ConcertSheetService-->>-ConcertSeriesService: 5. 예약가능한 좌석 반환
    ConcertSeriesService-->>-User: 6. 해당 콘서트 예약 가능한 좌석 반환
```
### 4. 좌석 예약 요청 API
Tip: 임시 배정 시간은 5분입니다.
```mermaid
sequenceDiagram
    actor User
    participant ConcertSheetService
    
    Note over User, TemporaryReservationService: 선택한 좌석 예약 신청
    User->>+ConcertSheetService: 1. 예약가능한 좌석 요청
    alt 좌석이 있을경우
        ConcertSheetService->>+TemporaryReservationService: 2-1. 선택한 좌석 신청
    else 좌석이 이미 예약된 경우
        ConcertSheetService->>User: 2-2. 예약된 좌석이므로 Exception
    end
    TemporaryReservationService->>TemporaryReservationService: 3. 좌석 임시 예약
    TemporaryReservationService-->>-ConcertSheetService: 4. 임시예약 신청 여부 반환
    ConcertSheetService-->>-User: 5. 임시예약 신청 여부 반환
```
### 5. 잔액 충전/조회 API
```mermaid
sequenceDiagram
    actor User
    participant PointService
    participant PointHistoryService
    
    Note over User,PointHistoryService: 1. 잔액충전/조회 API
    User->>+PointService: 2. 현재 잔액 정보 요청
    PointService-->>-User: 3. 정보 반환
    User->>+PointService: 4. 잔액 충전 요청
    PointService->>PointService: 5. 잔액 충전
    PointService-->>PointHistoryService: 6. 잔액 충전 History 생성 요청
    PointService-->>-User: 7. 잔액 충전 여부 반환
```
### 6. 결제 API
```mermaid
sequenceDiagram
    actor User
    participant PaymentService
    participant PointService
    participant PointHistoryService
    participant TemporaryReservationService
    participant ReservationService
    participant TokenService
    participant QueueService
    
    Note over User,PointHistoryService: 1. 콘서트 결제
    User->>+PaymentService: 2. 임시 에약한 좌석 결제 요청
    PaymentService->>+TemporaryReservationService: 3. 해당 좌석 유저가 임시예약 여부 요청
    TemporaryReservationService-->>-PaymentService: 4. 임시예약 여부 반환
    PaymentService->>PaymentService: 5. 결제 정보 생성
    PaymentService->>+PointService: 6. 포인트 사용 요청
    PointService-->>PointHistoryService: 7. 포인트 사용 History 생성 요청
    PointService-->>-PaymentService: 8. 포인트 사용 여부
    PaymentService-->>TemporaryReservationService: 9. PaymentCompleted 이벤트 발행
    TemporaryReservationService-->>ReservationService: 10. FinalizeTemporaryReservation 이벤트 발행
    ReservationService-->>ReservationService: 11. 예약 자리 확정
    ReservationService-->>TokenService: 12. ReservationCreated 이벤트 발행
    TokenService->>TokenService: 13. 토큰 만료로 삭제 or 만료 처리
    TokenService-->>QueueService: 14. TokenExpired 이벤트 발행
    QueueService->>QueueService: 15. 해당 토큰 대기 상태 종료로 변경
    PaymentService-->>-User: 16. 결제 완료 여부 반환
```
