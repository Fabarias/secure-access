package org.secureaccess.app.secureaccessbackend.modelo;

public class Usuario {

    public int getActivo() {
       return activo;
    }

    private String nombre;
    private int activo;
    public String getApellido() {
        return apellido;
    }

    private String apellido;
    private Credencial credencial;
    public Credencial getCredencial() {
        return credencial;
    }

    public void setCredencial(Credencial credencial) {
        this.credencial = credencial;
    }


    public Usuario(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }
    public String getRol() {
        return this.getCredencial().getRolUsuario();
    }

    public Usuario( int activo, String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.activo = activo;
        //this.usuarioID = usuarioID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

  }


