package Main;

import VkInit.TotalInformationVO;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import VkInit.ClientVK;

import javafx.scene.control.*;

public class Controller {

    public TextField textFld_userId;
    public Button btn_searchUser;
    public ImageView imgView_userPhoto;
    public Label lblCountLikes;
    public Label lblCountComments;
    public Label lblName;
    public ListView listUsersFromComments;

    ClientVK clientVK;

    public void setClientId(Integer userIdActor,String accessToken) throws ClientException, ApiException {
        clientVK = new ClientVK(userIdActor,accessToken);
    }

    @FXML
    public void searchingUser() throws ClientException, ApiException {
        String bufUserId =  textFld_userId.getText();
        TotalInformationVO totalInformationVO = clientVK.getClient(bufUserId);
        StringBuffer stringBuffer = new StringBuffer().append(totalInformationVO.getMainUser().getFirstName())
                .append("\t").append(totalInformationVO.getMainUser().getLastName());
        lblName.setText(stringBuffer.toString());
        imgView_userPhoto.setImage(new Image(totalInformationVO.getMainUser().getPhotoMaxOrig()));
        lblCountLikes.setText(totalInformationVO.getCountLikesFromWall().toString());
        lblCountComments.setText(totalInformationVO.getCountCommentsFromWall().toString());
    }

}


