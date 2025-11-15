package org.ong;
// Clase que representa un cliente de una ONG
//Importamos las librerias necesarias

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity // Indicamos que es una entidad de Hibernate/JPA
@Table(name = "clientesong") // Indicamos el nombre de la tabla en la base de datos

public class ClienteONG {
    @Id //Indicamos que es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Indicamos que el valor se genera automaticamente
    @Column(name = "idorganizacion") // Indicamos que es una columna de la tabla
    private int idOrganizacion;

    @Column(name = "nombreorganizacion")
    private String nombreOrganizacion;

    @Column(name = "paisorganizacion")
    private String paisOrganizacion;

    @Column(name = "tiposorganizacion")
    private String tiposOrganizacion;

    // Relación OneToMany con PersonaContacto
    @OneToMany(mappedBy = "clienteONG", cascade = CascadeType.ALL, orphanRemoval = true)// Indicamos que es una relación uno a muchos, cascada para que al eliminar un cliente se eliminen sus contactos y orphanRemoval para eliminar contactos huérfanos
    private List<PersonaContacto> personasContacto = new ArrayList<>();// Inicializamos la lista para evitar NullPointerException

    //Constructor vacio obligatorio para Hibernate
    public ClienteONG() {}
    //Constructor con parametros
    public ClienteONG(String nombreOrganizacion, String paisOrganizacion, String tiposOrganizacion) {
        this.nombreOrganizacion = nombreOrganizacion;
        this.paisOrganizacion = paisOrganizacion;
        this.tiposOrganizacion = tiposOrganizacion;
    }
    //Getters y Setters

    public int getIdOrganizacion() {
        return idOrganizacion;
    }
    public void setIdOrganizacion(int idOrganizacion) {
        this.idOrganizacion = idOrganizacion;
    }
    public String getNombreOrganizacion() {
        return nombreOrganizacion;
    }
    public void setNombreOrganizacion(String nombreOrganizacion) {
        this.nombreOrganizacion = nombreOrganizacion;
    }
    public String getPaisOrganizacion() {
        return paisOrganizacion;
    }
    public void setPaisOrganizacion(String paisOrganizacion) {
        this.paisOrganizacion = paisOrganizacion;
    }
    public String getTiposOrganizacion() {
        return tiposOrganizacion;
    }
    public void setTiposOrganizacion(String tiposOrganizacion) {
        this.tiposOrganizacion = tiposOrganizacion;
    }

    //Metodo toString para mostrar la información del cliente ONG
    @Override
    public String toString() {
        return "ClienteONG [idOrganizacion=" + idOrganizacion +
                ", nombreOrganizacion=" + nombreOrganizacion +
                ", paisOrganizacion=" + paisOrganizacion +
                ", tiposOrganizacion=" + tiposOrganizacion + "]";
    }
    public void addPersonaContacto(PersonaContacto persona) {
        personasContacto.add(persona);
        persona.setClienteONG(this);
    }
    public List<PersonaContacto> getPersonasContacto() {
        return personasContacto;
    }


}
