package co.sena.mercasena.observer;

/** Observador (modelo pull: consulta el inventario al ser notificado). */
public interface ObservadorStock {

    void actualizar(String sku, Inventario inventario);
}
