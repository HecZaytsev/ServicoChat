/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicochat;

/**
 *
 * @author Heck
 */
import java.rmi.*;
import java.net.InetAddress;
import java.util.ArrayList;

public interface ServicoChat extends Remote {
   public String login(String username, String password) throws RemoteException;
   public ArrayList<Usuario> buscaUsuarios() throws RemoteException;
   public boolean logout(String id, String nome) throws RemoteException;
   public boolean disponibilidade(String username) throws RemoteException;
   public boolean cadastrar(String username, String password) throws RemoteException;
   public String usuarioOnline(String username) throws RemoteException;
   public boolean adicionaBuffer(Mensagem msg) throws RemoteException;
   public Mensagem procuraMensagem(String id_remetente, String id_destinatario)throws RemoteException;
   public Mensagem getMensagem(String id_remetente, String id_destinatario)throws RemoteException;
}
