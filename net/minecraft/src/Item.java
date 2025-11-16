package net.minecraft.src;

import java.util.Random;

public class Item {
	protected static Random rand = new Random();
	public static Item[] itemsList = new Item[32000];
	public static Item shovel = (new ItemSpade(0, 2)).setIconIndex(82).setName("Iron Shovel");
	public static Item pickaxeSteel = (new ItemPickaxe(1, 2)).setIconIndex(98).setName("Iron Pickaxe");
	public static Item axeSteel = (new ItemAxe(2, 2)).setIconIndex(114).setName("Iron Axe");
	public static Item striker = (new ItemFlintAndSteel(3)).setIconIndex(5).setName("Flint and Steel");
	public static Item appleRed = (new ItemFood(4, 4, 16)).setIconIndex(10).setName("Apple");
	public static Item bow = (new ItemBow(5)).setIconIndex(21).setName("Bow");
	public static Item arrow = (new Item(6)).setIconIndex(37).setName("Arror");
	public static Item coal = (new Item(7)).setIconIndex(7).setName("Coal");
	public static Item diamond = (new Item(8)).setIconIndex(55).setName("Diamond");
	public static Item ingotIron = (new Item(9)).setIconIndex(23).setName("Iron Ingot");
	public static Item ingotGold = (new Item(10)).setIconIndex(39).setName("Gold Ingot");
	public static Item swordSteel = (new ItemSword(11, 2)).setIconIndex(66).setName("Iron Sword");
	public static Item swordWood = (new ItemSword(12, 0)).setIconIndex(64).setName("Wood Sword");
	public static Item shovelWood = (new ItemSpade(13, 0)).setIconIndex(80).setName("Wood Shovel");
	public static Item pickaxeWood = (new ItemPickaxe(14, 0)).setIconIndex(96).setName("Wood Pickaxe");
	public static Item axeWood = (new ItemAxe(15, 0)).setIconIndex(112).setName("Wood Axe");
	public static Item swordStone = (new ItemSword(16, 1)).setIconIndex(65).setName("Stone Sword");
	public static Item shovelStone = (new ItemSpade(17, 1)).setIconIndex(81).setName("Stone Shovel");
	public static Item pickaxeStone = (new ItemPickaxe(18, 1)).setIconIndex(97).setName("Stone Pickaxe");
	public static Item axeStone = (new ItemAxe(19, 1)).setIconIndex(113).setName("Stone Axe");
	public static Item swordDiamond = (new ItemSword(20, 3)).setIconIndex(67).setName("Diamond Sword");
	public static Item shovelDiamond = (new ItemSpade(21, 3)).setIconIndex(83).setName("Diamond Shovel");
	public static Item pickaxeDiamond = (new ItemPickaxe(22, 3)).setIconIndex(99).setName("Diamond Pickaxe");
	public static Item axeDiamond = (new ItemAxe(23, 3)).setIconIndex(115).setName("Diamond Axe");
	public static Item stick = (new Item(24)).setIconIndex(53).setFull3D().setName("Stick");
	public static Item bowlEmpty = (new Item(25)).setIconIndex(71).setName("Bowl");
	public static Item bowlSoup = (new ItemSoup(26, 10)).setIconIndex(72).setName("Bowl of Soup");
	public static Item swordGold = (new ItemSword(27, 0)).setIconIndex(68).setName("Gold Sword");
	public static Item shovelGold = (new ItemSpade(28, 0)).setIconIndex(84).setName("Gold Shovel");
	public static Item pickaxeGold = (new ItemPickaxe(29, 0)).setIconIndex(100).setName("Gold Pickaxe");
	public static Item axeGold = (new ItemAxe(30, 0)).setIconIndex(116).setName("Gold Axe");
	public static Item silk = (new Item(31)).setIconIndex(8).setName("String");
	public static Item feather = (new Item(32)).setIconIndex(24).setName("Feather");
	public static Item gunpowder = (new Item(33)).setIconIndex(40).setName("Gunpowder");
	public static Item hoeWood = (new ItemHoe(34, 0)).setIconIndex(128).setName("Wood Hoe");
	public static Item hoeStone = (new ItemHoe(35, 1)).setIconIndex(129).setName("Stone Hoe");
	public static Item hoeSteel = (new ItemHoe(36, 2)).setIconIndex(130).setName("Iron Hoe");
	public static Item hoeDiamond = (new ItemHoe(37, 3)).setIconIndex(131).setName("Waste of Diamonds");
	public static Item hoeGold = (new ItemHoe(38, 1)).setIconIndex(132).setName("Gold Hoe");
	public static Item seeds = (new ItemSeeds(39, Block.crops.blockID)).setIconIndex(9).setName("Seeds");
	public static Item wheat = (new Item(40)).setIconIndex(25).setName("Wheat");
	public static Item bread = (new ItemFood(41, 5, 4)).setIconIndex(41).setName("Bread");
	public static Item helmetLeather = (new ItemArmor(42, 0, 0, 0)).setIconIndex(0).setName("Leather Hat");
	public static Item plateLeather = (new ItemArmor(43, 0, 0, 1)).setIconIndex(16).setName("Leather Tunic");
	public static Item legsLeather = (new ItemArmor(44, 0, 0, 2)).setIconIndex(32).setName("Leather Trousers");
	public static Item bootsLeather = (new ItemArmor(45, 0, 0, 3)).setIconIndex(48).setName("Leather Boots");
	public static Item helmetChain = (new ItemArmor(46, 1, 1, 0)).setIconIndex(1).setName("Chain Helmet");
	public static Item plateChain = (new ItemArmor(47, 1, 1, 1)).setIconIndex(17).setName("Chain Chestplate");
	public static Item legsChain = (new ItemArmor(48, 1, 1, 2)).setIconIndex(33).setName("Chain Leggings");
	public static Item bootsChain = (new ItemArmor(49, 1, 1, 3)).setIconIndex(49).setName("Chain Boots");
	public static Item helmetSteel = (new ItemArmor(50, 2, 2, 0)).setIconIndex(2).setName("Iron Helmet");
	public static Item plateSteel = (new ItemArmor(51, 2, 2, 1)).setIconIndex(18).setName("Iron Chestplate");
	public static Item legsSteel = (new ItemArmor(52, 2, 2, 2)).setIconIndex(34).setName("Iron Greaves");
	public static Item bootsSteel = (new ItemArmor(53, 2, 2, 3)).setIconIndex(50).setName("Iron Boots");
	public static Item helmetDiamond = (new ItemArmor(54, 3, 3, 0)).setIconIndex(3).setName("Diamond Helmet");
	public static Item plateDiamond = (new ItemArmor(55, 3, 3, 1)).setIconIndex(19).setName("Diamond Chestplate");
	public static Item legsDiamond = (new ItemArmor(56, 3, 3, 2)).setIconIndex(35).setName("Diamond Greaves");
	public static Item bootsDiamond = (new ItemArmor(57, 3, 3, 3)).setIconIndex(51).setName("Diamond Boots");
	public static Item helmetGold = (new ItemArmor(58, 1, 4, 0)).setIconIndex(4).setName("Gold Helmet");
	public static Item plateGold = (new ItemArmor(59, 1, 4, 1)).setIconIndex(20).setName("Gold Chestplate");
	public static Item legsGold = (new ItemArmor(60, 1, 4, 2)).setIconIndex(36).setName("Gold Greaves");
	public static Item bootsGold = (new ItemArmor(61, 1, 4, 3)).setIconIndex(52).setName("Gold Boots");
	public static Item flint = (new Item(62)).setIconIndex(6).setName("Flint");
	public static Item porkRaw = (new ItemFood(63, 3, 1)).setIconIndex(87).setName("Raw Meat");
	public static Item porkCooked = (new ItemFood(64, 8, 1)).setIconIndex(88).setName("Cooked Meat");
	public static Item painting = (new ItemPainting(65)).setIconIndex(26).setName("Painting");
	public static Item appleGold = (new ItemFood(66, 42, 1)).setIconIndex(11).setName("Golden Apple");
	public static Item sign = (new ItemSign(67)).setIconIndex(42).setName("Sign");
	public static Item doorWood = (new ItemDoor(68, Material.wood)).setIconIndex(43).setName("Door");
	public static Item bucketEmpty = (new ItemBucket(69, 0)).setIconIndex(74).setName("Dear God...");
	public static Item bucketWater = (new ItemBucket(70, Block.waterMoving.blockID)).setIconIndex(75).setName("Water Bucket");
	public static Item bucketLava = (new ItemBucket(71, Block.lavaMoving.blockID)).setIconIndex(76).setName("Lava Bucket");
	public static Item minecartEmpty = (new ItemMinecart(72, 0)).setIconIndex(135).setName("Minecart");
	public static Item saddle = (new ItemSaddle(73)).setIconIndex(104).setName("Useless");
	public static Item doorSteel = (new ItemDoor(74, Material.iron)).setIconIndex(44).setName("Iron Door");
	public static Item redstone = (new ItemRedstone(75)).setIconIndex(56).setName("Redstone Dust");
	public static Item snowball = (new ItemSnowball(76)).setIconIndex(14).setName("Snowball");
	public static Item boat = (new ItemBoat(77)).setIconIndex(136).setName("Boat");
	public static Item leather = (new Item(78)).setIconIndex(103).setName("Leather");
	public static Item bucketMilk = (new ItemBucket(79, -1)).setIconIndex(77).setName("Milk Bucket");
	public static Item brick = (new Item(80)).setIconIndex(22).setName("Brick");
	public static Item clay = (new Item(81)).setIconIndex(57).setName("Clay");
	public static Item reed = (new ItemReed(82, Block.reed)).setIconIndex(27).setName("Reed");
	public static Item paper = (new Item(83)).setIconIndex(58).setName("Paper");
	public static Item book = (new Item(84)).setIconIndex(59).setName("Book");
	public static Item slimeBall = (new Item(85)).setIconIndex(30).setName("Gloop");
	public static Item minecartBox = (new ItemMinecart(86, 1)).setIconIndex(151).setName("Portable Storage");
	public static Item minecartEngine = (new ItemMinecart(87, 2)).setIconIndex(167).setName("Vrmm Vrmmmm");
	public static Item egg = (new Item(88)).setMaxStackSize(16).setIconIndex(12).setName("What came first?");
	public static Item compass = (new Item(89)).setIconIndex(54).setName("Arrrr, I bee needing me a compass!");
	public static Item fishingRod = (new Item(90)).setIconIndex(69).setName("Useless, again!");
	public static Item record13 = (new ItemRecord(2000, "13")).setIconIndex(240).setName("13 - C418");
	public static Item recordCat = (new ItemRecord(2001, "cat")).setIconIndex(241).setName("Cat - C418");
	
