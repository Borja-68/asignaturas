/**package com.mycompany.formulario;

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.mycompany.formulario.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SecondaryController {
@FXML private Label nombre;
@FXML private Label prov;
@FXML private Label direc;
@FXML private Label dni;
@FXML private Label loc;
@FXML private Label fech1;
@FXML private Label fech2;
@FXML private Label comb;
@FXML private Label pers;
@FXML private Label rbut;
@FXML private Label check;


    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         nombre.setText(PrimaryController.getNom());
         prov.setText(PrimaryController.getProw());
          direc.setText(PrimaryController.getDir());
           dni.setText(PrimaryController.getDn());
            loc.setText(PrimaryController.getLoc());
             fech1.setText(PrimaryController.getFecha1());
              fech2.setText(PrimaryController.getFecha2());
               comb.setText(PrimaryController.getComb());
                pers.setText(PrimaryController.getPer());
                 rbut.setText(PrimaryController.getGrupoEscape());
                  check.setText(PrimaryController.getCheck());
        
        
    } 

 
       
               
    }
     **/