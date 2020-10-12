package me.axieum.mcmod.authme.mixin;

import me.axieum.mcmod.authme.AuthMe;
import me.axieum.mcmod.authme.api.Status;
import me.axieum.mcmod.authme.config.AuthMeConfig;
import me.axieum.mcmod.authme.gui.AuthScreen;
import me.axieum.mcmod.authme.gui.widget.AuthButtonWidget;
import me.axieum.mcmod.authme.util.SessionUtil;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.axieum.mcmod.authme.AuthMe.CONFIG;

@Mixin(MultiplayerScreen.class)
public abstract class MultiplayerScreenMixin extends Screen
{
    private static Status status = Status.UNKNOWN;
    private static TexturedButtonWidget authButton;

    protected MultiplayerScreenMixin(Text title) { super(title); }

    @Inject(method = "init", at = @At("HEAD"))
    private void init(CallbackInfo info)
    {
        // Inject the authenticate button at top left, using lock texture or fallback text
        AuthMe.LOGGER.debug("Injecting authentication button into multiplayer screen");
        authButton = new AuthButtonWidget(CONFIG.authButton.x,
                                          CONFIG.authButton.y,
                                          button -> this.client.openScreen(new AuthScreen(this)),
                                          (x, y) -> {
                                              // Sync configuration with updated button position
                                              CONFIG.authButton.x = x;
                                              CONFIG.authButton.y = y;
                                              AuthMeConfig.save();
                                          },
                                          new TranslatableText("gui.authme.multiplayer.button.auth"),
                                          this);
        this.addButton(authButton);

        // Fetch current session status
        if (!FabricLoader.getInstance().isDevelopmentEnvironment()) {
            MultiplayerScreenMixin.status = Status.UNKNOWN;
        }
        SessionUtil.getStatus().thenAccept(status -> MultiplayerScreenMixin.status = status);
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info)
    {
        // Draw status text/icon on button
        drawCenteredString(matrices,
                           this.client.textRenderer,
                           Formatting.BOLD + status.toString(),
                           authButton.x + authButton.getWidth(),
                           authButton.y - 1,
                           status.color);
    }
}
