package Model;

import java.awt.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import com.sun.javafx.collections.MappingChange.Map;

import Controllers.AdminController;
import Controllers.ContactLocalGuideController;
import Controllers.PopUpLoginErrorController;
import application.Main;
import Model.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/*
 * System Class - Includes all operational methods. 
 * Singleton Usage of class instance throw whole app.
 */

// Hi Timi!!!!

public class SystemGuide4u implements java.io.Serializable{
	
	// Attributes 
	protected HashMap<String, LocalGuide> localGuidesList;
	protected HashMap<String, Traveller> travellersList;
	protected HashMap<Integer,Review> reviewsList;
	protected HashMap<Integer,Place> placeList;
	protected HashMap<Integer,TravelOption> travelOptionsList;
	protected ArrayList<Travel> travelLIST;
	protected static SystemGuide4u instance = new SystemGuide4u();


	// C'tor 
	
	private SystemGuide4u() {
		super();
		this.localGuidesList = new HashMap<String, LocalGuide>();
		this.travellersList = new HashMap<String, Traveller>();
		this.reviewsList = new HashMap<Integer, Review>();
		this.travelLIST=new ArrayList<Travel>();
		this.placeList = new HashMap<Integer, Place>();
		this.travelOptionsList = new HashMap<Integer, TravelOption>();
	}
	
	
	// Getters 
	
	public HashMap<Integer, Place> getPlaceList() {
		return placeList;
	}

	
	public HashMap<String, LocalGuide> getLocalGuidesList() {
		return localGuidesList;
	}

	public HashMap<String, Traveller> getTravellersList() {
		return travellersList;
	}

	public static SystemGuide4u getInstance() {
		return instance;
	}
	public HashMap<Integer, Review> getReviewsList() {
		return reviewsList;
	}
	
	public ArrayList<Travel> getTravelLIST() {
		return travelLIST;
	}
	
	// Setters

	public void setPlaceList(HashMap<Integer, Place> placeList) {
		this.placeList = placeList;
	}
	
	public void setLocalGuidesList(HashMap<String, LocalGuide> localGuidesList) {
		this.localGuidesList = localGuidesList;
	}
	
	
	public void setTravellersList(HashMap<String, Traveller> travellersList) {
		this.travellersList = travellersList;
	}
	

	public void setReviewsList(HashMap<Integer, Review> reviewsList) {
		this.reviewsList = reviewsList;
	}

	public void setTravelLIST(ArrayList<Travel> travelLIST) {
		this.travelLIST = travelLIST;
	}
	
	
    // Add Objects to system Lists methods 
	
	public void addGuide(LocalGuide guide) {
		this.localGuidesList.put(guide.getEmail(), guide);
	}
	public void addTraveller(Traveller traveller) {
		this.travellersList.put(traveller.getEmail(), traveller);
	}
	public void addTravel(Travel travel) {
		this.travelLIST.add(travel);
	}
	
	public void addReview(Review review) {
		System.out.println(review.getLocalGuideEmail()+ " Was Added!");
		this.reviewsList.put(review.getReviewID(), review);
	}
	
	public void addPlace(Place place) {
		this.placeList.put(place.getPlaceID(), place);
	}
	
	public void addLocalGuideUnavailbleDate(String localGuideEmail, LocalDate date) {
		
		for (Entry<String, LocalGuide> value : getLocalGuidesList().entrySet()) {
			
			LocalGuide guide = value.getValue();
			
			if(guide.getEmail().equals(localGuideEmail)) {
				if(!getLocalGuidesList().get(localGuideEmail).getUnavailableDates().contains(date)) {
					getLocalGuidesList().get(localGuideEmail).getUnavailableDates().add(date);
				}
			}
		}
		
		
	}
	
	// Remove Objects from system Lists methods
	
	public void removeLocalGuide(LocalGuide guide) {
		this.localGuidesList.remove(guide.getEmail());
	}
	public void removeTraveller(Traveller traveller) {
		this.travellersList.remove(traveller.getEmail());
	}
	
	
	// Check Regex methods 
	
