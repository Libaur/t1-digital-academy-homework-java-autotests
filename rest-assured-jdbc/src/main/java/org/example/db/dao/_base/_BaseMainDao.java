package org.example.db.dao._base;

import lombok.extern.slf4j.Slf4j;
import org.example.env.Env;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.ColumnMappers;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

@Slf4j
public class _BaseMainDao {

    protected Jdbi jdbi;

    protected _BaseMainDao() {
        log.info("Инициализация базового DAO");

        jdbi = Jdbi.create(DataSourceProvider.getH2DataSource(Env.DB.DB_CONFIG));
        jdbi.installPlugin(new SqlObjectPlugin());
        jdbi.getConfig(ColumnMappers.class).setCoalesceNullPrimitivesToDefaults(false);

        testConnection();

        log.info("Инициализация базового DAO закончена");
    }

    private void testConnection() {
        try {
            jdbi.withHandle(handle ->
                    handle.createQuery("SELECT 1").mapTo(Integer.class).one()
            );
            log.info("Тестовое соединение с БД успешно");
        } catch (Exception e) {
            log.error("Ошибка тестового соединения с БД", e);
            throw new RuntimeException("Не удалось установить соединение с БД", e);
        }
    }
}