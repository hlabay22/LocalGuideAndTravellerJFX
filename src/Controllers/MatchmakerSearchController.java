package Controllers;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import Model.*;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/*
 * Controller for MatchMaker Search option - (Automated and Optimal match between Travellers and LocalGuides)
 */
public class MatchmakerSearchController implements Initializable{

    @FXML
    private ImageView imgBackground;

    @FXML
    private Pane paneLogin;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblFullName;


    @FXML
    private Label lblCity;

    @FXML
    private Label lblCountry;

    @FXML
    private ImageView imgProfileImage;

    @FXML
    private Label lblLogo;

    @FXML
    private Label lblLangTitle;

    @FXML
    private Label lblLang1;

    @FXML
    private Label lblLang2;

    @FXML
    private Label lblTravelStyleTitle;

    @FXML
    private Label lblTravelStyle1;

    @FXML
    private Label lblTravelStyle2;

    @FXML
    private Label lblLang3;

    @FXML
    private Label lblTravelStyle3;

    @FXML
    private Label lblAboutTitle;

    @FXML
    private Label lblAbout;

    @FXML
    private Label lblRating;

    @FXML
    private Label lblOfTen;

    @FXML
    private Label lblCitySearch;

    @FXML
    private Label lblCountrySearch;

    @FXML
    private Label lblDateSearch;

    @FXML
    private TextField txtCity;

    @FXML
    private DatePicker datePick;

    @FXML
    private Button btnSearch;

    @FXML
    private Label lblNote;

    @FXML
    private ComboBox<String> comBoxCountry;

    @FXML
    private Button btnExit;
    
    @FXML
    private Button btnCon;
    
    @FXML
    private Button btnPlacesAndTravels;
    @FXML
    private Separator horizSep;
    
    Traveller traveller;
    
    LocalGuide localGuide;
	
	SystemGuide4u system=Main.system;
	
	

    // Buttons ActionEvent methods    
         
    @FXML
    void btnPlacesAndTravelsClick(ActionEvent event) {
    	
    	loadPlacesAndTravelsViaTravellerView(this.localGuide);

    }

    @FXML
    void btnExitClick(ActionEvent event) {
    	
    	btnExit.getScene().getWindow().hide();

    }
    
    @FXML
    void btnConClick(ActionEvent event) {
    	system.loadContactLocalGuidePage(this.localGuide);

    }


    @FXML
    void btnSearchClick(ActionEvent event) {
    	
    	matchmakerSearchAlgorithem();

    }
    
    
    
    // MatchMaker Algorithm method - process of given data information on both parties and matching pairs by 6 Levels of "Match Strength"
    
