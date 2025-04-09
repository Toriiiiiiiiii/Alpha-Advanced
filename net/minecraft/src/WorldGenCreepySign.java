package net.minecraft.src;

import java.util.Random;

public class WorldGenCreepySign extends WorldGenerator {
	private int blockId;
	private String[] messages = new String[]{"You're not alone.", "Turn Around.", "I will find you.", "You cannot hide.", "Be prepared.", "Leave.", "This world isn't"};
	private String[] messages2 = new String[] {"", "", "", "", "", "", "yours."};
	
	public WorldGenCreepySign() {
		this.blockId = Block.signStanding.blockID;
	}

	public boolean generate(World var1, Random var2, int var3, int var4, int var5) {
		for(int var6 = 0; var6 < 64; ++var6) {
			int var7 = var3 + var2.nextInt(8) - var2.nextInt(8);
			int var8 = var4 + var2.nextInt(4) - var2.nextInt(4);
			int var9 = var5 + var2.nextInt(8) - var2.nextInt(8);
			if(var1.getBlockId(var7, var8, var9) == 0 && var1.getBlockId(var7, var8-1, var9) != 0 && (Block.blocksList[var1.getBlockId(var7, var8-1, var9)]).isOpaqueCube()) {
				var1.setBlockAndMetadataWithNotify(var7, var8, var9, this.blockId, MathHelper.floor_double((double)(((var2.nextDouble()*360.0F) + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15);
//				Block.signStanding.onBlockAdded(var1, var7, var8, var9);
//				var1.setBlockTileEntity(var7, var8, var9, new TileEntitySign());
				
				TileEntitySign sign = (TileEntitySign)(var1.getBlockTileEntity(var7, var8, var9));
				
				if(sign != null) {
					int index = var2.nextInt(this.messages.length);
					sign.signText[1] = this.messages[index];
					sign.signText[2] = this.messages2[index];
				} else
					var1.setBlockWithNotify(var7, var8, var9, 0);
				
				break;
			}
		}

		return true;
	}
}
