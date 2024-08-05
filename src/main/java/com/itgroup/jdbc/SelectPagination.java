package com.itgroup.jdbc;

import com.itgroup.bean.Game;
import com.itgroup.dao.GamaDao;
import com.itgroup.dao.ShowData;
import com.itgroup.utility.Paging;

import java.util.List;
import java.util.Scanner;

public class SelectPagination {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("몇 페이지 볼꺼니?");
        String pageNumber = scan.next();
        System.out.println();

        System.out.print("페이지 당 몇 건씩 볼꺼니?");
        String pageSize = scan.next();
        System.out.println();

        System.out.print("all, FPS, RPG, Survival, 액션, 모바일, 전략 중 하나를 입력하세요: ");
        String mode = scan.next();

        GamaDao dao = new GamaDao();
        int totalcount = 0;
        totalcount = dao.getTotalCount(mode);
        Paging pageInfo = new Paging(pageNumber,pageSize,totalcount,"", mode, "");
        pageInfo.displayInformation();

        List<Game> gameList = dao.getPaginationData(pageInfo);

        for(Game bean : gameList){
            ShowData.printBean(bean);
        }
    }
}
