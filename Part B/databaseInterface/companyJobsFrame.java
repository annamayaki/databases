
package databaseInterface;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class companyJobsFrame extends javax.swing.JFrame {

    private Connection conn;
    private String user;
    private Vector<Object> temp;
    private Vector<String> ids;
    private Vector<Vector<Object>> table;
    
    public companyJobsFrame(Connection conn, String user) {
        
        this.conn = conn;
        this.user = user;
        Statement stmt;
        Object o;
        
        try {
            stmt = conn.createStatement();
            String query = "select firm from recruiter where username='"+user+"';";
            ResultSet res;
            res = stmt.executeQuery(query);
            res.next();
            String firm = res.getString("firm");
            query = "select id,start_date,salary,position,"
                    + "edra,submission_date,recruiter,announce_date "
                    +"from job "
                    +"inner join recruiter on job.recruiter=recruiter.username "
                    +"where recruiter.firm='"+firm+"' order by recruiter;";
            res = stmt.executeQuery(query);
            table = new Vector<Vector<Object>>();
           
            while (res.next()) {
                temp = new Vector<Object>();
                temp.add(res.getObject("id"));
                temp.add(res.getObject("announce_date"));
                temp.add(res.getObject("start_date"));
                temp.add(res.getObject("submission_date"));
                temp.add(res.getObject("position"));
                temp.add(res.getObject("salary"));
                temp.add(res.getObject("edra"));
                temp.add(res.getObject("recruiter"));
                
                table.add(temp);
            }
            
            res.close();
            stmt.close();
            
            ids = new Vector<String>();
            ids.add("Job ID");
            ids.add("Announcement Date");
            ids.add("Start Date");
            ids.add("Submission Date");
            ids.add("Position Description");
            ids.add("Salary");
            ids.add("Location");
            ids.add("Recruiter");
            
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(rootPane, "Failed to retrieve company jobs. Please try again.");
                Logger.getLogger(applyFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        initComponents();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            table, ids
        ));
        jTable1.setRowSelectionAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("< Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 729, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(52, 52, 52))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
