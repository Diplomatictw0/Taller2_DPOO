package Pruebas;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import Model.Bebida;
import Model.Pedido;
import Model.Producto;
import Model.ProductoMenu;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PedidoTest {

    private Pedido pedido;
    private ProductoMenu producto1;
    private ProductoMenu producto2;
    private Bebida bebida;

    @Before
    public void setUp() {
        // Inicializar un objeto Pedido, dos objetos ProductoMenu y un objeto Bebida para usar en las pruebas
        producto1 = new ProductoMenu("Hamburguesa Simple", 10000, 500);
        producto2 = new ProductoMenu("Papas Fritas", 5000, 300);
        bebida = new Bebida("Refresco", 2000);
        pedido = new Pedido("Cliente Test", "Dirección Test");
        pedido.agregarProducto(producto1);
        pedido.agregarProducto(producto2);
        pedido.agregarProducto(bebida);
    }

    @Test
    public void testGetIdPedido() {
        // Verificar que el método getIdPedido retorna el ID correcto del pedido
        assertEquals(0, pedido.getIdPedido());
    }

    @Test
    public void testGetNombreCliente() {
        // Verificar que el método getNombreCliente retorna el nombre correcto del cliente
        assertEquals("Cliente Test", pedido.getNombreCliente());
    }

    @Test
    public void testGetDireccionCliente() {
        // Verificar que el método getDireccionCliente retorna la dirección correcta del cliente
        assertEquals("Dirección Test", pedido.getDireccionCliente());
    }

    @Test
    public void testGetItemsPedido() {
        // Verificar que el método getItemsPedido retorna la lista correcta de productos en el pedido
        List<Producto> expectedItems = new ArrayList<>();
        expectedItems.add(producto1);
        expectedItems.add(producto2);
        expectedItems.add(bebida);
        assertEquals(expectedItems, pedido.getItemsPedido());
    }

    @Test
    public void testGetPrecioNetoPedido() {
        // Verificar que el método getPrecioNetoPedido retorna el precio neto correcto del pedido
        assertEquals(17000, pedido.getPrecioNetoPedido());
    }

    @Test
    public void testGetPrecioIVAPedido() {
        // Verificar que el método getPrecioIVAPedido retorna el IVA correcto del pedido
        assertEquals(3230, pedido.getPrecioIVAPedido());
    }

    @Test
    public void testGetPrecioTotalPedido() {
        // Verificar que el método getPrecioTotalPedido retorna el precio total correcto del pedido
        assertEquals(20230, pedido.getPrecioTotalPedido());
    }

    @Test
    public void testGetCaloriasPedido() {
        // Verificar que el método getCaloriasPedido retorna las calorías totales correctas del pedido
        assertEquals(1100, pedido.getCaloriasPedido());
    }

    @Test
    public void testGenerarTextoFactura() {
        // Verificar que el método generarTextoFactura genera el texto correcto para la factura del pedido
        String expectedText = "factura\n\nPedido 0\nNombre: Cliente Test\nDirección: Dirección Test" +
                              "\n\n- Hamburguesa Simple: $10000" +
                              "\n- Papas Fritas: $5000" +
                              "\n- Refresco: $2000" +
                              "\n\nPrecio neto: $17000" +
                              "\n\nPrecio iva: $3230" +
                              "\n\nPrecio total: $20230";
        assertEquals(expectedText, pedido.generarTextoFactura());
    }

    @Test(expected = IOException.class)
    public void testGuardarFacturaIOException() throws IOException {
        // Verificar que se lanza IOException al intentar guardar la factura en un archivo inválido
        File invalidFile = new File("invalid/file/path.txt");
        pedido.guardarFactura(invalidFile);
    }

    @Test
    public void testGuardarFactura() throws IOException {
        // Verificar que el método guardarFactura guarda la factura correctamente en un archivo válido
        File validFile = new File("valid/file/path.txt");
        pedido.guardarFactura(validFile);
        // Se debería verificar manualmente el contenido del archivo guardado
        // y asegurarse de que sea el esperado.
    }

    @Test(expected = RuntimeException.class)
    public void testExcepcionLimitePedido() {
        // Verificar que se lanza RuntimeException al intentar realizar un pedido con un valor total superior a $150,000
        Pedido pedidoExcepcion = new Pedido("Cliente Excepcion", "Dirección Excepcion");
        // Agregar productos al pedido para superar el límite
        for (int i = 0; i < 20; i++) {
            pedidoExcepcion.agregarProducto(new ProductoMenu("Producto " + i, 8000, 400));
        }
    }
}
