



/**
 * Represents the slime torpedo object as a 2D array
 *
 */
public class SlimeTorpedo extends SpaceObject {

	public static final String name = "SlimeTorpedo";
	
	public SlimeTorpedo() {
		
	}

	public SlimeTorpedo(String slimeTorpedoPath) throws SpaceObjectException {
		super(slimeTorpedoPath);
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Type getType() {
		return Type.SLIMETORPEDO;
	}
	
	
	
}
