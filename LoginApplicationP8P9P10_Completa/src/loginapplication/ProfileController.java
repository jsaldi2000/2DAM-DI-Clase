package loginapplication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class ProfileController implements Initializable {

    @FXML
    private ImageView imgAvatar; // Componente para mostrar la imagen de perfil del usuario
    @FXML
    private PasswordField txtPasswordNuevo; // Campo de contraseña para cambiar la contraseña actual
    @FXML
    private TextField txtEmail; // Campo de texto para mostrar/modificar el email
    @FXML
    private TextField txtNombre; // Campo de texto para mostrar/modificar el nombre
    @FXML
    private TextField txtApellidos; // Campo de texto para mostrar/modificar los apellidos
    @FXML
    private TextField txtDireccion; // Campo de texto para mostrar/modificar la dirección

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Cargar datos del usuario desde el archivo datos_usuario.txt
        cargarDatosUsuario();
    }

    // Método para cargar los datos del usuario desde el archivo de texto
    private void cargarDatosUsuario() {
        String usuarioLogueado = UsuarioLogueado.getInstance().getUsername();  // Obtener el nombre del usuario logueado
        String rutaArchivo = "src/loginapplication/ficheros/datos_usuario.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(","); // Dividir cada registro por comas
                if (datos[0].equals(usuarioLogueado)) { // Si el usuario coincide con el logueado, cargar sus datos
                    txtNombre.setText(datos[1]); // Nombre del usuario
                    txtApellidos.setText(datos[2]); // Apellidos del usuario
                    txtEmail.setText(datos[3]); // Email del usuario
                    txtDireccion.setText(datos[4]); // Dirección

                    // Cargar la imagen de perfil
                    String rutaBase = "/loginapplication/images/profilepics/";
                    String rutaCompleta = rutaBase + datos[5];
                    URL url = getClass().getResource(rutaCompleta);
                    Image avatar = new Image(url.toExternalForm());
                    imgAvatar.setImage(avatar); // Asignar la imagen al ImageView
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Imprimir error en caso de fallo de lectura
        }
    }

    // Acción del botón Cambiar Imagen: permite al usuario seleccionar una nueva imagen de perfil
    @FXML
    private void handleButtonCambiarImagenAction(MouseEvent event) {
        // Crear un cuadro de diálogo para seleccionar la imagen
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen de Perfil");

        // Configurar filtro para mostrar solo imágenes PNG
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.png")
        );

        // Abrir el diálogo de selección de archivos
        File archivoSeleccionado = fileChooser.showOpenDialog(null);

        if (archivoSeleccionado != null) { // Si el usuario selecciona un archivo
            try {
                // Obtener el nombre del usuario logueado
                String nombreUsuario = UsuarioLogueado.getInstance().getUsername();

                // Renombrar la imagen seleccionada al formato "nombreUsuario.png"
                String nuevoNombreArchivo = nombreUsuario + ".png";

                // Definir la carpeta destino para guardar la imagen de perfil
                String carpetaDestino = "src/loginapplication/images/profilepics/";
                File directorioDestino = new File(carpetaDestino);

                // Crear el archivo destino con el nombre basado en el usuario logueado
                File archivoDestino = new File(directorioDestino, nuevoNombreArchivo);

                // Copiar y renombrar el archivo seleccionado a la carpeta destino
                Files.copy(archivoSeleccionado.toPath(), archivoDestino.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Cargar la nueva imagen en el ImageView
                Image nuevaImagen = new Image(archivoDestino.toURI().toString());
                imgAvatar.setImage(nuevaImagen);

            } catch (IOException e) {
                e.printStackTrace();
                // Mostrar alerta si ocurre un error al cargar la imagen
                mostrarAlerta(Alert.AlertType.ERROR, "Error al cargar la imagen", "No se ha podido cargar la imagen seleccionada.");
            }
        }
    }

    // Acción del botón Guardar Cambios: guarda los datos modificados del perfil del usuario
    @FXML
    private void handleButtonGuardarCambiosAction() {
        // Obtener el nombre del usuario logueado
        String usuarioLogueado = UsuarioLogueado.getInstance().getUsername();

        // Obtener los valores actuales de los campos de texto
        String nombre = txtNombre.getText();
        String apellidos = txtApellidos.getText();
        String email = txtEmail.getText();
        String direccion = txtDireccion.getText();
        String imagenPerfil = usuarioLogueado + ".png";  // Nombre de la imagen de perfil

        // Definir la ruta del archivo de datos de usuario
        String rutaArchivo = "src/loginapplication/ficheros/datos_usuario.txt";
        StringBuilder contenidoActualizado = new StringBuilder();
        boolean usuarioEncontrado = false; // Bandera para verificar si el usuario ya está registrado

        // Leer y actualizar cada registro del archivo
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos[0].equals(usuarioLogueado)) { // Si el usuario coincide, actualizar sus datos
                    datos[1] = nombre;
                    datos[2] = apellidos;
                    datos[3] = email;
                    datos[4] = direccion;
                    datos[5] = imagenPerfil;
                    linea = String.join(",", datos); // Reemplazar la línea con los nuevos datos
                    usuarioEncontrado = true; // Usuario encontrado y actualizado
                }
                contenidoActualizado.append(linea).append("\n"); // Añadir línea al contenido actualizado
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Si el usuario no existe, crear un nuevo registro
        if (!usuarioEncontrado) {
            String nuevoUsuario = usuarioLogueado + "," + nombre + "," + apellidos + "," + email + "," + direccion + "," + imagenPerfil;
            contenidoActualizado.append(nuevoUsuario).append(System.lineSeparator());
        }

        // Escribir el contenido actualizado de nuevo en el archivo datos_usuario.txt
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            bw.write(contenidoActualizado.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Cambiar la contraseña si el campo no está vacío
        if (!txtPasswordNuevo.getText().isEmpty()) {
            cambiarContrasena(usuarioLogueado);
        }

        // Mostrar alerta de confirmación de que los datos se han guardado
        mostrarAlerta(Alert.AlertType.INFORMATION, "Datos de usuario actualizados", "La información se ha actualizado correctamente.");
    }

    // Método para cambiar la contraseña en el archivo usuarios.txt si el usuario la ha modificado
    private void cambiarContrasena(String usuarioLogueado) {
        String nuevaContrasena = txtPasswordNuevo.getText(); // Obtener la nueva contraseña
        String rutaArchivoUsuarios = "src/loginapplication/ficheros/usuarios.txt";
        StringBuilder contenidoActualizado = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivoUsuarios))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] credenciales = linea.split(",");
                // Si el usuario coincide, actualizar su contraseña
                if (credenciales[0].equals(usuarioLogueado)) {
                    credenciales[1] = nuevaContrasena;
                }
                linea = String.join(",", credenciales); // Unir credenciales y añadir al contenido actualizado
                contenidoActualizado.append(linea).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Escribir el contenido actualizado en el archivo usuarios.txt
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivoUsuarios))) {
            bw.write(contenidoActualizado.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Limpiar el campo de nueva contraseña después de guardarla
        txtPasswordNuevo.setText("");
    }

    // Método para mostrar alertas de información o error en la interfaz
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

