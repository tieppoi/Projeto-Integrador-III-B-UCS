package br.com.hospital.business;

import java.util.List;

import br.com.hospital.business.exception.BusinessException;
import br.com.hospital.dao.MedicoDAO;
import br.com.hospital.model.Medico;

public class MedicoBO {

    private final MedicoDAO medicoDAO;

    public MedicoBO(MedicoDAO medicoDAO) {
        this.medicoDAO = medicoDAO;
    }

    public Medico buscarPorId(int idMedico) throws Exception {
        if (idMedico <= 0) {
            throw new BusinessException("Informe um medico valido.");
        }
        return medicoDAO.buscarPorId(idMedico);
    }

    public List<Medico> listarDisponiveis() throws Exception {
        return medicoDAO.listarDisponiveis();
    }
}
