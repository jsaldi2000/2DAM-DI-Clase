package conexionsqlite;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class MainController {

    // Variable para la url de la bbdd
    private static final String URL = "jdbc:sqlite:C:\\Users\\JorgeSaldaña\\OneDrive - Universidad Europea\\24-25\\DI (Desarrollo de Interfaces)\\Jorge\\Prácticas\\P13 Conexión a Bases de Datos (Conectores JDBC)\\P13.db";
    //private static final String URL = "jdbc:mysql://[usuario]:[contraseña]@servidor/P13.db";
            
    // Variable para la ObservableList pedidos
    private ObservableList<Pedido> pedidos = FXCollections.observableArrayList();      

    @FXML
    private Button btnCargarPedidos;

    @FXML
    private TableView<Pedido> tablaPedidos;
    @FXML
    private TextField txtFiltroCliente;
    @FXML
    private Button btnOk;
    
    // Método para ejecutar consultas a la bbdd, y agregar pedidos a la lista
    private void ejecutarConsulta(String query) throws SQLException{
        Connection conn = DriverManager.getConnection(URL);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        //Limpiar la lista de pedidos antes de agregar nuevos
        pedidos.clear();
        
        //Recorremos el ResultSet y agregamos los pedidos a la lista
        while (rs.next()){
            
            pedidos.add(new Pedido(
                    rs.getInt("id"),
                    rs.getString("cliente"),
                    rs.getString("producto"),
                    rs.getInt("cantidad"),
                    rs.getString("fecha")
            ));
        }
        
    }

    @FXML
    private void HandleButtonCargarPedidosAction() throws SQLException {
        String query = "SELECT * FROM pedidos";
        ejecutarConsulta(query);
        
        //Actualizar los datos en la tabla
        tablaPedidos.setItems(pedidos);
        

    }

    @FXML
    private void handleButtonOkAction(ActionEvent event) throws SQLException {
        
        // Obtengo el valor del cliente desde el campo de texto
        String cliente = txtFiltroCliente.getText();
        
        // Creao la consulta SQL con parámetro
        //String query = "SELECT * FROM pedidos WHERE cliente = '" + cliente + "'";
        //ejecutarConsulta(query);
        
        //tablaPedidos.setItems(pedidos);
        
        // Creo una consulta con parámetro
        String query = "SELECT * FROM pedidos WHERE cliente = ?";
        
        // Ejecuto la consulta con PreparedStatement
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement stmt = conn.prepareStatement(query);
        
        // Establecer el parámetro del cliente
        stmt.setString(1, cliente);
        
        // Ejecutamos la consulta
        ResultSet rs = stmt.executeQuery();
        
        pedidos.clear();
        
        //Recorremos el ResultSet y agregamos los pedidos a la lista
        while (rs.next()){
            
            pedidos.add(new Pedido(
                    rs.getInt("id"),
                    rs.getString("cliente"),
                    rs.getString("producto"),
                    rs.getInt("cantidad"),
                    rs.getString("fecha")
            ));
        }
        
        //Actualizar los datos en la tabla
        tablaPedidos.setItems(pedidos);
 
    }

}
