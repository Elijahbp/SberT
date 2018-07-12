package VkInit;

import com.google.gson.JsonElement;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.ServiceClientCredentialsFlowResponse;
import com.vk.api.sdk.queries.users.UserField;
import com.vk.api.sdk.queries.wall.WallGetFilter;

public class ClientVK {

    private VkApiClient vk;
    private UserActor actor;
//    private static List<UserField> infoAboutUser;

    public ClientVK(Integer userIdActor,String accessToken) {
        TransportClient transportClient  = new HttpTransportClient();
        this.vk = new VkApiClient(transportClient);

        this.actor = new UserActor(userIdActor,accessToken);
    }



    public TotalInformationVO getClient(String userID){
        JsonElement jsonElement = null;
        try {
             jsonElement = vk.execute().batch(actor,
                     vk.users().get(actor).userIds(userID).fields(UserField.PHOTO_MAX_ORIG,UserField.PHOTO_200_ORIG),
                     this.vk.wall().get(this.actor).domain(userID).filter(WallGetFilter.ALL),
                     this.vk.wall().get(this.actor).domain(userID).filter(WallGetFilter.ALL)
                     ).execute();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        jsonElement.toString();
        return null;
    }
//vk.users().get(actor).userIds(userID).fields(UserField.PHOTO_MAX_ORIG,UserField.PHOTO_200_ORIG),
//                    vk.wall().get(this.actor).domain(userID).filter(WallGetFilter.ALL),
//                    vk.users().get(actor).userIds(userID)
//    public TotalInformationVO getTotalInformation(String userID) {
//        TotalInformationVO totalInformationVO = new TotalInformationVO();
//        totalInformationVO
//                .setUserID(getUser(userID))
//                .setCountLikes_Wall(getCountLikesFromWall(userID))
//                .setCountComments_Wall(getGetCountCommentsFromWall(userID))
//                .setPhotoId(getPhotoId(userID));
//        return totalInformationVO;
//    }

//    private UserXtrCounters getUser(String userID) {
//        UserXtrCounters userXtrCounters = null;
////        infoAboutUser = new Vector<>();
////        infoAboutUser.add(UserField.PHOTO_400_ORIG);
//
//        try {
//            userXtrCounters = vk.users().get(actor).userIds(userID).fields(UserField.PHOTO_MAX_ORIG,UserField.PHOTO_200_ORIG).execute().get(0);
//        } catch (ApiException e) {
//            e.printStackTrace();
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }
//        return userXtrCounters;
//    }
//
//        this.vk.wall().get(this.actor).domain(userID).filter(WallGetFilter.ALL).execute()
//        this.vk.wall().get(this.actor).domain(userID).filter(WallGetFilter.ALL).execute()


}