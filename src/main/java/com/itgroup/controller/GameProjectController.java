package com.itgroup.controller;

import com.itgroup.bean.Game;
import com.itgroup.dao.GamaDao;
import com.itgroup.utility.Paging;
import com.itgroup.utility.Utility;
import javafx.application.Platform;
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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class GameProjectController implements Initializable {

    @FXML private Button btnCart;
    @FXML private ComboBox<String> fieldSearch;
    @FXML private Label lblChoice;
    @FXML private Label pageStatus;
    @FXML private TableView<Game> gameTable;
    @FXML private Pagination pagination;
    @FXML private ImageView imageView;
    @FXML private TextArea textAreaContents;

    GamaDao dao = null;
    private ObservableList<Game> dataList = null;
    private String mode = null;
    private List<Game> cartList = null;
    private ObservableList<Game> cartData = FXCollections.observableArrayList();
    private Map<Integer, Game> cartMap = null;

    public Map<Integer, Game> getCartMap() {
        return cartMap;
    }

    public void setCartMap(Map<Integer, Game> cartMap) {
        this.cartMap = cartMap;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        dao = new GamaDao();
        cartList = new ArrayList<>();
        cartMap = new HashMap<>();


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
                    System.out.println(imageFile);
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
        gameTable.getSelectionModel().selectedItemProperty().addListener(gameListener);
        setContextMenu();
    }

    private void setContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem1 = new MenuItem("장바구니");
        MenuItem menuItem2 = new MenuItem("단가/무료 막대 그래프");

        contextMenu.getItems().addAll(menuItem1, menuItem2);

        gameTable.setContextMenu(contextMenu);

        menuItem1.setOnAction(event -> {
            try {
                int idx = gameTable.getSelectionModel().getSelectedIndex();
                System.out.println(idx);
                if(idx >= 0){
                    Game bean = gameTable.getSelectionModel().getSelectedItem();
                    if(this.cartMap.containsKey(bean.getGnum())){
                        Game existingGame = this.cartMap.get(bean.getGnum());
                        int count = existingGame.getCount() + 1;
                        existingGame.setCount(count);
                        System.out.println("갯수 : " + existingGame.getCount());
                    }else{
                        this.cartMap.put(bean.getGnum(), bean);
                    }

                    cartList.clear();
                    for(Game game : cartMap.values()){
                        cartList.add(game);
                        System.out.println("Map Value" + game);
                    }
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        });

        menuItem2.setOnAction(event -> {
            try {
                makeBarchart();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        });
    }

    private void makeBarchart() throws Exception{
        String fileName = Utility.FXML_PATH + "BarChart.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fileName));
        Parent parent = fxmlLoader.load();

        BarChartController controller = fxmlLoader.getController();
        controller.makeChartAll(gameTable.getItems());

        this.showModel(parent);

    }

    private void showModel(Parent parent) {
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    private void setPagination(int pageIndex) {
        pagination.setPageFactory(this::createpage);
        pagination.setCurrentPageIndex(pageIndex);


        String defaultImage = Utility.IMAGE_PATH + "default.png";
        String url = getClass().getResource(defaultImage).toString();
        Image image = new Image(url);
        imageView.setImage(image);
    }

    private Node createpage(Integer pageIndex) {
        int totalCount = 0;

        totalCount = dao.getTotalCount(mode);

        Paging pageInfo = new Paging(String.valueOf(pageIndex + 1), "10", totalCount, null, this.mode, null);
        pagination.setPageCount(pageInfo.getTotalPage());

        fillTableData(pageInfo);

        VBox vbox = new VBox(gameTable);

        return vbox;
    }

    private void fillTableData(Paging pageInfo) {
        List<Game> gameList = dao.getPaginationData(pageInfo);
        dataList = FXCollections.observableArrayList(gameList);

        gameTable.setItems(dataList);
        pageStatus.setText(pageInfo.getPagingStatus());
    }

    private void setTableColumns() {
        String[] fields = {"gname", "ggenre", "price", "reldate", "content"};
        String[] colNames = {"게임이름", "게임장르", "가격", "출시일자", "게임설명"};

        TableColumn tableColumn = null;
        for(int i = 0; i < fields.length; i++){
            tableColumn = gameTable.getColumns().get(i);
            tableColumn.setText(colNames[i]);

            tableColumn.setCellValueFactory(new PropertyValueFactory<>(fields[i]));

            tableColumn.setStyle("-fx-alignment:center;");
        }
    }

    public void onInsertGame(ActionEvent event) throws Exception{
        String fxmlFile = Utility.FXML_PATH + "GameInsert.fxml";
        URL url = getClass().getResource(fxmlFile);

        FXMLLoader fxmlLoader = new FXMLLoader(url);

        Parent container = fxmlLoader.load(); // fxml의 최상위 컨테이너 객체

        Scene scene = new Scene(container); //씬에 담기
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene); // 씬을 무대에 담기
        stage.setResizable(false);
        stage.setTitle("게임 등록하기");;
        stage.showAndWait(); // 창 띄우고 대기

        setPagination(0); // 화면 갱신
    }

    public void onUpdate(ActionEvent event) throws Exception{
        int idx = gameTable.getSelectionModel().getSelectedIndex();
        System.out.println(cartList);

        if(idx >= 0) {
            System.out.println("선택된 색인 번호 : " + idx);

            String fxmlFile = Utility.FXML_PATH + "GameUpdate.fxml";
            URL url = getClass().getResource(fxmlFile);

            FXMLLoader fxmlLoader = new FXMLLoader(url);

            Parent container = fxmlLoader.load();

            Game bean = gameTable.getSelectionModel().getSelectedItem();

            GameUpdateController controller = fxmlLoader.getController();
            controller.setBean(bean);

            Scene scene = new Scene(container);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("상품 수정하기");
            stage.showAndWait();

            setPagination(0);
        }else {
            String[] message = new String[] {"상품 선택 확인", "상품 미선택", "수정하고자 하는 상품을 선택해주세요."};
            Utility.showAlert(Alert.AlertType.ERROR, message);
        }
    }

    public void onDelete(ActionEvent event) {
        int idx = gameTable.getSelectionModel().getSelectedIndex();

        if (idx >= 0){
            String[] message = new String[]{"삭제 확인 메시지", "삭제 항목 선택 대화 상자", "이 항목을 정말로 삭제하시겠습니까?"};

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(message[0]);
            alert.setHeaderText(message[1]);
            alert.setContentText(message[2]);

            Optional<ButtonType> response = alert.showAndWait();

            if(response.get() == ButtonType.OK){
                Game bean = gameTable.getSelectionModel().getSelectedItem();
                int gnum = bean.getGnum();
                int cnt = dao.deleteGame(gnum);

                if(cnt != -1){
                    System.out.println("삭제 성공");
                    setPagination(0);
                }else {
                    System.out.println("삭제 실패");
                }
            }else {
                System.out.println("삭제를 취소하였습니다.");
            }
        }else{
            String[] message = new String[] {"삭제할 목록", "삭제할 대상 미선택", "삭제할 행을 선택해 주세요."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
        }
    }

    public void onCart(ActionEvent event) throws Exception{

        String fxmlFile = Utility.FXML_PATH + "MyCart.fxml";
        URL url = getClass().getResource(fxmlFile);

        FXMLLoader fxmlLoader = new FXMLLoader(url);

        Parent container = fxmlLoader.load(); // fxml의 최상위 컨테이너 객체


        MyCartController controller = fxmlLoader.getController();
        controller.setBean(cartList, this);

        Scene scene = new Scene(container); //씬에 담기
        String myStyle = getClass().getResource(Utility.CSS_PATH + "CartCss.css").toString();
        scene.getStylesheets().add(myStyle);

        Stage stage = new Stage();
//        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene); // 씬을 무대에 담기
        stage.setResizable(false);
        stage.setTitle("게임 장바구니");
        stage.showAndWait(); // 창 띄우고 대기
    }

    public void onClosing(ActionEvent event) {
        System.out.println("프로그램을 종료합니다.");
        Platform.exit();
    }

    public void choiceSelect(ActionEvent event) {
        this.mode = fieldSearch.getSelectionModel().getSelectedItem();
        if(mode.equals("전체보기")){
            mode = null;
        }
        System.out.println("필드 검색 모드 : [" + this.mode + "]");

        setPagination(0);
    }

    public void updateCartMap(List<Game> cartGameList) {
        this.cartMap.clear();
        for(Game bean : cartGameList){
            cartMap.put(bean.getGnum(), bean);
        }
    }
}
