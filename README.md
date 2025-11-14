📘 Proyecto ONG – Hibernate + MySQL (Actividad 3, Unidad 3)

Este proyecto corresponde a la Actividad 3 de la Unidad 3 de Acceso a Datos, donde se desarrolla una pequeña aplicación Java utilizando Hibernate para mapear la tabla clientesong de una base de datos MySQL llamada ONG.

El objetivo principal es:

Crear la entidad ClienteONG mediante anotaciones.

Crear la tabla correspondiente en MySQL.

Configurar Hibernate para gestionar la persistencia.

Probar la inserción de datos desde Java.

🔧 Tecnologías utilizadas
Herramienta	Uso
Java 24	Lenguaje de programación
Hibernate ORM 7.1.7	Mapeo objeto-relacional
MySQL Server 8.0.44	Gestor de base de datos
MySQL Connector/J 9.5.0	Conector JDBC
IntelliJ IDEA Ultimate	Entorno de desarrollo
Maven	Gestión automática de dependencias
📂 Estructura del proyecto
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

🗄️ Base de datos MySQL
Base de datos:
CREATE DATABASE ONG;
USE ONG;

Tabla creada automáticamente por Hibernate:

clientesong con los campos:

idorganizacion (PK, auto-increment)

nombreorganizacion

paisorganizacion

tiposorganizacion

Hibernate genera la tabla gracias a la propiedad:

<property name="hibernate.hbm2ddl.auto">update</property>

🧩 Entidad Java (ClienteONG.java)

La clase está mapeada mediante anotaciones JPA, cumpliendo lo pedido en el enunciado:

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

⚙️ Configuración de Hibernate (hibernate.cfg.xml)

Archivo ubicado en src/main/resources:

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

🚀 Clase Main – Inserción de datos
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

📌 Resultado

Al ejecutar la aplicación:

Hibernate se conecta a MySQL.

Si la tabla no existe, la crea.

Inserta un nuevo registro.

Muestra en consola el SQL generado.

La tabla clientesong contiene el nuevo registro.

📄 Documentación extra entregada

Archivo Aclaración.pdf incluido en el ZIP del ejercicio:

Explicación breve sobre el patrón MVC.

Explicación del desfase objeto-relacional.

Diagrama simple en PDF.

Justificación del uso de Hibernate.

Capturas solicitadas por la actividad (cuando se pidan).

✍️ Autor

Santiago Lafuente Hernández
2º DAM – Acceso a Datos
(Desarrollo realizado con acompañamiento técnico de ChatGPT)