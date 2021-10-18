package info.u_team.u_team_core.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import info.u_team.u_team_core.api.sync.DataHolder;
import info.u_team.u_team_core.intern.init.UCoreNetwork;
import info.u_team.u_team_core.intern.network.BufferPropertyContainerMessage;
import info.u_team.u_team_core.screen.UContainerScreen;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import net.minecraftforge.items.IItemHandler;

/**
 * A basic menu with synchronization capabilities that implements the {@link FluidContainerMenu}.
 *
 * @author HyCraftHD
 */
public abstract class UContainerMenu extends FluidContainerMenu {
	
	/**
	 * Server -> Client
	 */
	private final List<DataHolder> syncServerToClient;
	
	/**
	 * Client -> Server
	 */
	private final List<DataHolder> syncClientToServer;
	
	/**
	 * Creates a new container. Must be implemented by a sub class to be used.
	 *
	 * @param menuType Menu type
	 * @param containerId Container id
	 */
	protected UContainerMenu(MenuType<?> menuType, int containerId) {
		super(menuType, containerId);
		syncServerToClient = new ArrayList<>();
		syncClientToServer = new ArrayList<>();
	}
	
	/**
	 * Adds a new {@link DataHolder} that will sync values from the server to the client.
	 *
	 * @param holder Buffer reference holder
	 * @return The buffer reference holder
	 */
	protected <E extends DataHolder> E addServerToClientTracker(E holder) {
		syncServerToClient.add(holder);
		return holder;
	}
	
	/**
	 * Adds a new {@link DataHolder} that will sync values from the client to the server. <br />
	 * <br />
	 * THE AUTO SYNC ONLY WORKS IF YOU USE AN IMPLEMENTION OF {@link UContainerScreen}. If not you must manually call
	 * {@link #updateTrackedServerToClient()} every time you update values on the client that should be synced to the
	 * server.
	 *
	 * @param holder Buffer reference holder
	 * @return The buffer reference holder
	 */
	protected <E extends DataHolder> E addClientToServerTracker(E holder) {
		syncClientToServer.add(holder);
		return holder;
	}
	
	/**
	 * INTERNAL. Called by the packet handler to update the values on the right side.
	 *
	 * @param message Message
	 * @param side Side to handle the packet on
	 */
	public void updateValue(BufferPropertyContainerMessage message, LogicalSide side) {
		final var property = message.getProperty();
		final var buffer = message.getBuffer();
		if (side == LogicalSide.CLIENT) {
			syncServerToClient.get(property).set(buffer);
		} else if (side == LogicalSide.SERVER) {
			syncClientToServer.get(property).set(buffer);
		}
	}
	
	/**
	 * We use this method to send the tracked values to the client
	 */
	@Override
	public void broadcastChanges() {
		super.broadcastChanges();
		
		final List<Connection> networkManagers = containerListeners.stream() //
				.filter(listener -> listener instanceof ServerPlayer) //
				.map(listener -> ((ServerPlayer) listener).connection.getConnection()) //
				.collect(Collectors.toList());
		getDirtyMap(syncServerToClient).forEach((property, holder) -> {
			UCoreNetwork.NETWORK.send(PacketDistributor.NMLIST.with(() -> networkManagers), new BufferPropertyContainerMessage(containerId, property, holder.get()));
		});
	}
	
	/**
	 * We use this method to send the tracked values to the server
	 *
	 * @see #addClientToServerTracker(DataHolder)
	 */
	public void updateTrackedServerToClient() {
		getDirtyMap(syncClientToServer).forEach((property, holder) -> {
			UCoreNetwork.NETWORK.send(PacketDistributor.SERVER.noArg(), new BufferPropertyContainerMessage(containerId, property, holder.get()));
		});
	}
	
	/**
	 * Returns a map with all {@link DataHolder} that are dirty. The key is the property index.
	 *
	 * @param list The list of {@link DataHolder}
	 * @return A map with dirty values
	 */
	private Map<Integer, DataHolder> getDirtyMap(List<DataHolder> list) {
		return IntStream.range(0, list.size()) //
				.filter(index -> list.get(index).checkAndClearUpdateFlag()) //
				.boxed() //
				.collect(Collectors.toMap(Function.identity(), index -> list.get(index)));
	}
	
	/**
	 * Player can interact with this container
	 */
	@Override
	public boolean stillValid(Player player) {
		return true;
	}
	
	/**
	 * This methods adds a player inventory to the container.
	 *
	 * @param playerInventory Player inventory
	 * @param x Start x
	 * @param y Start y
	 */
	protected void addPlayerInventory(Inventory playerInventory, int x, int y) {
		for (var height = 0; height < 4; height++) {
			for (var width = 0; width < 9; width++) {
				if (height == 3) {
					addSlot(new Slot(playerInventory, width, width * 18 + x, height * 18 + 4 + y));
					continue;
				}
				addSlot(new Slot(playerInventory, width + height * 9 + 9, width * 18 + x, height * 18 + y));
			}
		}
	}
	
	/**
	 * This methods can add any {@link IInventory} to the container. You can specialize the inventory height (slot rows) and
	 * width (slot columns).
	 *
	 * @param inventory Some inventory
	 * @param inventoryHeight Slot rows
	 * @param inventoryWidth Slot columns
	 * @param x Start x
	 * @param y Start y
	 */
	protected void appendInventory(Container inventory, int inventoryHeight, int inventoryWidth, int x, int y) {
		appendInventory(inventory, 0, inventoryHeight, inventoryWidth, x, y);
	}
	
