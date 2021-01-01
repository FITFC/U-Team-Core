package info.u_team.u_team_core.gui.renderer;

import java.util.function.Supplier;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.*;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.math.MathHelper;

public class ScrollingTextRenderer extends ScalingTextRenderer {
	
	protected int width;
	protected float stepSize;
	protected int speedTime;
	protected int waitTime;
	
	protected float moveDifference = 0;
	protected long lastTime = 0;
	protected State state = State.WAITING;
	
	public ScrollingTextRenderer(Supplier<FontRenderer> fontRenderSupplier, Supplier<String> textSupplier, float x, float y) {
		super(fontRenderSupplier, textSupplier, x, y);
		width = 100;
		stepSize = 1;
		speedTime = 20;
		waitTime = 4000;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public float getStepSize() {
		return stepSize;
	}
	
	public void setStepSize(float stepSize) {
		this.stepSize = stepSize;
	}
	
	public int getSpeedTime() {
		return speedTime;
	}
	
	public void setSpeedTime(int speedtime) {
		this.speedTime = speedtime;
	}
	
	public int getWaitTime() {
		return waitTime;
	}
	
	public void setWaitTime(int waittime) {
		this.waitTime = waittime;
	}
	
	@Override
	protected void updatedText() {
		state = State.WAITING;
		moveDifference = 0;
		lastTime = 0;
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		final Minecraft minecraft = Minecraft.getInstance();
		final MainWindow window = minecraft.getMainWindow();
		
		final double scaleFactor = window.getGuiScaleFactor();
		
		final int nativeX = MathHelper.ceil(x * scaleFactor);
		final int nativeY = MathHelper.ceil(y * scaleFactor);
		
		final int nativeWidth = MathHelper.ceil(width * scaleFactor);
		final int nativeHeight = MathHelper.ceil((fontRenderSupplier.get().FONT_HEIGHT + 1) * scale * scaleFactor);
		
//		GL11.glPushMatrix();
//		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		
		RenderSystem.enableScissor(nativeX, window.getHeight() - (nativeY + nativeHeight), nativeWidth, nativeHeight);
		
//		GL11.glScissor(nativeX, window.getHeight() - (nativeY + nativeHeight), nativeWidth, nativeHeight);
		// AbstractGui.fill(matrixStack, 0, 0, window.getScaledWidth(), window.getScaledHeight(), 0xFF00FF00); // test scissor
		
		setText(textSupplier.get());
		renderFont(matrixStack, fontRenderSupplier.get(), getMovingX(x), y + 2);
		
		RenderSystem.disableScissor();
		
//		GL11.glDisable(GL11.GL_SCISSOR_TEST);
//		GL11.glPopMatrix();
	}
	
	protected float getMovingX(float x) {
		final float textWidth = getTextWidth();
		if (width < textWidth) {
			final float maxMove = width - textWidth;
			
			if (lastTime == 0) {
				lastTime = System.currentTimeMillis();
			}
			
			if (state == State.WAITING) {
				if (hasWaitTimePassed()) {
					state = moveDifference >= 0 ? State.LEFT : State.RIGHT;
					lastTime = 0;
				}
			} else {
				if (hasSpeedTimePassed()) {
					if (state == State.LEFT ? moveDifference >= maxMove : moveDifference <= 0) {
						moveDifference += state == State.LEFT ? -stepSize : +stepSize;
					} else {
						state = State.WAITING;
					}
					lastTime = 0;
				}
			}
			return x + moveDifference;
		}
		return x;
	}
	
	protected boolean hasWaitTimePassed() {
		return System.currentTimeMillis() - waitTime >= lastTime;
	}
	
	protected boolean hasSpeedTimePassed() {
		return System.currentTimeMillis() - speedTime >= lastTime;
	}
	
	private enum State {
		WAITING,
		LEFT,
		RIGHT;
	}
	
}
