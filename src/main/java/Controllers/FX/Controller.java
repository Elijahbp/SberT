package Controllers.FX;

import VkInit.TotalInformationVO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import VkInit.ClientVK;

import javafx.scene.control.*;

public class Controller {

    public Label totalStat;
    public TextField textFld_userId;
    public Button btn_searchUser;
    public ImageView imgView_userPhoto;

    ClientVK clientVK;

    Controller(){}
    public void setTokenUri(String newValue){
        clientVK = new ClientVK(newValue);
    }

    @FXML
    public void searchingUser() {
        String bufUserId =  textFld_userId.getText();
        TotalInformationVO totalInformationVO = clientVK.getTotalInformation(bufUserId);
        imgView_userPhoto.setImage(new Image(totalInformationVO.getUser().getPhoto200Orig()));
        totalStat.setText(String.valueOf(totalInformationVO.getCountLikes_Wall()));
    }

}


