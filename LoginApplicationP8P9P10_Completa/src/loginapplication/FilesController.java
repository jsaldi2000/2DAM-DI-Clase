package loginapplication;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class FilesController implements Initializable {

    @FXML
    private ListView<String> lvListaArchivos; // ListView para mostrar los nombres de archivos almacenados
    private ObservableList<String> observableFilesList; // Lista observable para reflejar los archivos en la ListView de forma automática
    private final String directorioArchivos = "src/loginapplication/archivos_subidos"; // Carpeta donde se guardan los archivos subidos por el usuario

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar observableFilesList y vincularlo a lvListaArchivos para que la UI se actualice automáticamente
        observableFilesList = FXCollections.observableArrayList();
        lvListaArchivos.setItems(observableFilesList);

        // Cargar los archivos existentes en el directorio en la lista al iniciar la aplicación
        cargarArchivosExistentes();
    }

    // Método para cargar los archivos existentes en la carpeta y mostrarlos en la lista
    private void cargarArchivosExistentes() {
        File directorio = new File(directorioArchivos);
        File[] archivos = directorio.listFiles(); // Obtener la lista de archivos en el directorio

        // Si el directorio tiene archivos, los añade a observableFilesList para que se muestren en la ListView
        if (archivos != null) {
            for (File archivo : archivos) {
                observableFilesList.add(archivo.getName()); // Agregar cada archivo al ObservableList
            }
        }
    }

    // Método para subir un archivo seleccionado por el usuario desde su sistema de archivos
    @FXML
    private void handleSubirArchivo(ActionEvent event) {
        FileChooser fileChooser = new FileChooser(); // Crear un FileChooser para seleccionar archivos
        File archivoSeleccionado = fileChooser.showOpenDialog(null); // Abrir diálogo de selección de archivos

        if (archivoSeleccionado != null) { // Si el usuario selecciona un archivo
            File destino = new File(directorioArchivos, archivoSeleccionado.getName()); // Crear el archivo de destino en el directorio de subida
            try {
                // Copiar el archivo al directorio de subida si no existe
                if (!destino.exists()) {
                    Files.copy(archivoSeleccionado.toPath(), destino.toPath());
                    observableFilesList.add(archivoSeleccionado.getName()); // Añadir el nombre del archivo a la lista observable
                }
            } catch (IOException e) {
                // Mostrar un mensaje de error si ocurre un problema al copiar el archivo
                mostrarAlerta("Error", "No se pudo subir el archivo", AlertType.ERROR);
            }
        }
    }

    // Método para descargar un archivo seleccionado por el usuario en lvListaArchivos
    @FXML
    private void handleDescargarArchivo(ActionEvent event) {
        String archivoSeleccionado = lvListaArchivos.getSelectionModel().getSelectedItem(); // Obtener archivo seleccionado en la lista
        if (archivoSeleccionado != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialFileName(archivoSeleccionado); // Configurar el nombre de archivo inicial en el diálogo de guardado
            File destino = fileChooser.showSaveDialog(null); // Mostrar diálogo de guardado para seleccionar la ubicación

            if (destino != null) {
                File origen = new File(directorioArchivos, archivoSeleccionado); // Crear un archivo origen en el directorio de subida
                try {
                    Files.copy(origen.toPath(), destino.toPath()); // Copiar el archivo al destino seleccionado por el usuario
                } catch (IOException e) {
                    // Mostrar un mensaje de error si ocurre un problema al descargar el archivo
                    mostrarAlerta("Error", "No se pudo descargar el archivo", AlertType.ERROR);
                }
            }
        }
    }

    // Método para eliminar un archivo seleccionado de lvListaArchivos y del sistema de archivos
    @FXML
    private void handleEliminarArchivo(ActionEvent event) {
        String archivoSeleccionado = lvListaArchivos.getSelectionModel().getSelectedItem(); // Obtener archivo seleccionado en la lista
        if (archivoSeleccionado != null) {
            File archivo = new File(directorioArchivos, archivoSeleccionado); // Crear archivo en el sistema de archivos
            if (archivo.delete()) { // Intentar eliminar el archivo
                observableFilesList.remove(archivoSeleccionado); // Si la eliminación es exitosa, quitar el archivo de la lista observable
            } else {
                // Mostrar un mensaje de error si no se puede eliminar el archivo
                mostrarAlerta("Error", "No se pudo eliminar el archivo", AlertType.ERROR);
            }
        }
    }

    // Método auxiliar para mostrar alertas de información o error en la interfaz
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo); // Crear una alerta con el tipo especificado
        alert.setTitle(titulo); // Título de la alerta
        alert.setHeaderText(null); // Sin encabezado para simplificar
        alert.setContentText(mensaje); // Mensaje que se muestra en la alerta
        alert.showAndWait(); // Mostrar la alerta y esperar hasta que el usuario la cierre
    }

}
