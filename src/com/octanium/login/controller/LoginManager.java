package com.octanium.login.controller;

import java.io.IOException;
import java.util.logging.*;

import com.octanium.commons.model.User;
import com.octanium.login.controller.LoginController;
import com.octanium.login.view.MainViewController;
import com.octanium.main.controller.MainDispatcherViewController;

import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/** Manages control flow for logins */
public class LoginManager {
  private Scene sceneLogin;
  private Scene sceneMain;
  

  public LoginManager(Scene sceneLogin) {
    this.sceneLogin = sceneLogin;
  }

  /**
   * Callback method invoked to notify that a user has been authenticated.
   * Will show the main application screen.
   */ 
  public void authenticated(User user) {
//    showMainView(sessionID);
	    
    showMainDispatcher(user);
  }

  /**
   * Callback method invoked to notify that a user has logged out of the main application.
   * Will show the login application screen.
   */ 
  public void logout() {
    showLoginScreen();
  }
  
  public void showLoginScreen() {
    try {
      FXMLLoader loader = new FXMLLoader(
        //getClass().getResource("com/octanium/login/view/login.fxml")
    		  Thread.currentThread().getContextClassLoader().getResource("com/octanium/login/view/login.fxml")
      );
      sceneLogin.setRoot((Parent) loader.load());
      LoginController controller = 
        loader.<LoginController>getController();
      controller.initManager(this);
      
      Stage stage = new Stage();
      stage.setTitle("Login");
      stage.setScene(sceneLogin);
      stage.show();
      
      if(sceneMain!=null)
      sceneMain.getWindow().hide();
    } catch (IOException ex) {
      Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private void showMainView(String sessionID) {
    try {
      FXMLLoader loader = new FXMLLoader(
        //getClass().getResource("mainview.fxml")
    		  Thread.currentThread().getContextClassLoader().getResource("com/octanium/login/view/mainview.fxml")
      );
      sceneLogin.setRoot((Parent) loader.load());
      MainViewController controller = 
        loader.<MainViewController>getController();
      controller.initSessionID(this, sessionID);
    } catch (IOException ex) {
      Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  private void showMainDispatcher(User user) {
	  try {
		  FXMLLoader loader = new FXMLLoader(
				  //getClass().getResource("mainview.fxml")
				  Thread.currentThread().getContextClassLoader().getResource("com/octanium/main/view/MainDispatcher.fxml")
				  );
		  sceneMain = new Scene(new StackPane());
		  sceneMain.setRoot((Parent) loader.load());
		  MainDispatcherViewController controller = 
				  loader.<MainDispatcherViewController>getController();
		  controller.initSessionID(this, user);
//	      Stage stage = (Stage) scene.getWindow();
//	      stage.setMaximized(true);
	      Stage stage = new Stage();
	      stage.setTitle("Principal");
	      stage.setScene(sceneMain);
	      stage.show();
	      
	      sceneLogin.getWindow().hide();
	      
	  } catch (IOException ex) {
		  Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
	  }
  }
}
