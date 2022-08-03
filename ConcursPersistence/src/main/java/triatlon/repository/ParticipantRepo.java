package triatlon.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import triatlon.domain.Arbitru;
import triatlon.domain.Participant;
import triatlon.repository.interfete.RepoParticipanti;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ParticipantRepo implements RepoParticipanti {

    private JdbcUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();

    public ParticipantRepo(Properties prop) {
        logger.info("Initializing CarsDBRepository with properties: {} ", prop);
        //this.dbUtils = new JdbcUtils(prop);
    }


    @Override
    public Iterable<Participant> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Participant>parti= new ArrayList<>();
        try (PreparedStatement preStm = con.prepareStatement("select * from Participant")) {
            try (ResultSet result = preStm.executeQuery()) {
                while (result.next()) {
                    int id=result.getInt("id_part");
                    String nume = result.getString("nume");
                    String prennume = result.getString("prenume");
                   Participant part=new Participant(nume,prennume);
                    part.setId((long) id);
                    parti.add(part);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error db+ex");
        }
        logger.traceExit(parti);
        return parti;
    }

    @Override
    public void add(Participant elem) {
        logger.traceEntry("saving task {} ", elem);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStm = con.prepareStatement("insert into Participant(nume,prenume) values(?,?)")) {
            preStm.setString(1, elem.getNume());
            preStm.setString(2, elem.getPrenume());
            int result = preStm.executeUpdate();
            logger.trace("Saved {} instances", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error db+ex");
        }
        logger.traceExit();
    }




    @Override
    public void update(long id,Participant entity) {

    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public Participant findParticipant(Long cod) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        Participant p=new Participant("","");
        try (PreparedStatement preStm = con.prepareStatement("select nume,prenume from Participant where id_part=?")) {
            preStm.setInt(1, Math.toIntExact(cod));
            try (ResultSet result = preStm.executeQuery()) {
                p.setNume(result.getString("nume"));
                p.setPrenume(result.getString("prenume"));
                p.setId((long) cod);

            }

        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error db+ex");
        }
        logger.traceExit(p);
        return p;

    }
}
