@echo off
echo ========================================
echo    MetroLima GO - Script de Compilacion
echo ========================================
echo.

echo 1. Limpiando proyecto...
call gradlew clean
if %errorlevel% neq 0 (
    echo ERROR: Fallo en la limpieza del proyecto
    pause
    exit /b 1
)

echo.
echo 2. Sincronizando dependencias...
call gradlew build --refresh-dependencies
if %errorlevel% neq 0 (
    echo ERROR: Fallo en la sincronizacion de dependencias
    pause
    exit /b 1
)

echo.
echo 3. Compilando proyecto...
call gradlew assembleDebug
if %errorlevel% neq 0 (
    echo ERROR: Fallo en la compilacion
    pause
    exit /b 1
)

echo.
echo ========================================
echo    ¡COMPILACION EXITOSA!
echo ========================================
echo.
echo ✅ El proyecto se compiló sin errores
echo 📱 La aplicación está lista para ejecutar
echo 🚇 MetroLima GO - Sistema de Metro de Lima
echo.
echo Características implementadas:
echo - ✅ Navegación entre pantallas
echo - ✅ Lista de estaciones con búsqueda
echo - ✅ Detalles de estaciones
echo - ✅ Planificador de rutas
echo - ✅ Alertas del metro (con base de datos)
echo - ✅ Configuración de usuario
echo - ✅ Base de datos local (Room)
echo - ✅ Arquitectura MVVM
echo.
echo El APK se ha generado en: app\build\outputs\apk\debug\
echo.
echo Para ejecutar en Android Studio:
echo 1. Abre Android Studio
echo 2. Sincroniza el proyecto (File -> Sync Project with Gradle Files)
echo 3. Ejecuta la aplicacion (Run -> Run 'app')
echo.
pause
