package sudoku;

import java.awt.Color;
import java.util.Stack;

public class Solver {
	Printer printer = new Printer();
	Slot[][] slot;
	Board board;
	boolean running = false;
	boolean change = false;
	int pos = 0;
	int num = 0;
	int limit = 0;
	int temp = 0;
	Position position;
	Stack<Position> stack = new Stack<>();

	Solver(Slot[][] slot, Board board) {
		this.slot = slot;
		this.board = board;
	}

	public void start() {
		if (!running) {
			System.out.println("start!");
			running = true;
			int loop = 0;
			while (true) {
				change = false;
				System.out.println("loop " + (loop + 1));
				basic();
				one();
				two();
				fill();
				printer.Print(slot, board);
				loop++;
				if (!change)
					break;
			}

			System.out.println("First progress compeleted");
			printer.Print(slot, board);
			printnominees();
			System.out.println("Start backtracking..");
			backtracking(0, 0);
		} else {
			System.out.println("Already started... Please wait...");
			return;
		}
	}

	public void basic() {

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (!slot[i][j].filled) {
					for (int k = 0; k < 9; k++) {
						if (j != k && slot[i][k].filled) {
							if (slot[i][j].deletenominee(slot[i][k].getnumber()))
								change = true;
						}
					}
				}
			}
		}

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (!slot[j][i].filled) {
					for (int k = 0; k < 9; k++) {
						if (j != k && slot[k][i].filled) {
							if (slot[j][i].deletenominee(slot[k][i].getnumber()))
								change = true;
						}
					}
				}
			}
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < 3; k++) {
					for (int l = 0; l < 3; l++) {
						if (!slot[3 * i + k][3 * j + l].filled) {
							for (int m = 0; m < 3; m++) {
								for (int n = 0; n < 3; n++) {
									if (k != m && l != n && slot[3 * i + m][3 * j + n].filled) {
										if (slot[3 * i + k][3 * j + l]
												.deletenominee(slot[3 * i + m][3 * j + n].getnumber()))
											change = true;
									}
								}
							}
						}
					}
				}
			}
		}

	}

	public void one() {
		int count[] = new int[9];
		for (int i = 0; i < 9; i++)
			count[i] = 0;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < 9; k++) {
					for (int m = 0; m < 3; m++) {
						for (int n = 0; n < 3; n++) {
							if (!slot[3 * i + m][3 * j + n].filled)
								if (slot[3 * i + m][3 * j + n].hasnominee(k + 1))
									count[k]++;
						}
					}
				}

				for (int k = 0; k < 9; k++) {
					if (count[k] == 1) {
						for (int m = 0; m < 3; m++) {
							for (int n = 0; n < 3; n++) {
								if (!slot[3 * i + m][3 * j + n].filled) {
									if (slot[3 * i + m][3 * j + n].hasnominee(k + 1)) {
										slot[3 * i + m][3 * j + n].fillnumber(k + 1);
										change = true;
									}
								}

							}
						}
					}
				}

				for (int k = 0; k < 9; k++)
					count[k] = 0;
			}
		}

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				for (int k = 0; k < 9; k++) {
					if (slot[i][j].hasnominee(k + 1)) {
						count[k]++;
					}
				}
			}

			for (int k = 0; k < 9; k++) {
				if (count[k] == 1) {
					for (int l = 0; l < 9; l++) {
						if (!slot[i][l].filled) {
							if (slot[i][l].hasnominee(k + 1)) {
								slot[i][l].fillnumber(k + 1);
								change = true;
							}
						}
					}
				}
			}

			for (int k = 0; k < 9; k++)
				count[k] = 0;

		}

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				for (int k = 0; k < 9; k++) {
					if (slot[j][i].hasnominee(k + 1)) {
						count[k]++;
					}
				}
			}
			for (int k = 0; k < 9; k++) {
				if (count[k] == 1) {
					for (int l = 0; l < 9; l++) {
						if (!slot[l][i].filled) {
							if (slot[l][i].hasnominee(k + 1)) {
								slot[l][i].fillnumber(k + 1);
								change = true;
							}
						}
					}
				}
			}

			for (int k = 0; k < 9; k++)
				count[k] = 0;

		}

	}

	public void two() {

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (!slot[i][j].filled && slot[i][j].countnominee == 2) {
					for (int k = j + 1; k < 9; k++) {
						if (j != k && !slot[i][k].filled && slot[i][k].countnominee == 2) {
							if (slot[i][j].equalsx(slot[i][k]))
								for (int l = 0; l < 9; l++) {
									slot[i][l].selectdelete(slot[i][j]);
								}
						}
					}
				}
			}
		}

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (!slot[j][i].filled && slot[j][i].countnominee == 2) {
					for (int k = j + 1; k < 9; k++) {
						if (j != k && !slot[k][i].filled && slot[k][i].countnominee == 2) {
							if (slot[j][i].equalsx(slot[k][i]))
								for (int l = 0; l < 9; l++)
									slot[l][i].selectdelete(slot[j][i]);

						}
					}
				}
			}
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < 3; k++) {
					for (int l = 0; l < 3; l++) {
						if (!slot[3 * i + k][3 * j + l].filled && slot[3 * i + k][3 * j + l].countnominee == 2) {
							for (int m = 0; m < 3; m++) {
								for (int n = 0; n < 3; n++) {
									if ((k != m || l != n) && !slot[3 * i + m][3 * j + n].filled
											&& slot[3 * i + m][3 * j + n].countnominee == 2) {
										if (slot[3 * i + m][3 * j + n].equalsx(slot[3 * i + k][3 * j + l])) {
											for (int o = 0; o < 3; o++) {
												for (int p = 0; p < 3; p++) {
													slot[3 * i + o][3 * j + p].selectdelete(slot[3 * i + m][3 * j + n]);
												}
											}

										}
									}
								}
							}
						}
					}
				}
			}
		}

	}

	public void backtracking(int x, int y) {
		printer.Print(slot, board);
		System.out.println(x + "," + y + " " + stack.size());

		int temp;
		temp = fill(x, y);

		if (temp == 0) {
			if (errorcheck()) {
				stack.push(new Position(y));
				backtracking(x + 1, 0);
			} else {
				unfill(x);
				backtracking(x, y + 1);
			}
		} else if (temp == 1) {

			unfill(x);
			backtracking(x - 1, stack.pop().num + 1);

		} else {
			System.out.println("Reach the end");
		}

	}

	public int fill(int x, int y) {

		int pos;
		int num;
		pos = x;
		num = y;

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (!slot[i][j].filled) {
					if (pos == 0) {
						if (slot[i][j].fillposnum(num)) {
							board.numbers[i][j].setBackground(Color.magenta);
							return 0;
						} else
							return 1;
					} else
						pos--;

				}
			}
		}

		return 2;

	}

	public void unfill(int x) {

		int pos;
		pos = x;

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (!slot[i][j].filled) {
					if (pos == 0) {
						slot[i][j].unfill();
						board.numbers[i][j].setBackground(Color.cyan);
						return;
					} else {
						pos--;
					}
				}
			}
		}

	}

	public boolean full() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (slot[i][j].number == 0)
					return false;
			}
		}

		return true;
	}

	public void fill() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (!slot[i][j].filled)
					if (slot[i][j].check())
						change = true;
			}
		}
	}

	public void printnominees() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (!slot[i][j].filled) {
					System.out.print(i + ", " + j + " : ");
					slot[i][j].printnominee();
				}
			}
		}
	}

	public boolean errorcheck() {

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				for (int k = j + 1; k < 9; k++) {
					if (slot[i][j].getnumber() != 0 && slot[i][k].getnumber() != 0)
						if (slot[i][j].getnumber() == slot[i][k].getnumber())
							return false;
				}
			}
		}

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				for (int k = j + 1; k < 9; k++) {
					if (slot[j][i].getnumber() != 0 && slot[k][i].getnumber() != 0)
						if (slot[j][i].getnumber() == slot[k][i].getnumber())
							return false;
				}
			}
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < 3; k++) {
					for (int l = 0; l < 3; l++) {
						for (int m = 0; m < 3; m++) {
							for (int n = 0; n < 3; n++) {
								if (slot[(3 * i) + k][(3 * j) + l].getnumber() != 0
										&& slot[(3 * i) + m][(3 * j) + n].getnumber() != 0)
									if (k != m || l != n)
										if (slot[(3 * i) + k][(3 * j) + l].getnumber() == slot[(3 * i) + m][(3 * j) + n]
												.getnumber())
											return false;
							}
						}
					}
				}
			}
		}

		return true;
	}

}
