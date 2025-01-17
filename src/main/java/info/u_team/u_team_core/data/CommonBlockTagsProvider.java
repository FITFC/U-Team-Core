package info.u_team.u_team_core.data;

import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;

public abstract class CommonBlockTagsProvider extends CommonTagsProvider<Block> {
	
	@SuppressWarnings("deprecation")
	public CommonBlockTagsProvider(GenerationData generationData) {
		super(generationData, Registry.BLOCK);
	}
	
	@Override
	public String getName() {
		return "Block-Tags";
	}
	
}
