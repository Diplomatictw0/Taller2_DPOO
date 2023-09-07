package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Model.Bebida; // Importa la clase Bebida
import Model.Combo;
import Model.Ingrediente;
import Model.Pedido;
import Model.ProductoMenu;

public class Restaurante {
    
    private List<Pedido> pedidos = new ArrayList<>();
    private List<Ingrediente> ingredientes = new ArrayList<>();
    private List<Combo> combos = new ArrayList<>();
    private List<ProductoMenu> menuBase = new ArrayList<>();
    private List<Bebida> bebidas = new ArrayList<>(); // Lista para almacenar bebidas
    private Pedido pedidoEnCurso;

    // Constructor
    public Restaurante() {
    }

    public void iniciarPedido(String nombreCliente, String direccionCliente) {
        this.pedidoEnCurso = new Pedido(nombreCliente, direccionCliente);
    }

    public void cerrarYGuardarPedido() throws IOException {
        pedidos.add(pedidoEnCurso);
        Pedido.incrementarNumeroPedidos();
        File archivo = new File("facturas/factura" + pedidoEnCurso.getIdPedido() + ".txt");
        pedidoEnCurso.guardarFactura(archivo);
        pedidoEnCurso = null;
    }
    
    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public List<ProductoMenu> getMenuBase() {
        return menuBase;
    }

    public List<Combo> getCombos() {
        return combos;
    }

    public List<Bebida> getBebidas() {
        return bebidas;
    }

    public Pedido getPedidoEnCurso() {
        return pedidoEnCurso;
    }

    public void cargarInformacionRestaurante(File archivoIngredientes, File archivoMenu, File archivoCombos, File archivoBebidas) throws FileNotFoundException, IOException {
        cargarMenu(archivoMenu);
        cargarIngredientes(archivoIngredientes);
        cargarCombos(archivoCombos);
        cargarBebidas(archivoBebidas); // Carga también el archivo de bebidas
    }

    private void cargarIngredientes(File archivoIngredientes) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(archivoIngredientes));

        String linea = br.readLine();

        while (linea != null) {
            String[] lista = linea.split(";");
            String ingred = lista[0];
            int precio = Integer.parseInt(lista[1]);

            Ingrediente elIngrediente = new Ingrediente(ingred, precio, precio);
            ingredientes.add(elIngrediente);

            linea = br.readLine();
        }
        br.close();
    }

    private void cargarMenu(File archivoMenu) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(archivoMenu));

        String linea = br.readLine();

        while (linea != null) {
            String[] lista = linea.split(";");
            String producto = lista[0];
            int precio = Integer.parseInt(lista[1]);

            ProductoMenu elProducto = new ProductoMenu(producto, precio, precio);
            menuBase.add(elProducto);

            linea = br.readLine();
        }
        br.close();
    }

    public ProductoMenu buscarProducto(String producto) {
        int i = 0;
        ProductoMenu finder = null;

        while (i < menuBase.size() && finder == null) {
            ProductoMenu productoCompleto = menuBase.get(i);

            if (productoCompleto.getNombre().equals(producto)) {
                finder = productoCompleto;
            }

            i++;
        }

        return finder;
    }

    public Bebida buscarBebida(String nombreBebida) {
        int i = 0;
        Bebida finder = null;

        while (i < bebidas.size() && finder == null) {
            Bebida bebidaCompleta = bebidas.get(i);

            if (bebidaCompleta.getNombre().equals(nombreBebida)) {
                finder = bebidaCompleta;
            }

            i++;
        }

        return finder;
    }

    public Ingrediente buscarIngrediente(String ingrediente) {
        int i = 0;
        Ingrediente finder = null;

        while (i < ingredientes.size() && finder == null) {
            Ingrediente ingredienteCompleto = ingredientes.get(i);

            if (ingredienteCompleto.getNombre().equals(ingrediente)) {
                finder = ingredienteCompleto;
            }

            i++;
        }

        return finder;
    }

    public Combo buscarCombo(String combo) {
        int i = 0;
        Combo finder = null;

        while (i < combos.size() && finder == null) {
            Combo comboCompleto = combos.get(i);

            if (comboCompleto.getNombre().equals(combo)) {
                finder = comboCompleto;
            }

            i++;
        }

        return finder;
    }

    private void cargarCombos(File archivoCombos) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(archivoCombos));

        String linea = br.readLine();

        while (linea != null) {
            String[] lista = linea.split(";");
            String nombre = lista[0];
            double descuento = Double.parseDouble(lista[1].substring(0, lista[1].length() - 1));

            Combo combo = new Combo(nombre, descuento);

            for (int i = 2; i < lista.length; i++) {
                String producto = lista[i];
                ProductoMenu productoCompleto = buscarProducto(producto);
                combo.agregarItemACombo(productoCompleto);
            }

            combos.add(combo);
            linea = br.readLine();
        }
        br.close();
    }

    // Método para cargar bebidas desde el archivo de bebidas
    
    public Pedido buscarPedidoPorId(int idPedido) {
        for (Pedido pedido : pedidos) {
            if (pedido.getIdPedido() == idPedido) {
                return pedido;
            }
        }
        return null; // Si no se encuentra el pedido
    }
    
    private void cargarBebidas(File archivoBebidas) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(archivoBebidas));

        String linea = br.readLine();

        while (linea != null) {
            String[] lista = linea.split(";");
            String nombre = lista[0];
            int precio = Integer.parseInt(lista[1]);

            Bebida bebida = new Bebida(nombre, precio);
            bebidas.add(bebida);

            linea = br.readLine();
        }
        br.close();
    }
    public Pedido buscarPedidoPorDireccion(String direccion) {
        for (Pedido pedido : pedidos) {
            if (pedido.getDireccionCliente().equals(direccion)) {
                return pedido;
            }
        }
        return null; // Si no se encuentra el pedido
    }
}
