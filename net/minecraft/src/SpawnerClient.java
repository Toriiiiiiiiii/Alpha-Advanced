package net.minecraft.src;

class SpawnerClient extends SpawnerAnimals {
	final PlayerControllerSP playerController;

	SpawnerClient(PlayerControllerSP var1, int var2, Class var3, Class[] var4) {
		super(var2, var3, var4);
		this.playerController = var1;
	}

	protected ChunkPosition getRandomSpawningPointInChunk(World var1, int var2, int var3) {
		Chunk chunk = var1.getChunkFromChunkCoords(var2, var3);
		int var4 = var2 * 16 + var1.rand.nextInt(16);
		int var6 = var3 * 16 + var1.rand.nextInt(16);
		int var5 = var1.rand.nextInt(var1.rand.nextInt(chunk == null ? 248 : (chunk.blocks2 == null ? 119 : 247)) + 8);
		return new ChunkPosition(var4, var5, var6);
	}
}
