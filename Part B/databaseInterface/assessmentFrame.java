
package databaseInterface;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class assessmentFrame extends javax.swing.JFrame {

    private Connection conn;
    private String user;
    private Vector<Object> temp;
    private Vector<String> ids;
    private Vector<Vector<Object>> table;
    private String query;
    
    public assessmentFrame(Connection conn, String user) {
        
        this.conn = conn;
        this.user = user;
        Statement stmt;
        
        try {
            
            stmt = conn.createStatement();
            query = "select total_score.job_id, job.position, "
                    +"total_score.ranking, total_score.total_cands "
                    +"from total_score "
                    +"inner join job on total_score.job_id = job.id "
                    +"where total_score.cand_id='"+user+"';";
            ResultSet res;
            res = stmt.executeQuery(query);
            
            
            table = new Vector<Vector<Object>>();
           
            while (res.next()) {
                temp = new Vector<Object>();
                temp.add(res.getObject(1));
                temp.add(res.getObject(2));
                temp.add(res.getObject(3));
                temp.add(res.getObject(4));
                table.add(temp);
               
            }
            
            stmt.close();
            
            ids = new Vector<String>();
            ids.add("Job ID");
            ids.add("Position Description");
            ids.add("Your Ranking");
            ids.add("Number of Candidates");
            
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Failed to retrieve assessment results. Please try again.");
            Logger.getLogger(applyFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        initComponents();
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("< Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(table, ids));
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(54, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(109, 109, 109)
                .addComponent(jButton1)
                .addGap(58, 58, 58))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

 
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}







/*

select total_score.job_id, job.position, total_score.ranking, total_score.total_cands
from total_score
inner join job on total_score.job_id = job.id
where total_score.cand_id='cleogeo';

*/