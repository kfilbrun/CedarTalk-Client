/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cedartalkclient;

import edu.cedarville.jvolante.cedartalknetworking.Message;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kfilbrun
 */
public class MainWindow extends javax.swing.JFrame {

    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
        new LoginWindow(this).setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"None"}
            },
            new String [] {
                "Active Users"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if(loggedIn){
            int rowClicked = jTable1.rowAtPoint(evt.getPoint());
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            String friendName = (String) model.getValueAt(rowClicked, 0);
            getChatWindow(friendName);
        }
    }//GEN-LAST:event_jTable1MouseClicked

    //Send logout message
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        Message msg = new Message(2, myUsername);
        dispatcher.sendMessage(msg);
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    private ClientDispatcher dispatcher;
    private Map<String, ChatWindow> chatWindows = new HashMap<>();
    private String myUsername = "";
    private boolean loggedIn = false;
    
    public void processMessage(Message message){
        switch(message.getID()){
            case 3:
                passMessageToWindow(message);
                break;
            case 4:
                addActiveUser(message);
                break;
            case 5:
                removeActiveUser(message);
                break;
            default:
                System.err.println("Incorrect message received" + message.getID() + " " + message.getData());
        }
    }
    
    public ClientDispatcher getDispatcher(){
        return dispatcher;
    }
    
    public void setLoggedIn(boolean l){
        loggedIn = l;
    }
    
    public void setMyUsername(String s){
        myUsername = s;
    }
    
    public String getMyUsername(){
        return myUsername;
    }
    
    private void removeActiveUser(Message msg){
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        for(int row = 0; row < model.getRowCount(); row++){
            if(model.getValueAt(row, 0).equals(msg.getData())){
                model.removeRow(row);
                return;
            }
        }
    }
    
    private void addActiveUser(Message msg){
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        String row1Val = (String) model.getValueAt(0, 0);
        //Remove the none if friends are being added
        if(row1Val.equals("None")){
            model.removeRow(0);
        }
        model.addRow(new Object[]{ msg.getData() });
    }
    
    private ChatWindow getChatWindow(String friendName){
        ChatWindow w = chatWindows.get(friendName);
        if(w == null){
            w = new ChatWindow(myUsername, friendName, dispatcher);
            chatWindows.put(friendName, w);
            w.setVisible(true);
            w.setAlwaysOnTop(true);
        }
        return w;
    }
    
    private void parseChatMessage(Message msg, String friendName, String chatMsg){
        String inMsg = msg.getData();
        String[] parts = inMsg.split(inMsg, 3);
        friendName = parts[0];
        chatMsg = parts[2];
    }
    
    private void passMessageToWindow(Message msg){
        String chatMsg = "";
        String friendName = "";
        parseChatMessage(msg, friendName, chatMsg);
        ChatWindow w = getChatWindow(friendName); //Creates if not available
        w = chatWindows.get(friendName);
        w.messageRecieved(chatMsg);
    }
}
