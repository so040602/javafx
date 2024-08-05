package com.itgroup.jdbc;

import com.itgroup.dao.GamaDao;

import java.util.Scanner;

public class DeleteMain {
    public static void main(String[] args) {

        // 사본에서 푸시하려고 시도함
        // 날씨가 많이 덥네요.
        // 오늘 점심은 시원한 것으로 먹읍시다.
        // 다시 내가 먼저 푸시함

        Scanner scan = new Scanner(System.in);
        GamaDao dao = new GamaDao();
        int gnum;
        int cnt = -1;
        System.out.print("삭제할 게임 넘버를 입력하세요.");
        gnum = scan.nextInt();

        cnt = dao.deleteGame(gnum);

        if(cnt == -1){
            System.out.println("삭제 실패");
        }else {
            System.out.println("삭제 성공");
        }
    }
}
