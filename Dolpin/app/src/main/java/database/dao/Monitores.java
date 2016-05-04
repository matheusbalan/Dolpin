package database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.MeuPreparedStatement;
import database.dbo.Monitor;
import database.exception.MonitorExistingException;
import database.exception.MonitorNotFoundException;

/**
 * Created by u14185 on 06/04/2016.
 */
public class Monitores {
    private MeuPreparedStatement meuPreparedStatement;

    public Monitores(MeuPreparedStatement meuPreparedStatement) {
        this.meuPreparedStatement = meuPreparedStatement;
    }

    public boolean existe(Monitor monitor) throws SQLException,IllegalArgumentException {
        if (monitor == null)
            throw new IllegalArgumentException("Parâmetro nulo");

        try {
            String sqlQuery = "SELECT * FROM Monitor WHERE nome = ?, telefone = ?, email = ?, senha = ?";

            meuPreparedStatement.prepareStatement(sqlQuery);

            meuPreparedStatement.setString(1, monitor.getNome());
            meuPreparedStatement.setString(2, monitor.getTelefone());
            meuPreparedStatement.setString(3, monitor.getEmail());
            meuPreparedStatement.setString(4, monitor.getSenha());

            ResultSet resultSet = meuPreparedStatement.executeQuery();

            return resultSet.first();
        }
        catch (SQLException ex) {
            throw new SQLException("Erro na busca do usuário");
        }
    }

    public void logar(Monitor monitor)throws SQLException, IllegalArgumentException, MonitorNotFoundException {
        if (monitor == null)
            throw new IllegalArgumentException("Parâmetro nulo");

        try {
            String sqlQuery = "SELECT * FROM Monitor WHERE email = ?, senha = ?";

            meuPreparedStatement.prepareStatement(sqlQuery);

            meuPreparedStatement.setString(1, monitor.getEmail());
            meuPreparedStatement.setString(2, monitor.getSenha());

            ResultSet resultSet = meuPreparedStatement.executeQuery();

            if(!resultSet.first())
                throw new MonitorNotFoundException();
        }
        catch (SQLException ex) {
            throw new SQLException("Erro na busca do usuário");
        }
    }

    public void cadastrar(Monitor monitor) throws SQLException, IllegalArgumentException, MonitorExistingException {
        if (monitor == null)
            throw new IllegalArgumentException("Parâmetro nulo");

        try {
            if (existe(monitor))
                throw new MonitorExistingException();

            String sqlQuery = "INSERT INTO Monitor (nome, telefone, email, senha) VALUES ?, ?, ?, ?";

            meuPreparedStatement.prepareStatement(sqlQuery);

            meuPreparedStatement.setString(1, monitor.getNome());
            meuPreparedStatement.setString(2, monitor.getTelefone());
            meuPreparedStatement.setString(3, monitor.getEmail());
            meuPreparedStatement.setString(4, monitor.getSenha());

            meuPreparedStatement.executeQuery();
            meuPreparedStatement.commit();
        }
        catch (SQLException ex) {
            throw new SQLException("Erro no cadastro do usuário");
        }
    }
}
