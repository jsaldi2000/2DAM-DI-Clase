/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package loginapplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author JorgeSaldaña
 */
public class LoginViewController implements Initializable {

    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtUsername;
    @FXML
    private Button btnEntrar;
    @FXML
    private Button btnRegistrarse;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleButtonEntrarAction(ActionEvent event) throws IOException {
        
        // Obtenemos los datos del login que introduce el usuario
        String usuario = txtUsername.getText();
        String contrasena = txtPassword.getText();

        // Ruta del archivo de usuarios y contraseñas
        String rutaArchivo = "src/loginapplication/ficheros/usuarios.txt";  // Asegúrate de que este archivo exista en tu proyecto

        if (validarUsuario(usuario, contrasena, rutaArchivo)) {
            // Si la validación es correcta, cargar la ventana principal
            // Cargar el archivo FXML
            Parent root = FXMLLoader.load(getClass().getResource("VentanaPrincipal.fxml"));
            
            // Crear la nueva escena
            Scene scene = new Scene(root);
            
            // Obtener la ventana actual donde se encuentra el botón btnEntrar (puede ser cualquier otro elemento de la ventana), la idea es conseguir identificar la ventana y crear la Stage
            Stage stage = (Stage) btnEntrar.getScene().getWindow();
     
            // Establecer la nueva escena en la ventana
            stage.setScene(scene);
            
            // Mostrar la ventana con la nueva escena
            stage.show();
            
            // Cargar icono y asignarlo
            Image icon = new Image(getClass().getResourceAsStream("/loginapplication/images/icon.png"));
            stage.getIcons().add(icon);  // Asignar el icono a la ventana
            
            // Establecer el título de la ventana
            stage.setTitle("Aplicación de gestión empresarial");
            
            // Evita que la ventana sea redimensionable
            stage.setResizable(false);
            
        } else {
            // Mostrar alerta de error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de inicio de sesión");
            alert.setHeaderText("Credenciales incorrectas");
            alert.setContentText("Por favor, verifica tu usuario y contraseña.");
            alert.showAndWait();
        }
    }

    private boolean validarUsuario(String usuario, String contrasena, String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] credenciales = linea.split(",");
                if (credenciales[0].equals(usuario) && credenciales[1].equals(contrasena)) {
                    return true; // Credenciales válidas
                }
            }
        } catch (IOException e) {
        }
        return false; // Credenciales incorrectas
    }
       
    
    @FXML
    private void handleButtonRegistarAction(ActionEvent event) {
    }
    
    }


