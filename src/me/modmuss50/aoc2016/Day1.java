package me.modmuss50.aoc2016;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by modmuss50 on 01/12/16.
 */
public class Day1 {

	// Compass so I dont get confused
	//      N
	//   W     E
	//      S

	static final int size = 450;
	static final int center = size / 2;
	static Pos[][] posArray = new Pos[size][size];
	static Pos starting = new Pos(center, center);
	static Pos currentPos;
	static Facing facing = Facing.NORTH;
	static List<Pos> visited = new LinkedList<>();
	static Pos firstVisited;

	public static void main(String[] args) throws IOException {
		posArray[center][center] = starting;
		currentPos = starting;
		for (String command : INPUT.split(", ")) {
			String direction = command.startsWith("R") ? "R" : "L";
			int distace = Integer.parseInt(command.replace("R", "").replace("L", ""));
			Facing newFace = rotate(direction, facing);
			int xMovement = getXMovement(distace, newFace);
			int zMomement = getZMovement(distace, newFace);

			// WTF IS THIS SHIT STARTING FROM HERE:
			boolean xNegitive = xMovement < 0;
			boolean zNegitive = zMomement < 0;
			int xCount = xMovement;
			int zCount = zMomement;
			if (xNegitive) {
				xCount = (-xMovement);
			}
			if (zNegitive) {
				zCount = (-zMomement);
			}
			boolean skipx = false;
			if (xCount == 0) {
				xCount = 1;
				skipx = true;
			}
			boolean skipz = false;
			if (zCount == 0) {
				zCount = 1;
				skipz = true;
			}
			Pos newPos = null;
			for (int x = 0; x < xCount; x++) {
				for (int z = 0; z < zCount; z++) {
					int movementx = 1;
					int movementz = 1;
					if (xNegitive) {
						movementx = -1;
					}
					if (xMovement == 0) {
						movementx = 0;
					}
					if (zNegitive) {
						movementz = -1;
					}
					if (zMomement == 0) {
						movementz = 0;
					}
					if (skipx) {
						movementx = 0;
					}
					if (skipz) {
						movementz = 0;
					}
					//TO HERE, IT WORKS NOT SURE WHY, ITS UGLY AS FUCK But it works.


					int xPos = currentPos.x + movementx;
					int zPos = currentPos.z + movementz;
					newPos = new Pos(xPos, zPos);
					if (firstVisited == null) {
						if (posArray[xPos][zPos] != null && posArray[xPos][zPos].x == xPos && posArray[xPos][zPos].z == zPos) {
							firstVisited = newPos;
							System.out.println(newPos);
						}
					}
					visited.add(newPos);
					posArray[xPos][zPos] = newPos;
					currentPos = newPos;
					facing = newFace;
				}
			}

		}
		genMap();
		int traveled = Math.abs(currentPos.x - center) + Math.abs(currentPos.z - center);
		System.out.println("Part 1 " + traveled);
		traveled = Math.abs(firstVisited.x - center) + Math.abs(firstVisited.z - center);
		System.out.println("Part 2 " + traveled);

	}

	static void genMap() throws IOException {
		int[] pixels = new int[size * size];
		for (int j = 0; j < size; j++) {
			for (int i = 0; i < size; i++) {
				Pos pos = posArray[j][i];
				if (pos != null) {
					pixels[j * size + i] = Color.RED.getRGB();
				} else {
					pixels[j * size + i] = Color.GRAY.getRGB();
				}
			}
		}

		BufferedImage pixelImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		pixelImage.setRGB(0, 0, size, size, pixels, 0, size);
		File imageFile = new File("Day1Map.png");
		ImageIO.write(pixelImage, "png", imageFile);
	}

	static int getXMovement(int distance, Facing dir) {
		if (dir == Facing.NORTH) {
			return distance;
		}
		if (dir == Facing.SOUTH) {
			return -distance;
		}
		return 0;
	}

	static int getZMovement(int distance, Facing dir) {
		if (dir == Facing.EAST) {
			return distance;
		}
		if (dir == Facing.WEST) {
			return -distance;
		}
		return 0;
	}

	static Facing rotate(String dir, Facing old) {
		if (dir.equalsIgnoreCase("R")) {
			switch (old) {
				case NORTH:
					return Facing.EAST;
				case EAST:
					return Facing.SOUTH;
				case SOUTH:
					return Facing.WEST;
				case WEST:
					return Facing.NORTH;
			}
		} else if (dir.equalsIgnoreCase("L")) {
			switch (old) {
				case NORTH:
					return Facing.WEST;
				case EAST:
					return Facing.NORTH;
				case SOUTH:
					return Facing.EAST;
				case WEST:
					return Facing.SOUTH;
			}
		}
		System.out.println("Error rotating around: " + dir);
		return null; //Help me
	}

	private static class Pos {
		public int x;
		public int z;

		public Pos(int x, int z) {
			this.x = x;
			this.z = z;
		}

		@Override
		public String toString() {
			return "Pos{" +
				"x=" + x +
				", z=" + z +
				'}';
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;

			Pos pos = (Pos) o;

			if (x != pos.x)
				return false;
			return z == pos.z;
		}

		@Override
		public int hashCode() {
			int result = x;
			result = 31 * result + z;
			return result;
		}
	}

	private enum Facing {
		NORTH,
		SOUTH,
		EAST,
		WEST
	}

	public static final String INPUT = "L2, L5, L5, R5, L2, L4, R1, R1, L4, R2, R1, L1, L4, R1, L4, L4, R5, R3, R1, L1, R1, L5, L1, R5, L4, R2, L5, L3, L3, R3, L3, R4, R4, L2, L5, R1, R2, L2, L1, R3, R4, L193, R3, L5, R45, L1, R4, R79, L5, L5, R5, R1, L4, R3, R3, L4, R185, L5, L3, L1, R5, L2, R1, R3, R2, L3, L4, L2, R2, L3, L2, L2, L3, L5, R3, R4, L5, R1, R2, L2, R4, R3, L4, L3, L1, R3, R2, R1, R1, L3, R4, L5, R2, R1, R3, L3, L2, L2, R2, R1, R2, R3, L3, L3, R4, L4, R4, R4, R4, L3, L1, L2, R5, R2, R2, R2, L4, L3, L4, R4, L5, L4, R2, L4, L4, R4, R1, R5, L2, L4, L5, L3, L2, L4, L4, R3, L3, L4, R1, L2, R3, L2, R1, R2, R5, L4, L2, L1, L3, R2, R3, L2, L1, L5, L2, L1, R4";

}
