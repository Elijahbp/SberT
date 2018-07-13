package Main;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static int API_ID = 6622888;
    //private static String CLIENT_SECRET = "ojmrMmjQ5A3qI1ktPxNx";
    private static String REDIRECT_URL = "https://oauth.vk.com/blank.html";
    private static String DISPLAY = "page";
    private static int SCOPE = 1 + 2 + 4096 + 8192;
//    369512160
//    "eeedd0add24b2084672dbfaf295c77245ce055b67d52545cd88d96278af397e4b37303841dd31050b302f"
    private static String VK_AUTH = "https://oauth.vk.com/authorize?" +
            "client_id=" + API_ID +
            "&display=" + DISPLAY +
            "&redirect_uri=" + REDIRECT_URL +
            "&response_type=" + "token" +
            "&scope=" + SCOPE +
            "&revoke=1" +
            "&v=5.80";

    private Integer userIdActor;
    private String accessToken;
    private String tokenUrl;

    public static void main(String[] args) {
        launch(args);
    }

    private void setSearchScene(Stage primaryStage) throws IOException, ClientException, ApiException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("sample.fxml"));
        Parent parent = fxmlLoader.load();
        primaryStage.setTitle("Main form");
        primaryStage.setScene(new Scene(parent));
        Controller controller = fxmlLoader.getController();
        splitTokenUrl();
        controller.setClientId(369512160,"eeedd0add24b2084672dbfaf295c77245ce055b67d52545cd88d96278af397e4b37303841dd31050b302f");
        primaryStage.show();
    }

    private void splitTokenUrl(){
//        this.userIdActor = Integer.parseInt(this.tokenUrl.split("&")[2].split("=")[1]);
//        accessToken = this.tokenUrl.split("#")[1].split("&")[0].split("=")[1];
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        setSearchScene(primaryStage);
//        WebView webView = new WebView();
//        WebEngine webEngine = webView.getEngine();
//        webEngine.load(VK_AUTH);
//
//        primaryStage.setScene(new Scene(webView));
//        primaryStage.show();
//
//        webEngine.locationProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                if(newValue.startsWith(REDIRECT_URL)){
//                    tokenUrl = newValue;
//                    primaryStage.close();
//                    try {
//                        setSearchScene(primaryStage);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (ClientException e) {
//                        e.printStackTrace();
//                    } catch (ApiException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
    }
}
