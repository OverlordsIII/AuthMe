package me.axieum.mcmod.authme.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.Comment;
@Config(name = "authmedev")
public class AuthMeDevConfig implements ConfigData {
    /**
     * Creates a development config. Only registered if in a dev env
     */
        @Comment("Instructs the mod to use the saved info that is stored below.")
        public boolean shouldUseSavedInfo = false;
        @Comment("Sets the Client's Current Session with a offline account. Reads from the username field")
        public boolean offlineDevMode = false;
        @Comment("The email of the mc account you want auto authed in dev")
        public String email = "";
        @Comment("The username of the mc account you want auto authed in dev. Only used in offline mode")
        public String username = "";
        @Comment("The password of the mc account you want auto authed in dev")
        public String password = "";
}

