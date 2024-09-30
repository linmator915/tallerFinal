Diagrama de Secuencia
![image](https://github.com/user-attachments/assets/d8e32461-755a-4ec4-8a07-00c55315596a)

Implementación
Para el desarrollo de este taller se puso en práctica el uso de springboot con webflux. Para la BD se usó postgress, creando la BD y tablas en PGAdmin 4.
Se usa como archivo de configuración un .yml donde se definen los datos de conexión a la BD.

-Controladores
Se crean 4 controladores:
Cashouts: Se mapean las funcionalidades para obtener los cashouts por id de usuario (GET) y la creación de cashouts (POST). En el get se envía como parámetro el id del usuario que va en la url y en la creación se envía como parámetro un requestbody de tipo Cashout.
User: Se mapean las funcionlidades para crear usuario (POST), consultar por id (GET) y actualizar balance(PUT)
Global Handler: Manejo de errores
TransactionHistoryMsController: Se mapea la funcionalidad para consultar las transacciones de un usuario (GET)

-Dominio
Se crean 2 dominios:
Cashout: se definen los parámetros id, userId y amount con sus respectivos getters and setters. Se define como tabla "cashouts"
User: se definen los parámetros userId, nombre y balance con sus respectivos getters and setters. Se define como tabla "users"

-Repositorios
Se crean los repositorios por cada entidad del dominio (Cashout y User)

-Excepciones
Se crean varias clases para el manejo de excepciones dentro de la aplicación

-Servicios
Se crean 3 servicios y 1 interfaz:
Cashout: Se definen los métodos crearCashout y obtenerCashoutsPorUser en los cuales se implementan las funciones para guardar cashout y consultar los cashouts de un usuario.
User: Se definen los métodos crearUser, ObtenerPorId, ValidarBalance y updateBalance para guardar un usuario en BD, validar si se puede realizar exitosamente un cashout de acuerdo al monto del balance que tiene y actualizar el balance del usuario de acuerdo a la validación anterior.
PaymentMicroservice: Se define una función hacerPayment la cual realiza el pago y retorna un estado Approved si el consumo del servicio es exitoso o Rejected si este falla.
IPaymentRestUser: Se define el metodo guardarPayment (POST) el cual recibe un requestbody desde la clase de servicio Payment.

-Test
Se implementan pruebas unitarias para las clases Service y controllers.
De igual manera, se realizaron pruebas desde postman realizando consumos de los endpoints definidos de acuerdo a lo solicitado en el taller.

![image](https://github.com/user-attachments/assets/29ec29b3-317d-4030-a12f-5fe7f52b8509)
![image](https://github.com/user-attachments/assets/25765dc4-1285-4eff-a873-c58784b18c7b)
![image](https://github.com/user-attachments/assets/72c47ec8-8bb4-46ff-b480-dea812b7ae03)




