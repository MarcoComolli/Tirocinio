package originalFiles;

public class BaseConstructDue {

	// /// eheh
	/**
	 * eheh
	 */
	// **/* eheh
	int y = 0;
	int x = 0;

	public void methodIf2()

	{
		if (true) {

		}

		if (true) {
		} else {
		}
		if (false) {
		}
		while (y > 0) {
			int x = 0;
		}
		if (y == 0) {
			int x = 0;
		}

		x = y > 3 ? 5 : 2;
	}

	/*
	 * ciao
	 */
	public void methodTry() {
		int c = 3;
		try {
			while (c > 0) {
				c--;
			}

		} catch (Exception e) {

		} finally {
			c = 4;
			if (c != 4) {
				c = 0;
			} else {
				c = 3;
			}
		}
	}

	public void methodSynchronized() {
		int z = 2;
		if (z == 2 || false) {
			synchronized (this) {
				z++;

			}
		}
	}

}
