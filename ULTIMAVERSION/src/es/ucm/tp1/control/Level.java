package es.ucm.tp1.control;

public enum Level {

	TEST(10, 3, 8, 0.5, 0), EASY(30, 3, 8, 0.5, 0.5), HARD(100, 5, 6, 0.7, 0.3);
	private int length;

	private int width;

	private int visibility;

	private double coinFrequency;

	private double obstacleFrequency;
	
	private double advObjFrequency;

	private Level(int length, int width, int visibility, double obstacleFrequency, double coinFrequency,double advObjFrequency) {
		this.length = length;
		this.width = width;
		this.visibility = visibility;
		this.obstacleFrequency = obstacleFrequency;
		this.coinFrequency = coinFrequency;
		this.advObjFrequency=advObjFrequency;
	}


	public static Level valueOfIgnoreCase(String inputString) {
		for (Level level : Level.values()) {
			if (level.name().equalsIgnoreCase(inputString)) {
				return level;
			}
		}
		return null;
	}
	public int returnlevelrow() {
		
		return this.width;
	}
    public int returnlevelcol() {
		
		return this.visibility;
	}
    public int returnlength(){
    	
    	return this.length;
    }
    public double returncoinfrequency() {
    	return this.coinFrequency;
    }
    public double returnobstaclefrequency() {
    	return this.obstacleFrequency;
    }
    public double advancedObjectsFrequency() {
		return this.advObjFrequency;
	}
	public static String all(String separator) {
		StringBuilder buffer = new StringBuilder();
		int levelCount = 0;
		for (Level level : Level.values()) {
			if (levelCount > 0) {
				buffer.append(separator);
			}
			buffer.append(level.name());
			levelCount++;
		}
		return buffer.toString();
	}

	public boolean hasAdvancedObjects() {
    return advObjFrequency>0;
	}

	
}
