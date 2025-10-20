package com.proyecto.myapplication.data.model

class Usuario {
    private var nombre: String? = null
    private var email: String? = null
    var edad: Int = 0

    // Constructor vacío
    constructor()

    // Constructor con parámetros
    constructor(nombre: String, email: String, edad: Int) {
        this.nombre = nombre
        this.email = email
        this.edad = edad
    }

    // Getters y setters
    fun getNombre(): String {
        return nombre!!
    }

    fun setNombre(nombre: String) {
        this.nombre = nombre
    }

    fun getEmail(): String {
        return email!!
    }

    fun setEmail(email: String) {
        this.email = email
    }

    // Método toString (opcional, para depuración)
    override fun toString(): String {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", edad=" + edad +
                '}'
    }

    // Método equals (opcional, si lo necesitas para comparaciones)
    override fun equals(obj: Any): Boolean {
        if (this === obj) return true
        if (obj == null || javaClass != obj.javaClass) return false
        val usuario = obj as Usuario
        return edad == usuario.edad &&
                nombre == usuario.nombre &&
                email == usuario.email
    }
}