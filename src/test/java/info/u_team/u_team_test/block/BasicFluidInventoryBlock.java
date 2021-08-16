package info.u_team.u_team_test.block;

import info.u_team.u_team_core.block.UTileEntityBlock;
import info.u_team.u_team_test.init.TestItemGroups;
import info.u_team.u_team_test.init.TestTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;

public class BasicFluidInventoryBlock extends UTileEntityBlock {
	
	public BasicFluidInventoryBlock() {
		super(TestItemGroups.GROUP, Properties.of(Material.METAL).strength(1), TestTileEntityTypes.BASIC_FLUID_INVENTORY);
	}
	
	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		return openContainer(world, pos, player, true);
	}
	
}
