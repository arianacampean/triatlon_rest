package triatlon.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import triatlon.domain.Participant;
import triatlon.domain.Proba;
import triatlon.domain.Proba_Participanti;
import triatlon.repository.interfete.RepoPP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PPRepo implements RepoPP {

    private JdbcUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();

    public PPRepo(Properties prop) {
        logger.info("Initializing CarsDBRepository with properties: {} ", prop);
        //this.dbUtils = new JdbcUtils(prop);
    }
    @Override
    public Iterable<Proba_Participanti> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Proba_Participanti> prob = new ArrayList<>();
        try (PreparedStatement preStm = con.prepareStatement("select * from Proba_Participanti")) {
            try (ResultSet result = preStm.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id_pp");
                    int id_proba = result.getInt("id_proba");
                    int id_part = result.getInt("id_part");
                    int puncte = result.getInt("puncte_obtinute");
                    Proba_Participanti pr = new Proba_Participanti((long)id_proba,(long)id_part,puncte);
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
    public void add(Proba_Participanti elem) {
        logger.traceEntry("saving task {} ", elem);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStm = con.prepareStatement("insert into Proba_Participanti(id_proba,id_part,puncte_obtinute) values(?,?,?)")) {
            preStm.setInt(1, Math.toIntExact(elem.getId_proba()));
            preStm.setInt(2, Math.toIntExact(elem.getId_participant()));
            preStm.setInt(3, Math.toIntExact((long) elem.getPuncte_obtinute()));
            int result = preStm.executeUpdate();
            logger.trace("Saved {} instances", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error db+ex");
        }
        logger.traceExit();
    }

    @Override
    public void update(long id,Proba_Participanti entity) {

    }

    @Override
    public void delete(Long aLong) {
    }

    @Override
    public List<Proba_Participanti> findPP_codPart(Long cod_part) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Proba_Participanti>list=new ArrayList<>();

        try (PreparedStatement preStm = con.prepareStatement("select * from Proba_Participanti where id_part=?")) {
            preStm.setInt(1, Math.toIntExact(cod_part));
            try (ResultSet result = preStm.executeQuery()) {
                while (result.next()) {
                   int proba= result.getInt("id_proba");
                    int puncte=result.getInt("puncte_obtinute");
                    Proba_Participanti p=new Proba_Participanti((long) proba,cod_part,puncte);
                    p.setId((long) result.getInt("id_pp"));
                    list.add(p);
                }
            }

        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error db+ex");
        }
        logger.traceExit(list);
        return list;
    }

    @Override
    public List<Proba_Participanti> findPP_codProba(Long cod_proba) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Proba_Participanti>list=new ArrayList<>();
        try (PreparedStatement preStm = con.prepareStatement("select id_pp,id_part,puncte_obtinute from Proba_Participanti where id_proba=?")) {
            preStm.setInt(1, Math.toIntExact(cod_proba));
            try (ResultSet result = preStm.executeQuery()) {
                while (result.next()) {
                   int id_part=( result.getInt("id_part"));
                   int puncte=(result.getInt("puncte_obtinute"));
                    Proba_Participanti p=new Proba_Participanti((long) cod_proba, (long) id_part,puncte);
                    p.setId((long) result.getInt("id_pp"));
                    list.add(p);
                }
            }

        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error db+ex");
        }
        logger.traceExit(list);
        return list;
    }
}
