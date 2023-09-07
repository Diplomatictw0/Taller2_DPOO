package View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import Controller.Restaurante;
import Model.Bebida;
import Model.Combo;
import Model.Ingrediente;
import Model.Producto;
import Model.ProductoAjustado;
import Model.ProductoMenu;
import Model.Pedido;

public class Aplicacion {

    private Restaurante restaurante;

    public void mostrarMenu() {
        System.out.println("\nOpciones de la aplicación\n");
        System.out.println("1. Cargar datos");
        System.out.println("2. Iniciar un nuevo pedido");
        System.out.println("3. Agregar un elemento a un pedido");
        System.out.println("4. Cerrar un pedido y guardar la factura");
        System.out.println("5. Consultar información de pedido según ID");
        System.out.println("6. Salir de la aplicación");
    }

    public void ejecutarOpcion() {
        System.out.println("Hamburguesas");

        boolean continuar = true;
        while (continuar) {
            try {
                mostrarMenu();
                int opcionSeleccionada = Integer.parseInt(input("\nPor favor seleccione una opción"));
                if (opcionSeleccionada == 1) {
                    ejecutarCargarInformacion();
                } else if (opcionSeleccionada == 2) {
                    ejecutarNuevoPedido();
                } else if (opcionSeleccionada == 3) {
                    ejecutarAgregarElemento();
                } else if (opcionSeleccionada == 4) {
                    ejecutarCerrarYGuardar();
                } else if (opcionSeleccionada == 5) {
                    ejecutarPedidoPorId();
                } else if (opcionSeleccionada == 6) {
                    System.out.println("\nSaliendo de la aplicación ...");
                    continuar = false;
                } else {
                    System.out.println("\nPor favor seleccione una opción válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nDebe seleccionar uno de los números de las opciones.");
            }
        }
    }

    private void ejecutarCargarInformacion() {
        this.restaurante = new Restaurante();

        File menu = new File("data/menu.txt");
        File ingredientes = new File("data/ingredientes.txt");
        File combos = new File("data/combos.txt");
        File bebidas = new File("data/bebidas.txt");

        if (menu.exists() && ingredientes.exists() && combos.exists() && bebidas.exists()) {
            try {
                restaurante.cargarInformacionRestaurante(ingredientes, menu, combos, bebidas);
                System.out.println("\nCarga iniciada correctamente");
            } catch (FileNotFoundException e) {
                System.out.println("El archivo indicado no se encontró");
            } catch (IOException e) {
                System.out.println("Hubo un problema leyendo el archivo");
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Algunos archivos de datos no existen. Asegúrate de que todos los archivos estén presentes.");
        }
    }

    private void ejecutarNuevoPedido() {
        if (restaurante.getPedidoEnCurso() != null) {
            System.out.println("Ya hay un pedido en curso");
            return;
        }

        String nombreCliente = input("\nPor favor ingrese su nombre");
        String direccionCliente = input("Por favor ingrese la id del pedido");

        restaurante.iniciarPedido(nombreCliente, direccionCliente);
        System.out.println("\nPedido iniciado correctamente");
    }

    private void ejecutarAgregarElemento() {
        mostrarMenuRestaurante();
        int agregado = Integer.parseInt(input("Seleccione un ítem para agregar (1. Producto, 2. Combo, 3. Bebida)"));

        if (agregado == 1) {
            // Agregar producto
            String nombreProducto = input("Ingrese el nombre del producto que quiere agregar");
            ProductoMenu productoBase = restaurante.buscarProducto(nombreProducto);
            if (productoBase == null) {
                System.out.println("Producto inválido");
                return;
            }

            List<Ingrediente> ingredientesAgregadosCompletos = new ArrayList<>();
            List<Ingrediente> ingredientesEliminadosCompletos = new ArrayList<>();

            int opcionAgregar = Integer.parseInt(input("¿Quiere agregar algún ingrediente? (1. Sí, 2. No)"));

            if (opcionAgregar == 1) {
                String ingredientesAgregados = input("Ingrese los ingredientes a agregar (Enter para saltar || Agregar separado por comas)");
                String[] listaIngredientesAgregados = ingredientesAgregados.split(",");

                for (String ingredienteAgregado : listaIngredientesAgregados) {
                    Ingrediente ingredienteAgregadoCompleto = restaurante.buscarIngrediente(ingredienteAgregado.trim());
                    if (ingredienteAgregadoCompleto == null) {
                        System.out.println("Ingrediente inválido: " + ingredienteAgregado);
                        return;
                    }
                    ingredientesAgregadosCompletos.add(ingredienteAgregadoCompleto);
                }
            }

            int opcionEliminar = Integer.parseInt(input("¿Quiere eliminar algún ingrediente? (1. Sí, 2. No)"));

            if (opcionEliminar == 1) {
                String ingredientesEliminados = input("Ingrese los ingredientes a eliminar (Enter para saltar || Agregar separado por comas)");
                String[] listaIngredientesEliminados = ingredientesEliminados.split(",");

                for (String ingredienteEliminado : listaIngredientesEliminados) {
                    Ingrediente ingredienteEliminadoCompleto = restaurante.buscarIngrediente(ingredienteEliminado.trim());
                    if (ingredienteEliminadoCompleto == null) {
                        System.out.println("Ingrediente inválido: " + ingredienteEliminado);
                        return;
                    }
                    ingredientesEliminadosCompletos.add(ingredienteEliminadoCompleto);
                }
            }

            Producto producto;

            if (!ingredientesAgregadosCompletos.isEmpty() || !ingredientesEliminadosCompletos.isEmpty()) {
                producto = new ProductoAjustado(productoBase, ingredientesAgregadosCompletos, ingredientesEliminadosCompletos);
            } else {
                producto = productoBase;
            }

            restaurante.getPedidoEnCurso().agregarProducto(producto);
            System.out.println("Producto agregado correctamente");
        } else if (agregado == 2) {
            // Agregar combo
            String nombreCombo = input("Ingrese el nombre del combo que quiere agregar");
            Combo combo = restaurante.buscarCombo(nombreCombo);
            if (combo == null) {
                System.out.println("Combo inválido");
                return;
            }
            restaurante.getPedidoEnCurso().agregarProducto(combo);
            System.out.println("Combo agregado correctamente");
        } else if (agregado == 3) {
            // Agregar bebida
            String nombreBebida = input("Ingrese el nombre de la bebida que quiere agregar");
            Bebida bebida = restaurante.buscarBebida(nombreBebida);
            if (bebida == null) {
                System.out.println("Bebida inválida");
                return;
            }
            restaurante.getPedidoEnCurso().agregarProducto(bebida);
            System.out.println("Bebida agregada correctamente");
        } else {
            System.out.println("\nPor favor seleccione una opción válida.");
        }
    }

    private void mostrarMenuRestaurante() {
        System.out.println("-- Menú ---------------------------------------");
        for (ProductoMenu producto : restaurante.getMenuBase()) {
            System.out.println("- " + producto.getNombre() + ": $" + producto.getPrecio());
        }

        System.out.println("-- Ingredientes --------------------------------");
        for (Ingrediente ingrediente : restaurante.getIngredientes()) {
            System.out.println("- " + ingrediente.getNombre() + ": $" + ingrediente.getCostoAdicional());
        }

        System.out.println("-- Combos -------------------------------------");
        for (Combo combo : restaurante.getCombos()) {
            System.out.println(combo.getNombre() + ": $" + combo.getPrecio());
            for (Producto productoCombo : combo.getItemsCombo()) {
                System.out.println("- " + productoCombo.getNombre());
            }
        }

        System.out.println("-- Bebidas ------------------------------------");
        for (Bebida bebida : restaurante.getBebidas()) {
            System.out.println("- " + bebida.getNombre() + ": $" + bebida.getPrecio());
        }
    }

    private void ejecutarCerrarYGuardar() {
		
		try {
			
			restaurante.cerrarYGuardarPedido();
			System.out.println("\nSu pedido se ha realizado exitosamente. Puede consultar su factura.");
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}

    private void ejecutarPedidoPorId() {
        int idPedidoBuscado;
        try {
            idPedidoBuscado = Integer.parseInt(input("Ingrese el ID del pedido que desea consultar"));
        } catch (NumberFormatException e) {
            System.out.println("ID de pedido inválido.");
            return;
        }

        Pedido pedidoEncontrado = restaurante.buscarPedidoPorId(idPedidoBuscado);

        if (pedidoEncontrado != null) {
            System.out.println("Información del Pedido:");
            System.out.println("ID: " + pedidoEncontrado.getIdPedido());
            System.out.println("Nombre del Cliente: " + pedidoEncontrado.getNombreCliente());
            System.out.println("Dirección del Cliente: " + pedidoEncontrado.getDireccionCliente());
            System.out.println("Productos:");

            for (Producto producto : pedidoEncontrado.getItemsPedido()) {
                System.out.println(producto.generarTextoFactura());
            }
        } else {
            System.out.println("No se encontró un pedido con el ID ingresado.");
        }
    }
    
    public String input(String mensaje) { 
        try {
            System.out.print(mensaje + ": ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
        } catch (IOException e) {
            System.out.println("Error leyendo de la consola");
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Aplicacion consola = new Aplicacion();
        consola.ejecutarOpcion();
    }
}