    public void matchmakerSearchAlgorithem() {
    	
    	String city = this.txtCity.getText();
    	String country = this.comBoxCountry.getSelectionModel().getSelectedItem();
    	LocalDate localDate = this.datePick.getValue();
    	
    	
    	for (Entry<String, LocalGuide> value : this.system.getLocalGuidesList().entrySet()) {
    		  
			  LocalGuide tempLocalGuide = value.getValue(); 
			  boolean GuideIsAvliable=true;
			  String guideCity=tempLocalGuide.getCity();
			  String guideLanguage=tempLocalGuide.getLanguage().getLanguage1();
			  String guideTravelStyle=tempLocalGuide.getTravelStyle().getTravelStyle1();
			  String guideCountry=tempLocalGuide.getCountry();

			  String travellerCity=city;
			  String travellerLanguage=this.traveller.getLanguage().getLanguage1();
			  String travellerTravelStyle=this.traveller.getTravelStyle().getTravelStyle1();
			  String travellerCountry=country;

			  for(LocalDate unAvaliable: tempLocalGuide.getUnavailableDates()) {
				  if(localDate.equals(unAvaliable)) {
					  GuideIsAvliable=false;
					  break;
				  }
			  }
			  
			  // MUST HAVE CONDITIONS ARE : match on city, country and date availability.
			  
			  // Option 1 - Perfect Match based On language,travel style,and age difference. [Perfect]
			  
    	     if(guideCity.equalsIgnoreCase(travellerCity) &&
    	    	(guideLanguage.equalsIgnoreCase(travellerLanguage)) && 
    	    	(guideTravelStyle.equalsIgnoreCase(travellerTravelStyle))&&
    	    	(guideCountry.equalsIgnoreCase(travellerCountry))&&
    	    	(GuideIsAvliable)&&
    	    	(((tempLocalGuide.getDateOfBirth().getYear()) - (this.traveller.getDateOfBirth().getYear())) <= 15)) {
    	    	  this.localGuide=tempLocalGuide;
    	    	  this.lblNote.setText("** Perfect Match!!");
    	    	  setMatchedLocalGuideDetails();
    	    	  showHideResult(true);
    	    	  break;
    	     }
    	     
			  // Option 1.1 - Perfect Match based On language,travel style,and age difference. WITH NO DATE AVAILIBILTY  [Almost Perfect]
			  
    	     if(guideCity.equalsIgnoreCase(travellerCity) &&
    	    	(guideLanguage.equalsIgnoreCase(travellerLanguage)) && 
    	    	(guideTravelStyle.equalsIgnoreCase(travellerTravelStyle))&&
    	    	(guideCountry.equalsIgnoreCase(travellerCountry))&&
    	    	(!GuideIsAvliable)&&
    	    	(((tempLocalGuide.getDateOfBirth().getYear()) - (this.traveller.getDateOfBirth().getYear())) <= 15)) {
    	    	  this.localGuide=tempLocalGuide;
    	    	  this.lblNote.setText("** Almost Perfect Match!! - Guide Not Availible in this Date! Contact for info about other Dates");
    	    	  setMatchedLocalGuideDetails();
    	    	  showHideResult(true);
    	    	  break;
    	     }
    	     
    	     // Option 2 - Great Match based On language, travel style. [Great]
    	     
    	     else if(guideCity.equalsIgnoreCase(travellerCity) &&
    	    	(guideCountry.equalsIgnoreCase(travellerCountry)) && 
    	    	(GuideIsAvliable) && 
    	    	(guideLanguage.equalsIgnoreCase(travellerLanguage))&&
    	    	(guideTravelStyle.equalsIgnoreCase(travellerTravelStyle))) {
	   	    	  this.localGuide=tempLocalGuide;
	   	    	  this.lblNote.setText("** Great Match!!");
	   	    	  setMatchedLocalGuideDetails();
	   	    	  showHideResult(true);
	   	    	  break;
	    	    	 
    	     }
    	     
    	     // Option 2.1 - Great Match based On language, travel style. WITH NO DATE AVAILIBILTY  [Almost Great]
    	     
    	     else if(guideCity.equalsIgnoreCase(travellerCity) &&
    	    	(guideCountry.equalsIgnoreCase(travellerCountry)) && 
    	    	(!GuideIsAvliable) && 
    	    	(guideLanguage.equalsIgnoreCase(travellerLanguage))&&
    	    	(guideTravelStyle.equalsIgnoreCase(travellerTravelStyle))) {
	   	    	  this.localGuide=tempLocalGuide;
	   	    	  this.lblNote.setText("** Almost Great Match!! - Guide Not Availible in this Date! Contact for info about other Dates");
	   	    	  setMatchedLocalGuideDetails();
	   	    	  showHideResult(true);
	   	    	  break;
	    	    	 
    	     }
    	     
    	     // Option 3 - Good Match based on language. [Very Good] 
    	     
    	     else if(guideCity.equalsIgnoreCase(travellerCity) &&
    	    	(guideCountry.equalsIgnoreCase(travellerCountry)) && 
    	    	(GuideIsAvliable) && 
    	    	(guideLanguage.equalsIgnoreCase(travellerLanguage))) {
	   	    	  this.localGuide=tempLocalGuide;
	   	    	  this.lblNote.setText("** Very Good Match!!");
	   	    	  setMatchedLocalGuideDetails();
	   	    	  showHideResult(true);
	   	    	  break;
    	    	 
    	     }
    	     
    	     // Option 3.1 - Good Match based on language. WITH NO DATE AVAILIBILTY [Almost Very Good] 
    	     
    	     else if(guideCity.equalsIgnoreCase(travellerCity) &&
    	    	(guideCountry.equalsIgnoreCase(travellerCountry)) && 
    	    	(!GuideIsAvliable) && 
    	    	(guideLanguage.equalsIgnoreCase(travellerLanguage))) {
	   	    	  this.localGuide=tempLocalGuide;
	   	    	  this.lblNote.setText("** Almost Very Good Match!! - Guide Not Availible in this Date! Contact for info about other Dates");
	   	    	  setMatchedLocalGuideDetails();
	   	    	  showHideResult(true);
	   	    	  break;
    	    	 
    	     }
    	     
    	     // Option 4 - Good Match base on travelStyle [Good]
    	     
    	     else if(guideCity.equalsIgnoreCase(travellerCity) &&
    	    	(guideCountry.equalsIgnoreCase(travellerCountry)) && 
    	    	(GuideIsAvliable) && 
    	    	(guideTravelStyle.equalsIgnoreCase(travellerTravelStyle))) {
    	    	 
	   	    	  this.localGuide=tempLocalGuide;
	   	    	  this.lblNote.setText("** Good Match!!");
	   	    	  setMatchedLocalGuideDetails();
	   	    	  showHideResult(true);
	   	    	  break;
    	    	 
    	     }
    	     
    	     // Option 4.1 - Good Match base on travelStyle [Almost Good]
    	     
    	     else if(guideCity.equalsIgnoreCase(travellerCity) &&
    	    	(guideCountry.equalsIgnoreCase(travellerCountry)) && 
    	    	(!GuideIsAvliable) && 
    	    	(guideTravelStyle.equalsIgnoreCase(travellerTravelStyle))) {
    	    	 
	   	    	  this.localGuide=tempLocalGuide;
	   	    	  this.lblNote.setText("** Almost Good Match!! - Guide Not Availible in this Date! Contact for info about other Dates");
	   	    	  setMatchedLocalGuideDetails();
	   	    	  showHideResult(true);
	   	    	  break;
    	    	 
    	     }
    	     
    	     // Option 5 - Plain Match base on MUST HAVE CONDITIONS only

    	     else if(guideCity.equalsIgnoreCase(travellerCity) &&
    	    	(guideCountry.equalsIgnoreCase(travellerCountry)) && 
    	    	(GuideIsAvliable)) {
	   	    	  this.localGuide=tempLocalGuide;
	   	    	  this.lblNote.setText("** Plain Match!!");
	   	    	  setMatchedLocalGuideDetails();
	   	    	  showHideResult(true);
	   	    	  break;

    	     }
    	     
    	     // Option 5.1 - Plain Match base on MUST HAVE CONDITIONS without DATE AVAILIBILTY
    	     
    	     else if(guideCity.equalsIgnoreCase(travellerCity) &&
    	    	(guideCountry.equalsIgnoreCase(travellerCountry)) && 
    	    	(!GuideIsAvliable)) {
	   	    	  this.localGuide=tempLocalGuide;
	   	    	  this.lblNote.setText("** Almost Plain Match!! - Guide Not Availible in this Date! Contact for info about other Dates");
	   	    	  setMatchedLocalGuideDetails();
	   	    	  showHideResult(true);
	   	    	  break;

    	     }

    	     // Option 6 - NO MATCH 
    	     
    	     else {
    	    	 lblNote.setText("** Sorry, No Match With Any Local Guide With These Details Was Found!");
    	    	 lblNote.setVisible(true);
    	     }
    	     
    	}
    }

