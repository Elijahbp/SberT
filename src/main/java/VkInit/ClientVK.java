package VkInit;

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

import java.util.HashMap;

public class ClientVK {

    private VkApiClient vk;
    private UserActor actor;
    private static HashMap<UserField, String> infoAboutUser;

    public ClientVK(String tokenUri) {
        TransportClient transportClient = new HttpTransportClient();
        this.vk = new VkApiClient(transportClient);

        String token_actor = tokenUri.split("#")[1].split("&")[0].split("=")[1];
        Integer user_actor_id = Integer.parseInt(tokenUri.split("&")[2].split("=")[1]);
        this.actor = new UserActor(user_actor_id, token_actor);

    }

    public TotalInformationVO getTotalInformation(String userID) {
        TotalInformationVO totalInformationVO = new TotalInformationVO();
        totalInformationVO
                .setUserID(getUser(userID))
                .setCountLikes_Wall(getCountLikesFromWall(userID))
                .setCountComments_Wall(getGetCountCommentsFromWall(userID));
        return totalInformationVO;
    }

    public UserXtrCounters getUser(String userID) {
        UserXtrCounters userXtrCounters = null;
        try {
            userXtrCounters = vk.users().get(actor).userIds(userID).execute().get(0);
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return userXtrCounters;
    }

    public int getGetCountCommentsFromWall(String userID) {
        int count = 0;

        try {
            GetResponse wallGetQuery = this.vk.wall().get(this.actor).domain(userID).filter(WallGetFilter.ALL).execute();
            for (WallPostFull postFull : wallGetQuery.getItems()) {
                count += postFull.getComments().getCount();
            }
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int getCountLikesFromWall(String userID) {

        int count = 0;
        try {
            GetResponse wallGetQuery = this.vk.wall().get(this.actor).domain(userID).filter(WallGetFilter.ALL).execute();
            for (WallPostFull postFull : wallGetQuery.getItems()) {
                count += postFull.getLikes().getCount();
            }
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return count;
    }
}