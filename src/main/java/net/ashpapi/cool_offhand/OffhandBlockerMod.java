package net.ashpapi.cool_offhand;

import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(OffhandBlockerMod.MODID)
public class OffhandBlockerMod {
    public static final String MODID = "offhandblocker";

    public OffhandBlockerMod() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, OffhandConfig.SPEC, "offhand.toml");
        MinecraftForge.EVENT_BUS.addListener(this::onRegisterCommands);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerKeys);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        KeybindHandler.registerKeybinds();
    }

    private void registerKeys(final RegisterKeyMappingsEvent event) {
        KeybindHandler.registerKeys(event);
    }

    private void onRegisterCommands(RegisterCommandsEvent event) {
        OffhandBlockerCommand.register(event.getDispatcher());
    }
}