//DEbemos indicar la relaccion entre entidades ClienteONG y PersonaContacto
package org.ong;
// Clase que representa un contacto de una persona de la ONG
//Importamos las librerias necesarias
import jakarta.persistence.*;
@Entity // Indicamos que es una entidad de Hibernate/JPA
@Table(name = "personascontacto") // Indicamos el nombre de la tabla en la base de datos
public class PersonaContacto {

    // Atributos

    @Id //Indicamos que es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Indicamos que el valor se genera automaticamente
    private int id;
    private String nombre;
    private String telefono;

    //Relación ManyToOne con ClienteONG
    @ManyToOne
    @JoinColumn(name = "idorganizacion", nullable = false) // Indicamos la columna que hace de clave foranea nulla=false para que no pueda ser nula.
    private ClienteONG clienteONG;

    //Constructor vacio obligatorio para Hibernate
    public PersonaContacto() {}

    //Constructor con parametros
    public PersonaContacto(String nombre, String telefono, ClienteONG clienteONG) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.clienteONG = clienteONG;

    }
// Getters y Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public ClienteONG getClienteONG() {
        return clienteONG;
    }
    public void setClienteONG(ClienteONG clienteONG) {
        this.clienteONG = clienteONG;
    }
//Metodo to String para mostrar la informacion de la persona de contacto
    @Override
    public String toString() {
        return "PersonaContacto [id=" + id + ", nombre=" + nombre + ", telefono=" + telefono + ", clienteONG=" + clienteONG.getNombreOrganizacion() + "]";
    }
}
