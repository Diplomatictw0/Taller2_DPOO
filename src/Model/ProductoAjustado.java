package Model;

import java.util.List;

public class ProductoAjustado implements Producto {
    private ProductoMenu base;
    private List<Ingrediente> agregados;
    private List<Ingrediente> eliminados;

    public ProductoAjustado(ProductoMenu base, List<Ingrediente> agregados, List<Ingrediente> eliminados) {
        this.base = base;
        this.agregados = agregados;
        this.eliminados = eliminados;
    }

    public int getPrecio() {
        int precio = base.getPrecio();

        for (Ingrediente ingrediente : agregados) {
            precio += ingrediente.getCostoAdicional();
        }

        for (Ingrediente ingrediente : eliminados) {
            precio -= ingrediente.getCostoAdicional();
        }

        return precio;
    }

    public String getNombre() {
        return base.getNombre();
    }

    public String generarTextoFactura() {
        StringBuilder textoFactura = new StringBuilder("\n- " + getNombre() + ": $" + getPrecio());

        if (!agregados.isEmpty()) {
            textoFactura.append("\n\tIngredientes agregados:");
            for (Ingrediente ingrediente : agregados) {
                textoFactura.append("\n\t\t- ").append(ingrediente.getNombre()).append(": +$").append(ingrediente.getCostoAdicional());
            }
        }

        if (!eliminados.isEmpty()) {
            textoFactura.append("\n\tIngredientes eliminados:");
            for (Ingrediente ingrediente : eliminados) {
                textoFactura.append("\n\t\t- ").append(ingrediente.getNombre()).append(": -$").append(ingrediente.getCostoAdicional());
            }
        }

        return textoFactura.toString();
    }

    @Override
    public int getCalorias() {
        int calorias = base.getCalorias();

        for (Ingrediente ingrediente : agregados) {
            calorias += ingrediente.getCalorias();
        }

        for (Ingrediente ingrediente : eliminados) {
            calorias -= ingrediente.getCalorias();
        }

        return calorias;
    }
}
