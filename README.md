# JDBC 도서 관리 시스템

## 프로젝트 소개
Java JDBC와 MariaDB를 이용한 도서 관리 프로그램입니다. 

## 주요 기능
- 도서 등록
- 도서 조회
- 도서 대여
- 도서 반납
- 중복 대여 방지

## 기술 스택
- Java
- JDBC
- MariaDB
- Maven

## 실행 방법
1. MariaDB 실행
2. library_db 데이터베이스 생성
3. 프로그램 실행

## 데이터베이스 구조

BOOKS
- book_id (PK)
- title
- author
- status

RENTALS
- rental_id (PK)
- book_id (FK)
- member_id
- rent_date
- return_date