package co.sena.mercasena.observer;

import java.util.HashSet;
import java.util.Set;

/** Marca en la tienda web los productos agotados. */
public class PublicadorTienda implements ObservadorStock {

    private final Set<String> agotados = new HashSet<>();

    @Override
    public void actualizar(String sku, Inventario inventario) {
        if (inventario.stock(sku) == 0) {
            agotados.add(sku);
        } else {
            agotados.remove(sku);
        }
    }

    public boolean estaAgotado(String sku) {
        return agotados.contains(sku);
    }
}
