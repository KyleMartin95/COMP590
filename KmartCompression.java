package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

public class KmartCompressor2 {

	public static void main(String[] args) throws IOException {
		String base = "pinwheel";
		String filename="C:\\Users\\Kyle\\Dropbox\\Spring 18\\COMP 590\\" + base + "\\" + base + ".720p.yuv";
		File file = new File(filename);
		int width = 800;
		int height = 450;
		int num_frames = 150;

		InputStream training_values = new FileInputStream(file);
		int[][] current_frame = new int[width][height];


		File compressed_out_file = new File("C:\\Users\\Kyle\\Dropbox\\Spring 18\\COMP 590\\" + base + "\\" + base + "-720compressed.dat");
		OutputStream compressed_out_stream = new FileOutputStream(compressed_out_file);
		DeflaterOutputStream dos = new DeflaterOutputStream(compressed_out_stream);

		for (int f=0; f < num_frames; f++) {
			System.out.println("Compressing frame " + f);
			current_frame = readFrame(training_values, width, height);
			for(int y = 0; y < height; y++) {
				for(int x = 0; x < width; x++) {
					dos.write(current_frame[x][y]);
				}
			}
		}

		File decompressed_out_file = new File("C:\\Users\\Kyle\\Dropbox\\Spring 18\\COMP 590\\" + base + "\\" + base + "-720decompressed.dat");
	    InputStream compressed_in_stream = new FileInputStream(compressed_out_file);
		OutputStream decompressed_out_stream = new FileOutputStream(decompressed_out_file);
		InflaterInputStream iis = new InflaterInputStream(compressed_in_stream);

		for (int f=0; f < num_frames; f++) {
			System.out.println("Decompressing frame " + f);
			for(int y = 0; y < height; y++) {
				for(int x = 0; x < width; x++) {
					int decompressedVal = iis.read();
					decompressed_out_stream.write(decompressedVal);
				}
			}
		}


	}

	private static int[][] readFrame(InputStream src, int width, int height)
			throws IOException {
		int[][] frame_data = new int[width][height];
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				frame_data[x][y] = src.read();
			}
		}
		return frame_data;
	}

}
