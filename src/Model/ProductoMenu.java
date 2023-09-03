package Model;

public class ProductoMenu implements Producto {
    
    //-------------------- Atributos -------------------
    
    private String nombre;
    
    private int precioBase;
    
    private int calorias;  // Agregado para almacenar las calorías del producto
    
    //-------------------- Constructor -------------------
    
    public ProductoMenu(String nombre, int precioBase, int calorias) {
        this.nombre = nombre;
        this.precioBase = precioBase;
        this.calorias = calorias;
    }
    
    //-------------------- Métodos --------------------
    
    public String getNombre() {
        return nombre;
    }
    
    public int getPrecio() {
        return precioBase;
    }
    
    public int getCalorias() {
        return calorias;
    }
    
    public String generarTextoFactura() {
        return "\n- " + getNombre() + ": $" + getPrecio();
    }
}

