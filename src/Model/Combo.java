package Model;

import java.util.ArrayList;
import java.util.List;

public class Combo implements Producto {

    private double descuento;
    private String nombreCombo;
    private List<Producto> itemsCombo;

    // Constructor
    public Combo(String nombreCombo, double descuento) {
        this.nombreCombo = nombreCombo;
        this.descuento = descuento;
        this.itemsCombo = new ArrayList<>();
    }

    // Agrega un producto al combo
    public void agregarItemACombo(Producto itemCombo) {
        itemsCombo.add(itemCombo);
    }

    // Calcula el precio del combo, considerando el descuento
    public int getPrecio() {
        int precio = 0;

        for (Producto producto : itemsCombo) {
            precio += producto.getPrecio();
        }

        precio *= 1 - (descuento / 100);

        return precio;
    }

    // Obtiene los productos que componen el combo
    public List<Producto> getItemsCombo() {
        return itemsCombo;
    }

    // Obtiene el nombre del combo
    public String getNombre() {
        return nombreCombo;
    }

    // Genera el texto para la factura, incluyendo el nombre, precio y calorías del combo
    public String generarTextoFactura() {
        int totalCalorias = 0;
        StringBuilder textoFactura = new StringBuilder("\n- " + getNombre() + ": $" + getPrecio());

        for (Producto productoCombo : itemsCombo) {
            textoFactura.append("\n\t- ").append(productoCombo.getNombre()).append(": $").append(productoCombo.getPrecio());
            if (productoCombo instanceof ProductoAjustado) {
                totalCalorias += ((ProductoAjustado) productoCombo).getCalorias();
            } else if (productoCombo instanceof ProductoMenu) {
                totalCalorias += ((ProductoMenu) productoCombo).getCalorias();
            }
        }

        textoFactura.append("\n\tCalorías totales: ").append(totalCalorias);
        return textoFactura.toString();
    }

    @Override
    public int getCalorias() {
        int totalCalorias = 0;
        for (Producto productoCombo : itemsCombo) {
            totalCalorias += productoCombo.getCalorias();
        }
        return totalCalorias;
    }
}
