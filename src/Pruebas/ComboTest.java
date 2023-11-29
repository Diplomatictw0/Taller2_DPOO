package Pruebas;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import Model.Combo;
import Model.ProductoMenu;

public class ComboTest {

    private Combo combo;
    private ProductoMenu producto1;
    private ProductoMenu producto2;

    @Before
    public void setUp() {
        // Inicializar un objeto Combo y dos objetos ProductoMenu para usar en las pruebas
        producto1 = new ProductoMenu("Hamburguesa Simple", 10000, 500);
        producto2 = new ProductoMenu("Papas Fritas", 5000, 300);
        combo = new Combo("Combo Hamburguesa", 10);
        combo.agregarItemACombo(producto1);
        combo.agregarItemACombo(producto2);
    }

    @Test
    public void testGetNombre() {
        // Verificar que el método getNombre retorna el nombre correcto del combo
        assertEquals("Combo Hamburguesa", combo.getNombre());
    }

    @Test
    public void testGetPrecio() {
        // Verificar que el método getPrecio retorna el precio correcto del combo
        assertEquals(13500, combo.getPrecio());
    }

    @Test
    public void testGenerarTextoFactura() {
        // Verificar que el método generarTextoFactura genera el texto correcto para la factura del combo
        String expectedText = "\n- Combo Hamburguesa: $13500" +
                              "\n\t- Hamburguesa Simple: $10000" +
                              "\n\t- Papas Fritas: $5000" +
                              "\n\tCalorías totales: 800";
        assertEquals(expectedText, combo.generarTextoFactura());
    }
}
