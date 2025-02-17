package conexionsqlitedocumentadoclase;

/**
 * Representa un pedido en la aplicación.
 * Contiene información sobre el ID del pedido, el producto, la cantidad solicitada y la fecha en que se solicita.
 * 
 * @author JorgeSaldaña
 * @version 1.0
 */
public class Pedido {
    
    /**
     * Indentificador del pedido
     */
    private final Integer id;
    
    /**
     * Nombre del cliente que realizó el pedido
     */
    private final String cliente;
    
    /**
     * Nombre del producto solicitado
     */
    private final String producto;
    
    /**
     * Cantidad solicitada del producto
     */
    private final Integer cantidad;
    
    /**
     * Fecha en la que se realizó el pedido
     */
    private final String fecha;

    /**
     * Crea una nueva instancia de Pedido con los datos especificados
     * 
     * @param id Identificador único del pedido
     * @param cliente Nombre del cliente que realiza el pedido
     * @param producto Nombre del producto solicitado
     * @param cantidad Cantidad de unidades solicitadas
     * @param fecha Fecha en la que se realizó el pedidod
     */
    public Pedido(Integer id, String cliente, String producto, Integer cantidad, String fecha) {
        this.id = id;
        this.cliente = cliente;
        this.producto = producto;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    /**
     * Obtiene el ID del pedido
     * 
     * @return ID del pedido realizado
     */
    public Integer getId() {
        return id;
    }

    /**
     * Obtiene el nombre del cliente del pedido
     * 
     * @return Nombre del cliente
     */
    public String getCliente() {
        return cliente;
    }

    /**
     * Obtiene el nombre del producto solicitado en el pedido
     * 
     * @return Nombre del producto solicitado
     */
    public String getProducto() {
        return producto;
    }

    /**
     * Obtiene la cantidad de unidades solicitadas en el pedido
     * 
     * @return Cantidad de unidades solicitadas 
     */
    public Integer getCantidad() {
        return cantidad;
    }

    /**
     * Obtiene la fecha en la que se introduce el pedid
     * 
     * @return Fecha de introducción de pedido
     */
    public String getFecha() {
        return fecha;
    } 
}