	public boolean checkID(TextField id) {
		String regEx = "^[0-9]{9}$";
		if(id.getText().matches(regEx)) {
			return true;
		}
		return false;
	}
	public boolean checkPhone(String phone) {
		String regEx = "^[0-9]{10}$";
		if(phone.matches(regEx)) {
			return true;
		}
		return false;
	}
	public boolean checkPassword(TextField password) {
		String regEx = "^[a-z]{3}[0-9]{3}$";
		if(password.getText().matches(regEx)) {
			return true;
		}
		return false;
	}
	public boolean checkValidateEmail(String email){
		  return Pattern.matches("[_a-zA-Z1-9]+(\\.[A-Za-z0-9]*)*@[A-Za-z0-9]+\\.[A-Za-z0-9]+(\\.[A-Za-z0-9]*)*", email);
		}
	   public static boolean checkFirstName( String firstName ) {
	      return firstName.matches( "[A-Z][a-z]*" );
	   }
	   public static boolean checkLastName( String lastName ) {
	      return lastName.matches( "[A-Z]+([ '-][a-zA-Z]+)*" );
	   }
	   
		// Check Email & Password methods
		
		public boolean checkPasswordAndEmailGuide(String email, String password) {
			return password.equals(this.localGuidesList.get(email).password);
		}
		
		public boolean checkPasswordAndEmailTraveller(String email, String password) {
			return password.equals(this.travellersList.get(email).password);
		}
	   
	   
	   
	// Get Objects by email
	public LocalGuide getGuideByEmail(String mail) {
		LocalGuide localGuide = this.localGuidesList.get(mail);
		return localGuide;
	}
	
	
	public Traveller getTravellerByMail(String mail) {
		Traveller traveller=this.travellersList.get(mail);
		return traveller;
	}
	

	
	// Populate methods - For Example and testing
	
	public void populateLocalGuideExample() {
		LocalGuide lg1 = new LocalGuide("xxx@gmail.com", "asd123", "Shim", "Metz", LocalDate.of(1992,2,5), Gender.Male, "Haifa", "Israel", 503309824 , new Language("Hebrew"), new TravelStyle("Hiking"), "I Love Food", true);
		this.localGuidesList.put(lg1.getEmail(), lg1);
		System.out.println("Populated LocalGuides");
		
	}
	
	public void populateTravellerExample() {
		Traveller t1 = new Traveller("har@gmail.com", "asd123", "Haron", "Labay", LocalDate.of(1992,2,5), Gender.Male, "Haifa", "Israel", 503309824 , new Language("Hebrew"), new TravelStyle("Hiking"), "I Love Food", true);
		this.travellersList.put(t1.getEmail(), t1);
		System.out.println("Populated Travellers");
		
	}
	
	public void populateReviewsExample() {
		Traveller t1 = new Traveller("har@gmail.com", "asd123", "Haron", "Labay", LocalDate.of(1992,2,5), Gender.Male, "Haifa", "Israel", 503309824 , new Language("Hebrew"), new TravelStyle("Hiking"), "I Love Food", true);
		LocalGuide lg1 = new LocalGuide("xxx@gmail.com", "asd123", "Shim", "Metz", LocalDate.of(1992,2,5), Gender.Male, "Haifa", "Israel", 503309824 , new Language("Hebrew"), new TravelStyle("Hiking"), "I Love Food", true);
		Review r1 = new Review(lg1.getEmail(), LocalDate.now(), t1.getEmail(), "Haifa", "Israel", "Great Guide, A little bit too French and Red head", 7.8);
		this.reviewsList.put(r1.getReviewID(),r1);
		
	}
	
	// Print all users data - Unused (Only for testing)
	public void printAllData() {
		System.out.println("Local guides: ");
		for(String mail: this.localGuidesList.keySet()) {
			LocalGuide temp=this.localGuidesList.get(mail);
			//System.out.println(temp.toString());
			System.out.print(temp.firstName+", ");
			System.out.println(temp.email);
			}
		System.out.println("\nTravellers: ");
		for(String mail: this.travellersList.keySet()) {
			Traveller temp=this.travellersList.get(mail);
			//System.out.println(temp.toString());
			System.out.print(temp.firstName+", ");
			System.out.println(temp.email);
			}
		System.out.println("\n");
		}
	
