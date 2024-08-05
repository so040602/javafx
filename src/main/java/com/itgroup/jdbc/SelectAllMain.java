package com.itgroup.jdbc;

import com.itgroup.bean.Game;
import com.itgroup.dao.GamaDao;
import com.itgroup.dao.ShowData;

import java.util.List;

public class SelectAllMain {
    public static void main(String[] args) {
        GamaDao dao = new GamaDao();
        List<Game> gameList = dao.sellectAll();

        System.out.println(gameList);

        for(Game bean : gameList){
            ShowData.printBean(bean);
        }
    }
}
