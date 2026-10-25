package model;

public class InfoAcademica {
    private int idAcademica;
    private int idFuncionario;
    private String universidad;
    private String nivelEstudio;
    private String tituloEstudio;

    public InfoAcademica() {}

    public int getIdAcademica() { return idAcademica; }
    public void setIdAcademica(int idAcademica) { this.idAcademica = idAcademica; }

    public int getIdFuncionario() { return idFuncionario; }
    public void setIdFuncionario(int idFuncionario) { this.idFuncionario = idFuncionario; }

    public String getUniversidad() { return universidad; }
    public void setUniversidad(String universidad) { this.universidad = universidad; }

    public String getNivelEstudio() { return nivelEstudio; }
    public void setNivelEstudio(String nivelEstudio) { this.nivelEstudio = nivelEstudio; }

    public String getTituloEstudio() { return tituloEstudio; }
    public void setTituloEstudio(String tituloEstudio) { this.tituloEstudio = tituloEstudio; }
}