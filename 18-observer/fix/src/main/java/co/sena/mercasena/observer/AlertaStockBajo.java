package co.sena.mercasena.observer;

import java.util.ArrayList;
import java.util.List;

public class AlertaStockBajo implements ObservadorStock {

    private final int umbral;
    private final List<String> alertas = new ArrayList<>();

    public AlertaStockBajo(int umbral) {
        this.umbral = umbral;
    }

    @Override
    public void actualizar(String sku, Inventario inventario) {
        if (inventario.stock(sku) <= umbral) {
            alertas.add(sku);
        }
    }

    public List<String> getAlertas() {
        return List.copyOf(alertas);
    }
}
