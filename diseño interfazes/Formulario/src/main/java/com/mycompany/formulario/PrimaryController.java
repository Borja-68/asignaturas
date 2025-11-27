/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.formulario;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author a24borjapp
 */
public class PrimaryController implements Initializable {
    @FXML private ComboBox comboBox;
    @FXML private CheckBox checkBox;
    @FXML private Label labelAviso;
    @FXML private ToggleGroup RadioButtonGroup;
    @FXML private Button btnReservas;
    @FXML private Button btnSalir;
 

    /**
     * Initializes the controller class.
     */
    
    public void handleCheckBox(){
        if (checkBox.isSelected()){
         //etiqueta será visible
         labelAviso.setVisible(true);
        }
        else{
        labelAviso.setVisible(false);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        labelAviso.setVisible(false);
        comboBox.getItems().addAll("Doble de uso individual","Doble","Junior Suite","Suite");
    }    
    
public void handlebtnReservas(ActionEvent event) throws IOException{
 Stage stage =(Stage) btnReservas.getScene().getWindow();
 Parent root = FXMLLoader.load(getClass().getResource("primary.fxml"));
if(event.getSource()==btnReservas){
 stage=(Stage) btnReservas.getScene().getWindow();
 root = FXMLLoader.load(getClass().getResource("secondary.fxml"));
}
 Scene scene = new Scene(root);
 stage.setScene(scene);
 stage.show();
}
public void handlebtnSalir(ActionEvent event) throws IOException{
 Platform.exit();
 System.exit(0);
}
}
