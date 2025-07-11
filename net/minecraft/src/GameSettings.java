package net.minecraft.src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

enum OptionId {
	MUSICVOLUME,
	SOUNDVOLUME,
	INVERTMOUSE,
	MOUSESENSITIVITY,
	RENDERDISTANCE,
	VIEWBOBBING,
	ANAGLYPH,
	LIMITFRAMERATE,
	DIFFICULTY,
	FANCYGRAPHICS,
	MUSICDELAY,
	FOV,
	DARKMODE,
	CLOUDSTYLE,
}

public class GameSettings {
	private static final String[] RENDER_DISTANCES = new String[]{"FAR", "NORMAL", "SHORT", "TINY"};
	private static final String[] DIFFICULTY_LEVELS = new String[]{"Peaceful", "Easy", "Normal", "Hard"};
	public float musicVolume = 1.0F;
	public float soundVolume = 1.0F;
	public float mouseSensitivity = 0.5F;
	public float fov = 0.5F;					// Sliders are between 0.0-1.0, and the variables that modify them should be too
	public float musicDelay = 0.5F;	
	public boolean invertMouse = false;
	public int renderDistance = 0;
	public boolean viewBobbing = true;
	public boolean anaglyph = false;
	public boolean limitFramerate = false;
	public boolean fancyGraphics = true;
	public boolean darkMode = false;
	public String ipText = "";
	public KeyBinding keyBindForward = new KeyBinding("Forward", 17);
	public KeyBinding keyBindLeft = new KeyBinding("Left", 30);
	public KeyBinding keyBindBack = new KeyBinding("Back", 31);
	public KeyBinding keyBindRight = new KeyBinding("Right", 32);
	public KeyBinding keyBindJump = new KeyBinding("Jump", 57);
	public KeyBinding keyBindInventory = new KeyBinding("Inventory", 23);
	public KeyBinding keyBindDrop = new KeyBinding("Drop", 16);
	public KeyBinding keyBindChat = new KeyBinding("Chat", 20);
	public KeyBinding keyBindToggleFog = new KeyBinding("Toggle fog", 33);
	public KeyBinding keyBindSneak = new KeyBinding("Sneak", 42);
	public KeyBinding[] keyBindings = new KeyBinding[]{this.keyBindForward, this.keyBindLeft, this.keyBindBack, this.keyBindRight, this.keyBindJump, this.keyBindSneak, this.keyBindDrop, this.keyBindInventory, this.keyBindChat, this.keyBindToggleFog};
	protected Minecraft mc;
	private File optionsFile;
	public int numberOfOptions = 14;
	public int difficulty = 2;
	public boolean thirdPersonView = false;
	public boolean cloudSettings = false;

	public GameSettings(Minecraft mc, File optionsFile) {
		this.mc = mc;
		this.optionsFile = new File(optionsFile, "options.txt");
		this.loadOptions();
	}

	public GameSettings() {
	}

	public String getKeyBindingDescription(int keybindId) {
		return this.keyBindings[keybindId].keyDescription + ": " + Keyboard.getKeyName(this.keyBindings[keybindId].keyCode);
	}

	public void setKeyBinding(int keybindId, int keyCode) {
		this.keyBindings[keybindId].keyCode = keyCode;
		this.saveOptions();
	}

	private OptionId intToOptionId(int optionId) {
		switch (optionId) {
			case 0:
				return OptionId.MUSICVOLUME;
			case 1:
				return OptionId.SOUNDVOLUME;
			case 2:
				return OptionId.INVERTMOUSE;
			case 3:
				return OptionId.MOUSESENSITIVITY;
			case 4:
				return OptionId.RENDERDISTANCE;
			case 5:
				return OptionId.VIEWBOBBING;
			case 6:
				return OptionId.ANAGLYPH;
			case 7:
				return OptionId.LIMITFRAMERATE;
			case 8:
				return OptionId.DIFFICULTY;
			case 9:
				return OptionId.FANCYGRAPHICS;
			case 10:
				return OptionId.MUSICDELAY;
			case 11:
				return OptionId.FOV;
			case 12:
				return OptionId.DARKMODE;
			case 13:
				return OptionId.CLOUDSTYLE;
		}
		return OptionId.MUSICVOLUME; // Has to be here for compilation
																 // TODO: error here
	}

	public void setOptionFloatValue(int optionId_int, float value) {
		OptionId optionId = this.intToOptionId(optionId_int);
		switch (optionId) {
			case MUSICVOLUME:
				this.musicVolume = value;
				this.mc.sndManager.onSoundOptionsChanged();
				break;
			case SOUNDVOLUME:
				this.soundVolume = value;
				this.mc.sndManager.onSoundOptionsChanged();
				break;
			case MOUSESENSITIVITY:
				this.mouseSensitivity = value;
				break;
			case MUSICDELAY:
				// this.musicDelay = (int) (value * 600 + 1);
				this.musicDelay = value;
				break;
			case FOV:
				// this.fov = value * 100 + 1;
				this.fov = value;
		}
		this.saveOptions();
	}

