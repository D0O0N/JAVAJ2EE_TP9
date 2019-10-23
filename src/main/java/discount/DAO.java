/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package discount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author pedago
 */
public class DAO {
    protected final DataSource myDataSource;

    /**
     *
     * @param dataSource la source de données à utiliser
     */
    public DAO(DataSource dataSource) {
        this.myDataSource = dataSource;
    }
    public int deleteTauxDeRemise(int customerId) throws DAOException {

        // Une requête SQL paramétrée
        String sql = "DELETE FROM CUSTOMER WHERE CUSTOMER_ID = ?";
        try (   Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)
        ) {
                // Définir la valeur du paramètre
                stmt.setInt(1, customerId);

                return stmt.executeUpdate();

        }  catch (SQLException ex) {
                Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
                throw new DAOException(ex.getMessage());
        }
    }
    public ArrayList<CodeEntity> getAllCode(){
        ArrayList<CodeEntity> allCode = new ArrayList<CodeEntity>();
        String sql = "SELECT (*) FROM DISCOUNT_CODE ";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

                try (ResultSet rs = stmt.executeQuery()) {
                        while (rs.next()) { // Tant qu'il y a des enregistrements
                                allCode.add(new CodeEntity(rs.getString("DISCOUNT_CODE"),rs.getFloat("RATE")));
                        }
                }
        }  catch (SQLException ex) {
                Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
        }
        return allCode;
    }
    
}
