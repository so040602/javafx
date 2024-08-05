package com.itgroup.jdbc;

import com.itgroup.bean.Game;
import com.itgroup.dao.GamaDao;
import com.itgroup.dao.ShowData;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UpdateMain {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        GamaDao dao = new GamaDao();
        Game upGame = new Game();
        int gnum;
        int cnt = -1;

        System.out.print("게임 번호 : ");
        gnum = scan.nextInt();

        upGame.setGnum(gnum);
        upGame.setGname("fderg");
        upGame.setGgenre("FPS");
        upGame.setImage01("123.jpg");
        upGame.setImage02("");
        upGame.setCompany("bfdf");
        upGame.setPrice(32000);
        upGame.setReldate("2024-01-19");
        upGame.setContent("fewgre");
        upGame.setCount(2);

        cnt = dao.updateGame(upGame);
        List<Game> gameList = new ArrayList<>();

        if(cnt != -1){
            System.out.println("Update 성공");
            gameList = dao.sellectAll();
            for(Game bean : gameList){
                ShowData.printBean(bean);
            }
        }else{
            System.out.println("Update 실패");
        }
    }
}
