package VkInit;

import com.google.gson.JsonElement;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import com.vk.api.sdk.queries.users.UserField;
import com.vk.api.sdk.queries.wall.WallGetFilter;

import java.util.List;

public class ClientVK {

    private VkApiClient vk;
    private UserActor actor;
//    private static List<UserField> infoAboutUser;

    public ClientVK(Integer userIdActor, String accessToken) {
        TransportClient transportClient = new HttpTransportClient();
        this.vk = new VkApiClient(transportClient);

        this.actor = new UserActor(userIdActor, accessToken);
    }


    public TotalInformationVO getClient(String userID) {
        JsonElement jsonElement = null;
        List<UserXtrCounters> usersXtrCounters = null;
        try {
            usersXtrCounters=  vk.users().get(actor).userIds(userID).fields(UserField.PHOTO_MAX_ORIG, UserField.PHOTO_200_ORIG).execute();
            GetResponse wallGetQuery = this.vk.wall().get(this.actor).domain(userID).filter(WallGetFilter.ALL).execute();

        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        for (UserXtrCounters userXtrCounters: usersXtrCounters) {
            System.out.println(userXtrCounters.getFirstName());
        }


        return null;
    }
}