	/**
	 * This methods can add any {@link IInventory} to the container. You can specialize the inventory height (slot rows) and
	 * width (slot columns). You must supplier a function that create a slot. With this you can set your own slot.
	 * implementations.
	 *
	 * @param inventory Some inventory
	 * @param function Function to create a slot.
	 * @param inventoryHeight Slot rows
	 * @param inventoryWidth Slot columns
	 * @param x Start x
	 * @param y Start y
	 */
	protected void appendInventory(Container inventory, SlotInventoryFunction function, int inventoryHeight, int inventoryWidth, int x, int y) {
		appendInventory(inventory, function, 0, inventoryHeight, inventoryWidth, x, y);
	}
	
	/**
	 * This methods can add any {@link IInventory} to the container. You can specialize the inventory height (slot rows) and
	 * width (slot columns).
	 *
	 * @param inventory Some inventory
	 * @param startIndex Start index of the handler
	 * @param inventoryHeight Slot rows
	 * @param inventoryWidth Slot columns
	 * @param x Start x
	 * @param y Start y
	 */
	protected void appendInventory(Container inventory, int startIndex, int inventoryHeight, int inventoryWidth, int x, int y) {
		appendInventory(inventory, Slot::new, startIndex, inventoryHeight, inventoryWidth, x, y);
	}
	
	/**
	 * This methods can add any {@link IInventory} to the container. You can specialize the inventory height (slot rows) and
	 * width (slot columns). You must supplier a function that create a slot. With this you can set your own slot.
	 * implementations.
	 *
	 * @param inventory Some inventory
	 * @param startIndex Start index of the handler
	 * @param function Function to create a slot.
	 * @param inventoryHeight Slot rows
	 * @param inventoryWidth Slot columns
	 * @param x Start x
	 * @param y Start y
	 */
	protected void appendInventory(Container inventory, SlotInventoryFunction function, int startIndex, int inventoryHeight, int inventoryWidth, int x, int y) {
		for (var height = 0; height < inventoryHeight; height++) {
			for (var width = 0; width < inventoryWidth; width++) {
				addSlot(function.getSlot(inventory, startIndex + (width + height * inventoryWidth), width * 18 + x, height * 18 + y));
			}
		}
	}
	
	/**
	 * This methods can add any {@link IItemHandler} to the container. You can specialize the inventory height (slot rows)
	 * and width (slot columns).
	 *
	 * @param handler Some item handler
	 * @param inventoryHeight Slot rows
	 * @param inventoryWidth Slot columns
	 * @param x Start x
	 * @param y Start y
	 */
	protected void appendInventory(IItemHandler handler, int inventoryHeight, int inventoryWidth, int x, int y) {
		appendInventory(handler, 0, inventoryHeight, inventoryWidth, x, y);
	}
	
	/**
	 * This methods can add any {@link IItemHandler} to the container. You can specialize the inventory height (slot rows)
	 * and width (slot columns). You must supplier a function that create a slot. With this you can set your own slot.
	 * implementations.
	 *
	 * @param handler Some item handler
	 * @param function Function to create a slot.
	 * @param inventoryHeight Slot rows
	 * @param inventoryWidth Slot columns
	 * @param x Start x
	 * @param y Start y
	 */
	protected void appendInventory(IItemHandler handler, SlotHandlerFunction function, int inventoryHeight, int inventoryWidth, int x, int y) {
		appendInventory(handler, function, 0, inventoryHeight, inventoryWidth, x, y);
	}
	
	/**
	 * This methods can add any {@link IItemHandler} to the container. You can specialize the inventory height (slot rows)
	 * and width (slot columns).
	 *
	 * @param handler Some item handler
	 * @param startIndex Start index of the handler
	 * @param inventoryHeight Slot rows
	 * @param inventoryWidth Slot columns
	 * @param x Start x
	 * @param y Start y
	 */
	protected void appendInventory(IItemHandler handler, int startIndex, int inventoryHeight, int inventoryWidth, int x, int y) {
		appendInventory(handler, ItemSlot::new, startIndex, inventoryHeight, inventoryWidth, x, y);
	}
	
	/**
	 * This methods can add any {@link IItemHandler} to the container. You can specialize the inventory height (slot rows)
	 * and width (slot columns). You must supplier a function that create a slot. With this you can set your own slot.
	 * implementations.
	 *
	 * @param handler Some item handler
	 * @param function Function to create a slot.
	 * @param startIndex Start index of the handler
	 * @param inventoryHeight Slot rows
	 * @param inventoryWidth Slot columns
	 * @param x Start x
	 * @param y Start y
	 */
	protected void appendInventory(IItemHandler handler, SlotHandlerFunction function, int startIndex, int inventoryHeight, int inventoryWidth, int x, int y) {
		for (var height = 0; height < inventoryHeight; height++) {
			for (var width = 0; width < inventoryWidth; width++) {
				addSlot(function.getSlot(handler, startIndex + (width + height * inventoryWidth), width * 18 + x, height * 18 + y));
			}
		}
	}
	
	/**
	 * Used as a function to customize slots with the append methods
	 *
	 * @author HyCraftHD
	 */
	@FunctionalInterface
	public static interface SlotInventoryFunction {
		
		/**
		 * Should return a slot with the applied parameters.
		 *
		 * @param inventory An inventory
		 * @param index Index for this inventory
		 * @param xPosition x coordinate
		 * @param yPosition y coordinate
		 * @return A slot instance
		 */
		Slot getSlot(Container inventory, int index, int xPosition, int yPosition);
	}
	
	/**
	 * Used as a function to customize slots with the append methods
	 *
	 * @author HyCraftHD
	 */
	@FunctionalInterface
	public static interface SlotHandlerFunction {
		
		/**
		 * Should return a slot with the applied parameters.
		 *
		 * @param itemHandler An item handler
		 * @param index Index for this item handler
		 * @param xPosition x coordinate
		 * @param yPosition y coordinate
		 * @return A Slot instance
		 */
		Slot getSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition);
	}
}
