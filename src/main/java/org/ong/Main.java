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
        //Iniciamos una transacción
        Transaction tx = session.beginTransaction();//NO escribe nada en la base de datos hasta que no hagamos commit
        //Creamos un nuevo cliente ONG
        ClienteONG c = new ClienteONG("Greenpeace", "Internacional", "Medio Ambiente");// No ponemos el id porque es autogenerado.
        session.persist(c); // Guardamos el cliente ONG en la base de datos
        tx.commit(); // Hacemos commit para que se escriba en la base de datos
        session.close(); // Cerramos la sesion
        sessionFactory.close(); // Cerramos la fábrica de sesiones
    }
}
