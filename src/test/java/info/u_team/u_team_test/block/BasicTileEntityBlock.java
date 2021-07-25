package info.u_team.u_team_test.block;

import info.u_team.u_team_core.block.UTileEntityBlock;
import info.u_team.u_team_test.init.TestItemGroups;
import info.u_team.u_team_test.init.TestTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;

public class BasicTileEntityBlock extends UTileEntityBlock {
	
	public static final DirectionProperty FACING = BlockStateProperties.FACING;
	
	public BasicTileEntityBlock() {
		super(TestItemGroups.GROUP, Properties.of(Material.STONE).strength(2F).sound(SoundType.GRAVEL).friction(0.8F).lightLevel(state -> 1), TestTileEntityTypes.BASIC);
		registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
	}
	
	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		return openContainer(world, pos, player, true);
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
	
}
