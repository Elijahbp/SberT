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
import com.vk.api.sdk.queries.likes.LikesGetListQuery;
import com.vk.api.sdk.queries.likes.LikesType;
import com.vk.api.sdk.queries.users.UserField;
import com.vk.api.sdk.queries.wall.WallGetFilter;

import java.awt.font.TextHitInfo;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class ClientVK {

    private final VkApiClient vk;
    private UserActor actor;
    private int countLikes = 0;
    private int countComments = 0;
    private TotalInformationVO totalInformationVO;
    public ClientVK(Integer userIdActor, String accessToken) {
        TransportClient transportClient = new HttpTransportClient();
        this.vk = new VkApiClient(transportClient);
        this.actor = new UserActor(userIdActor, accessToken);
    }

    public TotalInformationVO getUserTotalInformation(String userDomain) throws ClientException, ApiException {
        UserXtrCounters mainUser = getUser(userDomain);
        List<WallPostFull>wallPostFulls = getWallPostFull(mainUser.getId());
        setCountsFromWallPost(wallPostFulls);

        this.totalInformationVO = new TotalInformationVO(mainUser,countLikes,countComments,
                getUsersAndCountLikes(wallPostFulls));
        return totalInformationVO;
    }

    private UserXtrCounters getUser(String userID) throws ClientException, ApiException {
        List<UserXtrCounters> userList;
        UserXtrCounters user = null;
        userList = vk.users().get(actor).userIds(userID).fields(UserField.PHOTO_MAX_ORIG).execute();
        if (userList.size() ==1){
            user = userList.stream().filter(Objects::nonNull).findAny().get();
            return user;
        }else if (user == null){
            //TODO придумать что сделать
        }
        return null;
    }

    //!!! ПОРЯДОК СОХРАНЯЕТСЯ!!!
    private List<UserXtrCounters> getUsers(List<Integer> usersId) throws ClientException, ApiException {
        ArrayList<String> users = new ArrayList<>();
        usersId.forEach(integer -> users.add(integer.toString()));
        List<UserXtrCounters> usersList = new ArrayList<>();
        usersList.addAll(vk.users().get(actor).userIds(users).execute());
        return usersList;
    }



    private List<WallPostFull> getWallPostFull(int userId) throws ClientException, ApiException {
        List<WallPostFull>wallPostFulls = new ArrayList<>();
        GetResponse wallGetQuery = null;
        wallGetQuery = this.vk.wall().get(this.actor).ownerId(userId).filter(WallGetFilter.ALL).count(300).execute();
        wallPostFulls.addAll(wallGetQuery.getItems());
        return wallPostFulls;
    }

    //TODO подумать над тем, как лучше представить счетчики
    private void setCountsFromWallPost(List<WallPostFull>wallPostFulls){
        this.countLikes = 0;
        this.countComments = 0;
        for (WallPostFull postFull: wallPostFulls) {
            countComments += postFull.getComments().getCount();
            countLikes += postFull.getLikes().getCount();
        }
    }

    private HashMap<UserXtrCounters,Integer> getUsersAndCountLikes(List<WallPostFull>wallPostFulls) throws ClientException, ApiException {
        List<Integer> listUsersIdFromPost = new ArrayList<>();
        wallPostFulls.forEach(wallPostFull -> {
            try {
                listUsersIdFromPost.addAll(vk.likes().getList(actor,LikesType.POST).itemId(wallPostFull.getId()).ownerId(wallPostFull.getFromId()).count(50).execute().getItems());
            } catch (ApiException e) {
                e.printStackTrace();
            } catch (ClientException e) {
                e.printStackTrace();
            }
            synchronized (vk){
                    try {
                        vk.wait(340);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

        });

        List<UserXtrCounters> usersList = getUsers(listUsersIdFromPost);

        HashMap<UserXtrCounters,Integer> mapUsersAndCountLikes = new HashMap<>();

        usersList.forEach(user -> {
            listUsersIdFromPost.forEach(bufId -> {
                if (!mapUsersAndCountLikes.containsKey(user)){
                    mapUsersAndCountLikes.put(user,1);
                }else if (user.getId().equals(bufId)){
                    mapUsersAndCountLikes.put(user,mapUsersAndCountLikes.get(user)+1);
                }
            });
        });
        mapUsersAndCountLikes.forEach((userXtrCounters, integer) -> {
            System.out.println(userXtrCounters.getFirstName() + " "+ userXtrCounters.getLastName() +" = "+ integer);
        });

        return mapUsersAndCountLikes;
    }




    //TODO придумать что-то с кол-вом вызовом




}