package me.axieum.mcmod.authme.mixin;

import me.axieum.mcmod.authme.api.Status;
import me.axieum.mcmod.authme.util.SessionUtil;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.axieum.mcmod.authme.AuthMe.LOGGER;


@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin {
    private boolean hasDone = true;
    /**
     * Simply updates the status for the multiplayer screen to display easily,
     * and allow the player to directly join a world (and authed) without going first to
     * the MultiplayerScreen
     *
     * @param ci CallbackInfo supplied by mixin
     */
    @Inject(method = "init", at = @At("HEAD"))
    private void checkStatus(CallbackInfo ci){
        if (FabricLoader.getInstance().isDevelopmentEnvironment()  && hasDone){
            LOGGER.debug("Fetching Status for MultiplayerScreen");
            SessionUtil.getStatus();
            hasDone = false; //make sure we dont do this everytime we go to title screen, only the first time we go there
        }
    }
}