	// Init ComboBox methods
	
    public <T> void initCountryComBox(ComboBox<String> comBox) {
    	
    	ObservableList<String> countries = FXCollections.observableArrayList();
    	String[] locales1 = Locale.getISOCountries();
         for (String countrylist : locales1) {
             Locale obj = new Locale("", countrylist);
             String[] city = { obj.getDisplayCountry() };
             for (int x = 0; x < city.length; x++) {
                 countries.add(obj.getDisplayCountry());
             }
         }
         comBox.setItems((ObservableList<String>) countries);
    }
    
    public <T> void initLanguageComBox(ComboBox <String> comBox) {
    	
        SortedSet<String> allLanguages = new TreeSet<String>();
        String[] languages = Locale.getISOLanguages();
        for (int i = 0; i < languages.length; i++){
            Locale loc = new Locale(languages[i]);
            allLanguages.add(loc.getDisplayLanguage());
        }
    	
    	ObservableList<String> languagesX = FXCollections.observableArrayList(allLanguages);
        comBox.setItems((ObservableList<String>) languagesX);
         
    }
    
    
    public  void initTravelStyleComBox(ComboBox <String> comBox){
    	
    	comBox.getItems().setAll("Art","Sport","Shoping","Adventure", "Entertaiment", "Local Culture", "Nature","Hiking");
    	
    }
    
    
    public void initTransportTypeComBox(ComboBox <String> comBox) {
    	
    	comBox.getItems().setAll("Private Vehicle","Public Transportation");
    
    }
    
    
    public void initGenderComBox(ComboBox <String> comBox) {
    	comBox.getItems().setAll("Male","Female");
    }
    

    // Sorting by different Att's - Unused in final version.
    
    public void sortGuideByRate() {

         HashMap<String, Double> localGuideRate=new HashMap<String, Double>();
         for(String mail:this.localGuidesList.keySet()) {
        	 localGuideRate.put(mail, this.localGuidesList.get(mail).getRating());
         }
         
         localGuideRate.entrySet()
    	.stream()
    	.sorted(HashMap.Entry.<String, Double>comparingByValue())
    	.forEach(System.out::println);

    }
    
    
    public void sortGuideByFirstName() {
    	 HashMap<String, String> localGuideNames=new HashMap<String, String>();
         for(String mail:this.localGuidesList.keySet()) {
        	 localGuideNames.put(mail, this.localGuidesList.get(mail).getFirstName());
         }
         
         localGuideNames.entrySet()
     	.stream()
     	.sorted(HashMap.Entry.<String, String>comparingByValue())
     	.forEach(System.out::println);
   }
    
    
    public void sortGuideByCountry() {
    	///new comperable hash  
    	 HashMap<String, String> localGuideCountry=new HashMap<String, String>();
         for(String mail:this.localGuidesList.keySet()) {
        	 localGuideCountry.put(mail, this.localGuidesList.get(mail).getCountry());
         }
         
         localGuideCountry.entrySet()
     	.stream()
     	.sorted(HashMap.Entry.<String, String>comparingByValue())
     	.forEach(System.out::println);
   }
    
    
    public void sortTravellerByFirstName() {
    	///new comperable hash  
   	 HashMap<String, String> TravellerNames=new HashMap<String, String>();
        for(String mail:this.travellersList.keySet()) {
        	TravellerNames.put(mail, this.travellersList.get(mail).getFirstName());
        }
        
        TravellerNames.entrySet()
    	.stream()
    	.sorted(HashMap.Entry.<String, String>comparingByValue())
    	.forEach(System.out::println);
  }
    
    
    public void sortTravellerByCountry() {
    	///new comperable hash  
      	 HashMap<String, String> TravellerCountry=new HashMap<String, String>();
           for(String mail:this.travellersList.keySet()) {
        	   TravellerCountry.put(mail, this.travellersList.get(mail).getCountry());
           }
           
          TravellerCountry.entrySet()
       	.stream()
       	.sorted(HashMap.Entry.<String, String>comparingByValue())
       	.forEach(System.out::println);
     }

    
    // Load pages methods
    
