package sudoku;

//1->can be 0->can't be
public class Slot {
	int number;
	int[] nominee = new int[9];
	boolean filled = false;
	int countnominee = 9;

	Slot(int x) {
		number = x;
		for (int i = 0; i < 9; i++)
			nominee[i] = 1;

		if (x != 0) {
			filled = true;
		}

	}

	public int getnumber() {
		return number;
	}

	public boolean deletenominee(int x) {
		if (nominee[x - 1] == 0)
			return false;
		else {
			nominee[x - 1] = 0;
			return true;
		}
	}

	// check only !filled
	public boolean check() {
		int count = 0;
		int temp = 0;
		for (int i = 0; i < 9; i++) {
			if (nominee[i] == 1) {
				temp = i + 1;
				count++;
			}
		}
		countnominee = count;

		if (count == 1) {
			fillnumber(temp);
			return true;
		} else
			return false;

	}

	public void fillnumber(int x) {
		number = x;
		filled = true;
	}

	public void rfillnumber(int x) {
		number = x;
		// filled=true;
	}

	public void unfill() {
		number = 0;
	}

	public void printnominee() {

		for (int i = 0; i < 9; i++) {
			if (nominee[i] == 1)
				System.out.print((i + 1) + " ");
		}
		System.out.println();

	}

	public boolean hasnominee(int x) {
		if (nominee[x - 1] == 1)
			return true;
		else
			return false;
	}

	public boolean equalsx(Slot x) {
		int count = 0;
		for (int i = 0; i < 9; i++)
			if (this.nominee[i] == x.nominee[i])
				count++;

		if (!this.filled && !x.filled) {
			if (count == 9)
				return true;
			else
				return false;
		} else
			return false;
	}

	public void selectdelete(Slot x) {
		if (equalsx(x))
			return;
		else {
			for (int i = 0; i < 9; i++) {
				if (x.hasnominee(i + 1)) {
					if (hasnominee(i + 1))
						deletenominee(i + 1);
				}
			}
		}
	}

	public boolean fillposnum(int x) {
		for (int i = 0; i < 9; i++) {
			if (nominee[i] == 1) {
				if (x == 0) {
					rfillnumber(i + 1);
					return true;
				} else
					x--;

			}
		}
		return false;
	}
}
