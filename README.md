# 📘 Proyecto ONG – Hibernate + MySQL 
    (Actividad 3, Unidad 3)

Este proyecto corresponde a la Actividad 3 de la Unidad 3 de Acceso a Datos, donde se desarrolla una pequeña aplicación Java utilizando Hibernate para mapear la tabla clientesong de una base de datos MySQL llamada ONG.

El objetivo principal es:

Crear la entidad ClienteONG mediante anotaciones.

Crear la tabla correspondiente en MySQL.

Configurar Hibernate para gestionar la persistencia.

Probar la inserción de datos desde Java.

## 🔧 Tecnologías utilizadas
Herramienta	Uso
````
Java 24	Lenguaje de programación
Hibernate ORM 7.1.7	Mapeo objeto-relacional
MySQL Server 8.0.44	Gestor de base de datos
MySQL Connector/J 9.5.0	Conector JDBC
IntelliJ IDEA Ultimate	Entorno de desarrollo
Maven	Gestión automática de dependencias
````

## 📂 Estructura del proyecto
````
ONG/
│
├─ src/
│  ├─ main/
│  │   ├─ java/
│  │   │    └─ org/ong/
│  │   │          ├─ ClienteONG.java
│  │   │          └─ Main.java
│  │   └─ resources/
│  │          └─ hibernate.cfg.xml
│  └─ test/
│
├─ pom.xml
└─ README.md
````

## 🗄️ Base de datos MySQL

Base de datos:
````sql
CREATE DATABASE ONG;
USE ONG;

CREATE TABLE clientesong (
    idorganizacion INT AUTO_INCREMENT PRIMARY KEY,
    nombreorganizacion VARCHAR(100) NOT NULL,
    paisorganizacion VARCHAR(100) NOT NULL,
    tiposorganizacion VARCHAR(100) NOT NULL
);
````

## 🧩 Entidad Java (ClienteONG.java)

La clase está mapeada mediante anotaciones JPA, cumpliendo lo pedido en el enunciado:
````java
@Entity
@Table(name = "clientesong")

public class ClienteONG {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idorganizacion")
    private int idorganizacion;

    @Column(name = "nombreorganizacion")
    private String nombreorganizacion;

    @Column(name = "paisorganizacion")
    private String paisorganizacion;

    @Column(name = "tiposorganizacion")
    private String tiposorganizacion;

    public ClienteONG() {}

    public ClienteONG(String nombreorganizacion, String paisorganizacion, String tiposorganizacion) {
        this.nombreorganizacion = nombreorganizacion;
        this.paisorganizacion = paisorganizacion;
        this.tiposorganizacion = tiposorganizacion;
    }

    // Getters y setters…

    @Override
    public String toString() {
        return "ClienteONG{" +
                "idorganizacion=" + idorganizacion +
                ", nombreorganizacion='" + nombreorganizacion + '\'' +
                ", paisorganizacion='" + paisorganizacion + '\'' +
                ", tiposorganizacion='" + tiposorganizacion + '\'' +
                '}';
    }
}
````
## ⚙️ Configuración de Hibernate (hibernate.cfg.xml)

Archivo ubicado en src/main/resources:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">

<hibernate-configuration>
    <session-factory>

        <property name="hibernate.connection.driver_class">com.mysql.cj.jdbc.Driver</property>
        <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/ONG?useSSL=false&amp;serverTimezone=UTC</property>
        <property name="hibernate.connection.username">root</property>
        <property name="hibernate.connection.password">TU_PASSWORD</property>

        <property name="hibernate.dialect">org.hibernate.dialect.MySQLDialect</property>
        <property name="connection.pool_size">1</property>
        <property name="show_sql">true</property>
        <property name="hibernate.hbm2ddl.auto">update</property>

        <mapping class="org.ong.ClienteONG"/>
    </session-factory>
</hibernate-configuration>
```
## 🚀 Clase Main – Inserción de datos
````java
public class Main {
public static void main(String[] args) {

        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");

        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();

        Transaction tx = session.beginTransaction();

        ClienteONG c = new ClienteONG(
                "Organización GreenWorld",
                "España",
                "Ambiental"
        );

        session.persist(c);

        tx.commit();
        session.close();
        factory.close();

        System.out.println("Registro insertado correctamente.");
    }
}
````
## 📌 Resultado

Al ejecutar la aplicación:

Hibernate se conecta a MySQL.

Si la tabla no existe, la crea.

Inserta un nuevo registro.

Muestra en consola el SQL generado.

La tabla clientesong contiene el nuevo registro.

## ✍️ Autor

Santiago Lafuente Hernández

2º DAM – Acceso a Datos

(Desarrollo realizado con acompañamiento técnico de ChatGPT)

---
# 📝 Actividad 4: Relación OneToMany entre ClienteONG y PersonaContacto
## 📌 1. Objetivo de la actividad

En esta parte del proyecto se amplía la funcionalidad creada en la actividad anterior. Ahora la ONG necesita gestionar las personas de contacto asociadas a cada organización.
Para ello se implementa una relación OneToMany / ManyToOne entre las tablas:

clientesong (padre)

personascontacto (hijo)

Cada organización puede tener varias personas de contacto.

## 📌 2. Estructura implementada

**✔ Clase padre: ClienteONG**

Se añadió un atributo nuevo:
````java
@OneToMany(mappedBy = "clienteONG", cascade = CascadeType.ALL)
private List<PersonaContacto> personasContacto = new ArrayList<>();
````

Este campo representa todas las personas de contacto asociadas al cliente.

También se añadió un método para poder vincular contactos:
````java
public void addPersonaContacto(PersonaContacto persona) {
personasContacto.add(persona);
p.setClienteONG(this);
}
````

Esto garantiza que la relación se actualiza en ambos sentidos (padre e hijo).

**✔ Clase hija: PersonaContacto**

Se creó una clase nueva con sus campos:

idorganizacion

nombre

telefono

Y su relación con la clase padre:
````java
@ManyToOne
@JoinColumn(name = "idorganizacion")
private ClienteONG clienteONG;
````

Esto le dice a Hibernate que cada persona de contacto pertenece a una organización.

## 📌 3. Fichero de configuración hibernate.cfg.xml

Se añadió la nueva clase mapeada:
```xml
<mapping class="org.ong.PersonaContacto"/>
```
De esta forma Hibernate puede generar o actualizar la tabla personascontacto.

## 📌 4. Pruebas realizadas en Main.java

Se validó la relación creando varias personas de contacto y asociándolas a la misma organización:
````java
PersonaContacto p1 = new PersonaContacto("Juan", "666111222");
PersonaContacto p2 = new PersonaContacto("Marta", "666333444");

cliente.addPersonaContacto(p1);
cliente.addPersonaContacto(p2);

session.save(cliente);
transaction.commit();
````

Hibernate generó correctamente:

la tabla personascontacto

la columna idorganizacion como clave ajena

los registros insertados asociados a la ONG correspondiente

## 📌 5. Resultado final

El sistema ya soporta:

Registrar organizaciones (clientes ONG)

Registrar personas de contacto

Asociar múltiples contactos a una misma ONG

Guardar todo en la base de datos mediante Hibernate

Esta estructura servirá como base para ampliar el proyecto con nuevas funcionalidades, como consultas más complejas o integración con interfaces de usuario.

## ‍🎓 Autor: 

Santiago Lafuente Hernández

2º DAM – Acceso a Datos

---
