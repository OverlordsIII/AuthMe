package me.axieum.mcmod.authme;

import me.axieum.mcmod.authme.config.AuthMeConfig;
import me.axieum.mcmod.authme.config.AuthMeDevConfig;
import me.axieum.mcmod.authme.util.SessionUtil;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import me.sargunvohra.mcmods.autoconfig1u.serializer.PartitioningSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutionException;

public class AuthMe implements ClientModInitializer
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final AuthMeConfig CONFIG = AuthMeConfig.init();
    public static AuthMeDevConfig DEV_CONFIG = null;
    @Override
    public void onInitializeClient() {
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            DEV_CONFIG = AutoConfig.register(
                    AuthMeDevConfig.class,
                    JanksonConfigSerializer::new)
                    .getConfig();
        }
    }
}
