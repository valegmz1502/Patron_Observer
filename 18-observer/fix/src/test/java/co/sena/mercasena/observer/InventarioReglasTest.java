package co.sena.mercasena.observer;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Pruebas nuevas: demuestran los errores de las reglas 1, 4 y 2 y comprueban las reglas 3 y 5. */
class InventarioReglasTest {

    @Test
    void observadorVeElStockNuevo() {
        Inventario inv = new Inventario();
        int[] visto = {-1};
        inv.suscribir((sku, inventario) -> visto[0] = inventario.stock(sku));
        inv.actualizarStock("CAFE", 10);
        inv.actualizarStock("CAFE", 5);
        assertEquals(5, visto[0]);
    }

    @Test
    void suscribirDosVecesNoDuplica() {
        Inventario inv = new Inventario();
        AlertaStockBajo alerta = new AlertaStockBajo(5);
        inv.suscribir(alerta);
        inv.suscribir(alerta);
        inv.actualizarStock("CAFE", 3);
        assertEquals(1, alerta.getAlertas().size());
        assertEquals(1, inv.cantidadObservadores());
    }

    @Test
    void todosRecibenAvisoAunqueUnoSeDesuscriba() {
        Inventario inv = new Inventario();
        int[] avisos = {0};
        ObservadorStock[] primero = new ObservadorStock[1];
        primero[0] = (sku, i) -> i.desuscribir(primero[0]);
        inv.suscribir(primero[0]);
        inv.suscribir((sku, i) -> avisos[0]++);
        inv.actualizarStock("CAFE", 1);
        assertEquals(1, avisos[0]);
    }

    @Test
    void avisoUnaSolaVezYSeDesuscribe() {
        Inventario inv = new Inventario();
        AvisoDisponibilidad aviso = new AvisoDisponibilidad("CAFE", "Juan");
        inv.suscribir(aviso);
        inv.actualizarStock("CAFE", 4);
        inv.actualizarStock("CAFE", 6);
        assertEquals(1, aviso.getAvisos().size());
        assertEquals(0, inv.cantidadObservadores());
    }

    @Test
    void alertaEnElUmbral() {
        Inventario inv = new Inventario();
        AlertaStockBajo alerta = new AlertaStockBajo(5);
        inv.suscribir(alerta);
        inv.actualizarStock("CAFE", 6);
        assertEquals(0, alerta.getAlertas().size());
        inv.actualizarStock("CAFE", 5);
        assertEquals(1, alerta.getAlertas().size());
        inv.actualizarStock("CAFE", 4);
        assertEquals(2, alerta.getAlertas().size());
    }

    @Test
    void avisoDisponibilidadAvisaUnaSolaVez() {
        Inventario inv = new Inventario();
        AvisoDisponibilidad aviso = new AvisoDisponibilidad("CAFE", "Juan");
        inv.suscribir(aviso);
        inv.actualizarStock("CAFE", 5);
        inv.actualizarStock("CAFE", 8);
        assertEquals(1, aviso.getAvisos().size());
        assertEquals(0, inv.cantidadObservadores());
    }

    @Test
    void alertaConStockEnElUmbralYPorEncima() {
        Inventario inv = new Inventario();
        AlertaStockBajo alerta = new AlertaStockBajo(5);
        inv.suscribir(alerta);
        inv.actualizarStock("CAFE", 5);
        inv.actualizarStock("PANELA", 6);
        inv.actualizarStock("SAL", 4);
        assertEquals(List.of("CAFE", "SAL"), alerta.getAlertas());
    }
}
