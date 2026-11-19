package co.sena.mercasena.observer;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventarioTest {

    @Test
    void alertaCuandoElStockBaja() {
        Inventario inv = new Inventario();
        inv.actualizarStock("CAFE", 20);
        AlertaStockBajo alerta = new AlertaStockBajo(5);
        inv.suscribir(alerta);
        inv.actualizarStock("CAFE", 3);
        assertEquals(List.of("CAFE"), alerta.getAlertas());
    }

    @Test
    void laTiendaMarcaYDesmarcaAgotados() {
        Inventario inv = new Inventario();
        PublicadorTienda tienda = new PublicadorTienda();
        inv.suscribir(tienda);
        inv.actualizarStock("PANELA", 0);
        assertTrue(tienda.estaAgotado("PANELA"));
        inv.actualizarStock("PANELA", 10);
        assertFalse(tienda.estaAgotado("PANELA"));
    }

    @Test
    void desuscribirDejaDeNotificar() {
        Inventario inv = new Inventario();
        AlertaStockBajo alerta = new AlertaStockBajo(5);
        inv.suscribir(alerta);
        inv.desuscribir(alerta);
        inv.actualizarStock("CAFE", 1);
        assertTrue(alerta.getAlertas().isEmpty());
    }

    @Test
    void stockNegativo() {
        assertThrows(IllegalArgumentException.class, () -> new Inventario().actualizarStock("CAFE", -1));
    }
}
