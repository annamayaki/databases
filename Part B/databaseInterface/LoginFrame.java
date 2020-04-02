package databaseInterface;

import java.sql.*;
import javax.swing.JOptionPane;

public class LoginFrame extends javax.swing.JFrame {
    
    private Connection conn; 
    private String user;
    private char[] passArr;
    private String pass;
    private boolean cont = true;
    private String usrRedirectUI;
    
    public LoginFrame() {
        initComponents();
    }
    
    
    void createConnection() {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/erecruit", user, pass);
                Statement stmt  = conn.createStatement();
                String typeQuery = "SELECT user_type FROM user where username='"+user+"'";
                ResultSet res=stmt.executeQuery(typeQuery);
                res.next();
                usrRedirectUI=res.getString("user_type");
                res.close();
                
                
                stmt.close();
                checkUser();
            } catch (ClassNotFoundException ex) {
                System.out.println(ex);
            } catch (SQLException ex) {
                System.out.println(ex);
                JOptionPane.showMessageDialog(rootPane, "Login failed. Please try again.");
            }
    }
               
                    
        void checkUser()   {   
                    if (usrRedirectUI.equals("admin")) {
                        adminUI aUI = new adminUI(conn,user);
                        aUI.setVisible(true);
                        usernameInput.setText("");
                        passwordInput.setText("");
                        
                    }
                    else if(usrRedirectUI.equals("candidate")) {
                        candidateUI candUI = new candidateUI(conn, user, pass);
                        candUI.setVisible(true);
                        usernameInput.setText("");
                        passwordInput.setText("");
                    }
                    else if(usrRedirectUI.equals("recruiter")) {
                        recruiterUI recUI = new recruiterUI(conn, user, pass);
                        recUI.setVisible(true);
                        usernameInput.setText("");
                        passwordInput.setText("");
                    }
                    else {
                        JOptionPane.showMessageDialog(rootPane, "Failed to retrieve user information.Please contact the administrator.");
                    }
        }
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        loginButton = new javax.swing.JButton();
        usernameInput = new javax.swing.JTextField();
        passwordInput = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        loginButton.setText("Log In");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });
        loginButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                loginButtonKeyPressed(evt);
            }
        });

        usernameInput.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                usernameInputFocusLost(evt);
            }
        });

        passwordInput.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                passwordInputFocusLost(evt);
            }
        });

        jLabel1.setText("Username:");

        jLabel2.setText("Password:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(usernameInput, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                            .addComponent(passwordInput)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(loginButton)))
                .addGap(42, 42, 42))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(usernameInput, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(passwordInput, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(32, 32, 32)
                .addComponent(loginButton)
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        createConnection();
    }//GEN-LAST:event_loginButtonActionPerformed

    private void usernameInputFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_usernameInputFocusLost
        user = usernameInput.getText();
    }//GEN-LAST:event_usernameInputFocusLost

    private void passwordInputFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwordInputFocusLost
        passArr = passwordInput.getPassword();
        pass = new String(passArr);
    }//GEN-LAST:event_passwordInputFocusLost

    private void loginButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_loginButtonKeyPressed
        createConnection();
    }//GEN-LAST:event_loginButtonKeyPressed

    
    
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton loginButton;
    private javax.swing.JPasswordField passwordInput;
    private javax.swing.JTextField usernameInput;
    // End of variables declaration//GEN-END:variables
}
