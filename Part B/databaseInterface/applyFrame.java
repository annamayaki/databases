
package databaseInterface;

import static java.lang.Integer.parseInt;
import java.sql.Connection;  
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


public class applyFrame extends javax.swing.JFrame {

    
    private Connection conn;
    private String user;
    private int newId;
    private String idStr;
    private Object o;
    private Vector<Object> temp;
    private Vector<String> ids;
    private Vector<Vector<Object>> table;
    private ArrayList<Integer> jobId;
    private Boolean repeat = false;
    private String query;

    public applyFrame(Connection conn, String user) {
        
        this.conn = conn;
        this.user = user;
        Statement stmt;
        
        try {
            
            stmt = conn.createStatement();
            query = "select job.id, job.position, job.salary from job "
                    +"where job.id not in "
                    +"(select job.id from job "
                    +"inner join applies on job.id = applies.job_id "
                    +"where cand_usrname='"+user+"') "
                    +"and job.submission_date>CURRENT_DATE();";
            ResultSet res;
            res = stmt.executeQuery(query);
            table = new Vector<Vector<Object>>();
            jobId = new ArrayList<Integer>();
            while (res.next()) {
                temp = new Vector<Object>();
                o = res.getObject("id");
                temp.add(o);
                temp.add(res.getObject("position"));
                temp.add(res.getObject("salary"));
                int k = (Integer) o;
                System.out.println(k);
                jobId.add(k);
                System.out.println(jobId.get(0));
                table.add(temp);
            }
            stmt.close();
            ids = new Vector<String>();
            ids.add("Job ID");
            ids.add("Position Description");
            ids.add("Salary");
            
            
            initComponents();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Failed to retrieve open positions. Please try again.");
            Logger.getLogger(applyFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(table, ids));
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("Apply for Job");

        jButton1.setText("Apply");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField1.setText("[ job id ]");
        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField1FocusLost(evt);
            }
        });
        jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField1MouseClicked(evt);
            }
        });

        jButton2.setText("< Back");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(157, 157, 157))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addComponent(jButton2)
                .addContainerGap(80, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseClicked
        jTextField1.setText("");
    }//GEN-LAST:event_jTextField1MouseClicked

    private void jTextField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusLost
        idStr = jTextField1.getText();
        if (!idStr.equals("")){
            newId = Integer.parseInt(idStr);
        }
    }//GEN-LAST:event_jTextField1FocusLost

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (jobId.contains(newId)){
            try {
                Statement stmt = conn.createStatement();
                String query1 = "insert into applies(cand_usrname, job_id) values('"
                        +user+"', '"+newId+"');";
                stmt.executeUpdate(query1);
                stmt.close();
                repeat=true;
                
            } catch (SQLException ex) {
                Logger.getLogger(applyFrame.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(rootPane, "Appliction submission failed. Please try again.");
            }
            
            JOptionPane.showMessageDialog(rootPane, "Appliction submitted successfully.");
            if (repeat){
                try {
                    Statement stmt = conn.createStatement();
                    ResultSet res;
                    res = stmt.executeQuery(query);
                    table.clear();
                    while (res.next()) {
                        temp = new Vector<Object>();
                        temp.add(res.getObject("id"));
                        temp.add(res.getObject("position"));
                        temp.add(res.getObject("salary"));
                        table.add(temp);
                    }
                    stmt.close();
                    jobId.remove(jobId.indexOf(newId));
                    SwingUtilities.updateComponentTreeUI(this);
                    
                } catch (SQLException ex) {
                    Logger.getLogger(applyFrame.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(rootPane, "Application submitted successfully. UI failed to reload.");
                }
            }
        }
        else {
            JOptionPane.showMessageDialog(rootPane, "Please select a valid job ID.");
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

  


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
