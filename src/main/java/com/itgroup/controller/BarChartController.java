package com.itgroup.controller;

import com.itgroup.bean.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BarChartController implements Initializable {
    @FXML private BarChart<String, Integer> barChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("게임 막대 그래프 그리기");
    }

    public void makeChartAll(ObservableList<Game> gameList) {
        int mark = 0;
        for(Game bean : gameList){
            List<XYChart.Data<String, Integer>> lists = new ArrayList<>();

            mark = bean.getPrice();
            // 단가와 재고의 수치 정보가 너무 큰 차이가 나면 로그 함수를 고려해 보자.
            if(mark == 0){
                mark = 1000;
                lists.add(new XYChart.Data<String, Integer>("무료 게임", mark));
            }else {
                lists.add(new XYChart.Data<String, Integer>("유료 게임", bean.getPrice()));
            }

            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            series.setName(bean.getGname());
            series.setData(FXCollections.observableArrayList(lists));
            barChart.getData().add(series);
        }
        barChart.setTitle("무료 게임/유료 게임 막대 그래프");
    }
}
