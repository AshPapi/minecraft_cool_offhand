    package net.ashpapi.cool_offhand;

    import net.minecraft.network.chat.Component;
    import net.minecraft.world.InteractionHand;
    import net.minecraft.world.item.ItemStack;
    import net.minecraftforge.event.entity.player.PlayerInteractEvent;
    import net.minecraftforge.eventbus.api.SubscribeEvent;
    import net.minecraftforge.fml.common.Mod;

    @Mod.EventBusSubscriber(modid = OffhandBlockerMod.MODID)
    public class OffhandBlocker {
        @SubscribeEvent
        public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
            if (event.getHand() == InteractionHand.OFF_HAND) {
                ItemStack stack = event.getItemStack();
                if (OffhandConfig.isBlocked(stack.getItem())) {
                    event.setCanceled(true);
                    event.getEntity().displayClientMessage(Component.translatable("message.offhandblocker.item_blocked"), true);
                }
            }
        }
    }