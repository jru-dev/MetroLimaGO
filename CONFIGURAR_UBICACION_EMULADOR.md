# Configurar Ubicación en el Emulador de Android Studio

## Método 1: Configurar ubicación desde Android Studio (Recomendado)

1. **Abre el emulador** y ejecuta la app.

2. **En la barra de herramientas del emulador**, busca el ícono de **"..." (tres puntos)** o el menú de **Extended Controls**.

3. **Ve a "Location"** (Ubicación) en el menú lateral.

4. **Configura las coordenadas de Lima, Perú:**
   - **Latitud:** `-12.0464`
   - **Longitud:** `-77.0428`
   - O usa el botón "Set Location" y busca "Lima, Peru"

5. **Haz clic en "Set Location"** para aplicar los cambios.

6. **Vuelve a la app** y la ubicación debería actualizarse automáticamente.

## Método 2: Usar el botón en la app

1. En la pantalla del mapa, hay un botón con el ícono de ubicación en la barra superior (primer botón a la derecha).

2. **Haz clic en ese botón** para establecer manualmente la ubicación de Lima.

3. El mapa se centrará automáticamente en Lima, Perú.

## Método 3: Usar ADB (Línea de comandos)

Si prefieres usar la línea de comandos:

```bash
# Establecer ubicación de Lima, Perú
adb emu geo fix -77.0428 -12.0464
```

O si estás en PowerShell (Windows):

```powershell
adb emu geo fix -77.0428 -12.0464
```

**Nota:** El formato es `longitud latitud` (al revés de lo normal).

## Método 4: Simular movimiento (Opcional)

Para simular movimiento desde Lima hacia otra estación:

```bash
# Primero establecer Lima
adb emu geo fix -77.0428 -12.0464

# Luego puedes mover a otra ubicación
adb emu geo fix -77.0400 -12.0300
```

## Verificar que funciona

1. Abre la app del mapa.
2. Deberías ver tu ubicación (marcador azul) en Lima, Perú.
3. Las estaciones cercanas deberían aparecer ordenadas por distancia.
4. El mapa debería centrarse en Lima.

## Troubleshooting

- **Si la ubicación no se actualiza:** Cierra y vuelve a abrir la app.
- **Si aparece en otra región:** Verifica que las coordenadas sean correctas (-12.0464, -77.0428).
- **Si el GPS no funciona:** Verifica que los permisos de ubicación estén concedidos en la app.

