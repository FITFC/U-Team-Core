package info.u_team.u_team_core.util.registry;

import java.util.function.Function;

import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.*;
import net.minecraft.tileentity.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.fml.client.registry.*;

@OnlyIn(Dist.CLIENT)
public class ClientRegistry {
	
	public static <T extends Entity> void registerEntityRenderer(EntityType<T> clazz, IRenderFactory<? super T> rendererFactory) {
		RenderingRegistry.registerEntityRenderingHandler(clazz, rendererFactory);
	}
	
	public static <T extends TileEntity> void registerSpecialTileEntityRenderer(TileEntityType<T> clazz, TileEntityRenderer<? super T> renderer) {
		registerSpecialTileEntityRenderer(clazz, dispatcher -> renderer);
	}
	
	public static <T extends TileEntity> void registerSpecialTileEntityRenderer(TileEntityType<T> clazz, Function<? super TileEntityRendererDispatcher, ? extends TileEntityRenderer<? super T>> rendererFactory) {
		net.minecraftforge.fml.client.registry.ClientRegistry.bindTileEntityRenderer(clazz, rendererFactory);
	}
	
	public static void registerKeybinding(KeyBinding key) {
		net.minecraftforge.fml.client.registry.ClientRegistry.registerKeyBinding(key);
	}
	
}
