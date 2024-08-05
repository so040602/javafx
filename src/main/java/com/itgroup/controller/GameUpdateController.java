package com.itgroup.controller;

import com.itgroup.bean.Game;
import com.itgroup.dao.GamaDao;
import com.itgroup.utility.Utility;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class GameUpdateController implements Initializable {
    @FXML private TextField fxmlGnum;
    @FXML private TextField fxmlName;
    @FXML private ComboBox<String> fxmlGenre;
    @FXML private TextField fxmlimage01;
    @FXML private TextField fxmlimage02;
    @FXML private TextField fxmlCompany;
    @FXML private TextField fxmlPrice;
    @FXML private DatePicker fxmlReldate;
    @FXML private TextField fxmlcontent;
    @FXML private TextField fxmlCount;

    private Game oldBean = null;
    private Game newBean = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("수정 할꺼야");
    }

    public void setBean(Game newBean) {
        this.oldBean = newBean;

        fillPrevousData();

        fxmlGnum.setVisible(false);
    }

    private void fillPrevousData() {
        // 과거에 내가 등록했던 정보들을 해당 컨트롤에 다시 입력해 줍니다.
        fxmlGnum.setText(String.valueOf(this.oldBean.getGnum()));
        fxmlName.setText(this.oldBean.getGname());
        fxmlGenre.setValue(this.oldBean.getGgenre());
        fxmlimage01.setText(this.oldBean.getImage01());
        fxmlimage02.setText(this.oldBean.getImage02());
        fxmlCompany.setText(this.oldBean.getCompany());
        fxmlPrice.setText(String.valueOf(this.oldBean.getPrice()));
        String relData = this.oldBean.getReldate();
        if(relData == null || relData.equals("null")){

        }else {
            fxmlReldate.setValue(Utility.getDatePicker(relData));
        }
        fxmlcontent.setText(this.oldBean.getContent());
    }

    public void onUpdateGame(ActionEvent event) {
        //먼저 유효성 검사를 진행합니다.
        boolean bool = validationCheck();

        if(bool == true){
            GamaDao dao = new GamaDao();
            int cnt = -1;
            cnt = dao.updateGame(this.newBean);
            if(cnt == -1){
                System.out.println("수정 실패");
            }else {
                Node source = (Node)event.getSource();
                Stage stage = (Stage)source.getScene().getWindow();
                stage.close();
            }
        }else {
            System.out.println("유효성 검사를 통과하지 못했습니다.");
        }
    }

    private boolean validationCheck() {
        // 수정을 위한 핵심 키(primary key)
        int gnum = Integer.valueOf(fxmlGnum.getText().trim());

        String[] message = null;

        String gname = fxmlName.getText().trim();
        if(gname.length() <=0 || gname.length() >= 26) {
            message = new String[] {"유효성 검사 : 이름", "길이 제한 위배", "이름은 1글자 이상, 25글자 이하이여만 합니다"};
            Utility.showAlert(Alert.AlertType.WARNING, message) ;
            return false;
        }

        int selectedIndex = fxmlGenre.getSelectionModel().getSelectedIndex();
        String ggenre = null;
        if(selectedIndex == 0){
            message = new String[] {"유효성 검사 : 게임장르", "장르 미선택", "원하시는 게임 장르를 반드시 선택해 주세요."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }else{
            ggenre = fxmlGenre.getSelectionModel().getSelectedItem();
            System.out.println("선택한 장르");
            System.out.println(ggenre);
        }

        boolean bool = false;
        String image01 = fxmlimage01.getText().trim();
        if(image01 == null || image01.length() < 1) {
            message = new String[] {"유효성 검사 : 이미지01", "필수 입력 체크", "1번 이미지는 필수 입력 사항입니다."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }

        bool = image01.endsWith(".jpg") || image01.endsWith(".png");
        if(!bool){
            message = new String[] {"유효성 검사: 이미지 01", "확장자 점검", "이미지의 확장자는 '.jpg' 또는 '.png'이어야 합니다."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }

        String image02 = null;

        if(fxmlimage02.getText() != null && fxmlimage02.getText().length() != 0){
            image02 = fxmlimage02.getText().trim();

            bool = image02.endsWith(".jpg") || image02.endsWith(".png");
            if (!bool) {
                message = new String[]{"유효성 검사 : 이미지02", "확장자 점검", "이미지의 확장자는 '.jpg' 또는 'png'이어야 합니다."};
                Utility.showAlert(Alert.AlertType.WARNING, message);
                return false;
            }
        }

        String company = fxmlCompany.getText().trim();
        if(company.length() <= 1 || company.length() >= 26){
            message = new String[] {"유효성 검사 : 게임 회사", "길이 제한 위배", "게임 회사명은 1글자 이상 25글자 이하이어야 합니다."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }

        int price = 0;
        try {
            String _price = fxmlPrice.getText().trim();
            price = Integer.valueOf(_price);

            if(price > 100000){
                message = new String[] {"유효성 검사 : 가격", "허용 숫자 위반", "가격은 100000원 이하로 입력해 주세요."};
                Utility.showAlert(Alert.AlertType.WARNING, message);
                return false;
            }

        }catch (NumberFormatException ex){
            ex.printStackTrace();

            message = new String[] {"유효성 검사 : 가격", "무효한 숫자 형식", "올바른 숫자 형식을 입력해 주세요."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }


        LocalDate _relData = fxmlReldate.getValue();
        String relData = null;
        if(_relData == null){
            message = new String[] {"유효성 검사 : 출시 일자", "무효한 날짜 형식", "올바른 출시 일자 형식을 이벽해 주세요."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }else{
            relData = _relData.toString();
            relData = relData.replace("-", "/");
        }

        String content = fxmlcontent.getText().trim();
        if(content.length() <= 4 || content.length() >=151){
            message = new String[] {"유효성 검사 : 설명", "길이 제한 위배", "설명은 4글자 이상 150글자 이하이어야 합니다."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }

        int count = 0;
        try {
            String _count = fxmlCount.getText().trim();
            count = Integer.valueOf(_count);

            if (count <= 0) {
                message = new String[]{"유효성 검사 : 가격", "갯수 위반", "갯수는 1개 이상이어야만 합니다."};
                Utility.showAlert(Alert.AlertType.WARNING, message);
                return false;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }


        this.newBean = new Game();
        newBean.setGnum(gnum);
        newBean.setGname(gname);
        newBean.setGgenre(ggenre);
        newBean.setImage01(image01);
        newBean.setImage02(image02);
        newBean.setCompany(company);
        newBean.setPrice(price);
        newBean.setReldate(relData);
        newBean.setContent(content);
        newBean.setCount(count);

        return true;
    }

}
