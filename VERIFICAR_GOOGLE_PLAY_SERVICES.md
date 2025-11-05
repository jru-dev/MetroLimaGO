# Cómo Verificar Google Play Services en el Emulador

## Método 1: Verificar en el Emulador

1. **Abre el emulador**
2. **Busca la aplicación "Play Store"** en el menú de aplicaciones
   - Si NO existe → El emulador NO tiene Google Play Services
   - Si existe → Probablemente tiene Google Play Services

3. **Abre Settings (Configuración)**
   - Ve a **Apps** o **Aplicaciones**
   - Busca **"Google Play Services"**
   - Si aparece → Tiene Google Play Services instalado

## Método 2: Verificar desde Android Studio

1. Abre **Tools > Device Manager**
2. En la lista de dispositivos, verás una columna **"Play"** o **"Google Play"**
   - Si muestra un icono de Play Store → Tiene Google Play Services
   - Si muestra "No Play" → NO tiene Google Play Services

## Método 3: Verificar desde la Terminal/ADB

Abre una terminal en Android Studio y ejecuta:

```bash
adb shell pm list packages | grep -i google
```

Si ves paquetes como:
- `com.google.android.gms` (Google Play Services)
- `com.android.vending` (Google Play Store)

Entonces tu emulador TIENE Google Play Services.

## Solución: Crear Nuevo Emulador con Google Play

1. **Device Manager** → **Create Device**
2. Selecciona un dispositivo (ej: Pixel 5)
3. **IMPORTANTE**: En la selección de sistema, busca imágenes que digan:
   - ✅ **"Google Play"** en el nombre (ej: "Tiramisu API 33" con icono de Play Store)
   - ❌ NO uses las que dicen "API 33" sin el icono de Play Store

4. **Download** la imagen si no la tienes
5. **Finish** y **Start** el emulador
6. La primera vez, espera a que el emulador termine de cargar completamente
7. Abre **Play Store** y verifica que funcione

## Notas Importantes

- **Google Play Services** es necesario para que Google Maps funcione
- Los emuladores con Google Play NO tienen acceso root por defecto
- Si tu emulador actual NO tiene Google Play Services, la mejor opción es crear uno nuevo
- Las versiones más recientes de Android Studio facilitan encontrar imágenes con Google Play

