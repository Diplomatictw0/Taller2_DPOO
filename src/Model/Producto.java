package Model;

public interface Producto {

    int getPrecio();

    int getCalorias();  // Agregado el método para obtener las calorías del producto

    String getNombre();

    String generarTextoFactura();
}