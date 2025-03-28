package net.minecraft.client;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.io.File;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.EffectRenderer;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.EntityRenderer;
import net.minecraft.src.EnumOS;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GLAllocation;
import net.minecraft.src.GameSettings;
import net.minecraft.src.GameWindowListener;
import net.minecraft.src.GuiChat;
import net.minecraft.src.GuiConflictWarning;
import net.minecraft.src.GuiConnecting;
import net.minecraft.src.GuiErrorScreen;
import net.minecraft.src.GuiGameOver;
import net.minecraft.src.GuiIngame;
import net.minecraft.src.GuiIngameMenu;
import net.minecraft.src.GuiInventory;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiNewWorld;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ItemRenderer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.LoadingScreenRenderer;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MinecraftError;
import net.minecraft.src.MinecraftException;
import net.minecraft.src.MinecraftImpl;
import net.minecraft.src.ModelBiped;
import net.minecraft.src.MouseHelper;
import net.minecraft.src.MovementInputFromOptions;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.OSMap;
import net.minecraft.src.OpenGlCapsChecker;
import net.minecraft.src.PlayerController;
import net.minecraft.src.PlayerControllerCreative;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.RenderGlobal;
import net.minecraft.src.RenderManager;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.Session;
import net.minecraft.src.SoundManager;
import net.minecraft.src.Tessellator;
import net.minecraft.src.TextureCompassFX;
import net.minecraft.src.TextureFlamesFX;
import net.minecraft.src.TextureLavaFX;
import net.minecraft.src.TextureLavaFlowFX;
import net.minecraft.src.TextureWaterFX;
import net.minecraft.src.TextureWaterFlowFX;
import net.minecraft.src.ThreadDownloadResources;
import net.minecraft.src.ThreadSleepForever;
import net.minecraft.src.Timer;
import net.minecraft.src.UnexpectedThrowable;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;
import net.minecraft.src.WorldRenderer;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public abstract class Minecraft implements Runnable {
	public boolean hideUI = false;
	public PlayerController playerController;
	private boolean fullscreen = false;
	public int displayWidth;
	public int displayHeight;
	private OpenGlCapsChecker glCapabilities;
	private Timer timer = new Timer(20.0F);
	public World theWorld;
	public RenderGlobal renderGlobal;
	public EntityPlayerSP thePlayer;
	public EffectRenderer effectRenderer;
	public Session session = null;
	public String minecraftUri;
	public Canvas mcCanvas;
	public boolean appletMode = true;
	public volatile boolean isGamePaused = false;
	public RenderEngine renderEngine;
	public FontRenderer fontRenderer;
	public GuiScreen currentScreen = null;
	public LoadingScreenRenderer loadingScreen = new LoadingScreenRenderer(this);
	public EntityRenderer entityRenderer = new EntityRenderer(this);
	private ThreadDownloadResources downloadResourcesThread;
	private int ticksRan = 0;
	private int leftClickCounter = 0;
	private int tempDisplayWidth;
	private int tempDisplayHeight;
	public String loadMapUser = null;
	public int loadMapID = 0;
	public GuiIngame ingameGUI;
	public boolean skipRenderWorld = false;
	public ModelBiped playerModelBiped = new ModelBiped(0.0F);
	public MovingObjectPosition objectMouseOver = null;
	public GameSettings options;
	protected MinecraftApplet mcApplet;
	public SoundManager sndManager = new SoundManager();
	public MouseHelper mouseHelper;
	public File mcDataDir;
	public static long[] frameTimes = new long[512];
	public static int numRecordedFrameTimes = 0;
	private String serverName;
	private int serverPort;
	private TextureWaterFX textureWaterFX = new TextureWaterFX();
	private TextureLavaFX textureLavaFX = new TextureLavaFX();
	private static File minecraftDir = null;
	public volatile boolean running = true;
	public String debug = "";
	long prevFrameTime = -1L;
	public boolean inGameHasFocus = false;
	private int mouseTicksRan = 0;
	public boolean isRaining = false;
	long systemTime = System.currentTimeMillis();
	private int joinPlayerCounter = 0;

	public Minecraft(Component var1, Canvas var2, MinecraftApplet var3, int var4, int var5, boolean var6) {
		this.tempDisplayWidth = var4;
		this.tempDisplayHeight = var5;
		this.fullscreen = var6;
		this.mcApplet = var3;
		new ThreadSleepForever(this, "Timer hack thread");
		this.mcCanvas = var2;
		this.displayWidth = var4;
		this.displayHeight = var5;
		this.fullscreen = var6;
	}

	public abstract void displayUnexpectedThrowable(UnexpectedThrowable var1);

	public void setServer(String var1, int var2) {
		this.serverName = var1;
		this.serverPort = var2;
	}

	public void startGame() throws LWJGLException {
		if(this.mcCanvas != null) {
			Graphics var1 = this.mcCanvas.getGraphics();
			if(var1 != null) {
				var1.setColor(Color.BLACK);
				var1.fillRect(0, 0, this.displayWidth, this.displayHeight);
				var1.dispose();
			}

			Display.setParent(this.mcCanvas);
		} else if(this.fullscreen) {
			Display.setFullscreen(true);
			this.displayWidth = Display.getDisplayMode().getWidth();
			this.displayHeight = Display.getDisplayMode().getHeight();
			if(this.displayWidth <= 0) {
				this.displayWidth = 1;
			}

			if(this.displayHeight <= 0) {
				this.displayHeight = 1;
			}
		} else {
			Display.setDisplayMode(new DisplayMode(this.displayWidth, this.displayHeight));
		}

		Display.setTitle("Minecraft Minecraft Alpha v1.1.2_01");

		try {
			Display.create();
		} catch (LWJGLException var6) {
			var6.printStackTrace();

			try {
				Thread.sleep(1000L);
			} catch (InterruptedException var5) {
			}

			Display.create();
		}

		RenderManager.instance.itemRenderer = new ItemRenderer(this);
		this.mcDataDir = getMinecraftDir();
		this.options = new GameSettings(this, this.mcDataDir);
		this.renderEngine = new RenderEngine(this.options);
		this.fontRenderer = new FontRenderer(this.options, "/default.png", this.renderEngine);
		this.loadScreen();
		Keyboard.create();
		Mouse.create();
		this.mouseHelper = new MouseHelper(this.mcCanvas);

		try {
			Controllers.create();
		} catch (Exception var4) {
			var4.printStackTrace();
		}

		this.checkGLError("Pre startup");
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glClearDepth(1.0D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glCullFace(GL11.GL_BACK);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		this.checkGLError("Startup");
		this.glCapabilities = new OpenGlCapsChecker();
		this.sndManager.loadSoundSettings(this.options);
		this.renderEngine.registerTextureFX(this.textureLavaFX);
		this.renderEngine.registerTextureFX(this.textureWaterFX);
		this.renderEngine.registerTextureFX(new TextureCompassFX(this));
		this.renderEngine.registerTextureFX(new TextureWaterFlowFX());
		this.renderEngine.registerTextureFX(new TextureLavaFlowFX());
		this.renderEngine.registerTextureFX(new TextureFlamesFX(0));
		this.renderEngine.registerTextureFX(new TextureFlamesFX(1));
		this.renderGlobal = new RenderGlobal(this, this.renderEngine);
		GL11.glViewport(0, 0, this.displayWidth, this.displayHeight);
		this.effectRenderer = new EffectRenderer(this.theWorld, this.renderEngine);

		try {
			this.downloadResourcesThread = new ThreadDownloadResources(this.mcDataDir, this);
			this.downloadResourcesThread.start();
		} catch (Exception var3) {
		}

		this.checkGLError("Post startup");
		this.ingameGUI = new GuiIngame(this);
		if(this.serverName != null) {
			this.displayGuiScreen(new GuiConnecting(this, this.serverName, this.serverPort));
		} else {
			this.displayGuiScreen(new GuiMainMenu());
		}

	}

	private void loadScreen() throws LWJGLException {
		ScaledResolution var1 = new ScaledResolution(this.displayWidth, this.displayHeight);
		int var2 = var1.getScaledWidth();
		int var3 = var1.getScaledHeight();
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0D, (double)var2, (double)var3, 0.0D, 1000.0D, 3000.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
		GL11.glViewport(0, 0, this.displayWidth, this.displayHeight);
		GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
		Tessellator var4 = Tessellator.instance;
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_FOG);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.renderEngine.getTexture("/title/mojang.png"));
		var4.startDrawingQuads();
		var4.setColorOpaque_I(16777215);
		var4.addVertexWithUV(0.0D, (double)this.displayHeight, 0.0D, 0.0D, 0.0D);
		var4.addVertexWithUV((double)this.displayWidth, (double)this.displayHeight, 0.0D, 0.0D, 0.0D);
		var4.addVertexWithUV((double)this.displayWidth, 0.0D, 0.0D, 0.0D, 0.0D);
		var4.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
		var4.draw();
		short var5 = 256;
		short var6 = 256;
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		var4.setColorOpaque_I(16777215);
		this.scaledTessellator((this.displayWidth / 2 - var5) / 2, (this.displayHeight / 2 - var6) / 2, 0, 0, var5, var6);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_FOG);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		Display.swapBuffers();
	}

	public void scaledTessellator(int var1, int var2, int var3, int var4, int var5, int var6) {
		float var7 = 0.00390625F;
		float var8 = 0.00390625F;
		Tessellator var9 = Tessellator.instance;
		var9.startDrawingQuads();
		var9.addVertexWithUV((double)(var1 + 0), (double)(var2 + var6), 0.0D, (double)((float)(var3 + 0) * var7), (double)((float)(var4 + var6) * var8));
		var9.addVertexWithUV((double)(var1 + var5), (double)(var2 + var6), 0.0D, (double)((float)(var3 + var5) * var7), (double)((float)(var4 + var6) * var8));
		var9.addVertexWithUV((double)(var1 + var5), (double)(var2 + 0), 0.0D, (double)((float)(var3 + var5) * var7), (double)((float)(var4 + 0) * var8));
		var9.addVertexWithUV((double)(var1 + 0), (double)(var2 + 0), 0.0D, (double)((float)(var3 + 0) * var7), (double)((float)(var4 + 0) * var8));
		var9.draw();
	}

	public static File getMinecraftDir() {
		if(minecraftDir == null) {
			minecraftDir = getAppDir("minecraft");
		}

		return minecraftDir;
	}

	public static File getAppDir(String var0) {
		String var1 = System.getProperty("user.home", ".");
		File var2;
		switch(OSMap.osValues[getOs().ordinal()]) {
		case 1:
		case 2:
			var2 = new File(var1, '.' + var0 + '/');
			break;
		case 3:
			String var3 = System.getenv("APPDATA");
			if(var3 != null) {
				var2 = new File(var3, "." + var0 + '/');
			} else {
				var2 = new File(var1, '.' + var0 + '/');
			}
			break;
		case 4:
			var2 = new File(var1, "Library/Application Support/" + var0);
			break;
		default:
			var2 = new File(var1, var0 + '/');
		}

		if(!var2.exists() && !var2.mkdirs()) {
			throw new RuntimeException("The working directory could not be created: " + var2);
		} else {
			return var2;
		}
	}

	private static EnumOS getOs() {
		String var0 = System.getProperty("os.name").toLowerCase();
		return var0.contains("win") ? EnumOS.windows : (var0.contains("mac") ? EnumOS.macos : (var0.contains("solaris") ? EnumOS.solaris : (var0.contains("sunos") ? EnumOS.solaris : (var0.contains("linux") ? EnumOS.linux : (var0.contains("unix") ? EnumOS.linux : EnumOS.unknown)))));
	}

	public void displayGuiScreen(GuiScreen var1) {
		if(!(this.currentScreen instanceof GuiErrorScreen)) {
			if(this.currentScreen != null) {
				this.currentScreen.onGuiClosed();
			}

			if(var1 == null && this.theWorld == null) {
				var1 = new GuiMainMenu();
			} else if(var1 == null && this.thePlayer.health <= 0) {
				var1 = new GuiGameOver();
			}

			this.currentScreen = (GuiScreen)var1;
			if(var1 != null) {
				this.setIngameNotInFocus();
				ScaledResolution var2 = new ScaledResolution(this.displayWidth, this.displayHeight);
				int var3 = var2.getScaledWidth();
				int var4 = var2.getScaledHeight();
				((GuiScreen)var1).setWorldAndResolution(this, var3, var4);
				this.skipRenderWorld = false;
			} else {
				this.setIngameFocus();
			}

		}
	}

	private void checkGLError(String var1) {
		int var2 = GL11.glGetError();
		if(var2 != 0) {
			String var3 = GLU.gluErrorString(var2);
			System.out.println("########## GL ERROR ##########");
			System.out.println("@ " + var1);
			System.out.println(var2 + ": " + var3);
			System.exit(0);
		}

	}

	public void shutdownMinecraftApplet() {
		if(this.mcApplet != null) {
			this.mcApplet.clearApplet();
		}

		try {
			if(this.downloadResourcesThread != null) {
				this.downloadResourcesThread.closeMinecraft();
			}
		} catch (Exception var8) {
		}

		try {
			System.out.println("Stopping!");
			this.changeWorld1((World)null);

			try {
				GLAllocation.deleteTexturesAndDisplayLists();
			} catch (Exception var6) {
			}

			this.sndManager.closeMinecraft();
			Mouse.destroy();
			Keyboard.destroy();
		} finally {
			Display.destroy();
		}

		System.gc();
	}

	public void run() {
		this.running = true;

		try {
			this.startGame();
		} catch (Exception var13) {
			var13.printStackTrace();
			this.displayUnexpectedThrowable(new UnexpectedThrowable("Failed to start game", var13));
			return;
		}

		try {
			try {
				long var1 = System.currentTimeMillis();
				int var3 = 0;

				while(this.running && (this.mcApplet == null || this.mcApplet.isActive())) {
					AxisAlignedBB.clearBoundingBoxPool();
					Vec3D.initialize();
					if(this.mcCanvas == null && Display.isCloseRequested()) {
						this.shutdown();
					}

					if(this.isGamePaused && this.theWorld != null) {
						float var4 = this.timer.renderPartialTicks;
						this.timer.updateTimer();
						this.timer.renderPartialTicks = var4;
					} else {
						this.timer.updateTimer();
					}

					for(int var17 = 0; var17 < this.timer.elapsedTicks; ++var17) {
						++this.ticksRan;

						try {
							this.runTick();
						} catch (MinecraftException var12) {
							this.theWorld = null;
							this.changeWorld1((World)null);
							this.displayGuiScreen(new GuiConflictWarning());
						}
					}

					this.checkGLError("Pre render");
					this.sndManager.setListener(this.thePlayer, this.timer.renderPartialTicks);
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					if(this.theWorld != null) {
						while(this.theWorld.updatingLighting(10)) {
						}
					}

					if(!this.skipRenderWorld) {
						if(this.playerController != null) {
							this.playerController.setPartialTime(this.timer.renderPartialTicks);
						}

						this.entityRenderer.updateCameraAndRender(this.timer.renderPartialTicks);
					}

					if(!Display.isActive()) {
						if(this.fullscreen) {
							this.toggleFullscreen();
						}

						Thread.sleep(10L);
					}

					if(Keyboard.isKeyDown(Keyboard.KEY_F6)) {
						this.displayDebugInfo();
					} else {
						this.prevFrameTime = System.nanoTime();
					}

					Thread.yield();
					Display.update();
					if(this.mcCanvas != null && !this.fullscreen && (this.mcCanvas.getWidth() != this.displayWidth || this.mcCanvas.getHeight() != this.displayHeight)) {
						this.displayWidth = this.mcCanvas.getWidth();
						this.displayHeight = this.mcCanvas.getHeight();
						if(this.displayWidth <= 0) {
							this.displayWidth = 1;
						}

						if(this.displayHeight <= 0) {
							this.displayHeight = 1;
						}

						this.resize(this.displayWidth, this.displayHeight);
					}

					if(this.options.limitFramerate) {
						Thread.sleep(5L);
					}

					this.checkGLError("Post render");
					++var3;

					for(this.isGamePaused = !this.isMultiplayerWorld() && this.currentScreen != null && this.currentScreen.doesGuiPauseGame(); System.currentTimeMillis() >= var1 + 1000L; var3 = 0) {
						this.debug = var3 + " fps, " + WorldRenderer.chunksUpdated + " chunk updates";
						WorldRenderer.chunksUpdated = 0;
						var1 += 1000L;
					}
				}
			} catch (MinecraftError var14) {
			} catch (Throwable var15) {
				this.theWorld = null;
				var15.printStackTrace();
				this.displayUnexpectedThrowable(new UnexpectedThrowable("Unexpected error", var15));
			}

		} finally {
		}
	}

	private void displayDebugInfo() {
		if(this.prevFrameTime == -1L) {
			this.prevFrameTime = System.nanoTime();
		}

		long var1 = System.nanoTime();
		frameTimes[numRecordedFrameTimes++ & frameTimes.length - 1] = var1 - this.prevFrameTime;
		this.prevFrameTime = var1;
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0D, (double)this.displayWidth, (double)this.displayHeight, 0.0D, 1000.0D, 3000.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
		GL11.glLineWidth(1.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		Tessellator var3 = Tessellator.instance;
		var3.startDrawing(7);
		var3.setColorOpaque_I(538968064);
		var3.addVertex(0.0D, (double)(this.displayHeight - 100), 0.0D);
		var3.addVertex(0.0D, (double)this.displayHeight, 0.0D);
		var3.addVertex((double)frameTimes.length, (double)this.displayHeight, 0.0D);
		var3.addVertex((double)frameTimes.length, (double)(this.displayHeight - 100), 0.0D);
		var3.draw();
		long var4 = 0L;

		int var6;
		for(var6 = 0; var6 < frameTimes.length; ++var6) {
			var4 += frameTimes[var6];
		}

		var6 = (int)(var4 / 200000L / (long)frameTimes.length);
		var3.startDrawing(7);
		var3.setColorOpaque_I(541065216);
		var3.addVertex(0.0D, (double)(this.displayHeight - var6), 0.0D);
		var3.addVertex(0.0D, (double)this.displayHeight, 0.0D);
		var3.addVertex((double)frameTimes.length, (double)this.displayHeight, 0.0D);
		var3.addVertex((double)frameTimes.length, (double)(this.displayHeight - var6), 0.0D);
		var3.draw();
		var3.startDrawing(1);

		for(int var7 = 0; var7 < frameTimes.length; ++var7) {
			int var8 = (var7 - numRecordedFrameTimes & frameTimes.length - 1) * 255 / frameTimes.length;
			int var9 = var8 * var8 / 255;
			var9 = var9 * var9 / 255;
			int var10 = var9 * var9 / 255;
			var10 = var10 * var10 / 255;
			var3.setColorOpaque_I(-16777216 + var10 + var9 * 256 + var8 * 65536);
			long var11 = frameTimes[var7] / 200000L;
			var3.addVertex((double)((float)var7 + 0.5F), (double)((float)((long)this.displayHeight - var11) + 0.5F), 0.0D);
			var3.addVertex((double)((float)var7 + 0.5F), (double)((float)this.displayHeight + 0.5F), 0.0D);
		}

		var3.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	public void shutdown() {
		this.running = false;
	}

	public void setIngameFocus() {
		if(Display.isActive()) {
			if(!this.inGameHasFocus) {
				this.inGameHasFocus = true;
				this.mouseHelper.grabMouseCursor();
				this.displayGuiScreen((GuiScreen)null);
				this.mouseTicksRan = this.ticksRan + 10000;
			}
		}
	}

	public void setIngameNotInFocus() {
		if(this.inGameHasFocus) {
			if(this.thePlayer != null) {
				this.thePlayer.resetPlayerKeyState();
			}

			this.inGameHasFocus = false;
			this.mouseHelper.ungrabMouseCursor();
		}
	}

	public void displayInGameMenu() {
		if(this.currentScreen == null) {
			this.displayGuiScreen(new GuiIngameMenu());
		}
	}

	private void sendClickBlockToController(int var1, boolean var2) {
		if(!this.playerController.isInTestMode) {
			if(var1 != 0 || this.leftClickCounter <= 0) {
				if(var2 && this.objectMouseOver != null && this.objectMouseOver.typeOfHit == 0 && var1 == 0) {
					int var3 = this.objectMouseOver.blockX;
					int var4 = this.objectMouseOver.blockY;
					int var5 = this.objectMouseOver.blockZ;
					this.playerController.sendBlockRemoving(var3, var4, var5, this.objectMouseOver.sideHit);
					this.effectRenderer.addBlockHitEffects(var3, var4, var5, this.objectMouseOver.sideHit);
				} else {
					this.playerController.resetBlockRemoving();
				}

			}
		}
	}

	private void clickMouse(int var1) {
		if(var1 != 0 || this.leftClickCounter <= 0) {
			if(var1 == 0) {
				this.thePlayer.swingItem();
			}

			int var3;
			if(this.objectMouseOver == null) {
				if(var1 == 0 && !(this.playerController instanceof PlayerControllerCreative)) {
					this.leftClickCounter = 10;
				}
			} else if(this.objectMouseOver.typeOfHit == 1) {
				if(var1 == 0) {
					this.thePlayer.attackEntity(this.objectMouseOver.entityHit);
				}

				if(var1 == 1) {
					this.thePlayer.interactWithEntity(this.objectMouseOver.entityHit);
				}
			} else if(this.objectMouseOver.typeOfHit == 0) {
				int var2 = this.objectMouseOver.blockX;
				var3 = this.objectMouseOver.blockY;
				int var4 = this.objectMouseOver.blockZ;
				int var5 = this.objectMouseOver.sideHit;
				Block var6 = Block.blocksList[this.theWorld.getBlockId(var2, var3, var4)];
				if(var1 == 0) {
					this.theWorld.extinguishFire(var2, var3, var4, this.objectMouseOver.sideHit);
					if(var6 != Block.bedrock || this.thePlayer.unusedMiningCooldown >= 100) {
						this.playerController.clickBlock(var2, var3, var4, this.objectMouseOver.sideHit);
					}
				} else {
					ItemStack var7 = this.thePlayer.inventory.getCurrentItem();
					int var8 = var7 != null ? var7.stackSize : 0;
					if(this.playerController.onPlayerRightClick(this.thePlayer, this.theWorld, var7, var2, var3, var4, var5)) {
						this.thePlayer.swingItem();
					}

					if(var7 == null) {
						return;
					}

					if(var7.stackSize == 0) {
						this.thePlayer.inventory.mainInventory[this.thePlayer.inventory.currentItem] = null;
					} else if(var7.stackSize != var8) {
						this.entityRenderer.itemRenderer.resetEquippedProgress();
					}
				}
			}

			if(var1 == 1) {
				ItemStack var9 = this.thePlayer.inventory.getCurrentItem();
				if(var9 != null) {
					var3 = var9.stackSize;
					ItemStack var10 = var9.useItemRightClick(this.theWorld, this.thePlayer);
					if(var10 != var9 || var10 != null && var10.stackSize != var3) {
						this.thePlayer.inventory.mainInventory[this.thePlayer.inventory.currentItem] = var10;
						this.entityRenderer.itemRenderer.resetEquippedProgress2();
						if(var10.stackSize == 0) {
							this.thePlayer.inventory.mainInventory[this.thePlayer.inventory.currentItem] = null;
						}
					}
				}
			}

		}
	}

	public void toggleFullscreen() {
		try {
			this.fullscreen = !this.fullscreen;
			System.out.println("Toggle fullscreen!");
			if(this.fullscreen) {
				Display.setDisplayMode(Display.getDesktopDisplayMode());
				this.displayWidth = Display.getDisplayMode().getWidth();
				this.displayHeight = Display.getDisplayMode().getHeight();
				if(this.displayWidth <= 0) {
					this.displayWidth = 1;
				}

				if(this.displayHeight <= 0) {
					this.displayHeight = 1;
				}
			} else {
				if(this.mcCanvas != null) {
					this.displayWidth = this.mcCanvas.getWidth();
					this.displayHeight = this.mcCanvas.getHeight();
				} else {
					this.displayWidth = this.tempDisplayWidth;
					this.displayHeight = this.tempDisplayHeight;
				}

				if(this.displayWidth <= 0) {
					this.displayWidth = 1;
				}

				if(this.displayHeight <= 0) {
					this.displayHeight = 1;
				}

				Display.setDisplayMode(new DisplayMode(this.tempDisplayWidth, this.tempDisplayHeight));
			}

			this.setIngameNotInFocus();
			Display.setFullscreen(this.fullscreen);
			Display.update();
			Thread.sleep(1000L);
			if(this.fullscreen) {
				this.setIngameFocus();
			}

			if(this.currentScreen != null) {
				this.setIngameNotInFocus();
				this.resize(this.displayWidth, this.displayHeight);
			}

			System.out.println("Size: " + this.displayWidth + ", " + this.displayHeight);
		} catch (Exception var2) {
			var2.printStackTrace();
		}

	}

	private void resize(int var1, int var2) {
		if(var1 <= 0) {
			var1 = 1;
		}

		if(var2 <= 0) {
			var2 = 1;
		}

		this.displayWidth = var1;
		this.displayHeight = var2;
		if(this.currentScreen != null) {
			ScaledResolution var3 = new ScaledResolution(var1, var2);
			int var4 = var3.getScaledWidth();
			int var5 = var3.getScaledHeight();
			this.currentScreen.setWorldAndResolution(this, var4, var5);
		}

	}

	private void clickMiddleMouseButton() {
		if(this.objectMouseOver != null) {
			int var1 = this.theWorld.getBlockId(this.objectMouseOver.blockX, this.objectMouseOver.blockY, this.objectMouseOver.blockZ);
			if(var1 == Block.grass.blockID) {
				var1 = Block.dirt.blockID;
			}

			if(var1 == Block.stairDouble.blockID) {
				var1 = Block.stairSingle.blockID;
			}

			if(var1 == Block.bedrock.blockID) {
				var1 = Block.stone.blockID;
			}

			this.thePlayer.inventory.setCurrentItem(var1, this.playerController instanceof PlayerControllerCreative);
		}

	}

	public void runTick() {
		this.ingameGUI.updateTick();
		this.entityRenderer.getMouseOver(1.0F);
		if(this.thePlayer != null) {
			this.thePlayer.onPlayerUpdate();
		}

		if(!this.isGamePaused && this.theWorld != null) {
			this.playerController.onUpdate();
		}

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.renderEngine.getTexture("/terrain.png"));
		if(!this.isGamePaused) {
			this.renderEngine.updateDynamicTextures();
		}

		if(this.currentScreen == null && this.thePlayer != null && this.thePlayer.health <= 0) {
			this.displayGuiScreen((GuiScreen)null);
		}

		if(this.currentScreen != null) {
			this.mouseTicksRan = this.ticksRan + 10000;
		}

		if(this.currentScreen != null) {
			this.currentScreen.handleInput();
			if(this.currentScreen != null) {
				this.currentScreen.updateScreen();
			}
		}

		if(this.currentScreen == null || this.currentScreen.allowUserInput) {
			label234:
			while(true) {
				while(true) {
					while(true) {
						long var1;
						do {
							if(!Mouse.next()) {
								if(this.leftClickCounter > 0) {
									--this.leftClickCounter;
								}

								while(true) {
									while(true) {
										do {
											if(!Keyboard.next()) {
												if(this.currentScreen == null) {
													if(Mouse.isButtonDown(0) && (float)(this.ticksRan - this.mouseTicksRan) >= this.timer.ticksPerSecond / 4.0F && this.inGameHasFocus) {
														this.clickMouse(0);
														this.mouseTicksRan = this.ticksRan;
													}

													if(Mouse.isButtonDown(1) && (float)(this.ticksRan - this.mouseTicksRan) >= this.timer.ticksPerSecond / 4.0F && this.inGameHasFocus) {
														this.clickMouse(1);
														this.mouseTicksRan = this.ticksRan;
													}
												}

												this.sendClickBlockToController(0, this.currentScreen == null && Mouse.isButtonDown(0) && this.inGameHasFocus);
												break label234;
											}

											this.thePlayer.handleKeyPress(Keyboard.getEventKey(), Keyboard.getEventKeyState());
										} while(!Keyboard.getEventKeyState());

										if(Keyboard.getEventKey() == Keyboard.KEY_F11) {
											this.toggleFullscreen();
										} else {
											if(this.currentScreen != null) {
												this.currentScreen.handleKeyboardInput();
											} else {
												if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
													this.displayInGameMenu();
												}

												if(Keyboard.getEventKey() == Keyboard.KEY_S && Keyboard.isKeyDown(Keyboard.KEY_F3)) {
													this.forceReload();
												}

												if(Keyboard.getEventKey() == Keyboard.KEY_F1) {
													this.hideUI = !this.hideUI;
												}
												
												if(Keyboard.getEventKey() == Keyboard.KEY_F5) {
													this.options.thirdPersonView = !this.options.thirdPersonView;
												}

												if(Keyboard.getEventKey() == this.options.keyBindInventory.keyCode) {
													this.displayGuiScreen(new GuiInventory(this.thePlayer.inventory, this.thePlayer.inventory.craftingInventory));
												}

												if(Keyboard.getEventKey() == this.options.keyBindDrop.keyCode) {
													this.thePlayer.dropPlayerItemWithRandomChoice(this.thePlayer.inventory.decrStackSize(this.thePlayer.inventory.currentItem, 1), false);
												}

												if(this.isMultiplayerWorld() && Keyboard.getEventKey() == this.options.keyBindChat.keyCode) {
													this.displayGuiScreen(new GuiChat());
												}
											}

											for(int var4 = 0; var4 < 9; ++var4) {
												if(Keyboard.getEventKey() == Keyboard.KEY_1 + var4) {
													this.thePlayer.inventory.currentItem = var4;
												}
											}

											if(Keyboard.getEventKey() == this.options.keyBindToggleFog.keyCode) {
												this.options.setOptionValue(4, !Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && !Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) ? 1 : -1);
											}
										}
									}
								}
							}

							var1 = System.currentTimeMillis() - this.systemTime;
						} while(var1 > 200L);

						int var3 = Mouse.getEventDWheel();
						if(var3 != 0) {
							this.thePlayer.inventory.changeCurrentItem(var3);
						}

						if(this.currentScreen == null) {
							if(!this.inGameHasFocus && Mouse.getEventButtonState()) {
								this.setIngameFocus();
							} else {
								if(Mouse.getEventButton() == 0 && Mouse.getEventButtonState()) {
									this.clickMouse(0);
									this.mouseTicksRan = this.ticksRan;
								}

								if(Mouse.getEventButton() == 1 && Mouse.getEventButtonState()) {
									this.clickMouse(1);
									this.mouseTicksRan = this.ticksRan;
								}

								if(Mouse.getEventButton() == 2 && Mouse.getEventButtonState()) {
									this.clickMiddleMouseButton();
								}
							}
						} else if(this.currentScreen != null) {
							this.currentScreen.handleMouseInput();
						}
					}
				}
			}
		}

		if(this.theWorld != null) {
			if(this.thePlayer != null) {
				++this.joinPlayerCounter;
				if(this.joinPlayerCounter == 30) {
					this.joinPlayerCounter = 0;
					this.theWorld.joinEntityInSurroundings(this.thePlayer);
				}
			}

			this.theWorld.difficultySetting = this.options.difficulty;
			if(!this.isGamePaused) {
				this.entityRenderer.updateRenderer();
			}

			if(!this.isGamePaused) {
				this.renderGlobal.updateClouds();
			}

			if(!this.isGamePaused) {
				this.theWorld.updateEntities();
			}

			if(!this.isGamePaused || this.isMultiplayerWorld()) {
				this.theWorld.tick();
			}

			if(!this.isGamePaused && this.theWorld != null) {
				this.theWorld.randomDisplayUpdates(MathHelper.floor_double(this.thePlayer.posX), MathHelper.floor_double(this.thePlayer.posY), MathHelper.floor_double(this.thePlayer.posZ));
			}

			if(!this.isGamePaused) {
				this.effectRenderer.updateEffects();
			}
		}

		this.systemTime = System.currentTimeMillis();
	}

	private void forceReload() {
		System.out.println("FORCING RELOAD!");
		this.sndManager = new SoundManager();
		this.sndManager.loadSoundSettings(this.options);
		this.downloadResourcesThread.reloadResources();
	}

	public boolean isMultiplayerWorld() {
		return this.theWorld != null && this.theWorld.multiplayerWorld;
	}

	public void startWorld(String var1, boolean snowy, boolean autumn) {
		this.changeWorld1((World)null);
		System.gc();
		World var2 = new World(new File(getMinecraftDir(), "saves"), var1);
		if(var2.isNewWorld) {
			var2.snowCovered = snowy;
			var2.isAutumn = autumn;
			this.changeWorld(var2, "Generating level");
		} else {
			this.changeWorld(var2, "Loading level");
		}

	}
	
	public void changeWorld1(World var1) {
		this.changeWorld(var1, "");
	}

	public void changeWorld(World var1, String var2) {
		this.sndManager.playStreaming((String)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		if(this.theWorld != null) {
			this.theWorld.saveWorldIndirectly(this.loadingScreen);
		}

		this.theWorld = var1;
		if(var1 != null) {
			this.playerController.onWorldChange(var1);
			var1.fontRenderer = this.fontRenderer;
			if(!this.isMultiplayerWorld()) {
				this.thePlayer = (EntityPlayerSP)var1.createDebugPlayer(EntityPlayerSP.class);
			} else if(this.thePlayer != null) {
				this.thePlayer.preparePlayerToSpawn();
				if(var1 != null) {
					var1.spawnEntityInWorld(this.thePlayer);
				}
			}

			if(!var1.multiplayerWorld) {
				this.preloadWorld(var2);
			}

			if(this.thePlayer == null) {
				this.thePlayer = (EntityPlayerSP)this.playerController.createPlayer(var1);
				this.thePlayer.preparePlayerToSpawn();
				this.playerController.flipPlayer(this.thePlayer);
			}

			this.thePlayer.movementInput = new MovementInputFromOptions(this.options);
			if(this.renderGlobal != null) {
				this.renderGlobal.changeWorld(var1);
			}

			if(this.effectRenderer != null) {
				this.effectRenderer.clearEffects(var1);
			}

			this.playerController.onRespawn(this.thePlayer);
			var1.spawnPlayerWithLoadedChunks(this.thePlayer);
			if(var1.isNewWorld) {
				var1.saveWorldIndirectly(this.loadingScreen);
			}
		} else {
			this.thePlayer = null;
		}

		System.gc();
		this.systemTime = 0L;
	}

	private void preloadWorld(String var1) {
		this.loadingScreen.resetProgressAndMessage(var1);
		this.loadingScreen.displayLoadingString("Building terrain");
		short var2 = 128;
		int var3 = 0;
		int var4 = var2 * 2 / 16 + 1;
		var4 *= var4;

		for(int var5 = -var2; var5 <= var2; var5 += 16) {
			int var6 = this.theWorld.spawnX;
			int var7 = this.theWorld.spawnZ;
			if(this.thePlayer != null) {
				var6 = (int)this.thePlayer.posX;
				var7 = (int)this.thePlayer.posZ;
			}

			for(int var8 = -var2; var8 <= var2; var8 += 16) {
				this.loadingScreen.setLoadingProgress(var3++ * 100 / var4);
				this.theWorld.getBlockId(var6 + var5, 64, var7 + var8);

				while(this.theWorld.updatingLighting(10)) {
				}
			}
		}

		this.loadingScreen.displayLoadingString("Simulating world for a bit");
		boolean var9 = true;
		this.theWorld.dropOldChunks();
	}

	public void installResource(String var1, File var2) {
		int var3 = var1.indexOf("/");
		String var4 = var1.substring(0, var3);
		var1 = var1.substring(var3 + 1);
		if(var4.equalsIgnoreCase("sound")) {
			this.sndManager.addSound(var1, var2);
		} else if(var4.equalsIgnoreCase("newsound")) {
			this.sndManager.addSound(var1, var2);
		} else if(var4.equalsIgnoreCase("streaming")) {
			this.sndManager.addStreaming(var1, var2);
		} else if(var4.equalsIgnoreCase("music")) {
			this.sndManager.addMusic(var1, var2);
		} else if(var4.equalsIgnoreCase("newmusic")) {
			this.sndManager.addMusic(var1, var2);
		}

	}

	public OpenGlCapsChecker getOpenGlCapsChecker() {
		return this.glCapabilities;
	}

	public String debugInfoRenders() {
		return this.renderGlobal.getDebugInfoRenders();
	}

	public String getEntityDebug() {
		return this.renderGlobal.getDebugInfoEntities();
	}

	public String debugInfoEntities() {
		return "P: " + this.effectRenderer.getStatistics() + ". T: " + this.theWorld.getDebugLoadedEntities();
	}

	public void respawn() {
		this.theWorld.setSpawnLocation();
		if(this.thePlayer != null) {
			this.theWorld.setEntityDead(this.thePlayer);
		}

		this.thePlayer = (EntityPlayerSP)this.playerController.createPlayer(this.theWorld);
		this.thePlayer.preparePlayerToSpawn();
		this.playerController.flipPlayer(this.thePlayer);
		this.theWorld.spawnPlayerWithLoadedChunks(this.thePlayer);
		this.thePlayer.movementInput = new MovementInputFromOptions(this.options);
		this.playerController.onRespawn(this.thePlayer);
		this.preloadWorld("Respawning");
	}

	public static void startMainThread(String var0, String var1) {
		startMainThread(var0, var1, (String)null);
	}

	public static void startMainThread(String var0, String var1, String var2) {
		boolean var3 = false;
		Frame var5 = new Frame("Minecraft");
		Canvas var6 = new Canvas();
		var5.setLayout(new BorderLayout());
		var5.add(var6, "Center");
		var6.setPreferredSize(new Dimension(854, 480));
		var5.pack();
		var5.setLocationRelativeTo((Component)null);
		MinecraftImpl var7 = new MinecraftImpl(var5, var6, (MinecraftApplet)null, 854, 480, var3, var5);
		Thread var8 = new Thread(var7, "Minecraft main thread");
		var8.setPriority(10);
		var7.appletMode = false;
		var7.minecraftUri = "www.minecraft.net";
		if(var0 != null && var1 != null) {
			var7.session = new Session(var0, var1);
		} else {
			var7.session = new Session("Player" + System.currentTimeMillis() % 1000L, "");
		}

		if(var2 != null) {
			String[] var9 = var2.split(":");
			var7.setServer(var9[0], Integer.parseInt(var9[1]));
		}

		var5.setVisible(true);
		var5.addWindowListener(new GameWindowListener(var7, var8));
		var8.start();
	}

	public static void main(String[] var0) {
		String var1 = "Player" + System.currentTimeMillis() % 1000L;
		if(var0.length > 0) {
			var1 = var0[0];
		}

		String var2 = "-";
		if(var0.length > 1) {
			var2 = var0[1];
		}

		startMainThread(var1, var2);
	}
}
