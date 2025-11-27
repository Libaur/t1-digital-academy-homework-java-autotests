package org.example.env.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"system:env", "system:properties", "classpath:config/st/apiConfig.properties"})
public interface ApiConfig extends Config {

    @Key("api.baseUrl")
    String baseUrl();
}
