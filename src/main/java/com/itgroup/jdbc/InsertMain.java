package com.itgroup.jdbc;

import com.itgroup.bean.Game;
import com.itgroup.dao.GamaDao;
import com.itgroup.dao.ShowData;

import java.util.ArrayList;
import java.util.List;

public class InsertMain {
    public static void main(String[] args) {
        GamaDao dao = new GamaDao();
        Game insGame = new Game();
        insGame.setGname("abc");
        insGame.setGgenre("RPG");
        insGame.setImage01("");
        insGame.setImage02("");
        insGame.setCompany("abc");
        insGame.setPrice(32000);
        insGame.setReldate("2024-01-19");
        insGame.setContent("acbdsfed");
        int cnt = -1;
        cnt = dao.InsertGame(insGame);
        List<Game> gameList = new ArrayList<>();

        if(cnt != -1){
            System.out.println("Insert 성공");
            gameList = dao.sellectAll();
            for(Game bean : gameList){
                ShowData.printBean(bean);
            }
        }else{
            System.out.println("Insert 실패");
        }

    }
}
