package org.ong;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        // CONFIGURACIÓN DE HIBERNATE

        // Carga el archivo hibernate.cfg.xml
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        // Abre la sesión (equivale a abrir una conexión a la BBDD)
        Session session = sessionFactory.openSession();

        // Inicia una transacción (nada se escribe hasta commit)
        Transaction tx = session.beginTransaction();

        // INSERTAR ORGANIZACIONES Y CONTACTOS

        // Crear organizaciones
        ClienteONG ong = new ClienteONG("GreenPeace", "España", "Medioambiente");
        ClienteONG ong2 = new ClienteONG("Salvemos la Infancia", "España", "privada");
        ClienteONG ong3 = new ClienteONG("Salvemos a los ancianos", "Italia", "privada");

        // Crear personas de contacto
        PersonaContacto persona1 = new PersonaContacto("Laura Ruiz", "600123456", ong);
        PersonaContacto persona2 = new PersonaContacto("Carlos Soto", "612987654", ong);
        PersonaContacto persona3 = new PersonaContacto("Diana Ruiz", "1254892", ong2);
        PersonaContacto persona4 = new PersonaContacto("Laura Montelli", "54547", ong3);

        // Añadir cada persona al padre (OneToMany) para mantener la relación en ambos lados
        ong.addPersonaContacto(persona1);
        ong.addPersonaContacto(persona2);
        ong2.addPersonaContacto(persona3);
        ong3.addPersonaContacto(persona4);

        // Guardamos únicamente el padre; gracias al cascade=ALL se guardan los hijos solos
        session.persist(ong);
        session.persist(ong2);
        session.persist(ong3);

        // MODIFICAR DATOS (EJERCICIO 6)

        // Cambiar el nombre de la organización 3
        ong3.setNombreOrganizacion("Salvemos a los Mayores");

        // merge() sincroniza el objeto modificado con la base de datos
        session.merge(ong3);

        // Guardar cambios en la BBDD
        tx.commit();

        // EJERCICIO 7 - CONSULTA HQL

        System.out.println("\n=== CONSULTA HQL EMBEBIDA ===\n");

        // Nueva transacción para la consulta
        session.beginTransaction();

        /*
         * HQL (Hibernate Query Language):
         * - Trabaja con NOMBRES DE CLASES, no con tablas SQL.
         * - JOIN FETCH sirve para cargar también los hijos (personasContacto).
         * - Esto evita LazyInitializationException porque carga todo de golpe.
         */
        String hql = """
            SELECT o
            FROM ClienteONG o
            JOIN FETCH o.personasContacto
            WHERE o.nombreOrganizacion = 'Salvemos la Infancia'
            """;

        // Ejecutamos la consulta y obtenemos una lista de objetos ClienteONG
        List<ClienteONG> resultado = session.createQuery(hql, ClienteONG.class).getResultList();

        // MOSTRAR LOS RESULTADOS
        for (ClienteONG org : resultado) {

            System.out.println("Organización encontrada:");
            System.out.println(" - Nombre: " + org.getNombreOrganizacion());
            System.out.println(" - País: " + org.getPaisOrganizacion());
            System.out.println(" - Tipo: " + org.getTiposOrganizacion());
            System.out.println(" - Personas de contacto:");

            // Mostrar personas asociadas a la organización
            System.out.println("   Personas de contacto:");

            for (PersonaContacto pc : org.getPersonasContacto()) {
                // Por cada persona de contacto, mostramos su nombre y teléfono
                System.out.println("      * " + pc.getNombre() + " | Tel: " + pc.getTelefono());
            }

        }

        // Confirmamos la transacción de la consulta
        session.getTransaction().commit();

        // Cerrar todo
        session.close();
        sessionFactory.close();
    }
}
