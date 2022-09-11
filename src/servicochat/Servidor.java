package servicochat;

/**
 *
 * @author Heck
 */
import java.io.IOException;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.net.InetAddress;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

public class Servidor implements ServicoChat {

    public Conexao conn;
    public ArrayList<Usuario> conectados = new ArrayList();
    public ArrayList<Mensagem> buffer = new ArrayList();

    public Servidor() {
        try {
            // Testa conexão com banco de dados
            conn = new Conexao();
            if (conn.conexao != null) {
                System.out.println("Conectado ao Banco de Dados");
            } else {
                System.out.println("Erro ao conectar com o Banco de Dados");
            }
        } catch (IOException ex) {
            System.out.println(ex);
            System.out.println("Não foi possivel conectar ao Banco de Dados");
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) {
        // Verificar se é possivel realizar perguntas ao usuario pelo CMD
        // e Atribuir no DNS (Substituir HecZaytsev-Not)
        Scanner read = new Scanner(System.in);
        String dns_host;
        
        dns_host = read.next();
        
        try {
            // Instancia o objeto servidor e a sua stub  
            Servidor server = new Servidor();
            ServicoChat stub = (ServicoChat) UnicastRemoteObject.exportObject(server, 1100);

            // Registra a stub no RMI Registry para que ela seja obtida pelos clientes
            Registry registry = LocateRegistry.createRegistry(1100);
            registry.bind("chat", stub);
            //Naming.rebind("chat", stub);
            System.setProperty("java.rmi.server.hostname", dns_host);

            String nome = InetAddress.getLocalHost().getHostName();
            String ender = InetAddress.getLocalHost().getHostAddress();
            System.out.println("Servidor Serviço de Chat em execução");
            System.out.println("Maquina: " + nome);
            System.out.println("IP: " + ender);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String login(String username, String password) throws RemoteException {

        System.out.println("request login");
        // Verifica login
        String sql = " SELECT user_id from usuarios ";
        sql += " WHERE username = '" + username + "' ";
        sql += " AND senha = '" + password + "' ";
        try {
            PreparedStatement statement = conn.conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet result = statement.executeQuery();
            if (result.first()) { // Se existe
                int id = result.getInt("user_id");
                String id_s = Integer.toString(id);
                Usuario user = new Usuario(username, id_s);

                // Verifica se o usuario ja não esta conectado
                boolean encontrou = false;
                for (int i = 0; i < conectados.size(); i++) {
                    if (conectados.get(i).getId().equals(id_s)) {
                        encontrou = true;
                    }
                }
                if (!encontrou) {
                    conectados.add(user);
                }

                return id_s;
            } else {
                System.out.println("Credenciais não encontradas");
                return "";
            }

        } catch (SQLException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "";
    }

    @Override
    public boolean logout(String id, String nome) throws RemoteException {

        boolean removeu = false;

        for (int i = 0; i < conectados.size(); i++) {
            Usuario p = conectados.get(i);

            if ((p.getNome().equals(nome)) && (p.getId().equals(id))) {

                removeu = conectados.remove(p);
                break;
            }
        }

        return removeu;
    }

    @Override
    public ArrayList<Usuario> buscaUsuarios() throws RemoteException {
        return conectados; // Envia ao cliente, todos usuarios conectados para formar a tabela
    }

    public boolean disponibilidade(String username) throws RemoteException {

        String sql = " SELECT username from usuarios ";
        sql += " WHERE username = '" + username + "' ";
        try {
            PreparedStatement statement = conn.conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet result = statement.executeQuery();

            if (result.first()) { // Se existe
                return false;
            } else {
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean cadastrar(String username, String password) {
        String sql = " INSERT INTO  usuarios (username,senha,conectado) VALUES ('" + username + "','" + password + "','')";

        try {
            PreparedStatement statement = conn.conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.executeUpdate();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public String usuarioOnline(String username) {

        for (int i = 0; i < conectados.size(); i++) {
            Usuario p = conectados.get(i);

            if ((p.getNome().equals(username))) {
                // Usuario esta online
                return p.getId();
            }
        }

        return "";
    }

    @Override
    public boolean adicionaBuffer(Mensagem msg) {

        this.buffer.add(msg);

        return true;
    }

    @Override
    public Mensagem procuraMensagem(String id_remetente, String id_destinatario) {

        for (int i = 0; i < buffer.size(); i++) {
            Mensagem m = buffer.get(i);

            if (m.getId_remetente().equals(id_remetente) && m.getId_destinatario().equals(id_destinatario)) {
                // Mensagem é entre os envolvidos
                buffer.remove(i); // Remove Mensagem do buffer
                return m;         // E Envia ao client para printar na tela
            }
        }

        return null;
    }

    @Override
    public Mensagem getMensagem(String id_remetente, String id_destinatario) {

        for (int i = 0; i < buffer.size(); i++) {
            Mensagem m = buffer.get(i);

            if (m.getId_remetente().equals(id_remetente) && m.getId_destinatario().equals(id_destinatario)) {
                // Mensagem é entre os envolvidos
                return m;         // E Envia ao client para printar na tela
            }
        }

        return null;
    }

}
