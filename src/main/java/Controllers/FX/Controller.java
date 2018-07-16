package Controllers.FX;

import VkInit.TotalInformationVO;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import VkInit.ClientVK;

import javafx.scene.control.*;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    public TextField textFld_userId;
    public Button btn_searchUser;
    public ImageView imgView_userPhoto;
    public Label lblCountLikes;
    public Label lblCountComments;
    public Label lblName;
    public TableView tableCommentsInfo;
    public TableColumn<Map,String> columnUsersName;
    public TableColumn<Map,Integer> columnUsersComments;

    ClientVK clientVK;
    public static final String ColumnUserName = "name";
    public static final String ColumnCountLikes = "likes";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnUsersName.setCellValueFactory(new MapValueFactory<>(ColumnUserName));
        columnUsersComments.setCellValueFactory(new MapValueFactory<>(ColumnCountLikes));
    }

    public void setClientId(Integer userIdActor,String accessToken) throws ClientException, ApiException {
        clientVK = new ClientVK(userIdActor,accessToken);
    }

    @FXML
    public void searchingUser() throws ClientException, ApiException {
        String bufUserId =  textFld_userId.getText();
        TotalInformationVO totalInformationVO = clientVK.getUserTotalInformation(bufUserId);
        StringBuilder stringBuilder = new StringBuilder().append(totalInformationVO.getMainUser().getFirstName())
                .append("\t").append(totalInformationVO.getMainUser().getLastName());
        lblName.setText(stringBuilder.toString());
        stringBuilder = null;
        imgView_userPhoto.setImage(new Image(totalInformationVO.getMainUser().getPhotoMaxOrig()));
        lblCountLikes.setText(totalInformationVO.getCountLikesFromWall().toString());
        lblCountComments.setText(totalInformationVO.getCountCommentsFromWall().toString());
        tableCommentsInfo.setItems(generateDataInMap(totalInformationVO.getUsersAndCounLikesFromPost()));
        //TODO вывести в список данные
    }

    private ObservableList<Map> generateDataInMap(HashMap<UserXtrCounters, Integer> usersAndCounLikesFromPost) {
        ObservableList<Map> allData = FXCollections.observableArrayList();

        usersAndCounLikesFromPost.forEach((user, countLikes) -> {
            Map<String, String> dataRow = new HashMap<>();

            dataRow.put(ColumnUserName, user.getFirstName() + " "+user.getLastName());
            dataRow.put(ColumnCountLikes,countLikes.toString());
            allData.add(dataRow);
        });
        return allData;
    }



}


