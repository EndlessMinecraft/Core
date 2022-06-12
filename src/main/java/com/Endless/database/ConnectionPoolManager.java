package com.Endless.database;

import com.Endless.ELCore;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.bukkit.plugin.Plugin;

    public class ConnectionPoolManager {
        private HikariDataSource dataSource;

        private String hostname;

        private String database;

        private String username;

        private String password;

        private int port;

        private int minimumConnections;

        private int maximumConnections;

        private long connectionTimeout;

        private String testQuery;

        public ConnectionPoolManager(Plugin plugin) {
            init();
            setupPool();
        }

        public void init() {
            this.hostname = ELCore.getInstance().getServerConfig().getString("database.host");
            this.username = ELCore.getInstance().getServerConfig().getString("database.user");
            this.password = ELCore.getInstance().getServerConfig().getString("database.password");
            this.port = 3306;
            this.database = ELCore.getInstance().getServerConfig().getString("database.database");
            this.minimumConnections = 1;
            this.maximumConnections = 5;
            this.connectionTimeout = 0L;
            this.testQuery = "SELECT 1";
        }

        public void setupPool() {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database);
            config.setDriverClassName("com.mysql.jdbc.Driver");
            config.setUsername(this.username);
            config.setPassword(this.password);
            config.setMinimumIdle(this.minimumConnections);
            config.setMaximumPoolSize(this.maximumConnections);
            config.setConnectionTimeout(this.connectionTimeout);
            config.setConnectionTestQuery(this.testQuery);
            this.dataSource = new HikariDataSource(config);
        }

        public Connection getConnection() throws SQLException {
            return this.dataSource.getConnection();
        }

        public void close(Connection conn, PreparedStatement ps, ResultSet res) {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException sQLException) {}
            if (ps != null)
                try {
                    ps.close();
                } catch (SQLException sQLException) {}
            if (res != null)
                try {
                    res.close();
                } catch (SQLException sQLException) {}
        }

        public void closePool() {
            if (this.dataSource != null && !this.dataSource.isClosed())
                this.dataSource.close();
        }
    }
