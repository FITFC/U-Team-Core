package info.u_team.u_team_test.block;

import info.u_team.u_team_core.block.UEntityBlock;
import info.u_team.u_team_test.init.TestBlockEntityTypes;
import info.u_team.u_team_test.init.TestCreativeTabs;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;

public class BasicSyncBlock extends UEntityBlock {
	
	public BasicSyncBlock() {
		super(TestCreativeTabs.TAB, Properties.of(Material.METAL).strength(2).requiresCorrectToolForDrops(), TestBlockEntityTypes.BASIC_ENERGY_CREATOR);
	}
	
	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		return openMenu(world, pos, player, true);
	}
	
}