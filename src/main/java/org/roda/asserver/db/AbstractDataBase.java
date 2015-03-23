package org.roda.asserver.db;

/**
 * Created by kvvn on 3/23/15.
 */

import java.io.Closeable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractDataBase implements Closeable {

    private Connection connection;
    private String driver;
    private String url;
    private String user;
    private String password;

    public void initConnection(DataBaseConnectionData connection)
            throws Exception {
        initConnection(connection.DRIVER, connection.URL, connection.USER,
                connection.PASS);
    }

    public void initConnection(String driver, String url, String user,
                               String password) throws Exception {

        this.driver = driver;
        this.url = url;
        this.user = user;
        this.password = password;

        Class.forName(driver).newInstance();
        connection = DriverManager.getConnection(url, user, password);
        if (connection.isClosed()) {
            throw new DataBaseDriverException("Can't connect to data base");
        }
    }

    public void reconnect() throws Exception {
        if (connection != null && !connection.isValid(10)/* connection.isClosed() */) {

            Class.forName(driver).newInstance();
            connection = DriverManager.getConnection(url, user, password);
            if (connection.isClosed()) {
                throw new DataBaseDriverException("Can't connect to data base");
            }
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
        }
    }

    public void transaction() throws DataBaseDriverException {
        try {
            reconnect();
            connection.setAutoCommit(false);
        } catch (Exception e) {
            throw new DataBaseDriverException("Start transaction error", e);
        }
    }

    public void commit() throws DataBaseDriverException {
        try {
            connection.commit();
        } catch (SQLException e) {
            rollback();
            throw new DataBaseDriverException("Commit error", e);
        }
        try {
            if (!connection.getAutoCommit()) {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new DataBaseDriverException("Set auto commit 'true' error", e);
        }
    }

    public void rollback() throws DataBaseDriverException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new DataBaseDriverException("Rollback transaction error", e);
        }
    }

    public void quietRollback() {
        try {
            if (!connection.getAutoCommit()) {
                connection.rollback();
            }
        } catch (SQLException e) {
            // e.printStackTrace();
        }
    }

    public Savepoint createSavePoint() throws Exception {
        return connection.setSavepoint();
    }

    public Savepoint createSavePoint(String name) throws Exception {
        return connection.setSavepoint(name);
    }

    public void rollback(Savepoint savepoint) throws Exception {
        connection.rollback(savepoint);
    }

    public void releaseSavePoint(Savepoint savepoint) throws Exception {
        connection.releaseSavepoint(savepoint);
    }

    protected PreparedStatement getPreparedStatement(String sql)
            throws DataBaseDriverException {
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new DataBaseDriverException("Get 'PreparedStatement' error",
                    e);
        }
    }

    protected PreparedStatement getPreparedStatement(String sql,
                                                     boolean returnGeneratedKeys) throws DataBaseDriverException {
        try {
            if (returnGeneratedKeys) {
                return connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
            }
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new DataBaseDriverException("Get 'PreparedStatement' error",
                    e);
        }
    }

    protected PreparedStatement getPreparedStatement(String sql, String... args)
            throws DataBaseDriverException {
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setString(i + 1, prepareString(args[i]));
            }
            return ps;
        } catch (SQLException e) {
            throw new DataBaseDriverException("Get 'PreparedStatement' error",
                    e);
        }
    }

    protected PreparedStatement getPreparedStatement(String sql,
                                                     boolean returnGeneratedKeys, String... args)
            throws DataBaseDriverException {
        try {
            PreparedStatement ps;
            if (returnGeneratedKeys) {
                ps = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
            } else {
                ps = connection.prepareStatement(sql);
            }
            for (int i = 0; i < args.length; i++) {
                ps.setString(i + 1, prepareString(args[i]));
            }
            return ps;
        } catch (SQLException e) {
            throw new DataBaseDriverException("Get 'PreparedStatement' error",
                    e);
        }
    }

    protected void execute(String sql) throws DataBaseDriverException {
        try {
            Statement ps = connection.createStatement();
            ps.execute(sql);
        } catch (SQLException e) {
            throw new DataBaseDriverException("Execute query error", e);
        }
    }

    protected void execute(String sql, String... args)
            throws DataBaseDriverException {
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setString(i + 1, prepareString(args[i]));
            }
            ps.execute();
        } catch (SQLException e) {
            throw new DataBaseDriverException("Execute query error", e);
        }
    }

    protected int executeUpdate(String sql) throws DataBaseDriverException {
        try {
            Statement ps = connection.createStatement();
            return ps.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataBaseDriverException("Execute update query error", e);
        }
    }

    protected int executeUpdate(String sql, String... args)
            throws DataBaseDriverException {
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setString(i + 1, prepareString(args[i]));
            }
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseDriverException("Execute update query error", e);
        }
    }

    protected List<Map<String, String>> executeQuery(String sql)
            throws DataBaseDriverException {
        try {
            Statement ps = connection.createStatement();
            ResultSet rs = ps.executeQuery(sql);
            return toList(rs);
        } catch (Exception e) {
            throw new DataBaseDriverException("Execute query error", e);
        }
    }

    protected List<Map<String, String>> executeQuery(String sql, String... args)
            throws DataBaseDriverException {
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setString(i + 1, prepareString(args[i]));
            }
            ResultSet rs = ps.executeQuery();
            return toList(rs);
        } catch (Exception e) {
            throw new DataBaseDriverException("Execute query error", e);
        }
    }

    protected ResultSet getResultSet(String sql) throws DataBaseDriverException {
        try {
            Statement ps = connection.createStatement();
            return ps.executeQuery(sql);
        } catch (SQLException e) {
            throw new DataBaseDriverException("Execute query error", e);
        }
    }

    protected ResultSet getResultSet(String sql, String... args)
            throws DataBaseDriverException {
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setString(i + 1, prepareString(args[i]));
            }
            return ps.executeQuery();
        } catch (SQLException e) {
            throw new DataBaseDriverException("Execute query error", e);
        }
    }

    protected CallableStatement getCallableStatement(String sql)
            throws DataBaseDriverException {
        try {
            return connection.prepareCall(sql);
        } catch (SQLException e) {
            throw new DataBaseDriverException("Prepare call error", e);
        }
    }

    protected void executeCall(String sql) throws DataBaseDriverException {
        try {
            CallableStatement cs = connection.prepareCall(sql);
            cs.execute();
        } catch (SQLException e) {
            throw new DataBaseDriverException("Execute query error", e);
        }
    }

    protected void executeCall(String sql, String... args)
            throws DataBaseDriverException {
        try {
            CallableStatement cs = connection.prepareCall(sql);
            for (int i = 0; i < args.length; i++) {
                cs.setString(i + 1, prepareString(args[i]));
            }
            cs.execute();
        } catch (SQLException e) {
            throw new DataBaseDriverException("Execute query error", e);
        }
    }

    protected List<Map<String, String>> callQuery(String sql)
            throws DataBaseDriverException {
        try {
            CallableStatement cs = connection.prepareCall(sql);
            ResultSet rs = cs.executeQuery();
            return toList(rs);
        } catch (Exception e) {
            throw new DataBaseDriverException("Execute call query error", e);
        }
    }

    protected List<Map<String, String>> callQuery(String sql, String... args)
            throws DataBaseDriverException {
        try {
            CallableStatement cs = connection.prepareCall(sql);
            for (int i = 0; i < args.length; i++) {
                cs.setString(i + 1, prepareString(args[i]));
            }
            ResultSet rs = cs.executeQuery();
            return toList(rs);
        } catch (Exception e) {
            throw new DataBaseDriverException("Execute call query error", e);
        }
    }

    protected ResultSet getCallResultSet(String sql)
            throws DataBaseDriverException {
        try {
            CallableStatement cs = connection.prepareCall(sql);
            return cs.executeQuery();
        } catch (Exception e) {
            throw new DataBaseDriverException("Execute call query error", e);
        }
    }

    protected ResultSet getCallResultSet(String sql, String... args)
            throws DataBaseDriverException {
        try {
            CallableStatement cs = connection.prepareCall(sql);
            for (int i = 0; i < args.length; i++) {
                cs.setString(i + 1, prepareString(args[i]));
            }
            return cs.executeQuery();
        } catch (Exception e) {
            throw new DataBaseDriverException("Execute call query error", e);
        }
    }

    private List<Map<String, String>> toList(ResultSet rs) throws Exception {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        while (rs.next()) {
            Map<String, String> data = new HashMap<String, String>();
            for (int i = 1; i <= columns; ++i) {
                data.put(md.getColumnName(i), rs.getString(i));
            }
            list.add(data);
        }
        return list;
    }

    private String prepareString(String sql) {
        sql = sql.replaceAll("'", "''");
        return sql;
    }

}