	public void setOptionValue(int int_id, int value) {
		OptionId id = this.intToOptionId(int_id);
		switch (id) {
			case INVERTMOUSE:
				this.invertMouse = !this.invertMouse;
				break;
			case RENDERDISTANCE:
				this.renderDistance = this.renderDistance + value & 3;
				break;
			case VIEWBOBBING:
				this.viewBobbing = !this.viewBobbing;
				break;
			case ANAGLYPH:
				this.anaglyph = !this.anaglyph;
				this.mc.renderEngine.refreshTextures();
				break;
			case LIMITFRAMERATE:
				this.limitFramerate = !this.limitFramerate;
				break;
			case DIFFICULTY:
				this.difficulty = this.difficulty + value & 3;
				break;
			case FANCYGRAPHICS:
				this.fancyGraphics = !this.fancyGraphics;
				this.mc.renderGlobal.loadRenderers();
				break;
			/*case MUSICDELAY:
				this.musicDelay = (int)value;
				break;*/
			case DARKMODE:
				this.darkMode = !this.darkMode;
				break;
			case CLOUDSTYLE:
				this.cloudSettings = !this.cloudSettings;
				break;
		}
		this.saveOptions();
	}

	public int isSlider(int optionId_int) {
		OptionId optionId = this.intToOptionId(optionId_int);
		switch (optionId) {
			case MUSICVOLUME:
			case SOUNDVOLUME:
			case MOUSESENSITIVITY:
			case MUSICDELAY:
			case FOV:
				return 1;
			default:
				return 0;
		}
	}
	
	public int sliderLimit(int optionId_int) {
		OptionId optionId = this.intToOptionId(optionId_int);
		switch (optionId) {
			case MUSICVOLUME:
			case SOUNDVOLUME:
			case MOUSESENSITIVITY:

			case MUSICDELAY:
			case FOV:
				return 1;
			/*case MUSICDELAY:
				return 601;
			case FOV:
				// return 101;
				return 110;*/
			default:
				return 0;
		}
	}

	public float getOptionFloatValue(int optionId_int) {
		OptionId optionId = this.intToOptionId(optionId_int);
		switch (optionId) {
			case MUSICVOLUME:
				return this.musicVolume;
			case SOUNDVOLUME:
				return this.soundVolume;
			case MOUSESENSITIVITY:
				return this.mouseSensitivity;
			case MUSICDELAY:
				return this.musicDelay;
			case FOV:
				return this.fov;
		}
		return 0; // TODO: error here
	}

	public String getOptionDisplayString(int optionId_int) {
		OptionId optionId = this.intToOptionId(optionId_int);
		switch (optionId) {
			case MUSICVOLUME:
				return "Music: " + (this.musicVolume > 0.0F ? (int)(this.musicVolume * 100.0F) + "%" : "OFF");
			case SOUNDVOLUME:
				return "Sound: " + (this.soundVolume > 0.0F ? (int)(this.soundVolume * 100.0F) + "%" : "OFF");
			case INVERTMOUSE:
				return "Invert mouse: " + (this.invertMouse ? "ON" : "OFF");
			case MOUSESENSITIVITY:
				return (this.mouseSensitivity == 0.0F ? "Sensitivity: *yawn*" : (this.mouseSensitivity == 1.0F ? "Sensitivity: HYPERSPEED!!!" : "Sensitivity: " + (int)(this.mouseSensitivity * 200.0F) + "%"));
			case RENDERDISTANCE:
				return "Render distance: " + RENDER_DISTANCES[this.renderDistance];
			case VIEWBOBBING:
				return "View bobbing: " + (this.viewBobbing ? "ON" : "OFF");
			case ANAGLYPH:
				return "3d anaglyph: " + (this.anaglyph ? "ON" : "OFF");
			case LIMITFRAMERATE:
				return "Limit framerate: " + (this.limitFramerate ? "ON" : "OFF");
			case DIFFICULTY:
				return "Difficulty: " + DIFFICULTY_LEVELS[this.difficulty];
			case FANCYGRAPHICS:
				return "Graphics: " + (this.fancyGraphics ? "FANCY" : "FAST");
			case MUSICDELAY:
				int actualMusicDelay = (int)((this.musicDelay * 600 + 1));
        		return "Music delay: " + actualMusicDelay + " seconds";
			case FOV:
				int actualFoV = (int)((this.fov - 0.5F) * 80F + 70F);
				return "FOV: " + actualFoV;
			case DARKMODE:
				return "Dark mode: " + (this.darkMode ? "ON" : "OFF");
			case CLOUDSTYLE:
				return "Cloud style: " + (this.cloudSettings ? "Classic" : "Modern");
		}
		return "NONEXISTANT SETTING"; // TODO: error here
	}

