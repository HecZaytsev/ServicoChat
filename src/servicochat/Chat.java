/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicochat;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Heck
 */
public class Chat extends javax.swing.JFrame {

    /**
     * Creates new form Chat
     */
    public ServicoChat stub;
    public String self_id;
    public String self_name;
    public String other_id;
    public String other_name;
    public Timer timer;

    public Chat(ServicoChat stub, String self_id, String self_name, String other_id, String other_name) {
        this.stub = stub;
        this.self_id = self_id;
        this.self_name = self_name;
        this.other_id = other_id;
        this.other_name = other_name;
        initComponents();
        txtAreaTela.setEditable(false);
        txtOtherName.setText(other_name.toUpperCase());
        timer = new Timer();
        timer.scheduleAtFixedRate(new BuscaMensagens(), 1500, 1500);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtOtherName = new javax.swing.JLabel();
        spnTela = new javax.swing.JScrollPane();
        txtAreaTela = new javax.swing.JTextArea();
        txtEnvio = new javax.swing.JTextField();
        btnEnviar = new javax.swing.JButton();
        btnEncerra = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtOtherName.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txtOtherName.setText("NOME DA PESSOA");

        txtAreaTela.setColumns(20);
        txtAreaTela.setRows(5);
        spnTela.setViewportView(txtAreaTela);

        btnEnviar.setText("Enviar");
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });

        btnEncerra.setText("Encerrar Conversa");
        btnEncerra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEncerraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtEnvio, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(spnTela, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(32, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtOtherName, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEncerra))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(txtOtherName))
                    .addComponent(btnEncerra))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(spnTela, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(txtEnvio))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
        // TODO add your handling code here:
        Mensagem msg = new Mensagem(this.self_id, this.other_id, txtEnvio.getText(), LocalDate.now());
        try {
            stub.adicionaBuffer(msg);
            txtAreaTela.setText(txtAreaTela.getText()+this.self_name+" : "+txtEnvio.getText()+"\n");
            txtEnvio.setText("");
        } catch (RemoteException ex) {
            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnEnviarActionPerformed

    private void btnEncerraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEncerraActionPerformed
        // Fecha janela de conversa (em ambos lados?)
        timer.cancel();
        this.dispose();
    }//GEN-LAST:event_btnEncerraActionPerformed

    class BuscaMensagens extends TimerTask {

        public void run() {
            try {
                Mensagem mensagemRetorno = stub.procuraMensagem(other_id, self_id);
                if (mensagemRetorno != null) {
                    txtAreaTela.setText(txtAreaTela.getText()+other_name+" : "+mensagemRetorno.getTexto()+"\n");
                }
            } catch (RemoteException ex) {
                Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEncerra;
    private javax.swing.JButton btnEnviar;
    private javax.swing.JScrollPane spnTela;
    private javax.swing.JTextArea txtAreaTela;
    private javax.swing.JTextField txtEnvio;
    private javax.swing.JLabel txtOtherName;
    // End of variables declaration//GEN-END:variables
}
