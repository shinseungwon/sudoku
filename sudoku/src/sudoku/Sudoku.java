package sudoku;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Sudoku {

	public static void main(String[] args) {
		Slot[][] numbers = new Slot[9][9];
		Board board = new Board("Sudoku", numbers);
		int[][] getnumber = new int[9][9];
		int i = 0;
		int j = 0;
		File file = new File("sudoku.txt");
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(file);

			int content;
			while ((content = fis.read()) != -1) {
				// convert to char and display it
				getnumber[i][j] = content - '0';
				j++;

				if (j == 9) {
					i++;
					j = 0;
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				numbers[x][y] = new Slot(getnumber[x][y]);
				if (getnumber[x][y] != 0) {
					board.getnumbers(x, y).setText(getnumber[x][y] + "");
					board.getnumbers(x, y).setFont(board.getnumbers(x, y).getFont().deriveFont(Font.BOLD, 14f));
				}
			}
		}
	}

}
