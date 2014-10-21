/*
 * Game class
 * Logic isolation
 */
package sudoku.model.gameLogic;

import sudoku.model.gameData.GameBase;


//print numbers on the interface
//print touch location on the screen
//check if selected location is empty
//calculate the rest options of number(1-9) for a selected location
//delete entered number
//provide answers of the games
//generate random numbers for the game with selected level
public class GameLogic {

	// initialize numbers
	private String str = null;
	private int sudokuOriginal[] = new int[9 * 9];
	private int sudoku[] = new int[9 * 9];

	private GameBase gameBase;
	
	// for storing the used tiles, empty[] means the existing unusable data
	private int used[][][] = new int[9][9][];

	public GameLogic(int level) {
		gameBase = new GameBase();
		str = gameBase.returnStr(level);
		sudoku = fromPuzzleString(str);
		sudokuOriginal = fromPuzzleString(str);
		checkAnyEmptyTile();
		calculateAllUsedTiles();
	}

	// check if the tile is stored with 0
	public boolean checkTile0(int x, int y) {
		int a = getTile(x, y, "original");
		boolean zero = true;
		if (a == 0) {
			zero = true;
		} else {
			zero = false;
		}
		return zero;
	}

	/*
	 * return the number in a certain tile according to XY coordinate
	 * two types, original (unchangeable) and updated(changeable)
	 */
	private int getTile(int x, int y, String type) {
		int tile = 0;
		if (type == "original") {
			tile = sudokuOriginal[y * 9 + x];
		} else if (type == "updated") {
			tile = sudoku[y * 9 + x];
		}
		return tile;
	}

	public String getTileString(int x, int y, String type) {
		int v = getTile(x, y, type);
		if (v == 0) {
			return "";
		} else {
			return String.valueOf(v);
		}
	}

	// from a string, change to a integer from initialized string data
	protected int[] fromPuzzleString(String src) {
		int[] sudo = new int[src.length()];
		for (int i = 0; i < sudo.length; i++) {
			sudo[i] = src.charAt(i) - '0';
		}
		return sudo;
	}

	//to calculate all unusable data in all tiles
	public void calculateAllUsedTiles() {
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				used[x][y] = calculateUsedTiles(x, y);
			}
		}
	}

	// get usable data from certain tile with xy coordinate
	public int[] getUsedTilesByCoor(int x, int y) {
		return used[x][y];
	}

	// calculate unusable data in certain cell 
	public int[] calculateUsedTiles(int x, int y) {
		int[] c = new int[9];

		for (int i = 0; i < 9; i++) {
			if (i == y) {
				continue;
			}
			int t = getTile(x, i, "updated");
			if (t != 0) {
				c[t - 1] = t;
			}
		}

		for (int i = 0; i < 9; i++) {
			if (i == x) {
				continue;
			}
			int t = getTile(i, y, "updated");
			if (t != 0) {
				c[t - 1] = t;
			}
		}

		int startx = (x / 3) * 3;
		int starty = (y / 3) * 3;
		for (int i = startx; i < startx + 3; i++) {
			for (int j = starty; j < starty + 3; j++) {
				if (i == x && j == y) {
					continue;
				}
				int t = getTile(i, j, "updated");
				if (t != 0) {
					c[t - 1] = t;
				}
			}
		}
		// compress, remove 0
		int nused = 0;
		for (int t : c) {
			if (t != 0) {
				nused++;
			}
		}
		int c1[] = new int[nused];
		nused = 0;
		for (int t : c) {
			if (t != 0) {
				c1[nused++] = t;
			}
		}
		return c1;
	}

	/*
	 * check if selected tile is valid
	 */
	protected boolean setTileIfValid(int x, int y, int value) {
		int tiles[] = getUsedTiles(x, y);
		if (value != 0) {
			for (int tile : tiles) {
				if (tile == value)
					return false;
			}
		}
		setTile(x, y, value);
		calculateAllUsedTiles();
		return true;
	}

	protected int[] getUsedTiles(int x, int y) {
		return used[x][y];
	}

	private void setTile(int x, int y, int value) {
		sudoku[y * 9 + x] = value;

	}

	/*
	 * check if there is any empty tile to be entered number
	 */
	public boolean checkAnyEmptyTile() {
		boolean anyZero = false;
		for (int x = 0; x < 81; x++) {
			if (sudoku[x] == 0) {
				anyZero = true;
			}
		}
		return anyZero;
	}

}
