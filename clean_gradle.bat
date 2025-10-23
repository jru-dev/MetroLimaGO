@echo off
echo Limpiando cache de Gradle...

echo.
echo 1. Deteniendo daemons de Gradle...
gradlew --stop

echo.
echo 2. Limpiando cache de Gradle...
gradlew clean

echo.
echo 3. Eliminando directorio .gradle...
if exist .gradle rmdir /s /q .gradle

echo.
echo 4. Eliminando directorio build...
if exist build rmdir /s /q build

echo.
echo 5. Eliminando directorio app\build...
if exist app\build rmdir /s /q app\build

echo.
echo 6. Reconstruyendo proyecto...
gradlew build

echo.
echo ¡Limpieza completada! Ahora sincroniza el proyecto en Android Studio.
pause
