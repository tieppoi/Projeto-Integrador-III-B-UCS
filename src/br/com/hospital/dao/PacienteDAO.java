package br.com.hospital.dao;

import br.com.hospital.model.Paciente;

public interface PacienteDAO {

    Paciente buscarPorId(int id) throws Exception;
}
