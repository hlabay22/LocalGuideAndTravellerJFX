package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PopUpLoginErrorController {

    @FXML
    private Label lblError;

    @FXML
    private Button btnOK;

    @FXML
    void btnOKclick(ActionEvent event) {
    	btnOK.getScene().getWindow().hide();

    }
    
    public void setLblError(String msg) {
    	this.lblError.setText(msg);
    }

}