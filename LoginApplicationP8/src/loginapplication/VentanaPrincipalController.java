/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package loginapplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author JorgeSaldaña
 */
public class VentanaPrincipalController implements Initializable {

    @FXML
    private Button btnPerfil;
    @FXML
    private ImageView btnProfile;
    @FXML
    private Button btnTareas;
    @FXML
    private ImageView btnTasks;
    @FXML
    private Button btnArchivos;
    @FXML
    private ImageView btnFiles;
    @FXML
    private Button btnSalir;
    @FXML
    private ImageView btnExit;
    @FXML
    private AnchorPane mainContentArea;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Cargar la vista de perfil, al inicio de la carga de la VentanaPrincipa
        try {
            
            AnchorPane perfilView = FXMLLoader.load(getClass().getResource("Profile.fxml"));
            mainContentArea.getChildren().setAll(perfilView);  
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    

    @FXML
    private void handlePerfilAction(ActionEvent event) throws IOException {
        
        AnchorPane perfilView = FXMLLoader.load(getClass().getResource("Profile.fxml"));
        mainContentArea.getChildren().setAll(perfilView);  // Reemplaza el contenido actual del AnchorPane por la nueva vista
        
    }

    @FXML
    private void handleTareasAction(ActionEvent event) throws IOException {
        
        AnchorPane tasksView = FXMLLoader.load(getClass().getResource("Tasks.fxml"));
        mainContentArea.getChildren().setAll(tasksView);  // Reemplaza el contenido actual del AnchorPane por la nueva vista
        
    }

    @FXML
    private void handleArchivosAction(ActionEvent event) throws IOException {
        
        AnchorPane archivosView = FXMLLoader.load(getClass().getResource("Files.fxml"));
        mainContentArea.getChildren().setAll(archivosView);  // Reemplaza el contenido actual del AnchorPane por la nueva vista
    }

    @FXML
    private void handleSalirAction(ActionEvent event) {
        System.exit(0);
    }
    
}
