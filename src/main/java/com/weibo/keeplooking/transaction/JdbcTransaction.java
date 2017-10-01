package com.weibo.keeplooking.transaction;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Show basic usage of JDBC transaction.
 * 
 * @author Johnny
 *
 */
public class JdbcTransaction {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(JdbcTransaction.class);
    private Connection connection;

    @Before
    public void setup() throws InstantiationException, IllegalAccessException,
            ClassNotFoundException, SQLException {
        this.connect();
        this.addAccount("Johnny", new BigDecimal(1000.00));
        this.addAccount("Mike", new BigDecimal(500.00));
    }

    @SuppressWarnings("static-access")
    @Test
    public void transfer() {
        try {
            Assert.assertTrue(connection.TRANSACTION_REPEATABLE_READ == connection
                    .getTransactionIsolation());

            LOGGER.info("Balance of Johnny is {}.", this.getBanlance("Johnny"));
            LOGGER.info("Balance of Mike is {}.", this.getBanlance("Mike"));
        } catch (SQLException e) {
            LOGGER.error("Failed to get balance.", e);
        }

        try {
            // for TRANSACTION_READ_COMMITTED: block update on any row ???
            // for TRANSACTION_REPEATABLE_READ (default): block insert & update on any row ???
            connection.setTransactionIsolation(connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);
            this.updateBalance("Johnny", new BigDecimal(-300.00));
            this.updateBalance("Mike", new BigDecimal(300.00));
        } catch (Exception e) {
            LOGGER.error("Transfering 300.00 from Johnny to Mike failed.", e);
            if (connection != null) {
                try {
                    LOGGER.info("Transaction is rolling back...");
                    connection.rollback();
                } catch (SQLException excep) {
                    LOGGER.error("Rolling back failed!!!", e);
                }
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOGGER.error("setAutoCommit to true failed.", e);
            }
        }

        try {
            LOGGER.info("Balance of Johnny is {}.", this.getBanlance("Johnny"));
            LOGGER.info("Balance of Mike is {}.", this.getBanlance("Mike"));
        } catch (SQLException e) {
            LOGGER.error("Failed to get balance.", e);
        }
    }

    @After
    public void clear() throws SQLException {
        this.deleteAll();
        this.connection.close();
    }

    @SuppressWarnings("unused")
    private void doSomethingError() throws InterruptedException {
        Thread.sleep(60000);
        LOGGER.info("Exception occurring here, should roll back now.");
        System.out.println(1 / 0);
    }

    private void addAccount(String name, BigDecimal balance)
            throws SQLException {
        String id = UUID.randomUUID().toString().substring(0, 32);
        String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date());

        String sql = "insert into bank_account values(?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, id);
        ps.setString(2, name);
        ps.setBigDecimal(3, balance);
        ps.setString(4, dateTime);
        ps.executeUpdate();
        ps.close();
    }

    private void updateBalance(String name, BigDecimal delta)
            throws SQLException {
        String sql = "update bank_account set balance=balance+? where name=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setBigDecimal(1, delta);
        ps.setString(2, name);
        ps.executeUpdate();
        ps.close();
    }

    private BigDecimal getBanlance(String name) throws SQLException {
        BigDecimal balance = null;
        String sql = "select balance from bank_account where name=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            balance = rs.getBigDecimal(1);
        }
        ps.close();
        return balance;
    }

    private void deleteAll() throws SQLException {
        String sql = "delete from bank_account where 1=1";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.executeUpdate();
        ps.close();
    }

    private void connect() throws InstantiationException,
            IllegalAccessException, ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        connection = DriverManager
                .getConnection(
                        "jdbc:mysql://localhost:3306/test?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&autoReconnect=true",
                        "root", "qwer1234");
    }

}
