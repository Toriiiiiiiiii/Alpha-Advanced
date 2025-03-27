package net.minecraft.src;

public class Material {
	public static final Material air = new MaterialTransparent();
	public static final Material grass = new Material();
	public static final Material wood = (new Material()).setBurning();
	public static final Material rock = new Material();
	public static final Material iron = new Material();
	public static final Material water = new MaterialLiquid();
	public static final Material lava = new MaterialLiquid();
	public static final Material leaves = (new Material()).setBurning();
	public static final Material plants = new MaterialLogic();
	public static final Material sponge = new Material();
	public static final Material cloth = (new Material()).setBurning();
	public static final Material fire = new MaterialTransparent();
	public static final Material sand = new Material();
	public static final Material circuits = new MaterialLogic();
	public static final Material glass = new Material();
	public static final Material tnt = (new Material()).setBurning();
	public static final Material unused = new Material();
	public static final Material ice = new Material();
	public static final Material snow = new MaterialLogic();
	public static final Material craftedSnow = new Material();
	public static final Material cactus = new Material();
	public static final Material clay = new Material();
	private boolean canBurn;

	public boolean getIsLiquid() {
		return false;
	}

	public boolean isSolid() {
		return true;
	}

	public boolean getCanBlockGrass() {
		return true;
	}

	public boolean getIsSolid() {
		return true;
	}

	private Material setBurning() {
		this.canBurn = true;
		return this;
	}

	public boolean getCanBurn() {
		return this.canBurn;
	}
}
