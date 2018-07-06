package FX;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import vk_init.ClientVK;

import java.io.IOException;


public class Main extends Application {
    private static int API_ID = 6622888;
    //private static String CLIENT_SECRET = "ojmrMmjQ5A3qI1ktPxNx";
    private static String REDIRECT_URL = "https://oauth.vk.com/blank.html";
    private static String DISPLAY = "page";
    private static int SCOPE = 1 + 2 + 4096 + 8192;


    private static String VK_AUTH = "https://oauth.vk.com/authorize?" +
            "client_id=" + API_ID +
            "&display="  + DISPLAY +
            "&redirect_uri=" + REDIRECT_URL +
            "&response_type="+ "token" +
            "&scope="+ SCOPE +
            "&revoke=1"+
            "&v=5.59";

    ClientVK clientVK;

    public static void main(String[] args){
        launch(args);
    }

    private void auth(String newValue) {
        this.clientVK = new ClientVK(newValue);
        Controller.setClient(this.clientVK);
    }

    private void setSearchScene(Stage primaryStage) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Main form");
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load(VK_AUTH);

        primaryStage.setScene(new Scene(webView));
        primaryStage.show();



        webEngine.locationProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.startsWith(REDIRECT_URL)){
                    System.out.println(newValue);
                    primaryStage.close();
                    auth(newValue);
                    try {
                        setSearchScene(primaryStage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}


//TODO: решить проблему с зависимостями (не цепляет схему)
//TODO: придумать, как хранить данные авторизации, чтобы по сто раз не авторизовываться при каждом запуске