package simulator.launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import simulator.control.Controller;
import simulator.factories.Builder;
import simulator.factories.BuilderBasedFactory;
import simulator.factories.Factory;
import simulator.factories.MostCrowdedStrategyBuilder;
import simulator.factories.MoveAllStrategyBuilder;
import simulator.factories.MoveFirstStrategyBuilder;
import simulator.factories.NewCityRoadEventBuilder;
import simulator.factories.NewInterCityRoadEventBuilder;
import simulator.factories.NewJunctionEventBuilder;
import simulator.factories.NewVehicleEventBuilder;
import simulator.factories.RoundRobinStrategyBuilder;
import simulator.factories.SetContClassEventBuilder;
import simulator.factories.SetWeatherEventBuilder;
import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.TrafficSimulator;
import simulator.view.MainWindow;

public class Main {

	private final static Integer _timeLimitDefaultValue = 10;
	private static Integer tck = _timeLimitDefaultValue; //ticks
	private static String _inFile = null;
	private static String _outFile = null;
	private static Factory<Event> _eventsFactory = null;
	private static String mode ="gui";

	private static void parseArgs(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseHelpOption(line, cmdLineOptions);
			parseModeOption(line);
			parseInFileOption(line);
			parseOutFileOption(line);
			parseTicksOption(line);
			

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Events input file").build());
		cmdLineOptions.addOption(
				Option.builder("o").longOpt("output").hasArg().desc("Output file, where reports are written.").build());
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message").build());
		cmdLineOptions.addOption(Option.builder("t").longOpt("ticks").hasArg().desc(" Ticks to the simulator’s main loop (default value is 10)").build());
		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg().desc("Mode of the simullator(default value is gui)").build());

		return cmdLineOptions;
	}

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}
	private static void parseTicksOption(CommandLine line) throws ParseException{
		if(line.getOptionValue("t") != null) {
			tck=Integer.parseInt(line.getOptionValue("t"));
		}
	}

	private static void parseInFileOption(CommandLine line) throws ParseException {
		
		_inFile = line.getOptionValue("i");
		if(mode.equalsIgnoreCase("console")) {
			if (_inFile == null) {
				throw new ParseException("An events file is missing");
			}
		}
	}
	
	private static void parseModeOption(CommandLine line) throws ParseException{
		
		if(line.getOptionValue("m") != null) {
			mode=line.getOptionValue("m");
		}
	}
	

	private static void parseOutFileOption(CommandLine line) throws ParseException {
		if(mode.equalsIgnoreCase("console")){
		_outFile = line.getOptionValue("o");
		}
	}

	private static void initFactories() {
		List<Builder<LightSwitchingStrategy>> aux = new ArrayList<>();
		aux.add(new RoundRobinStrategyBuilder());
		aux.add(new MostCrowdedStrategyBuilder());
        Factory<LightSwitchingStrategy> lssF = new BuilderBasedFactory<>(aux);

        List<Builder<DequeuingStrategy>> aux2 = new ArrayList<>();
        aux2.add( new MoveFirstStrategyBuilder() );
        aux2.add( new MoveAllStrategyBuilder() );
        Factory<DequeuingStrategy> dqsF = new BuilderBasedFactory<>(aux2);

        List<Builder<Event>> auxfinal = new ArrayList<>();
        auxfinal.add( new NewJunctionEventBuilder(lssF,dqsF));
        auxfinal.add( new NewCityRoadEventBuilder() );
        auxfinal.add( new NewInterCityRoadEventBuilder() );
        auxfinal.add(new NewVehicleEventBuilder());
        auxfinal.add(new SetWeatherEventBuilder());
        auxfinal.add(new  SetContClassEventBuilder());
        
        List<Builder<Event>> init = auxfinal;
        _eventsFactory = new BuilderBasedFactory<>(init);
	}

	private static void startBatchMode() throws IOException {
		TrafficSimulator Trf=new TrafficSimulator();
		Controller c=new Controller(Trf,_eventsFactory);
		InputStream input=new FileInputStream(new File(_inFile));
		c.loadEvents(input);
		
		if(_outFile!=null) {
			c.run(tck,new FileOutputStream(new File(_outFile)));
		}
		else {
			c.run(tck,System.out);
		}
		
	}

	private static void start(String[] args) throws IOException {
		initFactories();
		parseArgs(args);
		if(mode.equalsIgnoreCase("console")) {
			startBatchMode(); 
		}
		else {
			startGuimode();
		}
	}

	private static void startGuimode() throws IOException {
		TrafficSimulator Trf=new TrafficSimulator();
		Controller c=new Controller(Trf,_eventsFactory);		
		if(_inFile!=null) {
			InputStream input=new FileInputStream(new File(_inFile));
			c.loadEvents(input);
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
			new MainWindow(c);
			}
			});
		
	}

	// example command lines:
	//
	// -i resources/examples/ex1.json
	// -i resources/examples/ex1.json -t 300 modo consola
	// -i resources/examples/ex1.json -o resources/tmp/ex1.out.json
	// --help
	

	public static void main(String[] args){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			start(args);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
