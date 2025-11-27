package org.example.db.dao._base;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class DatabaseManager {

    public void initializeDatabase() {
        log.info("Инициализация подключения к базе данных...");
        DataSourceProvider.getH2DataSource(org.example.env.Env.DB.DB_CONFIG);
        log.info("Подключение к базе данных инициализировано");
    }

    public void shutdownDatabase() {
        log.info("Завершение работы с базой данных...");
        DataSourceProvider.closeDataSource();
        log.info("Работа с базой данных завершена");
    }

    public boolean isDatabaseAvailable() {
        return DataSourceProvider.isDataSourceRunning();
    }
}