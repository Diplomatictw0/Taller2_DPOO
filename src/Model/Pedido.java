package Model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private static int numeroPedidos = 0;

    private int idPedido;

    private String nombreCliente;

    private String direccionCliente;

    private List<Producto> itemsPedido;

    public Pedido(String nombreCliente, String direccionCliente) {
        this.nombreCliente = nombreCliente;
        this.direccionCliente = direccionCliente;
        this.itemsPedido = new ArrayList<>();
        this.idPedido = numeroPedidos;
        numeroPedidos++;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void agregarProducto(Producto nuevoItem) {
        itemsPedido.add(nuevoItem);
    }

    public int getPrecioNetoPedido() {
        int precio = 0;
        for (Producto producto : itemsPedido) {
            precio += producto.getPrecio();
        }
        return precio;
    }

    public int getPrecioTotalPedido() {
        return getPrecioNetoPedido() + getPrecioIVAPedido();
    }

    public int getPrecioIVAPedido() {
        return (int) (getPrecioNetoPedido() * 0.19);
    }

    public int getCaloriasPedido() {
        int calorias = 0;
        for (Producto producto : itemsPedido) {
            calorias += producto.getCalorias();
        }
        return calorias;
    }
    
    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }
    public List<Producto> getItemsPedido() {
        return itemsPedido;
    }

    public String generarTextoFactura() {
		
		String textoFactura = "factura\n\nPedido " + idPedido + "\nNombre: " + nombreCliente + "\nDirección: " + direccionCliente + "\n";
		
		for(Producto producto: itemsPedido) {
			
			textoFactura += producto.generarTextoFactura();
			
		}
		
		textoFactura += "\n\nPrecio neto: $" + getPrecioNetoPedido();
		textoFactura += "\n\nPrecio iva: $" + getPrecioIVAPedido();
		textoFactura += "\n\nPrecio total: $" + getPrecioTotalPedido();
		
		return textoFactura;
	}
	
	
	public void guardarFactura(File archivo) throws IOException {
		
		PrintWriter pw = new PrintWriter(new FileWriter(archivo));
		pw.println(generarTextoFactura());
		pw.close();
		
	}
	
	public static void incrementarNumeroPedidos() {
		numeroPedidos++;
	}
}
