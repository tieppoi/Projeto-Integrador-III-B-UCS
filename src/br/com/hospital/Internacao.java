package br.com.hospital.model;

import java.time.LocalDate;

public class Internacao {

    private int id;
    private Paciente paciente;
    private Medico medico;
    private LocalDate dataEntrada;
    private LocalDate dataAlta;
    private String motivo;
    private String status;

    public Internacao() {
    }

    public Internacao(int id, Paciente paciente, Medico medico, LocalDate dataEntrada,
            LocalDate dataAlta, String motivo, String status) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.dataEntrada = dataEntrada;
        this.dataAlta = dataAlta;
        this.motivo = motivo;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public LocalDate getDataAlta() {
        return dataAlta;
    }

    public void setDataAlta(LocalDate dataAlta) {
        this.dataAlta = dataAlta;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
