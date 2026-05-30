package br.com.hospital.dao;

import br.com.hospital.model.Internacao;
import br.com.hospital.model.Medico;
import br.com.hospital.model.Paciente;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class InternacaoDAO {

    private Connection conexao;

    public InternacaoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserir(Internacao internacao) {
        String sql = "INSERT INTO internacoes (paciente_id, medico_id, data_entrada, data_alta, motivo, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, internacao.getPaciente().getId());
            stmt.setInt(2, internacao.getMedico().getId());
            stmt.setDate(3, Date.valueOf(internacao.getDataEntrada()));

            if (internacao.getDataAlta() != null) {
                stmt.setDate(4, Date.valueOf(internacao.getDataAlta()));
            } else {
                stmt.setNull(4, Types.DATE);
            }

            stmt.setString(5, internacao.getMotivo());
            stmt.setString(6, internacao.getStatus());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao registrar a internação.", e);
        }
    }

    public List<Internacao> buscarTodos() {
        List<Internacao> listaDeInternacoes = new ArrayList<>();
        String sql = "SELECT id, paciente_id, medico_id, data_entrada, data_alta, motivo, status FROM internacoes";

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Internacao internacao = new Internacao();
                internacao.setId(rs.getInt("id"));

                Paciente paciente = new Paciente();
                paciente.setId(rs.getInt("paciente_id"));
                internacao.setPaciente(paciente);

                Medico medico = new Medico();
                medico.setId(rs.getInt("medico_id"));
                internacao.setMedico(medico);

                if (rs.getDate("data_entrada") != null) {
                    internacao.setDataEntrada(rs.getDate("data_entrada").toLocalDate());
                }

                if (rs.getDate("data_alta") != null) {
                    internacao.setDataAlta(rs.getDate("data_alta").toLocalDate());
                }

                internacao.setMotivo(rs.getString("motivo"));
                internacao.setStatus(rs.getString("status"));

                listaDeInternacoes.add(internacao);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar as internações.", e);
        }

        return listaDeInternacoes;
    }

    public void atualizar(Internacao internacao) {
        String sql = "UPDATE internacoes SET paciente_id = ?, medico_id = ?, data_entrada = ?, data_alta = ?, motivo = ?, status = ? WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, internacao.getPaciente().getId());
            stmt.setInt(2, internacao.getMedico().getId());
            stmt.setDate(3, Date.valueOf(internacao.getDataEntrada()));

            if (internacao.getDataAlta() != null) {
                stmt.setDate(4, Date.valueOf(internacao.getDataAlta()));
            } else {
                stmt.setNull(4, Types.DATE);
            }

            stmt.setString(5, internacao.getMotivo());
            stmt.setString(6, internacao.getStatus());
            stmt.setInt(7, internacao.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar os dados da internação.", e);
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM internacoes WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir a internação.", e);
        }
    }
}
