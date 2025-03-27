package net.minecraft.src;

public class NetHandler {
	public void handleMapChunk(Packet51MapChunk var1) {
	}

	public void registerPacket(Packet var1) {
	}

	public void handleErrorMessage(String var1) {
	}

	public void handleKickDisconnect(Packet255KickDisconnect var1) {
		this.registerPacket(var1);
	}

	public void handleLogin(Packet1Login var1) {
		this.registerPacket(var1);
	}

	public void handleFlying(Packet10Flying var1) {
		this.registerPacket(var1);
	}

	public void handleMultiBlockChange(Packet52MultiBlockChange var1) {
		this.registerPacket(var1);
	}

	public void handleBlockDig(Packet14BlockDig var1) {
		this.registerPacket(var1);
	}

	public void handleBlockChange(Packet53BlockChange var1) {
		this.registerPacket(var1);
	}

	public void handlePreChunk(Packet50PreChunk var1) {
		this.registerPacket(var1);
	}

	public void handleNamedEntitySpawn(Packet20NamedEntitySpawn var1) {
		this.registerPacket(var1);
	}

	public void handleEntity(Packet30Entity var1) {
		this.registerPacket(var1);
	}

	public void handleEntityTeleport(Packet34EntityTeleport var1) {
		this.registerPacket(var1);
	}

	public void handlePlace(Packet15Place var1) {
		this.registerPacket(var1);
	}

	public void handleBlockItemSwitch(Packet16BlockItemSwitch var1) {
		this.registerPacket(var1);
	}

	public void handleDestroyEntity(Packet29DestroyEntity var1) {
		this.registerPacket(var1);
	}

	public void handlePickupSpawn(Packet21PickupSpawn var1) {
		this.registerPacket(var1);
	}

	public void handleCollect(Packet22Collect var1) {
		this.registerPacket(var1);
	}

	public void handleChat(Packet3Chat var1) {
		this.registerPacket(var1);
	}

	public void handleAddToInventory(Packet17AddToInventory var1) {
		this.registerPacket(var1);
	}

	public void handleVehicleSpawn(Packet23VehicleSpawn var1) {
		this.registerPacket(var1);
	}

	public void handleArmAnimation(Packet18ArmAnimation var1) {
		this.registerPacket(var1);
	}

	public void handleHandshake(Packet2Handshake var1) {
		this.registerPacket(var1);
	}

	public void handleMobSpawn(Packet24MobSpawn var1) {
		this.registerPacket(var1);
	}

	public void handleUpdateTime(Packet4UpdateTime var1) {
		this.registerPacket(var1);
	}

	public void handlePlayerInventory(Packet5PlayerInventory var1) {
		this.registerPacket(var1);
	}

	public void handleComplexEntity(Packet59ComplexEntity var1) {
		this.registerPacket(var1);
	}

	public void handleSpawnPosition(Packet6SpawnPosition var1) {
		this.registerPacket(var1);
	}
}
