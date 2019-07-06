package com.example.asus.dconfo_app.domain.model;

public class DeberEstudiante {
    Integer idEstHasDeber;
    Integer idEjercicio2;
    Integer idDocente;
    String TipoDeber;
    String fechaDeber;
    Integer idCalificacion;


   /* public Integer getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEjercicio(Integer idEjercicio) {
        this.idEjercicio = idEjercicio;
    }*/

    public Integer getIdEstHasDeber() {
        return idEstHasDeber;
    }

    public void setIdEstHasDeber(Integer idEstHasDeber) {
        this.idEstHasDeber = idEstHasDeber;
    }

    public Integer getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(Integer idDocente) {
        this.idDocente = idDocente;
    }

    public String getFechaDeber() {
        return fechaDeber;
    }

    public void setFechaDeber(String fechaDeber) {
        this.fechaDeber = fechaDeber;
    }

    public Integer getIdCalificacion() {
        return idCalificacion;
    }

    public void setIdCalificacion(Integer idCalificacion) {
        this.idCalificacion = idCalificacion;
    }

    public String getTipoDeber() {
        return TipoDeber;
    }

    public void setTipoDeber(String tipoDeber) {
        TipoDeber = tipoDeber;
    }

    public Integer getIdEjercicio2() {
        return idEjercicio2;
    }

    public void setIdEjercicio2(Integer idEjercicio2) {
        this.idEjercicio2 = idEjercicio2;
    }
}
