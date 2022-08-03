package triatlon.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import triatlon.domain.Proba;
import triatlon.repository.interfete.RepoProba;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
@Component
public class ProbaRepo implements RepoProba {


    private JdbcUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();

    public ProbaRepo() {
        Properties props=new Properties();
        try {
            props.load(ProbaRepo.class.getResourceAsStream("/triatlon/triatlonserver.properties"));
            System.out.println("Server properties set. ");
            props.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatserver.properties "+e);
            return;
        }

        logger.info("Initializing CarsDBRepository with properties: {} ", props);
        this.dbUtils = new JdbcUtils(props);
    }

    @Override
    public Iterable<Proba> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Proba> prob = new ArrayList<>();
        try (PreparedStatement preStm = con.prepareStatement("select * from Proba")) {
            try (ResultSet result = preStm.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id_proba");
                    String nume = result.getString("nume");
                    int id_arb=result.getInt("id_arb");
                    Proba pr = new Proba(nume, (long) id_arb);
                    pr.setId((long) id);
                    prob.add(pr);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error db+ex");
        }
        logger.traceExit(prob);
        return prob;

    }

    @Override
    public void add(Proba elem) {
        logger.traceEntry("saving task {} ", elem);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStm = con.prepareStatement("insert into Proba(nume,id_arb) values(?,?)")) {
            preStm.setString(1, elem.getNume());
            preStm.setInt(2, Math.toIntExact(elem.getId_arb()));
            int result = preStm.executeUpdate();
            logger.trace("Saved {} instances", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error db+ex");
        }
        logger.traceExit();
    }

    @Override
    public void update(long id,Proba entity) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        Proba p=new Proba(" ",null);
        try (PreparedStatement preStm = con.prepareStatement("update Proba set nume=?,id_arb=? where id_proba=?")) {
            preStm.setString(1, entity.getNume());
            preStm.setInt(2, Math.toIntExact(entity.getId_arb()));
            preStm.setInt(3, Math.toIntExact(id));
            preStm.executeUpdate();


        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error db+ex");
        }
        logger.traceExit(p);

    }


    @Override
    public void delete(Long id) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStm = con.prepareStatement("delete from Proba where id_proba=?")) {
            preStm.setInt(1, Math.toIntExact(id));
            int result = preStm.executeUpdate();
            logger.trace("Deleted {} instances", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error db+ex");
        }
        logger.traceExit();
    }

    @Override
    public Proba findProba(long id) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        Proba p=new Proba("",null);
        try (PreparedStatement preStm = con.prepareStatement("select nume,id_arb from Proba where id_proba=?")) {
            preStm.setInt(1, Math.toIntExact(id));
            try (ResultSet result = preStm.executeQuery()) {
                p.setNume(result.getString("nume"));
                p.setId_arb((long) result.getInt("id_arb"));
                p.setId((long) id);

            }

        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error db+ex");
        }
        logger.traceExit(p);
        return p;
    }
}
