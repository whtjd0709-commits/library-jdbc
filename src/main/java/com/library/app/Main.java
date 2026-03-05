package com.library.app;

import com.library.dao.BookDAO;
import com.library.dto.BookDTO;
import com.library.service.LibraryService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BookDAO bookDAO = new BookDAO();
        Scanner sc = new Scanner(System.in, "cp949");

        while (true) {
            System.out.println("\n=== 도서 관리 ===");
            System.out.println("1. 도서 등록");
            System.out.println("2. 전체 목록 조회");
            System.out.println("3. 도서 대여");
            System.out.println("4. 도서 반납");
            System.out.println("0. 종료");
            System.out.print("선택: ");
            
            int menu;

            try {
                menu = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력하세요.");
                continue;
            }

            try {
                if (menu == 1) {
                    System.out.print("제목: ");
                        String title = sc.nextLine().trim();

                        if(title.isEmpty()){
                            System.out.println("제목은 비어있을 수 없습니다.");
                            return;
                        }

                        System.out.print("저자: ");
                        String author = sc.nextLine().trim();

                        bookDAO.insert(title, author);
                        System.out.println("도서 등록 완료");
                
                } else if (menu == 2) {
                    
                    List<BookDTO> list = bookDAO.selectAll();
                    if(list.isEmpty()){
                        System.out.println("등록된 도서가 없습니다.");
                        return;
                        }
                    System.out.println("\n[도서 목록]");
                    for (BookDTO b : list) {
                        System.out.printf("%d | %s | %s | %s%n",
                                b.getBookId(), b.getTitle(), b.getAuthor(), b.getStatus());
                    }
                }else if(menu == 3){
                    System.out.print("대여할 책 ID : ");
                    int bookId = Integer.parseInt(sc.nextLine());

                    System.out.print("회원 ID : ");
                    int memberId = Integer.parseInt(sc.nextLine());

                    LibraryService service = new LibraryService();
                    service.rentBook(bookId, memberId);
                    
                }else if (menu == 4) {
                    System.out.print("반납할 책 ID : ");
                    int bookId = Integer.parseInt(sc.nextLine());

                    LibraryService service = new LibraryService();
                    service.returnBook(bookId);
                
                } else if (menu == 0) {
                    System.out.println("종료");
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        sc.close();
    }
}