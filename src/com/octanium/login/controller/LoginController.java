package com.octanium.login.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import com.google.gson.Gson;
import com.octanium.commons.model.User;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/** Controls the login screen */
public class LoginController {
  @FXML private TextField user;
  @FXML private TextField password;
  @FXML private Button loginButton;
  
  private static URI getBaseURI() {
	  return UriBuilder.fromUri("http://sanfermin-core.dev/").build();
  }   

  public void initialize() {}
  
  public void initManager(final LoginManager loginManager) {
    loginButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        //String sessionID = authorize();
    	  User usr = authorize();
        if (usr != null) {
          loginManager.authenticated(usr);
        }
      }
    });
  }

  /**
   * Check authorization credentials.
   * 
   * If accepted, return a sessionID for the authorized session
   * otherwise, return null.
   */   
  @SuppressWarnings("unchecked")
  private User authorize() {
	  ClientConfig config = new ClientConfig();

	    Client client = ClientBuilder.newClient(config);

	    WebTarget target = client.target(getBaseURI());

	    String response = target.path("user").//queryParam("name", "rcc").
	              path(user.getText()).
	              request().
	              accept(MediaType.APPLICATION_JSON).
	              get(Response.class)
	              .toString();

	    String plainAnswer = 
	        target.path("user")//.queryParam("name", "roberto")
	        .path(user.getText())
	        .request().accept(MediaType.APPLICATION_JSON).get(String.class);
	    
	  //convert the json string back to object
	    Gson gson = new Gson();
        //Map<String, String> mapUser = gson.fromJson(plainAnswer, HashMap.class);
	    User usr = new User();
	    usr=gson.fromJson(plainAnswer, User.class);
        
    return 
      //mapUser.get("login").equals(user.getText()) //&& "sesame".equals(password.getText()) 
    		usr.getLogin().equals(user.getText())
            ? generateSessionID(usr) 
            : null;
  }
  
  private static int sessionID = 0;

  private User generateSessionID(User usr) {
    sessionID++;
    //return "xyzzy - session " + sessionID;
    return usr;
  }
}
