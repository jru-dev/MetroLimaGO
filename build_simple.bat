@echo off
echo ========================================
echo    MetroLima GO - Compilacion Simple
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
echo 2. Compilando sin Hilt (version simplificada)...
call gradlew assembleDebug -x hiltAggregateDepsDebug -x kspDebugKotlin
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
echo NOTA: Esta es una versión simplificada sin Hilt
echo Para usar todas las funcionalidades, necesitas resolver
echo el problema de compatibilidad de versiones.
echo.
echo El APK se ha generado en: app\build\outputs\apk\debug\
echo.
pause
