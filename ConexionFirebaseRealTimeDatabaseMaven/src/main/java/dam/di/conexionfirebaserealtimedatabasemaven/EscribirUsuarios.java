package dam.di.conexionfirebaserealtimedatabasemaven;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class EscribirUsuarios {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        // Inicializar Firebase usando las credenciales almacenadas
        FileInputStream credenciales = new FileInputStream("netbeansrealtimedatabase-firebase-adminsdk-op9cv-94e680ea37.json");

        // Configuramos opciones de firebase
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(credenciales))
                .setDatabaseUrl("https://netbeansrealtimedatabase-default-rtdb.firebaseio.com/")
                .build();

        // Iniciar firebase con la configuracion
        FirebaseApp.initializeApp(options);

        // Obtenemos la instancia de la bbdd
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Obtenemos la referncia de la bbdd
        DatabaseReference ref = database.getReference("usuarios");

        // Crear dos objetos Usuario
        Usuario user1 = new Usuario("jorgesaldana", "Jorge", "Saldana", "jorge.saldana@universidadeuropea.es", 40);
        Usuario user2 = new Usuario("andreaferrer", "Andrea", "Ferrer", "andreaferrer@universidadeuropea.es", 31);

        // Escribir los objetos Usuario en la bbdd
        ref.child(user1.getUsername()).setValueAsync(user1);
        ref.child(user2.getUsername()).setValueAsync(user2);

        // Agregamos un delay de hasta 5 seg
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
