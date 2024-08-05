package com.itgroup.dao;

import com.itgroup.bean.Game;

public class ShowData {
    public static void printBean(Game bean) {
        int gnum = bean.getGnum();
        String gname = bean.getGname();
        String ggenre = bean.getGgenre();
        String image01 = bean.getImage01();
        String image02 = bean.getImage02();
        String company = bean.getCompany();
        int price = bean.getPrice();
        String reldate = bean.getReldate();
        String content = bean.getContent();
        int count = bean.getCount();

        System.out.println("게임 번호 : " + gnum);
        System.out.println("게임 이름 : " + gname);
        System.out.println("게임 장르 : " + ggenre);
        System.out.println("이미지01 : " + image01);
        System.out.println("이미지02 : " + image02);
        System.out.println("게임 회사 : " + company);
        System.out.println("가격 : " + price);
        System.out.println("출시 일자 : " + reldate);
        System.out.println("게임 설명 : " + content);
        System.out.println("갯수 : " + count);

    }
}
