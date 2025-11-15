package org.ong;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {
    public  static void main(String[] args) {
        Configuration config = new Configuration().configure();// Cargamos la configuracion de hibernate.cfg.xml
        SessionFactory sessionFactory = config.buildSessionFactory();// Creamos la fábrica de sesiones
        Session session = sessionFactory.openSession();// Abrimos una sesion, es como una conexion a la base de datos
        session.beginTransaction();//Iniciamos una transacción
        Transaction tx = session.beginTransaction();//NO escribe nada en la base de datos hasta que no hagamos commit
        //Creamos un nuevo cliente ONG
        // Crear organización
        ClienteONG ong = new ClienteONG("GreenPeace", "España", "Medioambiente");
        ClienteONG ong2 = new ClienteONG("Salvemos la Infancia", "España", "privada");
        ClienteONG ong3 = new ClienteONG("Salvemos a los ancianos", "Italia", "privada");
        // Crear personas de contacto
        PersonaContacto persona1 = new PersonaContacto("Laura Ruiz", "600123456", ong);
        PersonaContacto persona2 = new PersonaContacto("Carlos Soto", "612987654", ong);
        PersonaContacto persona3 = new PersonaContacto("Diana Ruiz", "1254892", ong2);
        PersonaContacto persona4 = new PersonaContacto("Laura Montelli", "54547", ong3);

        // Añadir hijos al padre
        ong.addPersonaContacto(persona1);
        ong.addPersonaContacto(persona2);
        ong2.addPersonaContacto(persona3);
        ong3.addPersonaContacto(persona4);


        // Guardar solo el padre (por cascada se guardan los hijos)
        session.persist(ong);
        session.persist(ong2);
        session.persist(ong3);

        //Modificamos los datos
        ong2.setNombreOrganizacion("Salvemos a los Mayores");// Modificamos el nombre de la organizacion
        session.merge(ong2); // Usamos merge para actualizar la entidad existente

        //Commit y cierre de sesión
        tx.commit(); // Hacemos commit para que se escriba en la base de datos
        session.close(); // Cerramos la sesion
        sessionFactory.close(); // Cerramos la fábrica de sesiones
    }
}
