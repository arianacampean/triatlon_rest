package triatlon.repository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import triatlon.domain.Arbitru;
import triatlon.repository.interfete.RepoArbitru;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ArbitruRepo implements RepoArbitru {

    private JdbcUtils dbUtils;
    private static final Logger logger=LogManager.getLogger();

    public ArbitruRepo(Properties prop) {
        logger.info("Initializing CarsDBRepository with properties: {} ", prop);
       // this.dbUtils = new JdbcUtils(prop);
    }

    //gaseste arbitru dupa username si parola
    @Override
    public Arbitru findArbitru(String usernamee, String parola) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        Arbitru arb=new Arbitru("","",usernamee,parola);
        try (PreparedStatement preStm = con.prepareStatement("select id_arb,nume,prenume from Arbitru where username=? and parola=?")) {
            preStm.setString(1,usernamee);
            preStm.setString(2,parola);
            try (ResultSet result = preStm.executeQuery()) {
                int id=result.getInt("id_arb");
                arb.setNume(result.getString("nume"));
                arb.setPrenume(result.getString("prenume"));
                arb.setId((long) id);

            }

        } catch (SQLException ex) {
            arb=null;
            logger.error(ex);
            System.err.println("Error db+ex");
        }
        logger.traceExit(arb);
        return arb;

    }



    @Override
    public Iterable<Arbitru> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Arbitru> arb = new ArrayList<>();
        try (PreparedStatement preStm = con.prepareStatement("select * from Arbitru")) {
            try (ResultSet result = preStm.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id_arb");
                    String nume = result.getString("nume");
                    String prennume = result.getString("prenume");
                    String username = result.getString("username");
                    String parola = result.getString("parola");
                    Arbitru ar = new Arbitru(nume, prennume, username,parola);
                    ar.setId((long) id);
                    arb.add(ar);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error db+ex");
        }
        logger.traceExit(arb);
        return arb;
    }

    @Override
    public void add(Arbitru elem) {
        logger.traceEntry("saving task {} ", elem);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStm = con.prepareStatement("insert into Arbitru(nume,prenume,parola) values(?,?,?)")) {
            preStm.setString(1, elem.getNume());
            preStm.setString(2, elem.getPrenume());
            preStm.setString(3, elem.getParola());
            int result = preStm.executeUpdate();
            logger.trace("Saved {} instances", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error db+ex");
        }
        logger.traceExit();
    }



    @Override
    public void update(long id,Arbitru entity) {

    }

    @Override
    public void delete(Long aLong) {

    }


}
