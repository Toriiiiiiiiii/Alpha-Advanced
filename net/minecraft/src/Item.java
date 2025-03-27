package net.minecraft.src;

import java.util.Random;

public class Item {
	protected static Random rand = new Random();
	public static Item[] itemsList = new Item[32000];
	public static Item shovel = (new ItemSpade(0, 2)).setIconIndex(82);
	public static Item pickaxeSteel = (new ItemPickaxe(1, 2)).setIconIndex(98);
	public static Item axeSteel = (new ItemAxe(2, 2)).setIconIndex(114);
	public static Item striker = (new ItemFlintAndSteel(3)).setIconIndex(5);
	public static Item appleRed = (new ItemFood(4, 4, 16)).setIconIndex(10);
	public static Item bow = (new ItemBow(5)).setIconIndex(21);
	public static Item arrow = (new Item(6)).setIconIndex(37);
	public static Item coal = (new Item(7)).setIconIndex(7);
	public static Item diamond = (new Item(8)).setIconIndex(55);
	public static Item ingotIron = (new Item(9)).setIconIndex(23);
	public static Item ingotGold = (new Item(10)).setIconIndex(39);
	public static Item swordSteel = (new ItemSword(11, 2)).setIconIndex(66);
	public static Item swordWood = (new ItemSword(12, 0)).setIconIndex(64);
	public static Item shovelWood = (new ItemSpade(13, 0)).setIconIndex(80);
	public static Item pickaxeWood = (new ItemPickaxe(14, 0)).setIconIndex(96);
	public static Item axeWood = (new ItemAxe(15, 0)).setIconIndex(112);
	public static Item swordStone = (new ItemSword(16, 1)).setIconIndex(65);
	public static Item shovelStone = (new ItemSpade(17, 1)).setIconIndex(81);
	public static Item pickaxeStone = (new ItemPickaxe(18, 1)).setIconIndex(97);
	public static Item axeStone = (new ItemAxe(19, 1)).setIconIndex(113);
	public static Item swordDiamond = (new ItemSword(20, 3)).setIconIndex(67);
	public static Item shovelDiamond = (new ItemSpade(21, 3)).setIconIndex(83);
	public static Item pickaxeDiamond = (new ItemPickaxe(22, 3)).setIconIndex(99);
	public static Item axeDiamond = (new ItemAxe(23, 3)).setIconIndex(115);
	public static Item stick = (new Item(24)).setIconIndex(53).setFull3D();
	public static Item bowlEmpty = (new Item(25)).setIconIndex(71);
	public static Item bowlSoup = (new ItemSoup(26, 10)).setIconIndex(72);
	public static Item swordGold = (new ItemSword(27, 0)).setIconIndex(68);
	public static Item shovelGold = (new ItemSpade(28, 0)).setIconIndex(84);
	public static Item pickaxeGold = (new ItemPickaxe(29, 0)).setIconIndex(100);
	public static Item axeGold = (new ItemAxe(30, 0)).setIconIndex(116);
	public static Item silk = (new Item(31)).setIconIndex(8);
	public static Item feather = (new Item(32)).setIconIndex(24);
	public static Item gunpowder = (new Item(33)).setIconIndex(40);
	public static Item hoeWood = (new ItemHoe(34, 0)).setIconIndex(128);
	public static Item hoeStone = (new ItemHoe(35, 1)).setIconIndex(129);
	public static Item hoeSteel = (new ItemHoe(36, 2)).setIconIndex(130);
	public static Item hoeDiamond = (new ItemHoe(37, 3)).setIconIndex(131);
	public static Item hoeGold = (new ItemHoe(38, 1)).setIconIndex(132);
	public static Item seeds = (new ItemSeeds(39, Block.crops.blockID)).setIconIndex(9);
	public static Item wheat = (new Item(40)).setIconIndex(25);
	public static Item bread = (new ItemFood(41, 5, 4)).setIconIndex(41);
	public static Item helmetLeather = (new ItemArmor(42, 0, 0, 0)).setIconIndex(0);
	public static Item plateLeather = (new ItemArmor(43, 0, 0, 1)).setIconIndex(16);
	public static Item legsLeather = (new ItemArmor(44, 0, 0, 2)).setIconIndex(32);
	public static Item bootsLeather = (new ItemArmor(45, 0, 0, 3)).setIconIndex(48);
	public static Item helmetChain = (new ItemArmor(46, 1, 1, 0)).setIconIndex(1);
	public static Item plateChain = (new ItemArmor(47, 1, 1, 1)).setIconIndex(17);
	public static Item legsChain = (new ItemArmor(48, 1, 1, 2)).setIconIndex(33);
	public static Item bootsChain = (new ItemArmor(49, 1, 1, 3)).setIconIndex(49);
	public static Item helmetSteel = (new ItemArmor(50, 2, 2, 0)).setIconIndex(2);
	public static Item plateSteel = (new ItemArmor(51, 2, 2, 1)).setIconIndex(18);
	public static Item legsSteel = (new ItemArmor(52, 2, 2, 2)).setIconIndex(34);
	public static Item bootsSteel = (new ItemArmor(53, 2, 2, 3)).setIconIndex(50);
	public static Item helmetDiamond = (new ItemArmor(54, 3, 3, 0)).setIconIndex(3);
	public static Item plateDiamond = (new ItemArmor(55, 3, 3, 1)).setIconIndex(19);
	public static Item legsDiamond = (new ItemArmor(56, 3, 3, 2)).setIconIndex(35);
	public static Item bootsDiamond = (new ItemArmor(57, 3, 3, 3)).setIconIndex(51);
	public static Item helmetGold = (new ItemArmor(58, 1, 4, 0)).setIconIndex(4);
	public static Item plateGold = (new ItemArmor(59, 1, 4, 1)).setIconIndex(20);
	public static Item legsGold = (new ItemArmor(60, 1, 4, 2)).setIconIndex(36);
	public static Item bootsGold = (new ItemArmor(61, 1, 4, 3)).setIconIndex(52);
	public static Item flint = (new Item(62)).setIconIndex(6);
	public static Item porkRaw = (new ItemFood(63, 3, 1)).setIconIndex(87);
	public static Item porkCooked = (new ItemFood(64, 8, 1)).setIconIndex(88);
	public static Item painting = (new ItemPainting(65)).setIconIndex(26);
	public static Item appleGold = (new ItemFood(66, 42, 1)).setIconIndex(11);
	public static Item sign = (new ItemSign(67)).setIconIndex(42);
	public static Item doorWood = (new ItemDoor(68, Material.wood)).setIconIndex(43);
	public static Item bucketEmpty = (new ItemBucket(69, 0)).setIconIndex(74);
	public static Item bucketWater = (new ItemBucket(70, Block.waterMoving.blockID)).setIconIndex(75);
	public static Item bucketLava = (new ItemBucket(71, Block.lavaMoving.blockID)).setIconIndex(76);
	public static Item minecartEmpty = (new ItemMinecart(72, 0)).setIconIndex(135);
	public static Item saddle = (new ItemSaddle(73)).setIconIndex(104);
	public static Item doorSteel = (new ItemDoor(74, Material.iron)).setIconIndex(44);
	public static Item redstone = (new ItemRedstone(75)).setIconIndex(56);
	public static Item snowball = (new ItemSnowball(76)).setIconIndex(14);
	public static Item boat = (new ItemBoat(77)).setIconIndex(136);
	public static Item leather = (new Item(78)).setIconIndex(103);
	public static Item bucketMilk = (new ItemBucket(79, -1)).setIconIndex(77);
	public static Item brick = (new Item(80)).setIconIndex(22);
	public static Item clay = (new Item(81)).setIconIndex(57);
	public static Item reed = (new ItemReed(82, Block.reed)).setIconIndex(27);
	public static Item paper = (new Item(83)).setIconIndex(58);
	public static Item book = (new Item(84)).setIconIndex(59);
	public static Item slimeBall = (new Item(85)).setIconIndex(30);
	public static Item minecartBox = (new ItemMinecart(86, 1)).setIconIndex(151);
	public static Item minecartEngine = (new ItemMinecart(87, 2)).setIconIndex(167);
	public static Item egg = (new Item(88)).setMaxStackSize(16).setIconIndex(12);
	public static Item compass = (new Item(89)).setIconIndex(54);
	public static Item fishingRod = (new Item(90)).setIconIndex(69);
	public static Item record13 = (new ItemRecord(2000, "13")).setIconIndex(240);
	public static Item recordCat = (new ItemRecord(2001, "cat")).setIconIndex(241);
	
