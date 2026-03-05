package com.library.service;

import com.library.dao.BookDAO;
import com.library.dao.RentalDAO;
import com.library.util.DBUtil;

import java.sql.Connection;

public class LibraryService {

    private BookDAO bookDAO = new BookDAO();
    private RentalDAO rentalDAO = new RentalDAO();

    // 도서 대여
    public void rentBook(int bookId, int memberId) {

        Connection conn = null;

        try {

            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            // 대여 가능할 때만 상태 변경
            int updated = bookDAO.markAsRentedIfAvailable(conn, bookId);

            if (updated == 0) {
                throw new RuntimeException("이미 대여중인 책입니다.");
            }

            // 대여 기록 저장
            rentalDAO.insert(conn, bookId, memberId);

            conn.commit();

            System.out.println("도서 대여 완료");

        } catch (Exception e) {

            try { conn.rollback(); } catch (Exception ignored) {}

            System.out.println("대여 실패 : " + e.getMessage());

        } finally {

            try { conn.setAutoCommit(true); } catch (Exception ignored) {}
            try { conn.close(); } catch (Exception ignored) {}
        }
    }


    // 도서 반납
    public void returnBook(int bookId) {

        Connection conn = null;

        try {

            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            // 책 상태 변경
            bookDAO.updateStatus(conn, bookId, "Y");

            // 반납일 업데이트
            int updated = rentalDAO.updateReturnDate(conn, bookId);

            if (updated == 0) {
                throw new RuntimeException("반납할 기록이 없습니다.");
            }

            conn.commit();

            System.out.println("도서 반납 완료");

        } catch (Exception e) {

            try { conn.rollback(); } catch (Exception ignored) {}

            System.out.println("반납 실패 : " + e.getMessage());

        } finally {

            try { conn.setAutoCommit(true); } catch (Exception ignored) {}
            try { conn.close(); } catch (Exception ignored) {}
        }
    }
}