package Pruebas;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import Model.Ingrediente;
import Model.ProductoAjustado;
import Model.ProductoMenu;

public class ProductoAjustadoTest {

    private ProductoMenu productoBase;
    private ProductoAjustado productoAjustado;

    @Before
    public void setUp() {
        // Inicializar un objeto ProductoMenu y un objeto ProductoAjustado para usar en las pruebas
        productoBase = new ProductoMenu("Hamburguesa Simple", 10000, 500);
        productoAjustado = new ProductoAjustado(productoBase, null, null);
    }

    @Test
    public void testGetNombre() {
        // Verificar que el método getNombre retorna el nombre correcto del producto ajustado
        assertEquals("Hamburguesa Simple", productoAjustado.getNombre());
    }

    @Test
    public void testGetPrecioBase() {
        // Verificar que el método getPrecioBase retorna el precio base correcto del producto ajustado
        assertEquals(10000, productoAjustado.getPrecio());
    }

    @Test
    public void testGetPrecio() {
        // Verificar que el método getPrecio retorna el precio correcto del producto ajustado
        assertEquals(10000, productoAjustado.getPrecio());
    }

    @Test
    public void testGenerarTextoFactura() {
        // Verificar que el método generarTextoFactura genera el texto correcto para la factura del producto ajustado
        String expectedText = "\n- Hamburguesa Simple: $10000";
        assertEquals(expectedText, productoAjustado.generarTextoFactura());
    }
}
