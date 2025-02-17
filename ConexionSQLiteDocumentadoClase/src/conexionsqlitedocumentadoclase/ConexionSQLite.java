/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package conexionsqlitedocumentadoclase;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicación.
 * Inicia la interfaz gráfica utilizando JavaFX y carga la ventana principal.
 * 
 * @author JorgeSaldaña
 * @version 1.0
 */
public class ConexionSQLite extends Application {
    
    /**
     * Método de inicio de la aplicación
     * Carga la interfaz definida en el Main.fxml y la muestra en una ventana
     * 
     * @param stage El escenario principal donde se renderiza la UI
     * @throws Exception Si ocurre un error al cargar el archivo FXML
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Lanza la aplicación JavaFX
     * 
     * @param args Argumentos posibles en la línea de comandos
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
