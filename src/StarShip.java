
/**
 * Represents the Starship object as a 2D array
 */
public class StarShip extends SpaceObject {

	
	public static final String name = "Starship";
	
	
	public StarShip() {

	}
	
	public StarShip(String starShipPath) throws SpaceObjectException {
		super(starShipPath);
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Type getType() {
		return Type.STARSHIP;
	}

}
