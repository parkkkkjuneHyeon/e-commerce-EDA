# E-commerce Event-Driven Architecture (EDA)

## 📋 프로젝트 개요

이 프로젝트는 Event-Driven Architecture 패턴을 활용한 마이크로서비스 기반의 전자상거래 시스템입니다. 각 서비스는 독립적으로 운영되며, 이벤트 브로커를 통해 느슨하게 결합된 아키텍처로 구성되어 있습니다.

## 🏗️ 시스템 아키텍처

```
                                    상품 정보 등록
                                         ↓
                              Seller → Catalog → Cassandra
                                         ↓
                                    상품 정보 이벤트
                                         ↓
         검색           상품 검색 이벤트        ┌─────────────────┐
Customer ─→ Search ←─── Redis ←─────────────────│                 │
    ↓                                          │   Event Broker  │
    │                                          │     (Kafka)     │
    │     ┌─ 주문 이벤트 ─→ Order ─→ 주문 요청 이벤트→ │                 │
    └─ 주문→                                    │                 │
                                               └─────────────────┘
                                                      ↓
                                               결제 요청 이벤트
                                                      ↓
    로그인 ─→ Member                              Payment ← 토스페이먼츠 API
                                                      ↓
                                               결제 완료 이벤트
                                                      ↓
                                                  Delivery ← 배송 조회
                                                      ↓
                                               External Delivery
                                                   Adapter
```

## 🔧 기술 스택

### Infrastructure
- **Container**: Docker, Docker Compose
- **Event Streaming**: Apache Kafka (3-node cluster)
- **Service Discovery**: Zookeeper
- **Database**: 
  - MySQL 8.0 (관계형 데이터)
  - Cassandra (NoSQL, 상품 카탈로그)
  - Redis (캐싱, 검색)

### Microservices
- **Member Service** (포트: 8081) - 회원 관리
- **Payment Service** (포트: 8082) - 결제 처리 (토스페이먼츠 연동)
- **Delivery Service** (포트: 8083) - 배송 관리
- **Search Service** (포트: 8084) - 검색 기능
- **Catalog Service** (포트: 8085) - 상품 카탈로그
- **Order Service** (포트: 8086) - 주문 처리

### External Integrations
- **토스페이먼츠**: 실제 결제 처리를 위한 외부 결제 게이트웨이

## 🚀 실행 방법

### 사전 요구사항
- Docker
- Docker Compose

### 설치 및 실행

1. **저장소 클론**
   ```bash
   git clone https://github.com/parkkkkjuneHyeon/e-commerce-EDA.git
   cd e-commerce-EDA
   ```

2. **전체 시스템 시작**
   ```bash
   docker-compose up -d
   ```

3. **서비스 상태 확인**
   ```bash
   docker-compose ps
   ```

### 개별 서비스 접근

| 서비스 | 포트 | 접근 URL |
|--------|------|----------|
| Member Service | 8081 | http://localhost:8081 |
| Payment Service | 8082 | http://localhost:8082 |
| Delivery Service | 8083 | http://localhost:8083 |
| Search Service | 8084 | http://localhost:8084 |
| Catalog Service | 8085 | http://localhost:8085 |
| Order Service | 8086 | http://localhost:8086 |

### 데이터베이스 접근

| 데이터베이스 | 포트 | 접속 정보 |
|--------------|------|-----------|
| MySQL | 3306 | root/1234, database: my_db |
| Redis | 6379 | localhost:6379 |
| Cassandra | 9042 | localhost:9042 |

### Kafka 클러스터

| 브로커 | 포트 | 접속 정보 |
|--------|------|-----------|
| Kafka1 | 19092 | localhost:19092 |
| Kafka2 | 19093 | localhost:19093 |
| Kafka3 | 19094 | localhost:19094 |

## 📊 이벤트 플로우

1. **상품 등록**: Seller → Catalog Service → Cassandra
2. **상품 검색**: Customer → Search Service → Redis
3. **주문 처리**: Customer → Order Service → Event Broker
4. **결제 처리**: Order Event → Payment Service → 토스페이먼츠 API → 결제 완료 이벤트
5. **배송 처리**: Payment Event → Delivery Service → External Delivery Adapter

### 결제 프로세스 상세
```
주문 생성 → Payment Service → 토스페이먼츠 결제창 → 결제 승인 → 결제 완료 이벤트 발행 → 배송 서비스 트리거
```

## 🔍 주요 특징

### Event-Driven Architecture
- **비동기 통신**: 서비스 간 Kafka를 통한 이벤트 기반 통신
- **느슨한 결합**: 서비스 간 직접적인 의존성 최소화
- **확장성**: 개별 서비스의 독립적인 스케일링 가능

### 실제 결제 시스템 연동
- **토스페이먼츠 API**: 실제 결제 처리를 위한 안전하고 신뢰할 수 있는 결제 게이트웨이
- **실시간 결제 상태**: 결제 승인/취소 상태를 실시간으로 이벤트 시스템에 반영

### 데이터 저장소 다양화
- **MySQL**: 트랜잭션이 중요한 주문, 결제, 회원 데이터
- **Cassandra**: 대용량 상품 카탈로그 데이터
- **Redis**: 빠른 검색을 위한 캐싱

### 고가용성
- **Kafka 클러스터**: 3개 브로커로 구성된 고가용성 메시지 브로커
- **장애 격리**: 마이크로서비스별 독립적인 장애 처리

## 🛠️ 개발 환경 설정

### 토스페이먼츠 연동 설정
결제 서비스를 사용하기 위해서는 토스페이먼츠 개발자 계정이 필요합니다:
1. [토스페이먼츠 개발자센터](https://developers.tosspayments.com/)에서 계정 생성
2. API 키 발급 (클라이언트 키, 시크릿 키)
3. PaymentService의 환경변수에 API 키 설정

### 로그 확인
```bash
# 전체 서비스 로그
docker-compose logs -f

# 특정 서비스 로그
docker-compose logs -f [service-name]

# 결제 서비스 로그 확인
docker-compose logs -f payment-service
```

### 서비스 재시작
```bash
# 특정 서비스 재시작
docker-compose restart [service-name]

# 전체 시스템 재시작
docker-compose restart
```

### 데이터베이스 초기화
```bash
# MySQL 접속
docker-compose exec mysql-server mysql -u root -p1234 my_db

# Cassandra 접속
docker-compose exec cassandra-node-0 cqlsh

# Redis 접속
docker-compose exec redis-server redis-cli
```

## 🚦 시스템 종료

```bash
# 서비스 중지
docker-compose down

# 볼륨까지 함께 삭제
docker-compose down -v
```


**GitHub Repository**: [https://github.com/parkkkkjuneHyeon/e-commerce-EDA](https://github.com/parkkkkjuneHyeon/e-commerce-EDA)
