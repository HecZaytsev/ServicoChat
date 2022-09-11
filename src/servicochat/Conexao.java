/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicochat;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Heck
 */
public class Conexao {

    public java.sql.Connection conexao;

    public Conexao() throws IOException {
        this.conexao = getConexao();
    }

    public static java.sql.Connection getConexao() throws IOException {

        try {
            // Carregando o JDBC Driver padrão
            String driverName = "com.mysql.cj.jdbc.Driver";

            Class.forName(driverName);

            String serverName = "localhost:3306";

            String mydatabase = "servicochat"; //"mysql"

            String url = "jdbc:mysql://" + serverName + "/" + mydatabase;

            String username = "root";

            String password = "root";

            Connection connection = DriverManager.getConnection(url, username, password);

            return connection;

        } catch (ClassNotFoundException e) {  //Driver não encontrado
            return null;

        } catch (SQLException e) {
            return null;

        }
    }

    public static void main(String[] args) {
        try {
            getConexao();
        } catch (IOException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