    public void reloadLoginPage() {
    	try {
    		Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(Main.class.getResource("/FXML/Login.fxml"));
			Scene scene = new Scene(root,1130,725);
			scene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Guide4U - Login");
			Image icon = new Image(getClass().getResourceAsStream("/img/g_logo.png"));
			primaryStage.getIcons().add(icon);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.show();
			primaryStage.setResizable(false);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
	public void popUpLoginError(String msg) {
        try {
        	Stage popUpLoginErr = new Stage();
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/FXML/popUpLoginError.fxml"));
            AnchorPane rootLayout = (AnchorPane) loader.load();
            Scene scene = new Scene(rootLayout);
            PopUpLoginErrorController p = loader.getController();
            p.setLblError(msg);
	        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        popUpLoginErr.setScene(scene);
	        popUpLoginErr.setTitle("Guide4U - Login Error");
			Image icon = new Image(getClass().getResourceAsStream("/img/g_logo.png"));
			popUpLoginErr.getIcons().add(icon);
	        popUpLoginErr.setResizable(false);
	        popUpLoginErr.show();
	        
	        
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
    public void loadAdminPage() {
    	try {
			Stage stage=new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin.fxml"));
			Parent root = loader.load();
			int screenWidth = (int) Screen.getPrimary().getVisualBounds().getWidth();
			int screenHeight = (int) Screen.getPrimary().getVisualBounds().getHeight();
			Scene scene = new Scene(root,screenWidth,screenHeight);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			AdminController adminController = loader.<AdminController>getController();
			adminController.initLocalGuideTable();
			stage.setScene(scene);
			stage.setTitle("Guide4U - Admin Dashboard");
			Image icon = new Image(getClass().getResourceAsStream("/img/g_logo.png"));
			stage.getIcons().add(icon);
			stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    
    public void loadContactLocalGuidePage(LocalGuide localGuide) {
    	
    	try {
			Stage stage=new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ContactLocalGuide.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
			ContactLocalGuideController contactController = loader.<ContactLocalGuideController>getController();
			contactController.setLocalGuide(localGuide);
			contactController.setData();
			stage.setScene(scene);
			stage.setTitle("Guide4U - Contact Local Guide");
			Image icon = new Image(getClass().getResourceAsStream("/img/g_logo.png"));
			stage.getIcons().add(icon);
			stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    	
    }
	
	// Write & Read to file methods - Unused in final version.
	
	public static void writeFile() {
		try {
			FileOutputStream fileOut = new FileOutputStream("guide4u.ser");//name of the folder we create
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(SystemGuide4u.getInstance());
			out.close();
			fileOut.close();
			System.out.println("Writen to file");
			
		
		} catch (IOException i) {
			i.printStackTrace();
		}
	}
	
	
	public static void readFile() {
		try {
			
			FileInputStream file = new FileInputStream("guide4u.ser");
			ObjectInputStream in = new ObjectInputStream(file);
			instance = (SystemGuide4u) in.readObject();
			in.close();
			file.close();
			System.out.println("file found");

		} catch (IOException e2) {
			e2.printStackTrace();
		} catch (ClassNotFoundException e3) {
			e3.printStackTrace();
		}
	}
	

    // Transform Traveller user to Local Guide method 
    
    public LocalGuide transferTravellerToGuide(Traveller t) {
    	LocalGuide localGuide= new LocalGuide(t.getEmail(), t.getPassword(), t.getFirstName(), t.getLastName(), t.getDateOfBirth(), t.getGender(), t.getCity(), t.getCountry(), t.getPhoneNumber(), t.getLanguage(), t.getTravelStyle(), t.getAboutMe(),t.emailNotifacations);
    	return localGuide;
    }
    

}
	
