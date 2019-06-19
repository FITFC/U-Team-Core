package info.u_team.u_team_core.container;

import info.u_team.u_team_core.api.sync.ISyncedData;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkHooks;

/**
 * A synchronized container with custom packets.
 * 
 * @author HyCraftHD
 *
 */
public abstract class USyncedContainer extends UContainer implements ISyncedData {
	
	/**
	 * This is the server constructor for the container.
	 * 
	 * @param type Container type
	 * @param id Window id
	 */
	public USyncedContainer(ContainerType<?> type, int id) {
		super(type, id);
	}
	
	/**
	 * This is the client constructor for the container.
	 * 
	 * @param type Container type
	 * @param id Window id
	 * @param buffer Initial data (specified with {@link NetworkHooks#openGui(player, containerSupplier,extraDataWriter)})
	 */
	public USyncedContainer(ContainerType<?> type, int id, PacketBuffer buffer) {
		super(type, id);
	}
}
