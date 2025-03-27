package net.minecraft.src;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class CompressedStreamTools {
	public static NBTTagCompound readCompressed(InputStream var0) throws IOException {
		DataInputStream var1 = new DataInputStream(new GZIPInputStream(var0));

		NBTTagCompound var2;
		try {
			var2 = read(var1);
		} finally {
			var1.close();
		}

		return var2;
	}

	public static void writeCompressed(NBTTagCompound var0, OutputStream var1) throws IOException {
		DataOutputStream var2 = new DataOutputStream(new GZIPOutputStream(var1));

		try {
			write(var0, var2);
		} finally {
			var2.close();
		}

	}

	public static NBTTagCompound decompress(byte[] var0) throws IOException {
		DataInputStream var1 = new DataInputStream(new GZIPInputStream(new ByteArrayInputStream(var0)));

		NBTTagCompound var2;
		try {
			var2 = read(var1);
		} finally {
			var1.close();
		}

		return var2;
	}

	public static byte[] compress(NBTTagCompound var0) throws IOException {
		ByteArrayOutputStream var1 = new ByteArrayOutputStream();
		DataOutputStream var2 = new DataOutputStream(new GZIPOutputStream(var1));

		try {
			write(var0, var2);
		} finally {
			var2.close();
		}

		return var1.toByteArray();
	}

	public static NBTTagCompound read(DataInput var0) throws IOException {
		NBTBase var1 = NBTBase.readNamedTag(var0);
		if(var1 instanceof NBTTagCompound) {
			return (NBTTagCompound)var1;
		} else {
			throw new IOException("Root tag must be a named compound tag");
		}
	}

	public static void write(NBTTagCompound var0, DataOutput var1) throws IOException {
		NBTBase.writeNamedTag(var0, var1);
	}
}
