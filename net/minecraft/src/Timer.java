package net.minecraft.src;

public class Timer {
	public float ticksPerSecond;
	private double lastHRTime;
	public int elapsedTicks;
	public float renderPartialTicks;
	public float timerSpeed = 1.0F;
	public float elapsedPartialTicks = 0.0F;
	private long lastSyncSysClock;
	private long lastSyncHRClock;
	private double timeSyncAdjustment = 1.0D;

	public Timer(float var1) {
		this.ticksPerSecond = var1;
		this.lastSyncSysClock = System.currentTimeMillis();
		this.lastSyncHRClock = System.nanoTime() / 1000000L;
	}

	public void updateTimer() {
		long var1 = System.currentTimeMillis();
		long var3 = var1 - this.lastSyncSysClock;
		long var5 = System.nanoTime() / 1000000L;
		double var9;
		if(var3 > 1000L) {
			long var7 = var5 - this.lastSyncHRClock;
			var9 = (double)var3 / (double)var7;
			this.timeSyncAdjustment += (var9 - this.timeSyncAdjustment) * (double)0.2F;
			this.lastSyncSysClock = var1;
			this.lastSyncHRClock = var5;
		}

		if(var3 < 0L) {
			this.lastSyncSysClock = var1;
			this.lastSyncHRClock = var5;
		}

		double var11 = (double)var5 / 1000.0D;
		var9 = (var11 - this.lastHRTime) * this.timeSyncAdjustment;
		this.lastHRTime = var11;
		if(var9 < 0.0D) {
			var9 = 0.0D;
		}

		if(var9 > 1.0D) {
			var9 = 1.0D;
		}

		this.elapsedPartialTicks = (float)((double)this.elapsedPartialTicks + var9 * (double)this.timerSpeed * (double)this.ticksPerSecond);
		this.elapsedTicks = (int)this.elapsedPartialTicks;
		this.elapsedPartialTicks -= (float)this.elapsedTicks;
		if(this.elapsedTicks > 10) {
			this.elapsedTicks = 10;
		}

		this.renderPartialTicks = this.elapsedPartialTicks;
	}
}
