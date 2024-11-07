/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package loginapplication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


public class TasksController implements Initializable {

    @FXML
    private TextField txtNuevaTarea;

    @FXML
    private ListView<String> lvListaTareas;
    
    private ObservableList<String> observableTaskList;  // Lista observable que refleja automáticamente las tareas en el ListView
    private String usuarioLogueado;  // Variable que almacena el nombre del usuario actualmente logueado
    private final String directorioTareas = "src/loginapplication/ficheros/tareas/";  // Directorio donde se guardan los archivos de tareas de cada usuario
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Obtener el nombre del usuario que ha iniciado sesión
        usuarioLogueado = UsuarioLogueado.getInstance().getUsername();

        // Crear una lista observable de tareas para reflejar cambios automáticos en la UI
        observableTaskList = FXCollections.observableArrayList();

        // Cargar las tareas desde el archivo correspondiente y añadirlas a observableTaskList
        cargarTareas();

        // Asociar la lista observable con el ListView para que se muestre en la interfaz
        lvListaTareas.setItems(observableTaskList);    }    
    
        // Método para cargar las tareas existentes desde el archivo de texto del usuario logueado
    public void cargarTareas() {
        String archivoTareas = directorioTareas + "tareas_" + usuarioLogueado + ".txt"; // Ruta del archivo de tareas del usuario

        // Intentar leer el archivo de tareas del usuario
        try (BufferedReader reader = new BufferedReader(new FileReader(archivoTareas))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                observableTaskList.add(linea);  // Añadir cada tarea leída a la lista observable
            }
        } catch (FileNotFoundException e) {
            // Si el archivo no se encuentra, significa que el usuario no tiene tareas guardadas aún
            System.out.println("No se encontraron tareas previas para el usuario: " + usuarioLogueado);
        } catch (IOException e) {
            // Si ocurre un error de E/S, se imprime la traza para el diagnóstico
            e.printStackTrace();
        }
    }

    @FXML
    private void handleButtonAniadirTareaAction(ActionEvent event) {
        // Obtener el texto de la nueva tarea ingresada por el usuario en el campo txtNuevaTarea
        String nuevaTarea = txtNuevaTarea.getText();

        // Verificar que el campo de texto no esté vacío antes de agregar la tarea
        if (!nuevaTarea.isEmpty()) {
            // Añadir la nueva tarea a la lista observable (reflejo automático en la interfaz)
            observableTaskList.add(nuevaTarea);

            // Llamar al método guardarTareas() para actualizar el archivo con las nuevas tareas
            guardarTareas();

            // Limpiar el campo de texto para permitir al usuario ingresar una nueva tarea
            txtNuevaTarea.clear();
        }
    }
    
        // Método para guardar todas las tareas actuales del usuario en su archivo correspondiente
    public void guardarTareas() {
        String archivoTareas = directorioTareas + "tareas_" + usuarioLogueado + ".txt"; // Ruta del archivo de tareas del usuario

        // Intentar escribir todas las tareas en el archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoTareas))) {
            for (String tarea : observableTaskList) {
                writer.write(tarea); // Escribir cada tarea en una nueva línea del archivo
                writer.newLine();    // Añadir un salto de línea después de cada tarea
            }
        } catch (IOException e) {
            // Si ocurre un error de E/S, se imprime la traza para el diagnóstico
            e.printStackTrace();
        }
    }

    // Acción del botón Eliminar: elimina la tarea seleccionada de la lista y la actualiza en el archivo
    @FXML
    private void handleButtonBorrarTareaAction(ActionEvent event) {
        // Obtener la tarea seleccionada en el ListView
        String tareaSeleccionada = (String) lvListaTareas.getSelectionModel().getSelectedItem();

        // Verificar que se haya seleccionado una tarea para eliminar
        if (tareaSeleccionada != null) {
            // Eliminar la tarea seleccionada de la lista observable
            observableTaskList.remove(tareaSeleccionada);

            // Llamar al método guardarTareas() para actualizar el archivo tras la eliminación
            guardarTareas();
        }
    }

    // Acción del botón Completar: marca la tarea seleccionada como completada y la guarda en el archivo
    @FXML
    private void handleButtonMarcarCompletadaAction(ActionEvent event) {
        // Obtener la tarea seleccionada en el ListView
        String tareaSeleccionada = (String) lvListaTareas.getSelectionModel().getSelectedItem();

        // Verificar que haya una tarea seleccionada y que no esté ya marcada como "Completada"
        if (tareaSeleccionada != null && !tareaSeleccionada.contains("(Completada)")) {
            // Obtener el índice de la tarea seleccionada en la lista observable
            int index = observableTaskList.indexOf(tareaSeleccionada);

            // Marcar la tarea como "Completada" añadiendo "(Completada)" al final del texto
            observableTaskList.set(index, tareaSeleccionada + " (Completada)");

            // Llamar al método guardarTareas() para guardar el estado actualizado de las tareas en el archivo
            guardarTareas();
        }
    }


    
}
