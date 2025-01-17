package info.u_team.u_team_test.screen;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.gui.elements.CheckboxButton;
import info.u_team.u_team_core.gui.elements.ImageActivatableButton;
import info.u_team.u_team_core.gui.elements.ImageButton;
import info.u_team.u_team_core.gui.elements.ImageToggleButton;
import info.u_team.u_team_core.gui.elements.ScalableActivatableButton;
import info.u_team.u_team_core.gui.elements.ScalableButton;
import info.u_team.u_team_core.gui.elements.ScalableCheckboxButton;
import info.u_team.u_team_core.gui.elements.ScalableEditBox;
import info.u_team.u_team_core.gui.elements.ScalableSlider;
import info.u_team.u_team_core.gui.elements.ScalableText;
import info.u_team.u_team_core.gui.elements.ScrollingText;
import info.u_team.u_team_core.gui.elements.UButton;
import info.u_team.u_team_core.gui.elements.UEditBox;
import info.u_team.u_team_core.gui.elements.USlider;
import info.u_team.u_team_core.screen.UScreen;
import info.u_team.u_team_core.util.RGBA;
import info.u_team.u_team_core.util.WidgetUtil;
import info.u_team.u_team_test.TestMod;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ButtonTestScreen extends UScreen {
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	private static final ResourceLocation TEXTURE1 = new ResourceLocation(TestMod.MODID, "textures/item/better_enderpearl.png");
	private static final ResourceLocation TEXTURE2 = new ResourceLocation(TestMod.MODID, "textures/item/basic_item.png");
	
	private UEditBox textFieldWidget;
	private ScalableEditBox scalableTextFieldWidget;
	private ScalableEditBox scalableTextFieldWidget2;
	
	private ScalableText scalingRenderer;
	private ScrollingText scrollingRenderer;
	
	private BasicTestList scrollingList;
	
	public ButtonTestScreen() {
		super(Component.literal("test"));
	}
	
	@Override
	protected void init() {
		// U Button Test
		final UButton uButton = addRenderableWidget(new UButton(10, 10, 200, 15, Component.literal("U Button")));
		uButton.setPressable(() -> LOGGER.info("Pressed U Button"));
		uButton.setTooltip((button, poseStack, mouseX, mouseY) -> {
			if (WidgetUtil.isHovered(button)) {
				renderTooltip(poseStack, Component.literal("U Button Tooltip"), mouseX, mouseY);
			}
		});
		
		// Scalable Button Test
		final ScalableButton scalableButton = addRenderableWidget(new ScalableButton(10, 30, 200, 15, Component.literal("Scalable Button"), 0.75F));
		scalableButton.setTextColor(new RGBA(0x00FFFF80));
		scalableButton.setPressable(button -> LOGGER.info("Pressed Scalable Button"));
		scalableButton.setTooltip((button, poseStack, mouseX, mouseY) -> {
			if (WidgetUtil.isHovered(button)) {
				renderTooltip(poseStack, Component.literal("Scalable Button Tooltip"), mouseX, mouseY);
			}
		});
		
		// Scalable Activatable Button Test
		final ScalableActivatableButton scalableActivatableButton = addRenderableWidget(new ScalableActivatableButton(10, 50, 200, 15, Component.literal("Scalable Activatable Button"), 0.75F, false, new RGBA(0x006442FF)));
		scalableActivatableButton.setPressable(() -> {
			LOGGER.info("Pressed Scalable Activatable Button");
			scalableActivatableButton.setActivated(!scalableActivatableButton.isActivated());
		});
		scalableActivatableButton.setTooltip((button, poseStack, mouseX, mouseY) -> {
			if (WidgetUtil.isHovered(button)) {
				renderTooltip(poseStack, Component.literal("Scalable Activatable Button Tooltip"), mouseX, mouseY);
			}
		});
		
		// Image Button Test
		final ImageButton imageButton = addRenderableWidget(new ImageButton(10, 70, 15, 15, TEXTURE1));
		imageButton.setPressable(() -> {
			LOGGER.info("Pressed Image Button");
		});
		imageButton.setTooltip((button, poseStack, mouseX, mouseY) -> {
			if (WidgetUtil.isHovered(button)) {
				renderTooltip(poseStack, Component.literal("Image Button Tooltip"), mouseX, mouseY);
			}
		});
		
		final ImageButton imageButton2 = addRenderableWidget(new ImageButton(30, 70, 15, 15, TEXTURE1));
		imageButton2.setButtonColor(new RGBA(0xFFFF2080));
		imageButton2.setImageColor(new RGBA(0x00FFFF80));
		imageButton2.setPressable(() -> {
			LOGGER.info("Pressed Image Button 2");
		});
		imageButton2.setTooltip((button, poseStack, mouseX, mouseY) -> {
			if (WidgetUtil.isHovered(button)) {
				renderTooltip(poseStack, Component.literal("Image Button 2 Tooltip"), mouseX, mouseY);
			}
		});
		
		// Image Activatable Button Test
		final ImageActivatableButton imageActivatableButton = addRenderableWidget(new ImageActivatableButton(10, 90, 15, 15, TEXTURE1, false, new RGBA(0x006442FF)));
		imageActivatableButton.setPressable(() -> {
			LOGGER.info("Pressed Image Activatable Button");
			imageActivatableButton.setActivated(!imageActivatableButton.isActivated());
		});
		imageActivatableButton.setTooltip((button, poseStack, mouseX, mouseY) -> {
			if (WidgetUtil.isHovered(button)) {
				renderTooltip(poseStack, Component.literal("Image Activatable Button Tooltip"), mouseX, mouseY);
			}
		});
		
		// Image Toggle Button Test
		final ImageToggleButton imageToggleButton = addRenderableWidget(new ImageToggleButton(10, 110, 15, 15, TEXTURE1, TEXTURE2, false));
		imageToggleButton.setPressable(() -> {
			LOGGER.info("Pressed Image Toggle Button");
		});
		imageToggleButton.setTooltip((button, poseStack, mouseX, mouseY) -> {
			if (WidgetUtil.isHovered(button)) {
				renderTooltip(poseStack, Component.literal("Image Toggle Button Tooltip"), mouseX, mouseY);
			}
		});
		
		final ImageToggleButton imageToggleButton2 = addRenderableWidget(new ImageToggleButton(30, 110, 15, 15, TEXTURE1, TEXTURE1, false));
		imageToggleButton2.setImageColor(new RGBA(0x00FF00FF));
		imageToggleButton2.setToggleImageColor(new RGBA(0xFF0000FF));
		imageToggleButton2.setPressable(() -> {
			LOGGER.info("Pressed Image Toggle Button 2");
		});
		imageToggleButton2.setTooltip((button, poseStack, mouseX, mouseY) -> {
			if (WidgetUtil.isHovered(button)) {
				renderTooltip(poseStack, Component.literal("Image Toggle Button 2 Tooltip"), mouseX, mouseY);
			}
		});
		
		// U Slider Test
		final USlider uSlider = addRenderableWidget(new USlider(10, 130, 200, 20, Component.literal("U Slider: "), Component.literal("%"), 0, 100, 20, false, true, false));
		uSlider.setSlider(() -> {
			LOGGER.info("Updated U Slider: " + uSlider.getValueInt());
		});
		uSlider.setTooltip((slider, poseStack, mouseX, mouseY) -> {
			if (WidgetUtil.isHovered(slider)) {
				renderTooltip(poseStack, Component.literal("U Slider Tooltip"), mouseX, mouseY);
			}
		});
		
		// Scalable Slider Test
		final ScalableSlider scalableSlider = addRenderableWidget(new ScalableSlider(10, 155, 200, 15, Component.literal("Scalable Slider: "), Component.literal("%"), 0, 100, 20, false, true, false, 0.5F));
		scalableSlider.setSlider(() -> {
			LOGGER.info("Updated Scalable Slider: " + scalableSlider.getValueInt());
		});
		scalableSlider.setTooltip((slider, poseStack, mouseX, mouseY) -> {
			if (WidgetUtil.isHovered(slider)) {
				renderTooltip(poseStack, Component.literal("Scalable Slider Tooltip"), mouseX, mouseY);
			}
		});
		
		final ScalableSlider scalableSlider2 = addRenderableWidget(new ScalableSlider(10, 175, 200, 30, Component.literal("Scalable Slider 2: "), Component.literal("%"), 0, 100, 20, false, true, false, 1.5F));
		scalableSlider2.setSliderBackgroundColor(new RGBA(0x0000FFFF));
		scalableSlider2.setSliderColor(new RGBA(0x00FF00FF));
		scalableSlider2.setTextColor(new RGBA(0xFF0000FF));
		scalableSlider2.setDisabledTextColor(new RGBA(0xFFFF0080));
		scalableSlider2.setSlider(() -> {
			LOGGER.info("Updated Scalable Slider 2: " + scalableSlider2.getValueInt());
			if (scalableSlider2.getValueInt() == 100) {
				scalableSlider2.active = false;
			}
		});
		scalableSlider2.setTooltip((slider, poseStack, mouseX, mouseY) -> {
			if (WidgetUtil.isHovered(slider)) {
				renderTooltip(poseStack, Component.literal("Scalable Slider 2 Tooltip"), mouseX, mouseY);
				scalableSlider2.active = true;
			}
		});
		
		// Checkbox Button Test
		final CheckboxButton checkboxButton = addRenderableWidget(new CheckboxButton(10, 210, 15, 15, Component.literal("Checkbox Button"), false, true));
		checkboxButton.setPressable(() -> {
			LOGGER.info("Pressed Checkbox Button");
		});
		checkboxButton.setTooltip((button, poseStack, mouseX, mouseY) -> {
			if (WidgetUtil.isHovered(button)) {
				renderTooltip(poseStack, Component.literal("Checkbox Button Tooltip"), mouseX, mouseY);
			}
		});
		
		final CheckboxButton checkboxButton2 = addRenderableWidget(new CheckboxButton(110, 230, 15, 15, Component.literal("Checkbox Button 2"), false, true));
		checkboxButton2.setLeftSideText(true);
		checkboxButton2.setPressable(() -> {
			LOGGER.info("Pressed Checkbox Button 2");
		});
		checkboxButton2.setTooltip((button, poseStack, mouseX, mouseY) -> {
			if (WidgetUtil.isHovered(button)) {
				renderTooltip(poseStack, Component.literal("Checkbox Button 2 Tooltip"), mouseX, mouseY);
			}
		});
		
		// Scalable Checkbox Button Test
		final ScalableCheckboxButton scalableCheckboxButton = addRenderableWidget(new ScalableCheckboxButton(10, 250, 15, 15, Component.literal("Scalable Checkbox Button"), false, true, 0.75F));
		scalableCheckboxButton.setPressable(() -> {
			LOGGER.info("Pressed Scalable Checkbox Button");
		});
		scalableCheckboxButton.setTooltip((button, poseStack, mouseX, mouseY) -> {
			if (WidgetUtil.isHovered(button)) {
				renderTooltip(poseStack, Component.literal("Scalable Checkbox Button Tooltip"), mouseX, mouseY);
			}
		});
		
		final ScalableCheckboxButton scalableCheckboxButton2 = addRenderableWidget(new ScalableCheckboxButton(110, 270, 15, 15, Component.literal("Scalable Checkbox Button 2"), false, true, 0.65F));
		scalableCheckboxButton2.setLeftSideText(true);
		scalableCheckboxButton2.setButtonColor(new RGBA(0x0000F0FF));
		scalableCheckboxButton2.setTextColor(new RGBA(0xA0A0A0FF));
		scalableCheckboxButton2.setPressable(() -> {
			LOGGER.info("Pressed Scalable Checkbox Button 2");
		});
		scalableCheckboxButton2.setTooltip((button, poseStack, mouseX, mouseY) -> {
			if (WidgetUtil.isHovered(button)) {
				renderTooltip(poseStack, Component.literal("Scalable Checkbox Button 2 Tooltip"), mouseX, mouseY);
			}
		});
		
		// U Text Field Test
		minecraft.keyboardHandler.setSendRepeatsToGui(true);
		
		textFieldWidget = addRenderableWidget(new UEditBox(font, 220, 110, 200, 20, textFieldWidget, Component.literal("U Text Field")));
		textFieldWidget.setMaxLength(500);
		textFieldWidget.setTooltip((textField, poseStack, mouseX, mouseY) -> {
			if (WidgetUtil.isHovered(textField)) {
				renderTooltip(poseStack, Component.literal("U Text Field Tooltip"), mouseX, mouseY);
			}
		});
		
		// Scalable Text Field Test
		scalableTextFieldWidget = addRenderableWidget(new ScalableEditBox(font, 220, 140, 200, 30, scalableTextFieldWidget, Component.literal("Scalable Text Field"), 1.5F));
		scalableTextFieldWidget.setMaxLength(500);
		scalableTextFieldWidget.setTooltip((textField, poseStack, mouseX, mouseY) -> {
			if (WidgetUtil.isHovered(textField)) {
				renderTooltip(poseStack, Component.literal("Scalable Text Field Tooltip"), mouseX, mouseY);
			}
		});
		
		scalableTextFieldWidget2 = addRenderableWidget(new ScalableEditBox(font, 220, 180, 200, 15, scalableTextFieldWidget, Component.literal("Scalable Text Field 2"), 0.5F));
		scalableTextFieldWidget2.setMaxLength(500);
		scalableTextFieldWidget2.setTooltip((textField, poseStack, mouseX, mouseY) -> {
			if (WidgetUtil.isHovered(textField)) {
				renderTooltip(poseStack, Component.literal("Scalable Text Field 2 Tooltip"), mouseX, mouseY);
			}
		});
		
		// Scaling Renderer Test
		scalingRenderer = new ScalableText(font, () -> "This is a test for the scaling text renderer", 220, 10);
		scalingRenderer.setColor(new RGBA(0xFF00FF40));
		scalingRenderer.setScale(1.5F);
		
		// Scrolling Renderer Test
		scrollingRenderer = new ScrollingText(font, () -> "This is a test for the scrolling text renderer that should be really long to test the scrolling", 220, 25);
		scrollingRenderer.setColor(new RGBA(0x00FFFFFF));
		scrollingRenderer.setWidth(200);
		scrollingRenderer.setScale(2F);
		
		// Scrolling List Test
		scrollingList = addWidget(new BasicTestList(220, 210, 200, 90));
	}
	
	@Override
	public void onClose() {
		super.onClose();
		minecraft.keyboardHandler.setSendRepeatsToGui(false);
	}
	
	@Override
	public void renderForeground(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		scalingRenderer.render(poseStack, mouseX, mouseY, partialTicks);
		scrollingRenderer.render(poseStack, mouseX, mouseY, partialTicks);
		scrollingList.render(poseStack, mouseX, mouseY, partialTicks);
	}
}
