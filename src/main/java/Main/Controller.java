package Main;

import VkInit.TotalInformationVO;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import javafx.event.ActionEvent;
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

    ClientVK clientVK;

    public void setClientId(Integer userIdActor,String accessToken) throws ClientException, ApiException {
        clientVK = new ClientVK(userIdActor,accessToken);
    }

    @FXML
    public void searchingUser() {
        String bufUserId =  textFld_userId.getText();
        TotalInformationVO totalInformationVO = clientVK.getClient(bufUserId);
        String s =  totalInformationVO.getUser().getPhoto200Orig();
        imgView_userPhoto.setImage(new Image(s));
        lblCountLikes.setText(totalInformationVO.getCountLikes_Wall().toString());
        lblCountComments.setText(totalInformationVO.getCountLikes_Wall().toString());
    }

}


