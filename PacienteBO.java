package br.com.hospital.business;

import br.com.hospital.business.exception.BusinessException;
import br.com.hospital.dao.PacienteDAO;
import br.com.hospital.model.Paciente;

public class PacienteBO {

    private final PacienteDAO pacienteDAO;

    public PacienteBO(PacienteDAO pacienteDAO) {
        this.pacienteDAO = pacienteDAO;
    }

    public Paciente buscarPorId(int idPaciente) throws Exception {
        if (idPaciente <= 0) {
            throw new BusinessException("Informe um paciente valido.");
        }

        Paciente paciente = pacienteDAO.buscarPorId(idPaciente);
        if (paciente == null) {
            throw new BusinessException("Paciente nao encontrado.");
        }

        return paciente;
    }
}
