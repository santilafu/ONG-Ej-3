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
# 📘 Actividad 6 – Gestión de datos de la ONG "Salvemos la Tierra" con Hibernate

En esta actividad se amplía el proyecto ONG desarrollado anteriormente, utilizando Hibernate para cargar, almacenar y modificar datos relacionados con organizaciones y sus personas de contacto.

El objetivo es demostrar el uso de:

- SessionFactory

- Persistencia con entidades JPA

- Relaciones OneToMany / ManyToOne

- Insertar registros

- Modificar registros existentes

- Confirmar transacciones

## 📂 1. Estructura del proyecto
````
ONG/
├── src/
│   ├── main/java/org/ong/
│   │     ├── ClienteONG.java
│   │     ├── PersonaContacto.java
│   │     └── Main.java
│   └── main/resources/
│         └── hibernate.cfg.xml
└── pom.xml
````
## 🧱 2. Entidades utilizadas
- ClienteONG

Representa a cada organización con sus datos básicos:

- idorganizacion (PK)

- nombreorganizacion

- paisorganizacion

- tiposorganizacion

Define una relación:
````java
@OneToMany(mappedBy = "clienteONG")
````

- Que indica que una organización puede tener muchas personas de contacto.

  - PersonaContacto

  - Representa a cada persona vinculada a una organización.

- Campos:

  -   idcontacto (PK)

  - nombre

  - telefono

- Relación:
````java
@ManyToOne
@JoinColumn(name = "idorganizacion")
````

Que enlaza cada contacto con su organización correspondiente.

## ⚙️ 3. Configuración de Hibernate (hibernate.cfg.xml)

Se utiliza la misma configuración de la actividad anterior.
Hibernate se conecta a la BBDD ONG, gestiona las tablas y aplica cambios automáticamente:
````xml
<property name="hibernate.hbm2ddl.auto">update</property>
````

Esto permite que las tablas se creen o actualicen según las entidades.

## 🧠 4. Objetivo de la actividad

Se deben:
````java
✔️ Insertar en la tabla clientesong:
id	nombreorganizacion	paisorganizacion	tiposorganizacion
1	Salvemos la infancia	España	privada
2	Salvemos a los ancianos	Italia	privada
✔️ Insertar en personacontacto:
id	idorganizacion	nombre	telefono
1	1	Diana Ruiz	1254892
2	1	Laura Montelli	54547
✔️ Modificar la organización número 2:
"Salvemos a los ancianos"  →  "Salvemos a los mayores"
````

## 📊 5. Resultado esperado en MySQL
````sql
Tabla clientesong
idorganizacion	nombreorganizacion	paisorganizacion	tiposorganizacion
1	Salvemos la infancia	España	privada
2	Salvemos a los mayores	Italia	privada
Tabla personacontacto
idcontacto	idorganizacion	nombre	telefono
1	1	Diana Ruiz	1254892
2	1	Laura Montelli	54547
````
## 📎 6. Conclusión

Esta actividad demuestra:

Inserción y modificación de datos con Hibernate

Manejo de relaciones entre entidades

Correcta gestión de transacciones (beginTransaction, commit, merge)

Funcionamiento completo de la capa de persistencia en una aplicación Java

---

# 📝 Actividad: Consulta HQL embebida en Hibernate
Gestión de organizaciones y contactos (ONG Salvemos la Tierra)

Este ejercicio consiste en realizar una consulta HQL (Hibernate Query Language) embebida dentro del código Java para obtener los detalles de una organización concreta y todas sus personas de contacto asociadas.

El objetivo es:

1. Crear una sesión de Hibernate.

2. Construir una consulta HQL para obtener datos de varias entidades relacionadas.

3. Ejecutar la consulta y recuperar los resultados.

4. Recorrer los datos y mostrarlos por pantalla.

## 📌 1. Preparación del entorno

Para esta actividad ya teníamos configurados:

- hibernate.cfg.xml

- Las clases de entidad:

  - ClienteONG (padre)

  - PersonaContacto (hijo)

- Relación OneToMany / ManyToOne entre ambas entidades.

Hibernate se encarga de mapear las relaciones y recuperar automáticamente la lista de contactos asociados a una organización.

## 📌 2. ¿Qué es una consulta HQL embebida?

Es una consulta escrita con el lenguaje propio de Hibernate, muy parecido a SQL pero trabajando con clases y objetos, no con tablas.

Ejemplo:
````hql
"from ClienteONG where nombreOrganizacion = :nombre"
````

No usamos nombres de tablas, sino nombres de clases.

La consulta “embebida” significa que va escrita directamente dentro del código Java, no en un XML ni en un archivo externo.

## 📌 3. Consulta HQL para obtener una organización y sus contactos

Queremos obtener los datos de:

**"Salvemos la Infancia" ubicada en España**

Y también cargar sus personas de contacto asociadas.

## 📌 4. Código completo del Main con comentarios

````java
package org.ong;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.List;

public class Main {
public static void main(String[] args) {

        // 1. Cargar configuración y crear SessionFactory
        Configuration config = new Configuration().configure();
        SessionFactory sessionFactory = config.buildSessionFactory();

        // 2. Abrir sesión
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        // 3. Consulta HQL embebida
        // Buscamos la organización "Salvemos la Infancia" de España.
        // Se usa HQL, trabajando con la clase ClienteONG en lugar de tablas SQL.
        List<ClienteONG> resultados = session.createQuery(
                "FROM ClienteONG WHERE nombreOrganizacion = :nombre AND paisOrganizacion = :pais",
                ClienteONG.class
        )
        .setParameter("nombre", "Salvemos la Infancia")
        .setParameter("pais", "España")
        .getResultList();

        // 4. Mostrar resultados por pantalla
        for (ClienteONG org : resultados) {

            // Información de la organización
            System.out.println("Organización encontrada:");
            System.out.println(" - ID: " + org.getIdOrganizacion());
            System.out.println(" - Nombre: " + org.getNombreOrganizacion());
            System.out.println(" - País: " + org.getPaisOrganizacion());
            System.out.println(" - Tipo: " + org.getTiposOrganizacion());

            // Personas asociadas
            System.out.println("   Personas de contacto:");
            for (PersonaContacto pc : org.getPersonasContacto()) {
                System.out.println("      * " + pc.getNombre() + " | Tel: " + pc.getTelefono());
            }
        }

        // 5. Finalizar transacción y cerrar sesión
        tx.commit();
        session.close();
        sessionFactory.close();
    }
}
`````
## 📌 5. Resultado esperado

Al ejecutar el programa, Hibernate mostrará:

- La organización encontrada.

- Sus datos.

- La lista completa de personas que tiene asociadas.

Ejemplo:
```hql
Organización encontrada:
- ID: 2
- Nombre: Salvemos la Infancia
- País: España
- Tipo: Privada
  Personas de contacto:
    * Diana Ruiz | Tel: 1254892
    * Laura Montelli | Tel: 54547
```
## 👨‍🎓 Autor
- Santiago Lafuente Hernández
- 2º DAM – Acceso a Datos
- (Desarrollo realizado con acompañamiento técnico de ChatGPT)