	public static Item shears = (new ItemTool(2002, 3, 2, new Block[] {Block.leaves, Block.leavesPlr})).setIconIndex(242).setName("Shears");
	public static Item ingotCopper = (new Item(2003)).setIconIndex(243).setName("Copper Ingot");
	
	public static Item swordCopper = (new ItemSword(2004, 2)).setIconIndex(244).setMaxDamage(36 << 2).setEfficiencyOnProperMaterial(10).setName("Copper Sword");
	public static Item shovelCopper = (new ItemSpade(2005, 2)).setIconIndex(245).setMaxDamage(36 << 2).setEfficiencyOnProperMaterial(10).setName("Copper Shovel");
	public static Item pickaxeCopper = (new ItemPickaxe(2006, 2)).setIconIndex(246).setMaxDamage(36 << 2).setEfficiencyOnProperMaterial(10).setName("Copper Pickaxe");
	public static Item axeCopper = (new ItemAxe(2007, 2)).setIconIndex(247).setMaxDamage(36 << 2).setEfficiencyOnProperMaterial(10).setName("Copper Axe");
	public static Item hoeCopper = (new ItemHoe(2008, 2)).setIconIndex(248).setMaxDamage(36 << 2).setEfficiencyOnProperMaterial(10).setName("Copper Hoe");
	
