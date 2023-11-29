/**
 * 
 */
package Pruebas;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import Model.ProductoMenu;

public class ProductoMenu_Test {

    private ProductoMenu producto;

    @Before
    public void setUp() {
        // Inicializar un objeto ProductoMenu para usar en las pruebas
        producto = new ProductoMenu("Hamburguesa Simple", 10000, 500);
    }

    @Test
    public void testGetNombre() {
        // Verificar que el método getNombre retorna el nombre correcto del producto
        assertEquals("Hamburguesa Simple", producto.getNombre());
    }

    @Test
    public void testGetPrecio() {
        // Verificar que el método getPrecio retorna el precio correcto del producto
        assertEquals(10000, producto.getPrecio());
    }

    @Test
    public void testGetCalorias() {
        // Verificar que el método getCalorias retorna las calorías correctas del producto
        assertEquals(500, producto.getCalorias());
    }

    @Test
    public void testGenerarTextoFactura() {
        // Verificar que el método generarTextoFactura genera el texto correcto para la factura
        String expectedText = "\n- Hamburguesa Simple: $10000";
        assertEquals(expectedText, producto.generarTextoFactura());
    }
}
