package VkInit;

import com.vk.api.sdk.objects.users.UserXtrCounters;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class TotalInformationVO {
    private UserXtrCounters mainUser;
    private int countLikesFromWall;
    private int countCommentsFromWall;
    private HashMap<UserXtrCounters,Integer> usersAndCounLikesFromPost;

    public TotalInformationVO() {
    }


    public TotalInformationVO(UserXtrCounters user, int countLikesFromWall, int countCommentsFromWall, HashMap<UserXtrCounters,Integer> usersAndCounLikesFromPost) {
        this.mainUser = user;
        this.countLikesFromWall = countLikesFromWall;
        this.countCommentsFromWall = countCommentsFromWall;
        this.usersAndCounLikesFromPost = usersAndCounLikesFromPost;

    }


    public TotalInformationVO setMainUser(UserXtrCounters mainUser) {
        this.mainUser = mainUser;
        return this;
    }
    public UserXtrCounters getMainUser() {
        return mainUser;
    }


    public HashMap<UserXtrCounters, Integer> getUsersAndCounLikesFromPost() {
        return usersAndCounLikesFromPost;
    }

    public TotalInformationVO setUsersAndCounLikesFromPost(HashMap<UserXtrCounters, Integer> usersAndCounLikesFromPost) {
        this.usersAndCounLikesFromPost = usersAndCounLikesFromPost;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TotalInformationVO)) return false;
        TotalInformationVO that = (TotalInformationVO) o;
        return countLikesFromWall == that.countLikesFromWall &&
                countCommentsFromWall == that.countCommentsFromWall &&
                Objects.equals(mainUser, that.mainUser) &&
                Objects.equals(usersAndCounLikesFromPost, that.usersAndCounLikesFromPost);
    }

    @Override
    public int hashCode() {

        return Objects.hash(mainUser, countLikesFromWall, countCommentsFromWall, usersAndCounLikesFromPost);
    }
}
