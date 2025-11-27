package org.example.db.dao._base;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.example.env.config.DBconfig;

import javax.sql.DataSource;

@UtilityClass
@Slf4j
public class DataSourceProvider {

    private static volatile HikariDataSource dataSource;

    public synchronized DataSource getH2DataSource(DBconfig config) {
        log.info("H2 datasource start");

        if (dataSource != null && !dataSource.isClosed()) {
            log.info("Используется существующий DataSource");
            return dataSource;
        }

        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(config.dbUrl());

        hikariConfig.setDriverClassName("org.h2.Driver");
        hikariConfig.setMaximumPoolSize(5);
        hikariConfig.setMinimumIdle(0);
        hikariConfig.setConnectionTimeout(50000);
        hikariConfig.setIdleTimeout(80000);
        hikariConfig.setMaxLifetime(1750000);

        hikariConfig.setConnectionTestQuery("SELECT 1");
        hikariConfig.setValidationTimeout(5000);

        hikariConfig.setLeakDetectionThreshold(60000);
        hikariConfig.setInitializationFailTimeout(-1);

        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        hikariConfig.addDataSourceProperty("socketTimeout", "30");
        hikariConfig.addDataSourceProperty("connectTimeout", "10");
        hikariConfig.addDataSourceProperty("tcpKeepAlive", "true");

        dataSource = new HikariDataSource(hikariConfig);
        log.info("H2 datasource config done");
        return dataSource;
    }

    public static void closeDataSource() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            log.info("DataSource закрыт");
        }
    }

    public static boolean isDataSourceRunning() {
        return dataSource != null && !dataSource.isClosed();
    }
}