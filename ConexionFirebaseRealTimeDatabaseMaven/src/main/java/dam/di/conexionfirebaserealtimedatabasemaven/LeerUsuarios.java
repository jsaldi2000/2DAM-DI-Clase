package dam.di.conexionfirebaserealtimedatabasemaven;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class LeerUsuarios {

    private static DatabaseReference ref;

    public static void main(String[] args) throws FileNotFoundException, IOException {

        FileInputStream credentials = new FileInputStream("netbeansrealtimedatabase-firebase-adminsdk-op9cv-94e680ea37.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(credentials))
                .setDatabaseUrl("https://netbeansrealtimedatabase-default-rtdb.firebaseio.com/")
                .build();

        FirebaseApp.initializeApp(options);

        //OIbtener la instancia de bbdd
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Obtener la referencia al nodo
        ref = database.getReference("usuarios");

        // Leemos los datos en método aparte
        leerDatosOrdenados();

    }

    private static void leerDatosOrdenados() {

        // Ordenar por usuarios
        Query query = ref.orderByChild("username");

        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot ds) {

                if (!ds.exists()) {
                    System.out.println("No hay datos en el nodo usuarios");
                }

                long registros = ds.getChildrenCount();
                System.out.println("Número de registros encontrados " + registros);

                if (registros == 0) {
                    System.out.println("No se encontraron usuarios dentro del nodo");
                }

                // Recorremos todos los nodos hijos dentro del ds (dataSnapshot)
                for (DataSnapshot snapshot : ds.getChildren()) {

                    String name = snapshot.child("name").getValue(String.class);
                    Integer age = snapshot.child("age").getValue(Integer.class);
                    String surname = snapshot.child("surname").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);
                    String username = snapshot.child("username").getValue(String.class);

                    // Mostramos los datos por consola
                    System.out.println("_____________________________");
                    System.out.println("Nombre: " + name);
                    System.out.println("Apellido: " + surname);
                    System.out.println("Edad: " + age);
                    System.out.println("Email: " + email);
                    System.out.println("Username: " + username);
                }

            }

            @Override
            public void onCancelled(DatabaseError de) {
                System.out.println("Error al leer los datos");
            }
        });

        // Agregamos un delay de hasta 5 seg
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
