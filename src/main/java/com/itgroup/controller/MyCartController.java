package com.itgroup.controller;

import com.itgroup.bean.Game;
import com.itgroup.dao.GamaDao;
import com.itgroup.utility.Paging;
import com.itgroup.utility.Utility;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

public class MyCartController implements Initializable {
    @FXML private TableView<Game> cartTableView;
    @FXML private ImageView imageView;
    @FXML private Pagination pagination;

    private List<Game> cartGameList = null;
    @FXML private  ImageView cartImage;
    private ObservableList<Game> datalist = null;
    private GameProjectController gameProjectController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Image image = new Image(getClass().getResource("/com/itgroup/images/cart.jpg").toExternalForm());
        cartImage.setImage(image);

        cartGameList = new ArrayList<>();

        setTableColumns();
        setPagination(0);

        ChangeListener<Game> gameListener = new ChangeListener<Game>() {
            @Override
            public void changed(ObservableValue<? extends Game> observableValue, Game oldValue, Game newValue) {
                if(newValue != null){
                    System.out.print("게임 이름 : ");
                    System.out.println(newValue);

                    String imageFile = "";

                    if(newValue.getImage01() != null){
                        imageFile = Utility.IMAGE_PATH + newValue.getImage01().trim();
                    }else {
                        imageFile = Utility.IMAGE_PATH + "default.png";
                    }
                    Image someImage = null;
                    if(getClass().getResource(imageFile) == null) {
                        imageView.setImage(null);
                    }else{
                        someImage = new Image(getClass().getResource(imageFile).toString());
                        imageView.setImage(someImage);
                    }
                }
            }
        };
        cartTableView.getSelectionModel().selectedItemProperty().addListener(gameListener);

    }

    private void setPagination(int pageIndex) {
        pagination.setPageFactory(this::createpage);
        pagination.setCurrentPageIndex(pageIndex);
    }

    private Node createpage(Integer pageIndex) {
        int totalCount = 0;

        totalCount = cartGameList.size();

        Paging pageInfo = new Paging(String.valueOf(pageIndex + 1),
                "10", totalCount, null, null, null);
        pagination.setPageCount(pageInfo.getTotalPage());

        fillDataCartTable(pageInfo);

        VBox vbox = new VBox(cartTableView);

        return vbox;
    }

    private void fillDataCartTable(Paging pageInfo) {

        int startIndex = pageInfo.getBeginRow() - 1;
        int endIndex = pageInfo.getEndRow();

        List<Game> cartList = new ArrayList<>();
        cartList = cartGameList.subList(startIndex, endIndex);

        for(Game bean : cartList){
            System.out.println("bean :" + bean);
        }

        datalist = FXCollections.observableArrayList(cartList);
        cartTableView.setItems(datalist);
    }

    private void setTableColumns() {
        String[] fields = {"gname", "ggenre", "price", "reldate", "content", "count"};
        String[] colNames = {"게임이름", "게임장르", "가격", "출시일자", "게임설명", "갯수"};

        TableColumn tableColumn = null;

        for(int i = 0; i < fields.length; i++){
            tableColumn = cartTableView.getColumns().get(i);
            tableColumn.setText(colNames[i]);

            tableColumn.setCellValueFactory(new PropertyValueFactory<>(fields[i]));

            tableColumn.setStyle("-fx-alignment:center;");
        }
    }

    public void setBean(List<Game> gameList, GameProjectController gameProjectController) {
        cartGameList.addAll(gameList);
        this.gameProjectController = gameProjectController;
    }


    public void deleteCart(ActionEvent event) throws Exception {
        int idx = cartTableView.getSelectionModel().getSelectedIndex();
        if(idx >= 0){
            String[] message = new String[]{"삭제 확인 메시지", "삭제 항목 선택 대화 상자", "이 항목을 정말로 삭제하시겠습니까?"};

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(message[0]);
            alert.setHeaderText(message[1]);
            alert.setContentText(message[2]);

            Optional<ButtonType> response = alert.showAndWait();

            if(response.get() == ButtonType.OK) {
                Game bean = cartTableView.getSelectionModel().getSelectedItem();

                if(bean != null) {
                    int count = bean.getCount() - 1;
                    if (count == 0) {
                        cartGameList.remove(bean);
                    } else {
                        bean.setCount(count);
                    }
                }
                setPagination(pagination.getCurrentPageIndex());
                gameProjectController.updateCartMap(cartGameList);
                cartTableView.refresh();
            }else {
                String[] message1 = new String[]{"삭제할 목록", "삭제할 대상 미선택", "삭제할 행을 선택해 주세요."};
                Utility.showAlert(Alert.AlertType.WARNING, message1);
            }
        }
    }

    public void totalValue(ActionEvent event) {
        int total = 0;
        int price = 0;
        for(Game bean : cartGameList){
            price = bean.getPrice() * bean.getCount();
            total += price;
        }
        System.out.println(total);

        String pattern = ",000";
        DecimalFormat df = new DecimalFormat(pattern);


        String[] message =new String[] {"총합 가격", "장바구니의 총 가격은 " + df.format(total) + "원 입니다.", "" };

        Utility.showAlert(Alert.AlertType.INFORMATION, message);

    }

}
