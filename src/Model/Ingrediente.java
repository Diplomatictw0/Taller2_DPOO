package Model;

public class Ingrediente {

    private String nombre;
    private int costoAdicional;
    private int calorias;  // Agregado para almacenar las calorías del ingrediente

    // Constructor
    public Ingrediente(String nombre, int costoAdicional, int calorias) {
        this.nombre = nombre;
        this.costoAdicional = costoAdicional;
        this.calorias = calorias;
    }

    // Constructor sin calorías
    public Ingrediente(String nombre, int costoAdicional) {
        this.nombre = nombre;
        this.costoAdicional = costoAdicional;
        this.calorias = 0;  // Valor por defecto
    }

    // Obtiene el nombre del ingrediente
    public String getNombre() {
        return nombre;
    }

    // Obtiene el costo adicional del ingrediente
    public int getCostoAdicional() {
        return costoAdicional;
    }

    // Obtiene las calorías del ingrediente
    public int getCalorias() {
        return calorias;
    }
}