	public static Item fertilizer = (new Item(2009)).setIconIndex(249).setName("Fertilizer");
	public static Item hamburger = (new ItemFood(2010, 5, 8)).setIconIndex(250).setName("Hamburger");
	public static Item mug = (new Item(2011)).setIconIndex(251).setMaxStackSize(1).setName("Mug");
	public static Item tea = (new ItemFood(2012, 6, 1)).setIconIndex(252).setName("Cup o' Tea");
	public static Item teaLeaves = (new Item(2013)).setIconIndex(253).setMaxStackSize(16).setName("Tea Leaves");
	public static Item friedEgg = (new ItemFood(2014, 4, 16)).setIconIndex(254).setName("Fried Egg");
	
	public static Item applePie = (new ItemFood(2015, 7, 1, 3)).setIconIndex(255).setName("Pie");
	public static Item aquamarine = (new Item(2016)).setIconIndex(241-16).setName("Aquamarine");
	
	public static Item aquamarineBucket = (new ItemBucket(2017, Block.waterMoving.blockID, true)).setIconIndex(75+16).setName("Bottemless Water Bucket");
	
	public final int shiftedIndex;
	protected int maxStackSize = 64;
	protected int maxDamage = 32;
	protected int iconIndex;
	protected boolean bFull3D = false;
	protected float efficiencyOnProperMaterial;
	public String name = "Item";

	public Item setMaxStackSize(int size) {
		this.maxStackSize = size;
		return this;
	}
	
	public Item setName(String name) {
		this.name = name;
		return this;
	}
	
	public Item setMaxDamage(int maxDmg) {
		this.maxDamage = maxDmg;
		return this;
	}
	
	public Item setEfficiencyOnProperMaterial(float efficiency) {
		this.efficiencyOnProperMaterial = efficiency;
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
