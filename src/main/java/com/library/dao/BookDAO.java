package com.library.dao;

import com.library.dto.BookDTO;
import com.library.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    public int insert(String title, String author) throws SQLException {
        String sql = "INSERT INTO BOOKS (title, author) VALUES (?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, title);
            pstmt.setString(2, author);

            return pstmt.executeUpdate();
        }
    }

    public List<BookDTO> selectAll() throws SQLException {
        String sql = "SELECT book_id, title, author, status FROM BOOKS ORDER BY book_id";
        List<BookDTO> list = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                list.add(new BookDTO(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("status")
                ));
            }
        }
        return list;
    }

    public int updateStatus(Connection conn, int bookId, String status) throws SQLException {

    String sql = "UPDATE BOOKS SET status = ? WHERE book_id = ?";

    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, status);
        pstmt.setInt(2, bookId);

        return pstmt.executeUpdate();
    }
}
    public String selectStatus(Connection conn, int bookId) throws SQLException {
        String sql = "SELECT status FROM BOOKS WHERE book_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getString("status");
                return null; // 책 ID 자체가 없음
            }
        }
    }
    public int markAsRentedIfAvailable(Connection conn, int bookId) throws SQLException {
    String sql = "UPDATE BOOKS SET status = 'N' WHERE book_id = ? AND status = 'Y'";
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, bookId);
        return pstmt.executeUpdate(); // 성공하면 1, 이미 대여중이면 0
    }
}
    }

