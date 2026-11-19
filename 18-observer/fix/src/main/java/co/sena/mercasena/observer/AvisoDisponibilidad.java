package co.sena.mercasena.observer;

import java.util.ArrayList;
import java.util.List;

/** "Avísame cuando llegue": avisa una vez y se desuscribe. */
public class AvisoDisponibilidad implements ObservadorStock {

    private final String skuEsperado;
    private final String cliente;
    private final List<String> avisos = new ArrayList<>();

    public AvisoDisponibilidad(String skuEsperado, String cliente) {
        this.skuEsperado = skuEsperado;
        this.cliente = cliente;
    }

    @Override
    public void actualizar(String sku, Inventario inventario) {
        if (sku.equals(skuEsperado) && inventario.stock(sku) > 0) {
            avisos.add(cliente + ": ya llegó " + sku);
            inventario.desuscribir(this);
        }
    }

    public List<String> getAvisos() {
        return List.copyOf(avisos);
    }
}
