package es.ucm.tp1.view;

import es.ucm.tp1.utils.StringUtils;
import es.ucm.tp1.logic.Game;
import es.ucm.tp1.logic.gameobjects.*;

public class GamePrinter {

	private static final String SPACE = " ";

	private static final String ROAD_BORDER_PATTERN = "═";

	private static final String LANE_DELIMITER_PATTERN = "─";

	private static final int CELL_SIZE = 7;

	private static final int MARGIN_SIZE = 2;

	private static final String CRASH_MSG = String.format("Player crashed!%n");

	private static final String WIN_MSG = String.format("Player wins!%n");

	private static final String DO_EXIT_MSG = "Player leaves the game";

	private static final String GAME_OVER_MSG = "[GAME OVER] ";

	private static final String SUPERCOIN_PRESENT = "Supercoin is present";

	private static final String DISTANCE_MSG = "Distance: ";

	private static final String COINS_MSG = "Coins: ";

	private static final String CYCLE_MSG = "Cycle: ";

	private static final String TOTAL_OBSTACLES_MSG = "Total obstacles: ";

	private static final String TOTAL_COINS_MSG = "Total coins: ";

	private static final String ELAPSED_TIME_MSG = "Elapsed Time: ";

	private static final String NEW_RECORD_MSG = "New record!: ";

	private static final String RECORD_MSG = "Record: ";
	private static final String THUNDER_MSG = "Thunder hit position: (";
	
	private static final String[] INFO_AVAILABLE = new String[] {
			"[Car] the racing car",
			"[Coin] gives 1 coin to the player",
			"[Obstacle] hits car",
			"[GRENADE] Explodes in 3 cycles, harming everyone around",
			"[WALL] hard obstacle",
			"[TURBO] pushes the car: 3 columns",
			"[SUPERCOIN] gives 1000 coins",
			"[TRUCK] moves towards the player",
			"[PEDESTRIAN] person crossing the road up and down"
	}; 

	private Game gam;

	private String indentedRoadBorder;

	private String indentedLanesSeparator;

	private String margin;
	
	private SuperCoin sp;

	public GamePrinter(Game game) {
		this.gam = game;
		inibordes();

	}
	
	public void inibordes() {

		margin = StringUtils.repeat(SPACE, MARGIN_SIZE);

		String roadBorder = ROAD_BORDER_PATTERN
				+ StringUtils.repeat(ROAD_BORDER_PATTERN, (CELL_SIZE + 1) * gam.getVisibility());
		indentedRoadBorder = String.format("%n%s%s%n", margin, roadBorder);

		String laneDelimiter = StringUtils.repeat(LANE_DELIMITER_PATTERN, CELL_SIZE);
		String lanesSeparator = SPACE + StringUtils.repeat(laneDelimiter + SPACE, gam.getVisibility() - 1)
				+ laneDelimiter + SPACE;

		indentedLanesSeparator = String.format("%n%s%s%n", margin, lanesSeparator);
	}
	
	protected String getInfo() {
		StringBuilder buffer = new StringBuilder();
		/* @formatter:off */
		buffer
	    
		.append(DISTANCE_MSG).append(gam.distance()).append(StringUtils.LINE_SEPARATOR)
		.append(COINS_MSG).append(gam.playerCoins()).append(StringUtils.LINE_SEPARATOR)
		.append(CYCLE_MSG).append(gam.getCycle()).append(StringUtils.LINE_SEPARATOR)
		.append(TOTAL_OBSTACLES_MSG).append(Obstacle.getobstacles()+Pedestrian.getobstacles()+Wall.getobstacles()+Truck.getobstacles()).append(StringUtils.LINE_SEPARATOR)
		.append(TOTAL_COINS_MSG).append(Coin.getmoney());
		/* @formatter:on */

		if (gam.isTestMode()) {
			/* @formatter:off */
			buffer
			.append(StringUtils.LINE_SEPARATOR)
			.append(ELAPSED_TIME_MSG).append(gam.time());
			/* @formatter:on */
		}
		if(SuperCoin.hasSuperCoin()) {
			buffer.append(StringUtils.LINE_SEPARATOR);
			buffer.append(SUPERCOIN_PRESENT);
		}

		return buffer.toString();
	}

	@Override
	public String toString() {
		inibordes();
		StringBuilder str = new StringBuilder();

		// Game Status

		str.append(getInfo());

		// Paint game

		str.append(indentedRoadBorder);

		String verticalDelimiter = SPACE;

		for (int y = 0; y < gam.getlevelrow(); y++) {
			str.append(this.margin).append(verticalDelimiter);
			for (int x = gam.posplayer(); x < gam.getVisibility()+gam.posplayer(); x++) {
				str.append(StringUtils.centre(gam.getPositionToString(x, y), CELL_SIZE)).append(verticalDelimiter);
			}
			if (y < gam.getlevelrow() - 1) {
				str.append(this.indentedLanesSeparator);
			}
		}
		str.append(this.indentedRoadBorder);

		return str.toString();
	}
	public static void Objectsinfo() {
	StringBuilder buffer=new StringBuilder("Available objects:\n");
	for(String aux :INFO_AVAILABLE) {
		buffer.append(aux);
		buffer.append("\n");
	}
	System.out.print(buffer.toString());
	}

	public static void record(Game game) {
		System.out.println(game.getLevel()+" record is "+ game.Record()+" s ");
	} 
}
