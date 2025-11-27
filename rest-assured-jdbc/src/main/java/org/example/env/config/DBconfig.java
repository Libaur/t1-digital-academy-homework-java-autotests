package org.example.env.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"system:env", "system:properties", "classpath:config/st/dbConfig.properties"})
public interface DBconfig extends Config {

    @Key("db.url")
    String dbUrl();
}
