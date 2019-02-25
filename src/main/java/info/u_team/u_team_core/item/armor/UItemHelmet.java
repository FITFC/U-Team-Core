package info.u_team.u_team_core.item.armor;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;

public class UItemHelmet extends UItemArmor {
	
	public UItemHelmet(String name, Properties properties, IArmorMaterial material) {
		this(name, null, properties, material);
	}
	
	public UItemHelmet(String name, ItemGroup group, Properties properties, IArmorMaterial material) {
		super(name, group, properties, material, EntityEquipmentSlot.HEAD);
	}
	
}
