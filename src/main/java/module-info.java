module com.itgroup.kimcwproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;
    requires java.desktop;

    opens com.itgroup.kimcwproject to javafx.fxml;
    exports com.itgroup.kimcwproject;


    // JavaFX 애플리케이션에서 발생하는 접근 제어 문제
    // graphics 모듈이 application 폴더를 접근할 수 있도록 처리합니다.
    exports com.itgroup.application to javafx.graphics;

    // 모듈 com.itgroup.www가 com.itgroup.controller 패키지를 javafx.fxml 모듈에 공개합니다.
    opens com.itgroup.controller to javafx.fxml;

    opens com.itgroup.application to javafx.fxml;

    opens com.itgroup.bean to javafx.base;

}