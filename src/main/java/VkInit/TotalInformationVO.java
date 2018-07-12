package VkInit;

import com.vk.api.sdk.objects.users.UserXtrCounters;

public class TotalInformationVO {
    public UserXtrCounters user;
    public int countLikes_Wall;
    public int countComments_Wall;
    public String photoId;
    public TotalInformationVO() {
    }

    public UserXtrCounters getUser() {
        return user;
    }

    public TotalInformationVO setUserID(UserXtrCounters user) {
        this.user = user;
        return this;
    }

    public Integer getCountLikes_Wall() {
        return countLikes_Wall;
    }

    public TotalInformationVO setCountLikes_Wall(int countLikes_Wall) {
        this.countLikes_Wall = countLikes_Wall;
        return this;
    }

    public Integer getCountComments_Wall() {
        return countComments_Wall;
    }

    public TotalInformationVO setCountComments_Wall(int countComments_Wall) {
        this.countComments_Wall = countComments_Wall;
        return this;
    }

    public TotalInformationVO setPhotoId(String photoId){
        this.photoId = photoId;
        return this;
    }
}
