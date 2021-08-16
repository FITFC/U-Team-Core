package info.u_team.u_team_test.global_loot_modifier;

import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.JsonObject;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.items.ItemHandlerHelper;

public class AutoSmeltLootModifier extends LootModifier {
	
	public AutoSmeltLootModifier(LootItemCondition[] conditions) {
		super(conditions);
	}
	
	@Override
	protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
		return generatedLoot.stream().map(stack -> smeltItem(stack, context)).collect(Collectors.toList());
	}
	
	private static ItemStack smeltItem(ItemStack stack, LootContext context) {
		return context.getLevel() //
				.getRecipeManager() //
				.getRecipeFor(RecipeType.SMELTING, new SimpleContainer(stack), context.getLevel()) //
				.map(SmeltingRecipe::getResultItem).filter(itemStack -> !itemStack.isEmpty()) //
				.map(itemStack -> ItemHandlerHelper.copyStackWithSize(itemStack, stack.getCount() * itemStack.getCount())) //
				.orElse(stack);
	}
	
	public static class Serializer extends GlobalLootModifierSerializer<AutoSmeltLootModifier> {
		
		@Override
		public AutoSmeltLootModifier read(ResourceLocation name, JsonObject json, LootItemCondition[] conditions) {
			return new AutoSmeltLootModifier(conditions);
		}
		
		@Override
		public JsonObject write(AutoSmeltLootModifier instance) {
			return makeConditions(instance.conditions);
		}
	}
	
}
