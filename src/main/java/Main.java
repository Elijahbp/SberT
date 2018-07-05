
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.objects.wall.WallPostFull;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import com.vk.api.sdk.queries.users.UserField;
import com.vk.api.sdk.queries.wall.WallGetFilter;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import vk_init.ClientVK;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Main extends Application {
    private static int API_ID = 6622888;
    private static String CLIENT_SECRET = "ojmrMmjQ5A3qI1ktPxNx";
    private static String REDIRECT_URL = "https://oauth.vk.com/blank.html";
    private static int SCOPE = 1 + 2 + 4096 + 8192;
    private static String VK_AUTH = "https://oauth.vk.com/authorize?" +
            "client_id=" + API_ID +
            "&display=page" +
            "&redirect_uri=" + REDIRECT_URL +
            "&response_type=token" +
            "&scope="+ SCOPE +
            "&revoke=1"+
            "&v=5.59";


    public static void main(String[] args) throws ClientException, ApiException, IOException {
        Main.launch();
    }

    private void auth(String newValue) throws ClientException, ApiException {
        ClientVK clientVK = new ClientVK(newValue);
        HashMap<String,Object> getInf = clientVK.getTotalInformation("asja_1111");
        System.out.println(((UserXtrCounters)getInf.get("user")).getFirstName() + " " + ((UserXtrCounters) getInf.get("user")).getLastName());
        System.out.println(getInf.get("countLikes_Wall"));
        System.out.println(getInf.get("countComments_Wall"));
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
                    try {
                        auth(newValue);
                    } catch (ClientException e) {
                        e.printStackTrace();
                    } catch (ApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