	public static Item shears = (new ItemTool(2002, 3, 2, new Block[] {Block.leaves, Block.leavesPlr})).setIconIndex(242);
	public static Item ingotCopper = (new Item(2003)).setIconIndex(243);
	
	public static Item swordCopper = (new ItemSword(2004, 2)).setIconIndex(244);
	public static Item shovelCopper = (new ItemSpade(20205, 2)).setIconIndex(245);
	public static Item pickaxeCopper = (new ItemPickaxe(2006, 2)).setIconIndex(246);
	public static Item axeCopper = (new ItemAxe(2007, 2)).setIconIndex(247);
	public static Item hoeCopper = (new ItemHoe(2008, 2)).setIconIndex(248);
	
	public static Item fertilizer = (new Item(2009)).setIconIndex(249);
	public static Item hamburger = (new ItemFood(2010, 5, 8)).setIconIndex(250);
	public static Item mug = (new Item(2011)).setIconIndex(251).setMaxStackSize(1);
	public static Item tea = (new ItemFood(2012, 6, 1)).setIconIndex(252);
	public static Item teaLeaves = (new Item(2013)).setIconIndex(253).setMaxStackSize(16);
	public static Item friedEgg = (new ItemFood(2014, 4, 16)).setIconIndex(254);
	
	public final int shiftedIndex;
	protected int maxStackSize = 64;
	protected int maxDamage = 32;
	protected int iconIndex;
	protected boolean bFull3D = false;

	public Item setMaxStackSize(int size) {
		this.maxStackSize = size;
		return this;
	}
	
	protected Item(int var1) {
		this.shiftedIndex = 256 + var1;
		if(itemsList[256 + var1] != null) {
			System.out.println("CONFLICT @ " + var1);
		}

		itemsList[256 + var1] = this;
	}

	public Item setIconIndex(int var1) {
		this.iconIndex = var1;
		return this;
	}

	public int getIconIndex(ItemStack var1) {
		return this.iconIndex;
	}

	public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7) {
		return false;
	}

	public float getStrVsBlock(ItemStack var1, Block var2) {
		return 1.0F;
	}

	public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3) {
		return var1;
	}

	public int getItemStackLimit() {
		return this.maxStackSize;
	}

	public int getMaxDamage() {
		return this.maxDamage;
	}

	public void hitEntity(ItemStack var1, EntityLiving var2) {
	}

	public void onBlockDestroyed(ItemStack var1, int var2, int var3, int var4, int var5) {
	}

	public int getDamageVsEntity(Entity var1) {
		return 1;
	}

	public boolean canHarvestBlock(Block var1) {
		return false;
	}

	public void saddleEntity(ItemStack var1, EntityLiving var2) {
	}

	public Item setFull3D() {
		this.bFull3D = true;
		return this;
	}

	public boolean isFull3D() {
		return this.bFull3D;
	}
}
