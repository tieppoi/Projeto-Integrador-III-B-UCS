package br.com.hospital.dao;

import br.com.hospital.model.Paciente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    private Connection conexao;

    public PacienteDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserir(Paciente paciente) {
        String sql = "INSERT INTO pacientes (nome, cpf, idade) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getCpf());
            stmt.setInt(3, paciente.getIdade());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir o paciente no banco de dados.", e);
        }
    }

    public List<Paciente> buscarTodos() {
        List<Paciente> listaDePacientes = new ArrayList<>();
        String sql = "SELECT id, nome, cpf, idade FROM pacientes";

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Paciente paciente = new Paciente();
                paciente.setId(rs.getInt("id"));
                paciente.setNome(rs.getString("nome"));
                paciente.setCpf(rs.getString("cpf"));
                paciente.setIdade(rs.getInt("idade"));
                listaDePacientes.add(paciente);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar os pacientes no banco de dados.", e);
        }

        return listaDePacientes;
    }

    public void atualizar(Paciente paciente) {
        String sql = "UPDATE pacientes SET nome = ?, cpf = ?, idade = ? WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getCpf());
            stmt.setInt(3, paciente.getIdade());
            stmt.setInt(4, paciente.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar os dados do paciente.", e);
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM pacientes WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir o paciente.", e);
        }
    }
}
