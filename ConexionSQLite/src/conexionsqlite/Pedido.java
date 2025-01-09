package conexionsqlite;

import java.util.Date;

public class Pedido {
    private final Integer id;
    private final String cliente;
    private final String producto;
    private final Integer cantidad;
    private final String fecha;

    public Pedido(Integer id, String cliente, String producto, Integer cantidad, String fecha) {
        this.id = id;
        this.cliente = cliente;
        this.producto = producto;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public String getProducto() {
        return producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public String getFecha() {
        return fecha;
    } 
}
