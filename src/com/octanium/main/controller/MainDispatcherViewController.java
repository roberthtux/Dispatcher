package com.octanium.main.controller;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.octanium.commons.model.User;
import com.octanium.commons.utils.Constants;
import com.octanium.login.controller.LoginManager;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MainDispatcherViewController {
    @FXML
    private Button btnMenuBar;
    @FXML
    private Button btnRegistro;
    @FXML
    private Button btnHorarios;
    @FXML
    private Button btnApertura;
    @FXML
    private Button btnDepositos;
    @FXML
    private Button btnFacturacion;
    @FXML
    private Button btnTickets;
    @FXML
    private Button btnCierre;
    @FXML
    private Button btnCambioNip;
    @FXML
    private VBox dataPane;
    @FXML
    private Button logoutButton;
    @FXML
    private Label sessionLabel;

    private User user;
    
    public void setDataPane(Node node) {
        // update VBox with new form(FXML) depends on which button is clicked
        dataPane.getChildren().setAll(node);
    }

    public VBox fadeAnimate(String url) throws IOException {
        VBox v = (VBox) FXMLLoader.load(getClass().getResource(url));
      /*  FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(v);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();*/
        return v;
    }

    public void loadPane(ActionEvent event) throws IOException {
//    	FXMLLoader loader = new FXMLLoader(
//	    		  Thread.currentThread().getContextClassLoader().getResource("com/octanium/main/view/Registro.fxml")
//	      );
    	
    	setDataPane(fadeAnimate("/view/FXML1.fxml"));
    }

    public void loadRegistro(ActionEvent event) throws IOException {
    	setDataPane(fadeAnimate("/com/octanium/main/view/Registro.fxml"));
    }

    public void loadHorarios(ActionEvent event) throws IOException {
        setDataPane(fadeAnimate("/com/octanium/main/view/Horarios.fxml"));
    }
    public void loadApertura(ActionEvent event) throws IOException {
    	setDataPane(fadeAnimate("/com/octanium/main/view/Apertura.fxml"));
    }
    
    public void initialize() {
    }
	  
	  public void initSessionID(final LoginManager loginManager, User user) {
		  this.user=user;
	    sessionLabel.setText(user.getName() +" "+user.getFirstLastname() +" "+ user.getSecondLastname());
	    
	    ClientConfig config = new ClientConfig();
    	Client client = ClientBuilder.newClient(config);
    	URI uri = UriBuilder.fromUri(Constants.URI).build();
    	WebTarget target = client.target(uri);
    	
    	String plainAnswer = 
    			target.path("profile")
    			.path(this.user.getId().toString())
    			.request().accept(MediaType.APPLICATION_JSON).get(String.class);

    	Gson gson = new Gson();
    	List<Map<String, String>> mapProfile = gson.fromJson(plainAnswer, new TypeToken<List<Map<String, Object>>>() {}.getType());
	    
	    logoutButton.setOnAction(new EventHandler<ActionEvent>() {
	      @Override public void handle(ActionEvent event) {
	        loginManager.logout();
	      }
	    });
	  }
}