	public void loadOptions() {
		try {
			if(!this.optionsFile.exists()) {
				return;
			}

			BufferedReader reader = new BufferedReader(new FileReader(this.optionsFile));
			String currentLine = "";

			while(true) {
				currentLine = reader.readLine();
				if(currentLine == null) {
					reader.close();
					break;
				}

				String[] settingKeyValue = currentLine.split(":");
				switch (settingKeyValue[0]) {
					case "music":
						this.musicVolume = this.parseFloat(settingKeyValue[1]);
						break;
					case "sound":
						this.soundVolume = this.parseFloat(settingKeyValue[1]);
						break;
					case "mouseSensitivity":
						this.mouseSensitivity = this.parseFloat(settingKeyValue[1]);
						break;
					case "invertYMouse":
						this.invertMouse = settingKeyValue[1].equals("true");
						break;
					case "viewDistance":
						this.renderDistance = Integer.parseInt(settingKeyValue[1]);
						break;
					case "bobView":
						this.viewBobbing = settingKeyValue[1].equals("true");
						break;
					case "anaglyph3d":
						this.anaglyph = settingKeyValue[1].equals("true");
						break;
					case "limitFramerate":
						this.limitFramerate = settingKeyValue[1].equals("true");
						break;
					case "difficulty":
						this.difficulty = Integer.parseInt(settingKeyValue[1]);
						break;
					case "fancyGraphics":
						this.fancyGraphics = settingKeyValue[1].equals("true");
						break;
					case "musicDelay":
						this.musicDelay = this.parseFloat(settingKeyValue[1]);
						this.musicDelay = this.musicDelay < 0.0F || this.musicDelay > 1.0F ? 0.5F : this.musicDelay;	// Overflow glitch
						break;
					case "FOV":
						this.fov = this.parseFloat(settingKeyValue[1]);
						this.fov = this.fov < 0.0F || this.fov > 1.0F ? 0.5F : this.fov;	// Overflow glitch
						break;
					case "darkMode":
						this.darkMode = settingKeyValue[1].equals("true");
						break;
					case "ipText":
						if (settingKeyValue.length == 2) {
							this.ipText = settingKeyValue[1];
						}
						else {
							this.ipText = "";
						}
						break;
					case "cloudStyle":
						this.cloudSettings = settingKeyValue[1].equals("true");
						break;
				}

				for(int keyIterator = 0; keyIterator < this.keyBindings.length; ++keyIterator) {
					if(settingKeyValue[0].equals("key_" + this.keyBindings[keyIterator].keyDescription)) {
						this.keyBindings[keyIterator].keyCode = Integer.parseInt(settingKeyValue[1]);
					}
				}
			}
		} catch (Exception error) {
			System.out.println("Failed to load options");
			error.printStackTrace();
		}

	}

	private float parseFloat(String var1) {
		return var1.equals("true") ? 1.0F : (var1.equals("false") ? 0.0F : Float.parseFloat(var1));
	}

	public void saveOptions() {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(this.optionsFile));
			writer.println("music:" + this.musicVolume);
			writer.println("sound:" + this.soundVolume);
			writer.println("invertYMouse:" + this.invertMouse);
			writer.println("mouseSensitivity:" + this.mouseSensitivity);
			writer.println("viewDistance:" + this.renderDistance);
			writer.println("bobView:" + this.viewBobbing);
			writer.println("anaglyph3d:" + this.anaglyph);
			writer.println("limitFramerate:" + this.limitFramerate);
			writer.println("difficulty:" + this.difficulty);
			writer.println("fancyGraphics:" + this.fancyGraphics);
			writer.println("cloudStyle:" + this.cloudSettings);

			for(int keyIterator = 0; keyIterator < this.keyBindings.length; ++keyIterator) {
				writer.println("key_" + this.keyBindings[keyIterator].keyDescription + ":" + this.keyBindings[keyIterator].keyCode);
			}

			writer.println("musicDelay:" + this.musicDelay);
			writer.println("FOV:" + this.fov);
			writer.println("darkMode:" + this.darkMode);
			writer.println("ipText:" + this.ipText);
			
			writer.close();
		} catch (Exception error) {
			System.out.println("Failed to save options");
			error.printStackTrace();
		}

	}
}
