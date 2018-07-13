package VkInit;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.objects.wall.WallPostFull;
import com.vk.api.sdk.objects.wall.responses.GetCommentsResponse;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import com.vk.api.sdk.queries.users.UserField;
import com.vk.api.sdk.queries.wall.WallGetFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClientVK {

    private VkApiClient vk;
    private UserActor actor;
    private int countLikes =0;
    private int countComments =0;
    public ClientVK(Integer userIdActor, String accessToken) {
        TransportClient transportClient = new HttpTransportClient();
        this.vk = new VkApiClient(transportClient);
        this.actor = new UserActor(userIdActor, accessToken);
    }


    public TotalInformationVO getClient(String userID) throws ClientException, ApiException {
        UserXtrCounters bufUser = null;
        GetResponse wallGetQuery = null;
        try {
            bufUser =  getUser(userID);
            wallGetQuery = this.vk.wall().get(this.actor).domain(userID).filter(WallGetFilter.ALL).execute();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }

        getCountsFromWallPost(wallGetQuery);
        TotalInformationVO totalInformationVO = null;
        if (bufUser != null){
            totalInformationVO = new TotalInformationVO(bufUser,this.countLikes,this.countComments,getUsersFromComments(wallGetQuery));
        }

        return totalInformationVO;
    }

    private void getCountsFromWallPost(GetResponse wallGetQuery){
        this.countLikes = 0;
        this.countComments = 0;
        assert wallGetQuery != null;
        for (WallPostFull postFull: wallGetQuery.getItems()) {
            this.countComments += postFull.getComments().getCount();
            this.countLikes += postFull.getLikes().getCount();
        }
    }

    private List<UserXtrCounters> getUsersFromComments(GetResponse wallGetQuery) throws ClientException, ApiException {
        List<GetCommentsResponse> wallCommentsGetQuery = new ArrayList<>();
        List<UserXtrCounters> usersList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            wallCommentsGetQuery.add(this.vk.wall().getComments(actor,wallGetQuery.getItems().get(i).getId()).count(10).execute());
        }
        List<Integer> usersIdList = new ArrayList<>();
        wallCommentsGetQuery.forEach(getCommentsResponse -> {
            getCommentsResponse.getItems().forEach(wallComment -> {
                usersIdList.add(wallComment.getFromId());
            });
        });

        usersIdList.forEach(System.out::println);
        usersIdList.forEach(integer->{
                usersList.add(getUser(integer.toString()));
            }
        );
        usersList.forEach(System.out::println);
        return usersList;
    }

    private UserXtrCounters getUser(String userId){
        UserXtrCounters user = null;
        try {
            user = vk.users().get(actor).userIds(userId).fields(UserField.PHOTO_MAX_ORIG).execute().get(0); //решил использовать получение объекта в статическом виде, т.к. мы получаем один объект от одного id
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return user;
    }

}