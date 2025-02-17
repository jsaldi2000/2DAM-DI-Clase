package conexionsqlitedocumentadoclase;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
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

/**
 * Controlador principal de la aplicación.
 * Maneja la interacción con la base de datos SQLIte y la visualización de los datos en la interfaz
 * 
 * @author JorgeSaldaña
 * @version 1.0
 */
public class MainController {

    
    /**
     * URL de conexión a la base de datos SQLite
     * 
     */
    private static final String URL = "jdbc:sqlite:C:\\Users\\JorgeSaldaña\\OneDrive - Universidad Europea\\24-25\\DI (Desarrollo de Interfaces)\\Jorge\\Prácticas\\P13 Conexión a Bases de Datos (Conectores JDBC)\\P13.db";       
    
    /**
     * Lista observable para almacenar los pedidos
     */
    private ObservableList<Pedido> pedidos = FXCollections.observableArrayList();      

    /**
     * Botón para cargar todos los pedidos desde la base de datos
     */
    @FXML
    private Button btnCargarPedidos;

    /**
     * Tabla de la interfaz donde se muestran los pedidos
     */
    @FXML
    private TableView<Pedido> tablaPedidos;
    
    /**
     * Campo de texto para filtrar pedidos por cliente
     */
    @FXML
    private TextField txtFiltroCliente;
    
    /**
     * Botón para ejecutar la búsqueda de pedidos filtrados
     */
    @FXML
    private Button btnOk;
    
    /**
     * Campo de texto para ingresar el nombre del nuevo cliente
     */
    @FXML
    private TextField txtCliente;
    
    /**
     * Campo de texto para ingresar el nombre del producto
     */
    @FXML
    private TextField txtProducto;
    
    /**
     * Campo de texto para ingresar la cantidad de productos a ordenar
     */
    @FXML
    private TextField txtCantidad;
    
    /**
     * Campo de texto para ingresar la fecha del pedido
     */
    @FXML
    private TextField txtFecha;
    
    /**
     * Ejecuta una consulta SQL y almacena los resultados en la lista observable de pedidos.
     * 
     * @param query Consulta a ejecutar
     * @throws SQLException Se lanza si ocurre un error en la conexión con la base de datos "pedidos", en SQLite; o en la consulta.
     */
    private void ejecutarConsulta(String query) throws SQLException{
        Connection conn = DriverManager.getConnection(URL);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        pedidos.clear();
        
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

    /**
     * Maneja la acción del botón "Cargar Pedidos".
     * Carga todos los pedidos de la base de datos y los muestra en la tabla
     * 
     * @throws SQLException Se lanza si ocurre un error en la ejecución de la consulta
     */
    @FXML
    private void HandleButtonCargarPedidosAction() throws SQLException {
        String query = "SELECT * FROM pedidos";
        ejecutarConsulta(query);
        
        tablaPedidos.setItems(pedidos);
        

    }

    /**
     * Maneja la acción del botón "Buscar Pedido".
     * Filtra los pedidos según el cliente ingresado en el campo texto.
     * 
     * @throws SQLException  Se lanza si ocurre un error en la ejecución de la consulta
     */
    @FXML
    private void handleButtonOkAction() throws SQLException {
        
        String cliente = txtFiltroCliente.getText();
                
        String query = "SELECT * FROM pedidos WHERE cliente = ?";
        
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement stmt = conn.prepareStatement(query);
        
        stmt.setString(1, cliente);
        
        ResultSet rs = stmt.executeQuery();
        
        pedidos.clear();
        
        while (rs.next()){
            
            pedidos.add(new Pedido(
                    rs.getInt("id"),
                    rs.getString("cliente"),
                    rs.getString("producto"),
                    rs.getInt("cantidad"),
                    rs.getString("fecha")
            ));
        }
        
        tablaPedidos.setItems(pedidos);
 
    }
    
    /**
     * Inserta un nuevo pedido en la base de datos.
     * 
     * @param cliente Nombre del cliente que solicita el pedido
     * @param producto Nombre del producto solicitado
     * @param cantidad Cantidad de unidades solicitadas
     * @param fecha Fecha en la que se introduce el pedido
     * 
     * @throws SQLException Se lanza si ocurre un error en la inserción del nuevo pedido
     */
    private void insertarPedido (String cliente, String producto, int cantidad, String fecha) throws SQLException{
        
        String query = "INSERT INTO pedidos (cliente, producto, cantidad, fecha) VALUES (?, ?, ?, ?)";
        
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement stmt = conn.prepareStatement(query);
        
        stmt.setString(1, cliente);
        stmt.setString(2, producto);
        stmt.setInt(3, cantidad);
        stmt.setString(4, fecha);
        
        stmt.executeUpdate();
        
    }

    /**
     * Maneja la acción del botón "Enviar Pedido".
     * Inserta un nuevo pedido en la base de datos y actualiza la tabla.
     * 
     * @throws SQLException Se lanza si ocurre un error en la inserción de datos
     */
    @FXML
    private void handleButtonEnviarPedidoAction() throws SQLException {
        
        String cliente = txtCliente.getText();
        String producto = txtProducto.getText();
        int cantidad = Integer.parseInt(txtCantidad.getText());
        String fecha = txtFecha.getText();
        
        insertarPedido(cliente, producto, cantidad, fecha);
        
        txtCliente.setText("");
        txtProducto.setText("");
        txtCantidad.setText("");
        txtFecha.setText("");
        
        String query = "SELECT * FROM pedidos";
        ejecutarConsulta(query);
        tablaPedidos.setItems(pedidos);
    }

    @FXML
    private void abrirManual(ActionEvent event) throws IOException {
        File manual = new File("documentacion/manual.html");
        Desktop.getDesktop().browse(manual.toURI());
    }

    @FXML
    private void handleButtonAbrirManualAction(ActionEvent event) throws IOException {
        File manual = new File("documentacion/manual.html");
        Desktop.getDesktop().browse(manual.toURI());
    }

}
