package com.itgroup.jdbc;

import com.itgroup.dao.GamaDao;

import java.util.Scanner;

public class SelectTotalCount {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        GamaDao dao = new GamaDao();
        int count = 0;

        System.out.print("all, FPS, RPG, Survival, 액션, 모바일, 전략 중 하나를 입력하세요: ");
        String ggenre = scan.next();

        count = dao.getTotalCount(ggenre);

        System.out.println(ggenre + "의 개수는 " + count + "이다.");
    }
}
