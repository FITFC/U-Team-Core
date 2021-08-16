package info.u_team.u_team_core.gui.elements;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.api.gui.IScaleProvider;
import info.u_team.u_team_core.api.gui.IScaleable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class ScalableTextField extends UTextField implements IScaleable, IScaleProvider {
	
	protected float scale;
	
	public ScalableTextField(Font fontRenderer, int x, int y, int width, int height, UTextField previousTextField, Component title, float scale) {
		this(fontRenderer, x, y, width, height, previousTextField, title, scale, EMPTY_TOOLTIP);
	}
	
	public ScalableTextField(Font fontRenderer, int x, int y, int width, int height, UTextField previousTextField, Component title, float scale, ITooltip tooltip) {
		super(fontRenderer, x, y, width, height, previousTextField, title, tooltip);
		this.scale = scale;
	}
	
	@Override
	public float getScale() {
		return scale;
	}
	
	@Override
	public void setScale(float scale) {
		this.scale = scale;
	}
	
	@Override
	public void renderForeground(PoseStack matrixStack, Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		final var currentScale = getCurrentScale(matrixStack, mouseX, mouseY, partialTicks);
		
		final var positionFactor = 1 / scale;
		
		matrixStack.pushPose();
		matrixStack.scale(currentScale, currentScale, 0);
		
		final var currentTextColor = getCurrentTextColor(matrixStack, mouseX, mouseY, partialTicks);
		
		final var currentText = font.plainSubstrByWidth(value.substring(displayPos), getInnerWidth());
		
		final var cursorOffset = cursorPos - displayPos;
		final var selectionOffset = Math.min(highlightPos - displayPos, currentText.length());
		
		final var isCursorInText = cursorOffset >= 0 && cursorOffset <= currentText.length();
		final var shouldCursorBlink = isFocused() && frame / 6 % 2 == 0 && isCursorInText;
		final var isCursorInTheMiddle = cursorPos < value.length() || value.length() >= maxLength;
		
		final var xOffset = (int) ((bordered ? x + 4 : x) * positionFactor);
		final var yOffset = (int) ((bordered ? y + (int) (height - 8 * scale) / 2 : y) * positionFactor);
		
		var leftRenderedTextX = xOffset;
		
		if (!currentText.isEmpty()) {
			final var firstTextPart = isCursorInText ? currentText.substring(0, cursorOffset) : currentText;
			leftRenderedTextX = font.drawShadow(matrixStack, formatter.apply(firstTextPart, displayPos), xOffset, yOffset, currentTextColor.getColorARGB());
		}
		
		var rightRenderedTextX = leftRenderedTextX;
		
		if (!isCursorInText) {
			rightRenderedTextX = cursorOffset > 0 ? xOffset + width : xOffset;
		} else if (isCursorInTheMiddle) {
			rightRenderedTextX = leftRenderedTextX - 1;
			--leftRenderedTextX;
		}
		
		if (!currentText.isEmpty() && isCursorInText && cursorOffset < currentText.length()) {
			font.drawShadow(matrixStack, formatter.apply(currentText.substring(cursorOffset), cursorPos), leftRenderedTextX, yOffset, currentTextColor.getColorARGB());
		}
		
		if (!isCursorInTheMiddle && suggestion != null) {
			font.drawShadow(matrixStack, suggestion, rightRenderedTextX - 1, yOffset, getCurrentSuggestionTextColor(matrixStack, mouseX, mouseY, partialTicks).getColorARGB());
		}
		
		if (shouldCursorBlink) {
			if (isCursorInTheMiddle) {
				GuiComponent.fill(matrixStack, rightRenderedTextX, yOffset - 1, rightRenderedTextX + 1, yOffset + 1 + 9, getCurrentCursorColor(matrixStack, mouseX, mouseY, partialTicks).getColorARGB());
			} else {
				font.drawShadow(matrixStack, "_", rightRenderedTextX, yOffset, currentTextColor.getColorARGB());
			}
		}
		
		if (selectionOffset != cursorOffset) {
			final var selectedX = xOffset + font.width(currentText.substring(0, selectionOffset));
			renderHighlight((int) (rightRenderedTextX * currentScale), (int) ((yOffset - 1) * currentScale), (int) ((selectedX - 1) * currentScale), (int) ((yOffset + 1 + 9) * currentScale));
		}
		
		matrixStack.popPose();
	}
	
	@Override
	public float getCurrentScale(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		return getCurrentScale(mouseX, mouseY);
	}
	
	public float getCurrentScale(double mouseX, double mouseY) {
		return scale;
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (!visible) {
			return false;
		} else {
			final var clicked = clicked(mouseX, mouseY);
			
			if (canLoseFocus) {
				setFocus(clicked);
			}
			
			if (isFocused() && clicked && button == 0) {
				var clickOffset = Mth.floor(mouseX) - x;
				if (bordered) {
					clickOffset -= 4;
				}
				
				clickOffset /= getCurrentScale(mouseX, mouseY);
				
				final var currentText = font.plainSubstrByWidth(value.substring(displayPos), getInnerWidth());
				moveCursorTo(font.plainSubstrByWidth(currentText, clickOffset).length() + displayPos);
				return true;
			} else {
				return false;
			}
		}
	}
	
}
