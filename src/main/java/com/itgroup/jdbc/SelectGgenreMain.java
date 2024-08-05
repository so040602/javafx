package com.itgroup.jdbc;

import com.itgroup.bean.Game;
import com.itgroup.dao.GamaDao;
import com.itgroup.dao.ShowData;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SelectGgenreMain {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        GamaDao dao = new GamaDao();
        List<Game> gameList = new ArrayList<>();
        String ggenre;

        System.out.print("all, FPS, RPG, Survival, 액션, 모바일, 전략 중 하나를 입력하세요: ");
        ggenre = scan.next();

        gameList = dao.SelectGgenreGame(ggenre);

        for(Game bean : gameList){
            ShowData.printBean(bean);
        }
    }
}
