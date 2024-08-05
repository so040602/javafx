package com.itgroup.utility;

import javafx.scene.control.Alert;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Utility {
    public static final String FXML_PATH = "/com/itgroup/fxml/"; // fxml 파일이 있는 경로
    public static final String IMAGE_PATH = "/com/itgroup/images/"; // 이미지 파일이 있는 경로
    public static final String CSS_PATH = "/com/itgroup/css/"; // 이미지 파일이 있는 경로
    public static final String DATA_PATH = "\\src\\main\\java\\com\\itgroup\\data\\";

    private static final Map<String,String> categoryMap = new HashMap<>();

    private static String makeMapData(String category, String mode){
        // 사용자가 카테고리 선택시 '한글'을 보고 선택하므로 key에 한글 이름이 들어가야 합니다.
        // 맵 자료구조는 Value를 이용하여 Key 검색을 못합니다.

        // mode가 "key"(키 찾기), "value"(값 찾기)
        categoryMap.put("음료수", "baverage");
        categoryMap.put("빵", "bread");
        categoryMap.put("마카롱", "macaron");
        categoryMap.put("케익", "cake");

        if(mode.equals("value")){// 키로 value 찾기
            System.out.println(category);
        }else {// 값으로 키 찾기
            for(String key : categoryMap.keySet()){
                if(categoryMap.get(key).equals(category)){
                    return key; // value이 발견되었으므로 key를 반환한다.
                }
            }
            return null; //key가 없군요.
        }

        return categoryMap.get(category);
    }

    public static String getCategoryName(String category, String mode) {
        return makeMapData(category, mode);
    }

    public static void showAlert(Alert.AlertType alertType, String[] message) {
        // 단순 메시지 박스를 보여 주기 위한 유틸리티 메소드입니다.
        Alert alert = new Alert(alertType);
        alert.setTitle(message[0]);
        alert.setHeaderText(message[1]);
        alert.setContentText(message[2]);
        alert.showAndWait();
    }

    public static LocalDate getDatePicker(String inputData) {
        //문자열을 LocalData 타입으로 변환하여 반환합니다.
        // 회원 가입일자, 게시물 작성 일자, 상품 등록 일자 등에서 사용할 수 있습니다.
        int year = Integer.valueOf(inputData.substring(0,4));
        int month = Integer.valueOf(inputData.substring(5,7));
        int day = Integer.valueOf(inputData.substring(8));
        return LocalDate.of(year,month,day);
    }

}
