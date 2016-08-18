package com.octanium.main.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.octanium.login.controller.LoginController;
import com.octanium.login.controller.LoginManager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainManager {
	  private Scene scene;
	  
	 public void showRegistro() {
		    try {
		      FXMLLoader loader = new FXMLLoader(
		    		  Thread.currentThread().getContextClassLoader().getResource("com/octanium/login/view/login.fxml")
		      );
		      scene.setRoot((Parent) loader.load());
//		      LoginController controller = 
//		        loader.<LoginController>getController();
//		      controller.initManager(this);
		      
		      Stage stage = new Stage();
		      stage.setTitle("Login");
		      stage.setScene(scene);
		      stage.show();
		  
		    } catch (IOException ex) {
		      Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
		    }
		  }
	 
}
