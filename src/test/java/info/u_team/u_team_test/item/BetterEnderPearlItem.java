package info.u_team.u_team_test.item;

import info.u_team.u_team_core.item.UItem;
import info.u_team.u_team_test.entity.BetterEnderPearlEntity;
import info.u_team.u_team_test.init.TestCreativeTabs;
import info.u_team.u_team_test.init.TestSoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class BetterEnderPearlItem extends UItem {
	
	public BetterEnderPearlItem() {
		super(TestCreativeTabs.TAB, new Properties().rarity(Rarity.EPIC));
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		final var stack = player.getItemInHand(hand);
		
		world.playSound(null, player.getX(), player.getY(), player.getZ(), TestSoundEvents.BETTER_ENDERPEARL_USE.get(), SoundSource.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 1.5F));
		
		player.getCooldowns().addCooldown(this, 10);
		
		if (!world.isClientSide()) {
			final var pearl = new BetterEnderPearlEntity(world, player);
			pearl.setItem(stack);
			pearl.shootFromRotation(player, player.getYRot(), player.getXRot(), 0.0F, 2.5F, 1.2F);
			world.addFreshEntity(pearl);
		}
		
		player.awardStat(Stats.ITEM_USED.get(this));
		if (!player.getAbilities().instabuild) {
			stack.shrink(1);
		}
		
		return InteractionResultHolder.success(stack);
	}
}