    // Getters & Setters 
    
    public Traveller getTraveller() {
		return traveller;
	}

	public void setTraveller(Traveller traveller) {
		this.traveller = traveller;
	}

	public LocalGuide getLocalGuide() {
		return localGuide;
	}

	public void setLocalGuide(LocalGuide localGuide) {
		this.localGuide = localGuide;
	}
	
	public void showHideResult(boolean value) {
		
		this.lblNote.setVisible(value);
		this.horizSep.setVisible(value);
		this.lblTravelStyleTitle.setVisible(value);
		this.lblLangTitle.setVisible(value);
		this.lblAboutTitle.setVisible(value);
		this.lblFullName.setVisible(value);
		this.imgProfileImage.setVisible(value);
		this.lblCity.setVisible(value);
		this.lblCountry.setVisible(value);
		this.lblLang1.setVisible(value);
		this.lblLang2.setVisible(value);
		this.lblLang3.setVisible(value);
		this.lblTravelStyle1.setVisible(value);
		this.lblTravelStyle2.setVisible(value);
		this.lblTravelStyle3.setVisible(value);
		this.lblAbout.setVisible(value);
		this.lblRating.setVisible(value);
		this.lblOfTen.setVisible(value);
		this.btnCon.setVisible(value);
		this.btnPlacesAndTravels.setVisible(value);
	}
	
