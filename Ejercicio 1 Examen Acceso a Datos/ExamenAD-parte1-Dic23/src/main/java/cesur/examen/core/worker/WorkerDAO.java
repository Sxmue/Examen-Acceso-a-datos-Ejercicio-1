package cesur.examen.core.worker;

import cesur.examen.core.common.DAO;
import cesur.examen.core.common.JDBCUtils;
import lombok.extern.java.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * EXAMEN DE ACCESO A DATOS
 * Diciembre 2023
 *
 * Nombre del alumno:Samuel Leiva Álvarez
 * Fecha:11/12/23
 *
 * No se permite escribir en consola desde las clases DAO, Service y Utils usando System.out.
 * En su lugar, usa log.info(), log.warning() y log.severe() para mostrar información interna
 * o para seguir la traza de ejecución.
 */
@Log public class WorkerDAO implements DAO<Worker> {

    /* Please, use this constants for the queries */
    private final String QUERY_ORDER_BY = "Select * from trabajador order by desde";
    private final String QUERY_BY_DNI = "Select * from trabajador where dni=?";
    private final String UPDATE_BY_ID = "Update trabajador set nombre=?,dni=?,desde=? where id=?";

    @Override
    public Worker save(Worker worker) {
        return null;
    }

    /**
     * Update Worker in database.
     * Remember that date objects in jdbc should be converted to sql.Date type.
     * @param worker
     * @return the updated worker or null if the worker does not exist in database.
     */
    @Override
    public Worker update(Worker worker) {
        Worker out = null;

        /* Make implementation here ...  */

        try (PreparedStatement pst = JDBCUtils.getConn().prepareStatement(UPDATE_BY_ID)) {

            pst.setString(1, worker.getName());
            pst.setString(2, worker.getDni());
            pst.setDate(3, JDBCUtils.dateUtilToSQL(worker.getFrom()));
            pst.setLong(4, worker.getId());

            int result = pst.executeUpdate();

            if (result == 1) {
                out=worker;
            }
            log.info("Updateado correctamente");

        }catch (SQLException e) {
            log.severe("Error in getWorkerById()");
            log.severe("Error UPDATEANDO");
            throw new RuntimeException(e);
        }

            return out;
    }

    @Override
    public boolean remove(Worker worker) {
        return false;
    }

    @Override
    public Worker get(Long id) {
        return null;
    }

    /**
     * Retrieve the worker that has this dni. Null if there is no wrker.
     * @param dni
     * @return
     */
    public Worker getWorkerByDNI(String dni) {

        /* Implemented for your pleasure */

        if( JDBCUtils.getConn()==null){
            throw new RuntimeException("Connection is not created!");
        }

        Worker out = null;

        try( PreparedStatement st = JDBCUtils.getConn().prepareStatement(QUERY_BY_DNI) ){
            st.setString(1,dni);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                Worker w = new Worker();
                w.setId( rs.getLong("id") );
                w.setName( rs.getString("nombre"));
                w.setDni( rs.getString("dni"));
                w.setFrom( rs.getDate("desde"));
                out=w;
            }
        } catch (SQLException e) {
            log.severe("Error in getWorkerById()");
            throw new RuntimeException(e);
        }
        return out;
    }

    @Override
    public List<Worker> getAll() {
        return null;
    }

    /**
     * Get a list with all workers, ordered by from column.
     * The first is the oldest worker and the last are the newest.
     * If there is no worker, the list is empty.
     * @return
     */
    public List<Worker> getAllOrderByFrom(){
        ArrayList<Worker> out = new ArrayList<>();

        /* Make implementation here ...  */

        try (Statement st = JDBCUtils.getConn().createStatement()) {

            ResultSet rs = st.executeQuery(QUERY_ORDER_BY);


            while (rs.next()) {

                Worker w = new Worker();

                w.setDni(rs.getString("dni"));
                w.setFrom(rs.getDate("desde"));
                w.setId(rs.getLong("id"));
                w.setName(rs.getString("nombre"));

                //Se lo añadimos al arraylist
                out.add(w);


            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return out;

    }
}
