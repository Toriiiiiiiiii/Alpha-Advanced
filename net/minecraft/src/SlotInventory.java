package net.minecraft.src;

public class SlotInventory extends Slot {
	private final GuiContainer guiContainer;
	public final int xDisplayPosition;
	public final int yDisplayPosition;

	public SlotInventory(GuiContainer var1, IInventory var2, int var3, int var4, int var5) {
		super(var2, var3);
		this.guiContainer = var1;
		this.xDisplayPosition = var4;
		this.yDisplayPosition = var5;
	}

	public boolean getIsMouseOverSlot(int var1, int var2) {
		int var3 = (this.guiContainer.width - this.guiContainer.xSize) / 2;
		int var4 = (this.guiContainer.height - this.guiContainer.ySize) / 2;
		var1 -= var3;
		var2 -= var4;
		return var1 >= this.xDisplayPosition - 1 && var1 < this.xDisplayPosition + 16 + 1 && var2 >= this.yDisplayPosition - 1 && var2 < this.yDisplayPosition + 16 + 1;
	}
}
