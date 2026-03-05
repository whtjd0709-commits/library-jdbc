# JDBC 도서 관리 시스템

## 기술 스택
* Java
* JDBC
* MariaDB
* Maven
* DBeaver

## 데이터베이스 생성

CREATE DATABASE library_db;
USE library_db;

CREATE TABLE BOOKS (
  book_id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(200) NOT NULL,
  author VARCHAR(100) NOT NULL,
  status CHAR(1) DEFAULT 'Y'
);

CREATE TABLE RENTALS (
  rental_id INT AUTO_INCREMENT PRIMARY KEY,
  book_id INT,
  member_id INT,
  rent_date DATETIME,
  return_date DATETIME,
  FOREIGN KEY (book_id) REFERENCES BOOKS(book_id)
);
```

---

## 실행 방법

1. MariaDB 서버 실행
2. 위 SQL을 실행하여 `library_db` 데이터베이스 생성
3. `DBUtil.java`에서 DB 연결 정보 설정

```
자신의 DB 환경에 맞게 수정
private static final String URL = "jdbc:mariadb://localhost:3306/library_db";
private static final String USER = "root";
private static final String PASSWORD = "비밀번호";
```

4. `Main.java` 실행

프로그램 실행 시 아래 메뉴가 나타납니다.

```
1. 도서 등록
2. 도서 조회
3. 도서 대여
4. 도서 반납
0. 종료