	public void setMatchedLocalGuideDetails() {
		this.lblFullName.setText(this.localGuide.getFirstName()+" "+this.localGuide.getLastName());
		this.lblCity.setText(this.localGuide.getCity());
		this.lblCountry.setText(this.localGuide.getCountry());
		setLangData();
		setTravelStyleData();
		this.lblRating.setText(this.localGuide.getRatingAsString());
		this.lblAbout.setText(this.localGuide.getAboutMe());
		//Decimal 2 
		this.lblRating.setText(new DecimalFormat("##.##").format(this.localGuide.getRating()));
	}

	public void setLangData() {
		if ((this.localGuide.getLanguage().getLanguage2()==null) && (this.localGuide.getLanguage().getLanguage3()==null)) {
			this.lblLang1.setText(this.localGuide.getLanguage().getLanguage1());
			this.lblLang2.setText("");
			this.lblLang3.setText("");
		}
	
	else if (this.localGuide.getLanguage().getLanguage3()==null) {
			this.lblLang1.setText(this.localGuide.getLanguage().getLanguage1());
			this.lblLang2.setText(this.localGuide.getLanguage().getLanguage2());
			this.lblLang3.setText("");
		
		}
	else {
			this.lblLang1.setText(this.localGuide.getLanguage().getLanguage1());
			this.lblLang2.setText(this.localGuide.getLanguage().getLanguage2());
			this.lblLang3.setText(this.localGuide.getLanguage().getLanguage3());
		}
	}
	
	public void setTravelStyleData() {
		if ((this.localGuide.getTravelStyle().getTravelStyle2()==null) && (this.localGuide.getTravelStyle().getTravelStyle3()==null)) {
			this.lblTravelStyle1.setText(this.localGuide.getTravelStyle().getTravelStyle1());
			this.lblTravelStyle2.setText("");
			this.lblTravelStyle3.setText("");
		}
	
	else if (this.localGuide.getTravelStyle().getTravelStyle3()==null) {
			this.lblTravelStyle1.setText(this.localGuide.getTravelStyle().getTravelStyle1());
			this.lblTravelStyle2.setText(this.localGuide.getTravelStyle().getTravelStyle2());
			this.lblTravelStyle3.setText("");
		
		}
	else {
			this.lblTravelStyle1.setText(this.localGuide.getTravelStyle().getTravelStyle1());
			this.lblTravelStyle2.setText(this.localGuide.getTravelStyle().getTravelStyle2());
			this.lblTravelStyle3.setText(this.localGuide.getTravelStyle().getTravelStyle3());
		}
	}
	
	// Load page methods
    public void loadPlacesAndTravelsViaTravellerView(LocalGuide localGuide2) {
		try {

			Stage stage=new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/LocalGuidePlacesAndTravels.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			LocalGuidePlacesAndTravelsController placesAndTravelsController = loader.<LocalGuidePlacesAndTravelsController>getController();
			placesAndTravelsController.setLocalGuide(localGuide);
			placesAndTravelsController.initTablesData();
			placesAndTravelsController.setProfileData();
			placesAndTravelsController.hideButtons();
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.setTitle("Guide4U - Local Guide Places & Travel Options");
			stage.initStyle(StageStyle.UNDECORATED);
			stage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
    
    // Initialize data method  
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		this.system = Main.system;
		system.initCountryComBox(this.comBoxCountry);
		
		// Disable Picking Past Dates On The availability datePicker.
		this.datePick.setDayCellFactory(picker -> new DateCell() {
	        public void updateItem(LocalDate date, boolean empty) {
	            super.updateItem(date, empty);
	            LocalDate today = LocalDate.now();
	            setDisable(empty || date.compareTo(today) < 0 );
	        }
	    });
	}

}
