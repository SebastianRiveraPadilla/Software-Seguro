package model;

import java.sql.Date;

public class GrupoFamiliar {
    private int idGrupoFamiliar;
    private int idFuncionario;
    private String nombres;
    private String apellidos;
    private String rol;
    private Date fechaNacimiento;

    public GrupoFamiliar() {}

    public int getIdGrupoFamiliar() { return idGrupoFamiliar; }
    public void setIdGrupoFamiliar(int idGrupoFamiliar) { this.idGrupoFamiliar = idGrupoFamiliar; }

    public int getIdFuncionario() { return idFuncionario; }
    public void setIdFuncionario(int idFuncionario) { this.idFuncionario = idFuncionario; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public Date getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
}