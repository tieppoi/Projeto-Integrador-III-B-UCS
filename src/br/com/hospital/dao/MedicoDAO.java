package br.com.hospital.dao;

import br.com.hospital.model.Medico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO {

    private Connection conexao;

    public MedicoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserir(Medico medico) {
        String sql = "INSERT INTO medicos (nome, crm, especialidade, disponivel) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, medico.getNome());
            stmt.setString(2, medico.getCrm());
            stmt.setString(3, medico.getEspecialidade());
            stmt.setBoolean(4, medico.isDisponivel());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir o médico no banco de dados.", e);
        }
    }

    public List<Medico> buscarTodos() {
        List<Medico> listaDeMedicos = new ArrayList<>();
        String sql = "SELECT id, nome, crm, especialidade, disponivel FROM medicos";

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Medico medico = new Medico();
                medico.setId(rs.getInt("id"));
                medico.setNome(rs.getString("nome"));
                medico.setCrm(rs.getString("crm"));
                medico.setEspecialidade(rs.getString("especialidade"));
                medico.setDisponivel(rs.getBoolean("disponivel"));
                listaDeMedicos.add(medico);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar os médicos no banco de dados.", e);
        }

        return listaDeMedicos;
    }

    public void atualizar(Medico medico) {
        String sql = "UPDATE medicos SET nome = ?, crm = ?, especialidade = ?, disponivel = ? WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, medico.getNome());
            stmt.setString(2, medico.getCrm());
            stmt.setString(3, medico.getEspecialidade());
            stmt.setBoolean(4, medico.isDisponivel());
            stmt.setInt(5, medico.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar os dados do médico.", e);
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM medicos WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir o médico.", e);
        }
    }
}
