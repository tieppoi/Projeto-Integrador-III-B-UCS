package br.com.hospital.dao;

import java.util.List;

import br.com.hospital.model.Internacao;

public interface InternacaoDAO {

    void inserir(Internacao internacao) throws Exception;

    void atualizar(Internacao internacao) throws Exception;

    Internacao buscarPorId(int idInternacao) throws Exception;

    Internacao buscarInternacaoAtivaPorPaciente(int idPaciente) throws Exception;

    List<Internacao> listarTodas() throws Exception;
}
