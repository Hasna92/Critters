package assignment5;

//import project5.Critter.CritterShape;

public class CloverphobicCritter extends Critter {

	@Override
	public String toString() { return "S"; }

	public CloverphobicCritter() {
		Params.LOOK_ENERGY_COST = 0;
		Params.WALK_ENERGY_COST = 0;
		Params.RUN_ENERGY_COST = 0;
		Params.REFRESH_CLOVER_COUNT = 0;
	}

	public boolean fight(String not_used) { return false; }

	@Override
	public void doTimeStep() {
		/* Move to somewhere without clover */
		for (int dir = 0; dir < 8; dir++) {
			if(this.look(dir, false) == null) {
				walk(dir);
				return;
			}
		}
		for (int dir = 0; dir < 8; dir++) {
			if(this.look(dir, true) == null) {
				run(dir);
				return;
			}
		}
		return;
	}

	public static String runStats2(java.util.List<Critter> avoidingCritters) {
		if(avoidingCritters.size() != 0) {
			return "So far so good";
		}
		else {
			return "Clover suck";
		}
	}

	@Override
	public CritterShape viewShape() { return CritterShape.DIAMOND; }

	@Override
	public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.SALMON; }

}
