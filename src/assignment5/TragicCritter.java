package assignment5;

public class TragicCritter extends Critter {

	@Override
	public String toString() { return "T"; }

	public TragicCritter() {
		Params.LOOK_ENERGY_COST = 0;
		Params.WALK_ENERGY_COST = 0;
		Params.RUN_ENERGY_COST = 0;
		Params.REFRESH_CLOVER_COUNT = 0;
	}

	public boolean fight(String not_used) { return true; }

	@Override
	public void doTimeStep() {
		boolean moveFlag = true;
		/* Move to where its kin is */
		if(moveFlag) {
			for (int dir = 0; dir < 8; dir++) {
				if(this.look(dir, false) == null) {
					walk(dir);
					return;
				}
				if(this.look(dir, true) == null) {
					run(dir);
					return;
				}
			}
		}
	}

	public static String runStats(java.util.List<Critter> avoidingCritters) {
		if(avoidingCritters.size() >= 2) {
			return "Still can't get to each other";
		}
		else {
			return "Collided, sadly";
		}
	}
	@Override
	public CritterShape viewShape() { return CritterShape.STAR; }

	@Override
	public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.CRIMSON; }

}
