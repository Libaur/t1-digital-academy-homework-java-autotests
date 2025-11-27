package org.example.env;

import org.aeonbits.owner.ConfigFactory;
import org.example.env.config.DBconfig;
import org.example.env.config.ApiConfig;

public final class Env {

    public static class DB {

        public static final DBconfig DB_CONFIG = ConfigFactory.create(DBconfig.class);
    }

    public static class API {

        public static final ApiConfig API_CONFIG = ConfigFactory.create(ApiConfig.class);
    }
}
