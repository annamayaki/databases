
package databaseInterface;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class showInterviewFrame extends javax.swing.JFrame {
    private Connection conn;
    private String user;
    private Vector<Object> temp;
    private Vector<String> ids;
    private Vector<Vector<Object>> table;
    private ArrayList<Integer> jobId;
    private String query;
    
    public showInterviewFrame(Connection conn, String user) {
        this.conn = conn;
        this.user = user;
        Statement stmt;
       
        
        try {
            
            stmt = conn.createStatement();
            query = "select dur,start_time,personality,comments,candidate_username,job "+
                    "from interview "+
                    "inner join job on job.id=interview.job "+
                    "where job.recruiter='"+user+"';";
            ResultSet res;
            res = stmt.executeQuery(query);
            table = new Vector<Vector<Object>>();
            jobId = new ArrayList<Integer>();
            while (res.next()) {
                temp = new Vector<Object>();
             
                temp.add(res.getObject(1));
                temp.add(res.getObject(2));
                temp.add(res.getObject(3));
                temp.add(res.getObject(4));
                temp.add(res.getObject(5));
                temp.add(res.getObject(6));
        
                table.add(temp);
            }
            stmt.close();
            ids = new Vector<String>();
            ids.add("Duration");
            ids.add("Start Time");
            ids.add("Personality");
            ids.add("Comments");
            ids.add("Candidate");
            ids.add("Job ID");
            
            
            initComponents();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Failed to retrieve interviews. Please try again.");
            Logger.getLogger(applyFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(table,ids
        ));
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(25, 25, 25))
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
