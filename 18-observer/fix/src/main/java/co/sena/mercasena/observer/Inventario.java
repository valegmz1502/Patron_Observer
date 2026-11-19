package co.sena.mercasena.observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Sujeto observado. */
public class Inventario {

    private final Map<String, Integer> stock = new HashMap<>();
    private final List<ObservadorStock> observadores = new ArrayList<>();

    public void suscribir(ObservadorStock observador) {
        if (!observadores.contains(observador)) {
            observadores.add(observador);
        }
    }

    public void desuscribir(ObservadorStock observador) {
        observadores.remove(observador);
    }

    public int stock(String sku) {
        return stock.getOrDefault(sku, 0);
    }

    public void actualizarStock(String sku, int cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        stock.put(sku, cantidad);
        notificar(sku);
    }

    public int cantidadObservadores() {
        return observadores.size();
    }

    private void notificar(String sku) {
        for (ObservadorStock observador : new ArrayList<>(observadores)) {
            observador.actualizar(sku, this);
        }
    }
}
