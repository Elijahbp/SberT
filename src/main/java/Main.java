
import com.vk.api.sdk.actions.Friends;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.friends.responses.GetResponse;
import com.vk.api.sdk.objects.search.Hint;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.queries.friends.FriendsGetListsQuery;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class Main extends Application {
    private static int API_ID = 6622888;
    private static String CLIENT_SECRET = "ojmrMmjQ5A3qI1ktPxNx";
    private static String REDIRECT_URL = "https://oauth.vk.com/blank.html";
    private static int SCOPE = 1 + 2 + 8195;
    private static String VK_AUTH = "https://oauth.vk.com/authorize?" +
            "client_id=" +API_ID+
            "&display=page" +
            "&redirect_uri=" +REDIRECT_URL+
            "&response_type=token" +
            "&scope="+SCOPE+
            "&revoke=1"+
            "&v=5.59";
    private String tokenUrl;

    public static void main(String[] args) throws ClientException, ApiException, IOException {
        Main.launch();
    }

    private void auth() throws ClientException, ApiException {

        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(transportClient);


        String token = tokenUrl.split("#")[1].split("&")[0].split("=")[1];
        Integer user_id= Integer.parseInt(tokenUrl.split("&")[2].split("=")[1]);
        UserActor actor = new UserActor(user_id,token);

        List<UserXtrCounters> friendsGetListsQuery = vk.users().get(actor).userIds("203566279").execute();
        System.out.println(friendsGetListsQuery.toString());
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
                    tokenUrl = newValue;
                    System.out.println(newValue);
                    primaryStage.close();
                    try {
                        auth();
                    } catch (ClientException e) {
                        e.printStackTrace();
                    } catch (ApiException e) {
                        e.printStackTrace();
                    }
                    //primaryStage.close();
                }
            }
        });
    }
}
