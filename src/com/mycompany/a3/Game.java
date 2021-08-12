package com.mycompany.a3;



import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.UITimer;



public class Game extends Form implements Runnable{
	private GameWorld gw; 
	private MapView mv;
	private ScoreView sv;
	private UITimer timer; 
	private boolean playMode; //true = playing, false = pause
	private Button modeButton;
	private Button accelerateButton;
	private Button breakButton;
	private Button leftButton;
	private Button rightButton;
	private Button positionButton; 
	private CheckBox soundCheckOption;
	private SpeedCommand accCommand;
	private SpeedCommand breakCommand;
	private DirectionCommand leftCommand;
	private DirectionCommand rightCommand;

	
	public Game() { 
		gw = new GameWorld(); 
		mv = new MapView();
		sv = new ScoreView();
		gw.addObserver(mv);
		gw.addObserver(sv);	
		
		playMode = true;

		/*
		 * Create buttons for the game and style them by passing it 
		 * to the styleButton method
		 */
		accelerateButton = new Button("Accelerate");
		breakButton = new Button("Break");
		leftButton = new Button("Left");
		rightButton = new Button("Right");
		positionButton = new Button("Position");
		modeButton = new Button("Pause");
	
		
		styleButton(accelerateButton);
		styleButton(breakButton);
		styleButton(leftButton);
		styleButton(rightButton);
		styleButton(modeButton);
		styleButton(positionButton);
		
		
		/*
		 * ================GUI SETUP=====================
		 */
		//Set up the screen as a border layout
		this.setLayout(new BorderLayout());
		
		
		/*
		 * Toolbar items
		 */
		Label gameTitle = new Label("ThePath Game");
		Toolbar gameBar = new Toolbar();
		setToolbar(gameBar);
		gameBar.setTitleComponent(gameTitle);
		Command sideMenuItem = new Command("Options");
		Command aboutMenuItem = new Command("About") {
			public void actionPerformed(ActionEvent ev) {
				String aboutMessage = "Jerald Velicaria\nCSC 133 Section 03\nver.A3.4.30.2021";
				Dialog.show("About", aboutMessage, "Ok" ,null);
			}
		};
		Command exitAppItem = new Command("Exit") {
			public void actionPerformed(ActionEvent ev) {
				String exitMessage = "Are you sure you want to close the application?";
				Boolean bYes = Dialog.show("Exit", exitMessage, "Yes" , "No");
				if(bYes) {
					Display.getInstance().exitApplication();
				}
			}
		};
		Command accelerateMenuItem = new Command("Accelerate") {
			public void actionPerformed(ActionEvent ev) {
				gw.changeAntSpeed('a');
			}
		};
		Command helpButton = new Command("Help") {
			public void actionPerformed(ActionEvent ev) {
				String helpMessage = "a - Accelerate\nb - Brake\nl/r - Left / Right\n";
				Dialog.show("Controls", helpMessage, "Ok" ,null);
			}
		};
		soundCheckOption = new CheckBox("Game Sound OFF");
		
		//style the game title
		gameTitle.getAllStyles().setFgColor(ColorUtil.BLACK);
		soundCheckOption.getAllStyles().setBgTransparency(255);
		soundCheckOption.getAllStyles().setBgColor(ColorUtil.WHITE);
		
		
		//add menu option items to the gameBar/Side Menu 
		gameBar.addCommandToSideMenu(sideMenuItem);
		gameBar.addComponentToSideMenu(soundCheckOption);
		gameBar.addCommandToSideMenu(accelerateMenuItem);
		gameBar.addCommandToSideMenu(aboutMenuItem);
		gameBar.addCommandToSideMenu(exitAppItem);
		gameBar.addCommandToRightBar(helpButton);
		
		
		
		
		/*
		 * Top Container / ScoreView with the GridLayout positioned on the north
		 */
		Container scoreView = sv;
		scoreView.setLayout(new FlowLayout(Component.CENTER));
		Label timeLabel = new Label("Time: 0");
		Label livesLabel = new Label(" Lives Left: 3" );
		Label lastFlagLabel = new Label(" Last Flag Reached: 1" );
		Label healthLabel = new Label(" Health Level: 10");
		Label soundLabel = new Label(" Sound: OFF");
		
		
		timeLabel.getAllStyles().setFgColor(ColorUtil.BLUE);
		livesLabel.getAllStyles().setFgColor(ColorUtil.BLUE);
		lastFlagLabel.getAllStyles().setFgColor(ColorUtil.BLUE);
		healthLabel.getAllStyles().setFgColor(ColorUtil.BLUE);
		soundLabel.getAllStyles().setFgColor(ColorUtil.BLUE);

		
		scoreView.add(timeLabel);
		scoreView.add(livesLabel);
		scoreView.add(lastFlagLabel);
		scoreView.add(healthLabel);
		scoreView.add(soundLabel);
		
		add(BorderLayout.NORTH,scoreView);
		
		
		
		/*
		 * left Container with the BoxLayout positioned on the west
		 * Add accelerate and left buttons to this container
		 */
		Container leftContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		leftContainer.getAllStyles().setPadding(Component.TOP, 50);
		leftContainer.add(accelerateButton);
		leftContainer.add(leftButton);
		leftContainer.getAllStyles().setBorder(Border.createLineBorder(4,ColorUtil.BLACK));
		
		add(BorderLayout.WEST,leftContainer);
		
		
		
		/*
		 * right container positioned to the east
		 * add the break right button to this container    
		 */
		Container rightContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		rightContainer.getAllStyles().setPadding(Component.TOP, 50);
		rightContainer.add(breakButton);
		rightContainer.add(rightButton);
		rightContainer.getAllStyles().setBorder(Border.createLineBorder(4,ColorUtil.BLACK));
		
		add(BorderLayout.EAST,rightContainer);
		
		
		
		/*
		 * Let the center container/layout to be the MapView 
		 * As of A2, let it just be empty with a red border around it 
		 */
		Container centerContainer = mv;
		centerContainer.getAllStyles().setBgTransparency(255);
		centerContainer.getAllStyles().setBgColor(ColorUtil.WHITE);
		centerContainer.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.rgb(255, 0, 0)));
		add(BorderLayout.CENTER,centerContainer);
		
		
		
		/*
		 * bottom Container positioned on the south, components are laid out
		 * in a flowlayout; all collision commands and tick command are added here
		 */

		Container bottomContainer = new Container(new FlowLayout(Component.CENTER));
		bottomContainer.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.BLACK));
		bottomContainer.add(positionButton);
		bottomContainer.add(modeButton);
		add(BorderLayout.SOUTH,bottomContainer);

		timer = new UITimer(this);
		timer.schedule(20, true, this);
		
		
		
		
		

		/*
		 * Add all button commands to their corresponding Command class with the
		 * appropriate parameters passed to their constructors 
		 */
		SoundCheckCommand soundOption = new SoundCheckCommand(gw, gameBar);
		
		accCommand = new SpeedCommand(gw, "Accelerate");
		breakCommand = new SpeedCommand(gw, "Break");
		leftCommand = new DirectionCommand(gw, "Left");
		rightCommand = new DirectionCommand(gw, "Right");
		
		ModeCommand modeCommand = new ModeCommand("Pause", this, centerContainer);
		PositionCommand positionCommand = new PositionCommand("Position",this, centerContainer, gw);
		
		//set up the button to their commands
		soundCheckOption.setCommand(soundOption);
	    accelerateButton.setCommand(accCommand);
	    breakButton.setCommand(breakCommand);
	    leftButton.setCommand(leftCommand);
	    rightButton.setCommand(rightCommand);

	    modeButton.setCommand(modeCommand);
	    positionButton.setCommand(positionCommand);
	    positionButton.setEnabled(false);
	    
	    //Add key listeners to the buttons allowed to have a key-binding
	    addKeyListener('a', accCommand);
	    addKeyListener('b', breakCommand);
	    addKeyListener('l', leftCommand);
	    addKeyListener('r', rightCommand);
	    
			
		this.show();
		gw.setDisplayDimension(centerContainer.getHeight(), centerContainer.getWidth());
		gw.init();
		gw.createSounds();
		mv.setGameMode(true);
		revalidate();
	
	}
		

	//style the buttons being passed;
	public void styleButton(Button b) {
		b.getUnselectedStyle().setBgTransparency(255);
		b.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
		b.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		b.getUnselectedStyle().setBorder(Border.createLineBorder(3,ColorUtil.BLACK));
		b.getAllStyles().setPadding(Component.TOP, 5);
		b.getAllStyles().setPadding(Component.BOTTOM, 5);
	}



	public void run() {
		gw.gameTicked(20);
		mv.repaint();
	}
	
	
	
	public void pauseGame() { 
		this.playMode = false; 
		timer.cancel();
		this.modeButton.setSelectCommandText("Play");
		
		this.accelerateButton.setEnabled(false);
		this.breakButton.setEnabled(false);
		this.leftButton.setEnabled(false);
		this.rightButton.setEnabled(false);
		
		removeKeyListener('a', this.accCommand);
		removeKeyListener('b', this.breakCommand);
		removeKeyListener('l', this.leftCommand);
		removeKeyListener('r', this.rightCommand);
		mv.setGameMode(false);
		this.soundCheckOption.setEnabled(true);
		this.positionButton.setEnabled(true);
		

		this.modeButton.setText("Play");
		gw.pauseBGSound();
	}
	
	public void resumeGame() {  
		this.playMode = true; 
		timer.schedule(20, true, this);
		this.modeButton.setSelectCommandText("Pause");
		this.accelerateButton.setEnabled(true);
		this.breakButton.setEnabled(true);
		this.leftButton.setEnabled(true);
		this.rightButton.setEnabled(true);
		
		addKeyListener('a', this.accCommand);
	    addKeyListener('b', this.breakCommand);
	    addKeyListener('l', this.leftCommand);
	    addKeyListener('r', this.rightCommand);
		
		this.soundCheckOption.setEnabled(false);
		this.positionButton.setEnabled(false);
		mv.setGameMode(true);
		if(mv.getPosMode() == true) {
			mv.setPosMode(false);
		}
		
	
		this.modeButton.setText("Pause");
		gw.playBGSound();
	}
	
	
	
	public boolean getMode() {
		return this.playMode; 
	}
}
