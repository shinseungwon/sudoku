package sudoku;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Board extends Frame {
	private static final long serialVersionUID = 1L;
	Panel sudokuboard = new Panel();
	Panel buttons = new Panel();
	Button start = new Button("Start!");
	Label[][] numbers = new Label[9][9];
	Slot[][] slot;

	public Board(String title, Slot[][] slot) {
		super(title);
		this.slot = slot;
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				System.exit(0);
			}
		});
		init();
		super.setSize(400, 400);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frm = super.getSize();
		int xpos = (int) (screen.getWidth() / 2 - frm.getWidth() / 2);
		int ypos = (int) (screen.getHeight() / 2 - frm.getWidth() / 2);
		super.setLocation(xpos, ypos);
		super.setVisible(true);
	}

	public void init() {
		// make a interface in here

		sudokuboard.setLayout(new GridLayout(9, 9, 5, 5));
		buttons.setLayout(new GridLayout(1, 0));
		buttons.add(start);
		start.addActionListener(new Action(slot, this));
		this.setLayout(new BorderLayout());
		this.add("Center", sudokuboard);
		this.add("South", buttons);

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				numbers[i][j] = new Label("");
				numbers[i][j].setAlignment(1);
				sudokuboard.add(numbers[i][j]);
				numbers[i][j].setBackground(Color.YELLOW);
			}
		}

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				numbers[i][j].setBackground(Color.GREEN);

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				numbers[6 + i][j].setBackground(Color.GREEN);

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				numbers[3 + i][3 + j].setBackground(Color.GREEN);

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				numbers[i][6 + j].setBackground(Color.GREEN);

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				numbers[6 + i][6 + j].setBackground(Color.GREEN);

	}

	public Label getnumbers(int x, int y) {
		return numbers[x][y];
	}

	class Action implements ActionListener {
		Solver solver;
		Slot[][] slot;

		Action(Slot[][] slot, Board board) {
			this.slot = slot;
			solver = new Solver(slot, board);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub

			solver.start();
		}

	}

}
