# MercaSENA · Patrón Observer (Comportamiento)

**Dominio:** Alertas automáticas cuando cambia el stock

## Contexto

Cuando cambia el stock de un producto en MercaSENA, varias partes del sistema deben enterarse: la **alerta de stock bajo** para compras, la **tienda web** (que marca productos agotados) y los clientes que pidieron **"avísame cuando llegue"**. El inventario no debería conocer a cada una, así que se usa **Observer**: los interesados se suscriben y el inventario los notifica.

Clases principales:

- `Inventario`: sujeto.
- `ObservadorStock`: interfaz de los observadores. Usa el modelo *pull*: el observador consulta al inventario el stock actual.
- `AlertaStockBajo`, `PublicadorTienda` y `AvisoDisponibilidad`: observadores concretos.

## Reglas de negocio

1. Cuando se actualiza el stock, los observadores deben ver el **valor nuevo** al consultar el inventario.
2. **Todos** los observadores suscritos reciben la notificación, aunque alguno se desuscriba durante la notificación.
3. `AvisoDisponibilidad` avisa **una sola vez**: cuando el stock vuelve a ser mayor que 0, registra el aviso y **se desuscribe a sí mismo**.
4. Suscribir dos veces el mismo observador no genera notificaciones duplicadas.
5. `AlertaStockBajo` registra el SKU cuando el stock queda en el umbral o por debajo de él.
6. El stock no puede ser negativo (`IllegalArgumentException`).

## Tu misión

Este proyecto implementa el patrón **Observer** dentro de la tienda en línea **MercaSENA**. El código *compila*, pero tiene **errores sembrados**: algunos los detectan las pruebas que ya existen y otros **no**. Tu trabajo es encontrarlos todos, demostrarlos con pruebas y corregirlos con ayuda de la IA, sin perder el control técnico.

> La fuente de verdad son las **reglas de negocio** de este README. La IA no las conoce si no se las das.

## Protocolo de trabajo (obligatorio)

1. **Prepara el repositorio.** Crea un repositorio en GitHub con este proyecto y haz el primer commit sin cambios: `git commit -m "chore: estado inicial"`.
2. **Predice sin IA (20 min).** Lee este README y el código. Escribe tus hipótesis en `BITACORA_IA.md`, sección 1.
3. **Ejecuta las pruebas.** Corre `mvn test` o las pruebas desde IntelliJ / Eclipse. Anota cuáles fallan.
4. **Demuestra cada error con una prueba.** Por cada error que encuentres, incluidos los que las pruebas actuales no detectan, escribe primero una prueba que **falle**. Haz commit: `test: demuestra <error>`.
5. **Consulta al jurado de IAs.** Envía el mismo prompt a ChatGPT, Gemini y DeepSeek. Usa el prompt de 5 piezas: contexto, tarea, restricciones, formato y criterio de verificación. Registra en la bitácora en qué coincidieron y en qué no.
6. **Corrige.** Aplica la corrección que **tú** entiendes y puedes explicar. La prueba debe pasar. Haz commit: `fix: corrige <error>`.
7. **Refactoriza con IA.** Pide a la IA una mejora de legibilidad que no cambie el comportamiento. Todas las pruebas deben seguir en verde. Haz commit: `refactor: ...`.
8. **Revisa la seguridad.** Pregúntate qué podría hacer un usuario malicioso con este código y anota tu respuesta en la bitácora.
9. **Prepara la defensa.** El instructor te pedirá explicar, **sin IA**, cualquier línea que hayas cambiado.

## Cómo ejecutar

```bash
mvn test
```

Requisitos: JDK 17 o superior y Maven 3.9 o superior, o el Maven que trae IntelliJ IDEA o Eclipse.

## Qué NO hacer

- No borres ni debilites pruebas para que pasen.
- No pegues el proyecto completo en una IA sin explicarle las reglas de negocio.
- No aceptes una corrección que no sabes explicar.

## Referencia del patrón

- https://refactoring.guru/es/design-patterns/observer
