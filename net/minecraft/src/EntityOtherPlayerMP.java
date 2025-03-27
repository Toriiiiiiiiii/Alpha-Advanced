package net.minecraft.src;

public class EntityOtherPlayerMP extends EntityPlayer {
	private int otherPlayerMPPosRotationIncrements;
	private double otherPlayerMPX;
	private double otherPlayerMPY;
	private double otherPlayerMPZ;
	private double otherPlayerMPYaw;
	private double otherPlayerMPPitch;
	float unusedFloat = 0.0F;

	public EntityOtherPlayerMP(World var1, String var2) {
		super(var1);
		this.username = var2;
		this.yOffset = 0.0F;
		this.stepHeight = 0.0F;
		if(var2 != null && var2.length() > 0) {
			this.skinUrl = "http://www.minecraft.net/skin/" + var2 + ".png";
			System.out.println("Loading texture " + this.skinUrl);
		}

		this.noClip = true;
		this.renderDistanceWeight = 10.0D;
	}

	public boolean attackEntityFrom(Entity var1, int var2) {
		return true;
	}

	public void setPositionAndRotation(double var1, double var3, double var5, float var7, float var8, int var9) {
		this.yOffset = 0.0F;
		this.otherPlayerMPX = var1;
		this.otherPlayerMPY = var3;
		this.otherPlayerMPZ = var5;
		this.otherPlayerMPYaw = (double)var7;
		this.otherPlayerMPPitch = (double)var8;
		this.otherPlayerMPPosRotationIncrements = var9;
	}

	public void onUpdate() {
		super.onUpdate();
		this.prevLimbYaw = this.limbYaw;
		double var1 = this.posX - this.prevPosX;
		double var3 = this.posZ - this.prevPosZ;
		float var5 = MathHelper.sqrt_double(var1 * var1 + var3 * var3) * 4.0F;
		if(var5 > 1.0F) {
			var5 = 1.0F;
		}

		this.limbYaw += (var5 - this.limbYaw) * 0.4F;
		this.limbSwing += this.limbYaw;
	}

	public float getShadowSize() {
		return 0.0F;
	}

	public void onLivingUpdate() {
		super.updateEntityActionState();
		if(this.otherPlayerMPPosRotationIncrements > 0) {
			double var1 = this.posX + (this.otherPlayerMPX - this.posX) / (double)this.otherPlayerMPPosRotationIncrements;
			double var3 = this.posY + (this.otherPlayerMPY - this.posY) / (double)this.otherPlayerMPPosRotationIncrements;
			double var5 = this.posZ + (this.otherPlayerMPZ - this.posZ) / (double)this.otherPlayerMPPosRotationIncrements;

			double var7;
			for(var7 = this.otherPlayerMPYaw - (double)this.rotationYaw; var7 < -180.0D; var7 += 360.0D) {
			}

			while(var7 >= 180.0D) {
				var7 -= 360.0D;
			}

			this.rotationYaw = (float)((double)this.rotationYaw + var7 / (double)this.otherPlayerMPPosRotationIncrements);
			this.rotationPitch = (float)((double)this.rotationPitch + (this.otherPlayerMPPitch - (double)this.rotationPitch) / (double)this.otherPlayerMPPosRotationIncrements);
			--this.otherPlayerMPPosRotationIncrements;
			this.setPosition(var1, var3, var5);
			this.setRotation(this.rotationYaw, this.rotationPitch);
		}

		this.prevCameraYaw = this.cameraYaw;
		float var9 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
		float var2 = (float)Math.atan(-this.motionY * (double)0.2F) * 15.0F;
		if(var9 > 0.1F) {
			var9 = 0.1F;
		}

		if(!this.onGround || this.health <= 0) {
			var9 = 0.0F;
		}

		if(this.onGround || this.health <= 0) {
			var2 = 0.0F;
		}

		this.cameraYaw += (var9 - this.cameraYaw) * 0.4F;
		this.cameraPitch += (var2 - this.cameraPitch) * 0.8F;
	}
}
