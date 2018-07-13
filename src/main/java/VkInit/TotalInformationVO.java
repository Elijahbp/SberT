package VkInit;

import com.vk.api.sdk.objects.users.UserXtrCounters;

import java.util.List;

public class TotalInformationVO {
    private UserXtrCounters mainUser;
    private int countLikesFromWall;
    private int countCommentsFromWall;
    private String photoId;



    private List<UserXtrCounters> usersFromComments;



    public TotalInformationVO() {
    }

    public TotalInformationVO(UserXtrCounters user, int countLikesFromWall, int countCommentsFromWall, List<UserXtrCounters> usersFromComments) {
        this.mainUser = user;
        this.countLikesFromWall = countLikesFromWall;
        this.countCommentsFromWall = countCommentsFromWall;
        this.usersFromComments =  usersFromComments;
        this.photoId = user.getPhotoMaxOrig();
    }

    public List<UserXtrCounters> getUsersFromComments() {
        return usersFromComments;
    }

    public void setUsersFromComments(List<UserXtrCounters> usersFromComments) {
        this.usersFromComments = usersFromComments;
    }

    public UserXtrCounters getMainUser() {
        return mainUser;
    }

    public TotalInformationVO setUserID(UserXtrCounters user) {
        this.mainUser = user;
        return this;
    }

    public Integer getCountLikesFromWall() {
        return countLikesFromWall;
    }

    public TotalInformationVO setCountLikesFromWall(int countLikesFromWall) {
        this.countLikesFromWall = countLikesFromWall;
        return this;
    }

    public Integer getCountCommentsFromWall() {
        return countCommentsFromWall;
    }

    public TotalInformationVO setCountCommentsFromWall(int countCommentsFromWall) {
        this.countCommentsFromWall = countCommentsFromWall;
        return this;
    }

    public TotalInformationVO setPhotoId(String photoId){
        this.photoId = photoId;
        return this;
    }
}
