package Model;

public class Bebida implements Producto {

    private String nombre;
    private int precio;

    public Bebida(String nombre, int precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public String generarTextoFactura() {
        return "\n- " + getNombre() + ": $" + getPrecio();
    }

	@Override
	public int getCalorias() {
		// TODO Auto-generated method stub
		return 0;
	}
}
