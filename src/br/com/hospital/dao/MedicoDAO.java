package br.com.hospital.dao;

import java.util.List;

import br.com.hospital.model.Medico;

public interface MedicoDAO {

    Medico buscarPorId(int id) throws Exception;

    List<Medico> listarDisponiveis() throws Exception;

    void atualizarDisponibilidade(int idMedico, boolean disponivel) throws Exception;
}
