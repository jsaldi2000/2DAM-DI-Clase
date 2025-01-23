package dam.di.conexionfirebaserealtimedatabasemaven;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.IOException;

public class EscribirSimple {

    public static void main(String[] args) throws IOException {

        FileInputStream serviceAccount = new FileInputStream("netbeansrealtimedatabase-firebase-adminsdk-op9cv-94e680ea37.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://netbeansrealtimedatabase-default-rtdb.firebaseio.com/")
                .build(); 

        FirebaseApp.initializeApp(options);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("experiencias");

        ref.setValueAsync("solocroquetas2"); 

        try {
            Thread.sleep(5000); 
        } catch (InterruptedException e) { 
            e.printStackTrace(); 
        }
    }
}
