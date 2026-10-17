# Guía de pruebas unitarias

Las pruebas del servicio de clientes usan JUnit 5 y Mockito. Cada prueba
simula el repositorio, por lo que no requiere una base de datos externa.

## Ejecución completa

Desde la carpeta `HotelSpringBoot`:

```powershell
.\mvnw.cmd test
```

## Ejecución de una prueba específica

Para ejecutar un método individual se puede usar el selector `-Dtest`:

```powershell
.\mvnw.cmd "-Dtest=ClienteServiceTest#debeObtenerClientePorId" test
```

## Alcance

Las pruebas de `ClienteService` cubren:

- creación y persistencia de clientes;
- consulta de todos los clientes;
- respuesta cuando no hay resultados;
- consulta por identificador;
- excepción cuando el cliente no existe.

Las pruebas unitarias deben mantenerse aisladas del entorno y validar una
regla de negocio por caso. Las pruebas que requieran base de datos o servicios
externos deben ubicarse en una suite de integración separada.
