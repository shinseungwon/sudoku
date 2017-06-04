package sudoku;

public class Printer {
	public void Print(Slot[][] slot, Board board) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (slot[i][j].getnumber() != 0)
					board.getnumbers(i, j).setText(slot[i][j].getnumber() + "");
				else
					board.getnumbers(i, j).setText("");
			}
		}
	}
}
