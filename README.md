 # Week 09 - Spring Data JPA 기초 (Book REST CRUD)

 **목표:** 7주차에 배운 SQL/MariaDB와 5주차 REST CRUD를 연결합니다. 
 `@Entity`, `JpaRepository`, MariaDB 연동과 기본 REST CRUD를 경험합니다.


> 수업용 샘플입니다. 실제 DB 접속 정보와 비밀번호는 프로젝트 파일에 기록하지 마세요. 
> **Spring Boot 3.5.6 / Java 17 이상 / Gradle 8.14.3 / MariaDB**

## 준비

1. DBeaver에서 자신의 MariaDB에 접속되는지 확인합니다.
2. IntelliJ에서 프로젝트 폴더를 `Open`하여 Gradle 프로젝트로 불러옵니다.
3. Run > Edit Configurations > 애플리케이션 실행 구성 > Environment variables에 아래 **세 변수**를 등록합니다.

| 이름 | 값의 형식(예시이며 실제 비밀번호 아님) |
|---|---|
| `DB_URL` | `jdbc:mariadb://YOUR_HOST:3306/YOUR_DATABASE` |
| `DB_USERNAME` | `YOUR_USERNAME` |
| `DB_PASSWORD` | 개인별 발급 비밀번호 |

> `application.properties`는 로컬과 Render에서 **같은 파일**을 사용합니다. 로컬 값은 IntelliJ 환경변수에, Render 값은 Render 서비스의 Environment에 입력합니다. `PORT`는 Render에서 자동 설정되며 로컬은 기본 8080입니다.

## 명령행 실행

macOS / Linux:
```bash
./gradlew bootRun
```

Windows:
```bat
gradlew.bat bootRun
```

프로젝트의 `gradlew`/`gradlew.bat`는 샘플용 **Gradle 부트스트랩 스크립트**입니다. 시스템에 Gradle이 없으면 처음 실행 시 Gradle 8.14.3을 인터넷에서 다운로드합니다. 공식 Gradle Wrapper JAR 파일을 번들로 포함한 형태는 아닙니다. IntelliJ의 Gradle 작업 실행을 이용해도 됩니다. 실행 시 `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`가 설정되어 있어야 합니다.

## Render 배포

1. 코드를 GitHub Repository에 Push합니다. `.env` 또는 DB 비밀번호를 커밋하지 않습니다.
2. Render에서 **Web Service**를 생성하고 GitHub Repository를 연결합니다.
3. Runtime을 Docker로 설정합니다(`Dockerfile` 사용).
4. Render > Service > **Environment**에서 `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`를 입력합니다. 실제 값을 README에 적지 않습니다.
5. 배포 완료 후 `https://YOUR-SERVICE.onrender.com/api/books`에 접속해 응답을 확인합니다.
6. Render에서 API로 데이터를 추가하고, DBeaver에서 개인 DB에 저장된 내용을 확인합니다.

**중요:** 수업용 MariaDB 서버가 외부 인터넷(Render)에서 접근 가능한 주소/포트를 제공하고, 방화벽/접속 허용 설정이 충족되어야 Render 연동이 됩니다. DB 서버가 교내망 또는 VPN에서만 접근 가능하면 Render는 접속할 수 없습니다. DB 계정에는 본인 DB에 필요한 최소 권한만 부여하세요.

## 보안 및 DB 주의

- Git에 커밋하는 `application.properties`에는 **환경변수 참조만** 있습니다.
- `spring.jpa.hibernate.ddl-auto=update`는 **수업 실습용**입니다. 운영 서비스에서는 스키마 마이그레이션과 권한 정책을 별도로 관리해야 합니다.
- 같은 DB를 9주차와 10주차에 사용한다면 10주차를 시작하기 전에 기존 `books` 테이블을 백업하세요.
- 개인별 DB에 기존에 만든 SQL 실습 테이블(`product` 등)이 있더라도 `books` 테이블과 이름이 다르면 영향을 주지 않습니다.

## 실행 테스트

```bash
./gradlew test
```

테스트는 **H2 인메모리 DB**를 사용하므로 개인 MariaDB 계정 없이 실행할 수 있습니다. 다만 수업 과제의 MariaDB 접속과 Render 배포는 별도로 직접 검증해야 합니다.

 ## 예제 구조

 ```text
 src/main/java/com/webservice/sample/
 ├── BookApplication.java
 ├── entity/Book.java
 ├── repository/BookRepository.java
 ├── service/BookService.java
 ├── controller/BookController.java
 └── dto/BookRequest.java, BookResponse.java
 ```

 ## API

 | Method | URL | 설명 |
 |---|---|---|
 | POST | `/api/books` | 도서 등록 (`201 Created`) |
 | GET | `/api/books` | 전체 조회 |
 | GET | `/api/books/{id}` | 단건 조회 (`404` 처리) |
 | PUT | `/api/books/{id}` | 수정 (`repository.save()` 방식) |
 | DELETE | `/api/books/{id}` | 삭제 (`204 No Content`) |

 ```bash
 curl -X POST http://localhost:8080/api/books \
   -H 'Content-Type: application/json' \
   -d '{"title":"Spring Boot Basics","author":"Kim","price":30000,"category":"Programming"}'

 curl http://localhost:8080/api/books
 curl http://localhost:8080/api/books/1

 curl -X PUT http://localhost:8080/api/books/1 \
   -H 'Content-Type: application/json' \
   -d '{"title":"Spring Data JPA","author":"Kim","price":35000,"category":"Programming"}'

 curl -i -X DELETE http://localhost:8080/api/books/1
 ```

 > 위 `1`은 예시 ID입니다. POST로 받은 **실제 ID**를 사용하세요. API 입력은 `id` 외 4개 필드이며, 학생 과제에서는 본인 주제로 `id` 외 **5개 이상 필드**가 필요합니다.

 ## SQL 연습 / DB 확인

 `sql/01_sql_practice.sql` 파일을 DBeaver에서 열어 SQL **CREATE/INSERT/UPDATE/DELETE/SELECT**를 연습하세요. 
 `movie_sql` 테이블은 SQL 직접 실습용이고, `books`는 JPA가 생성/관리하는 테이블입니다. 서로 다른 이름을 사용합니다. 
 `sql/02_existing_table_example.sql`을 참고하여 **기존 수업 테이블에 데이터 추가**도 별도로 진행하세요.

 ```sql
 SELECT * FROM books;
 ```

 응답과 실제 DB 데이터를 비교하고 콘솔의 Hibernate INSERT/SELECT/UPDATE/DELETE를 확인합니다.
