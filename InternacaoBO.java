package br.com.hospital.business;

import java.time.LocalDate;
import java.util.List;

import br.com.hospital.business.exception.BusinessException;
import br.com.hospital.dao.InternacaoDAO;
import br.com.hospital.dao.MedicoDAO;
import br.com.hospital.dao.PacienteDAO;
import br.com.hospital.model.Internacao;
import br.com.hospital.model.Medico;
import br.com.hospital.model.Paciente;

public class InternacaoBO {

    private static final String STATUS_ATIVA = "ATIVA";
    private static final String STATUS_FINALIZADA = "FINALIZADA";

    private final InternacaoDAO internacaoDAO;
    private final PacienteDAO pacienteDAO;
    private final MedicoDAO medicoDAO;

    public InternacaoBO(InternacaoDAO internacaoDAO, PacienteDAO pacienteDAO, MedicoDAO medicoDAO) {
        this.internacaoDAO = internacaoDAO;
        this.pacienteDAO = pacienteDAO;
        this.medicoDAO = medicoDAO;
    }

    public void internarPaciente(int idPaciente, int idMedico, String motivo) throws Exception {
        validarTextoObrigatorio(motivo, "Informe o motivo da internacao.");

        Paciente paciente = pacienteDAO.buscarPorId(idPaciente);
        if (paciente == null) {
            throw new BusinessException("Paciente nao encontrado.");
        }

        Medico medico = medicoDAO.buscarPorId(idMedico);
        if (medico == null) {
            throw new BusinessException("Medico nao encontrado.");
        }

        if (!medico.isDisponivel()) {
            throw new BusinessException("O paciente nao pode ser internado porque o medico nao esta disponivel.");
        }

        Internacao internacaoAtiva = internacaoDAO.buscarInternacaoAtivaPorPaciente(idPaciente);
        if (internacaoAtiva != null) {
            throw new BusinessException("O paciente ja possui uma internacao ativa.");
        }

        Internacao novaInternacao = new Internacao();
        novaInternacao.setPaciente(paciente);
        novaInternacao.setMedico(medico);
        novaInternacao.setMotivo(motivo.trim());
        novaInternacao.setDataEntrada(LocalDate.now());
        novaInternacao.setStatus(STATUS_ATIVA);

        internacaoDAO.inserir(novaInternacao);
        medicoDAO.atualizarDisponibilidade(idMedico, false);
    }

    public void darAlta(int idInternacao) throws Exception {
        if (idInternacao <= 0) {
            throw new BusinessException("Informe uma internacao valida.");
        }

        Internacao internacao = internacaoDAO.buscarPorId(idInternacao);
        if (internacao == null) {
            throw new BusinessException("Internacao nao encontrada.");
        }

        if (!STATUS_ATIVA.equalsIgnoreCase(internacao.getStatus())) {
            throw new BusinessException("A internacao informada ja foi finalizada.");
        }

        internacao.setDataAlta(LocalDate.now());
        internacao.setStatus(STATUS_FINALIZADA);

        internacaoDAO.atualizar(internacao);
        medicoDAO.atualizarDisponibilidade(internacao.getMedico().getId(), true);
    }

    public List<Internacao> listarInternacoes() throws Exception {
        return internacaoDAO.listarTodas();
    }

    private void validarTextoObrigatorio(String texto, String mensagem) throws BusinessException {
        if (texto == null || texto.trim().isEmpty()) {
            throw new BusinessException(mensagem);
        }
    }
}
