# SosafeApp
test SOSAFE

Test para SOSAFE
Este proyecto está encaminado para presentar una prueba de la empresa SOSAFE y presentarme como desarrollador Android Junior.
Como tal la aplicación realizada consta de 4 vistas.
1 - Ubica POIs en un mapa
2 - Gestiona POIs guardados previamente como favoritos
3 - Muestra los POIs en una vista detallada ya sea dependiendo de si están en db local o desde petición.
4 - Vista de opciones (falta su desarrollo)

Como tal el proyecto está desarollado con estándares de MVVM (Es como lo he implementado siempre, sé que no es lo más óptimo, pero mejorará),
 sin cleanArchitecture (lo aprenderé pronto), algunos estándares de material desing, retrofit + coroutines, live data, soporte de lenguajes, 
 google maps y room.

Comenzando 🚀
Para obtener el proyecto simplemente descargalo o clonalo desde mi repositorio público mi Username es: JavierSantiagoS

Pre-requisitos 📋
- Android Studio
- Emulador con servicio de localización e internet

Da un ejemplo
Instalación 🔧
- Primero abre el proyecto en tu IDE
- Acto seguido ejecutalo en un emulador android (Android >= 5.1)
- Una vez la app instalada en tu emulador procede a crear una localización desde el menú de configuraciones del emulador.
- Acepta los permisos de localización (Si no lo haces no podrás geolocalizar y no te permitirá usar la app correctamente)
- Un vez aceptes todos los permisos puedes comenzar por la vista de Map (Ya estarás en ella una vez ingreses a la app)
- En el buscador escribe una palabra clave para los puntos de interés, por ejemplo: restaurant y preciona la tecla para buscar
- Una vez el consumo esté realizado tendrás todos los POIs desplegados en el mapa referente a la palabra que escribiste
- Cuando selecciones un POI, verás una pantalla desplegando su información y su correcta localización, más los comentarios referentes a ese lugar,
 asimismo podrás guardar ese lugar como favorito para que te sea más fácil volver a él cuando lo requieras
 - Ahora que tenemos un lugar guardado como favorito podremos proceder a utilizar la vista de Favorites presionando en el segundo ícono de la barra de navegación
 - Acá podremos ver POI previamente guardado y lo prodremos borrar también si así lo deseamos, pero por ahora clickeemos en él para observar sus datos de nuevo,
 pero esta vez cargados desde tu base de datos
 - Ahora volvamos al mapa y agreguemos una nueva ubicación como favorita, tratemos de usar una nueva palabra para buscar: School
 - Seleccionamos un marker y lo guardaremos como favorito, ahora entramos de nuevo a Favorites y borramos el primero, vemos como se actualiza la lista
 - Una vez actualizada volvemos al mapa y presionaremos el mismo POI que guardamos al último, notaremos que la db está actuando ya que ese POI está como 
 favorito inclusive desde el map
 - La aplicación tiene soporte para lenguajes: español e inglés (Para cambiarlo desde la app estará en el menú opciones)
 - Actualimente el menú de opciones se encuentra vacío, pero se agregará su funcionalidad en poco tiempo.

 - !Usala para ver qué lugares cercanos te gustan más!


Construido con 🛠️
Android Studio


Retrofit - Consumo REST
Maps - Google maps
Coroutines - Manejo de hilos
MVVM - Arquitectura
Glide - Gestión de imagenes
LiveData - observers y data in real time
Room - db Local

Autor ✒️
Javier Santiago Salazar

