package gameLogic;

import java.util.Random;

public class Die {

	public static final int MAX_FACE = 6;

	public static final int[] FACES = { 1, 2, 3, 4, 5, 6 };

	private int face;

	public Die() {
		face = 1;
	}

	public void roll() {
		face = FACES[(new Random().nextInt(5))];
	}

	public int getFace() {
		return face;
	}
}