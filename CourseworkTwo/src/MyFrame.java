import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.event.*;

/*
  Should handle GUI, you can also create other class for GUI to clean up the code. 
  Explain your design choice.
*/
public class MyFrame extends JFrame implements ActionListener 
{
	private static final long serialVersionUID = 190462509;

	public static JComboBox<String> sortSelection;
	public static ArrayList<String> inputtedString;
	public static ArrayList<String> newlyAcceptedInputs;
	public static String[] stringList;
	public  Integer[] confirmedList;
	
	public static final int givenHeight = 800;
	public static final int givenWidth = 800;
	public static final int defaultListLength = 10;
	public static final int standardSpeed = 250;
	public static final int maximumSpeed = 1000;
	public static final int minimumSpeed = 1;

	private static final Color currentlyScanningColor = Color.GREEN;
	private static final Color currentlyWorkingOnColor = Color.BLUE;
	private static final Color currentlyComparingColor = Color.RED;
	
	private JPanel mainInputtingPanel; // components where users can input their list of numbers in
	private JPanel mainSortingPanel; // the panel to place all bars and to call the algorithm method/class
	public  JPanel panelHoldingSortedBars;
	private JTextField userNumberInput; // user to input their list of numbers in
	private JLabel userInstruction;
	private JLabel userInstruction2;
	private JLabel userInstruction3;
	private JLabel speedAnimatorLabel; // label to show what ms delay user wants
	private JLabel[] sortBarValue;
	private JButton clearMyList;
	private JButton goSort;
	private JSlider speedAnimatorSlider; // the slider for user to be able to adjust their ms delay
	private GridBagConstraints gbc;
	private GridBagConstraints gridBagRestrictions;
	private DefaultListCellRenderer centeredList;
	
	MyFrame()
	{
		super("Sorting Algorithm Visualiser");

		SortVisualizerMain.mainFrame = this;
		this.setLayout(new GridLayout(2,1)); //generally, the window requires only 2 "rows" to add all relevant components
		
		inputtedString = new ArrayList<String>();	
		
		userNumberInput();
		mainInputtingPanel.setPreferredSize(new Dimension(800,400));
		mainInputtingPanel.setBackground(Color.BLACK);
		mainInputtingPanel.setVisible(true);
		add(mainInputtingPanel);

		mainSortingSection();
		mainSortingPanel.setPreferredSize(new Dimension(800,400));
		mainSortingPanel.setBackground(Color.BLACK);
		mainSortingPanel.setVisible(true);
		add(mainSortingPanel);
		
		addComponentListener(new FrameComponentListener());
		setSize(givenHeight, givenWidth);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);

	}

	private void userNumberInput() 
	{
		//all components added here only belongs in the user input section
		
		userInstruction = new JLabel();
		userInstruction.setText("Please input at least 5 numbers. Press 'Enter' after every number and once you're ready, press the sort button below!");
		userInstruction.setForeground(Color.WHITE);
		
		userInstruction2 = new JLabel();
		userInstruction2.setText("Press the clear button to redo your list of numbers");
		userInstruction2.setForeground(Color.WHITE);
		
		userInstruction3 = new JLabel();
		userInstruction3.setText("To choose another sort with a new batch of numbers, press the clear button and enter your numbers again!");
		userInstruction3.setForeground(Color.WHITE);

		userNumberInput = new JTextField();
		userNumberInput.setPreferredSize(new Dimension(380, 40));
		userNumberInput.addActionListener(new NumberInputSection());

		String[] sortList = { "Select your Sorting Algorithm here", "Bubble", "Selection", "Merge" };
		sortSelection = new JComboBox<>(sortList);
		sortSelection.setPreferredSize(new Dimension(380, 40));
		centeredList = new DefaultListCellRenderer();
		centeredList.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
		sortSelection.setRenderer(centeredList);

		speedAnimatorSlider = new JSlider(minimumSpeed, maximumSpeed, standardSpeed);
		speedAnimatorSlider.setPreferredSize(new Dimension(380, 40));
		speedAnimatorSlider.setMinorTickSpacing(10);
		speedAnimatorSlider.setMajorTickSpacing(100);
		speedAnimatorSlider.setPaintTicks(true);
		speedAnimatorSlider.setPaintTrack(true);
		speedAnimatorSlider.setPaintLabels(true);
		speedAnimatorSlider.addChangeListener(new AnimatorSpeedControl());
		speedAnimatorLabel = new JLabel();
		speedAnimatorLabel.setForeground(Color.WHITE);
		
		clearMyList = new JButton("Please clear!");
		clearMyList.setPreferredSize(new Dimension(150, 80));
		clearMyList.setFont(new Font(null, Font.BOLD, 15));
		clearMyList.addActionListener(new ClearingList());
		
		goSort = new JButton("Go Sort!");
		goSort.setPreferredSize(new Dimension(150, 80));
		goSort.setFont(new Font(null, Font.BOLD, 20));
		goSort.addActionListener(new InitializeSorting());

		mainInputtingPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		mainInputtingPanel.add(userInstruction, gbc);
		mainInputtingPanel.add(userInstruction2, gbc);
		mainInputtingPanel.add(userInstruction3, gbc);
		mainInputtingPanel.add(userNumberInput, gbc);
		mainInputtingPanel.add(sortSelection, gbc);
		mainInputtingPanel.add(speedAnimatorSlider, gbc);
		mainInputtingPanel.add(speedAnimatorLabel, gbc);
		mainInputtingPanel.add(clearMyList, gbc);
		mainInputtingPanel.add(goSort);
	}

	private void mainSortingSection()
	{
		
		gridBagRestrictions = new GridBagConstraints();
		gridBagRestrictions.insets = new Insets(0,1,0,1);
		gridBagRestrictions.anchor = GridBagConstraints.SOUTH;
		
		mainSortingPanel = new JPanel(new BorderLayout());
		panelHoldingSortedBars = new JPanel(new GridBagLayout());

		panelHoldingSortedBars.setBackground(Color.BLACK);
		
		mainSortingPanel.add(panelHoldingSortedBars);
	}

	public void preDisplayVertBarCollection(Integer[] sortValuesArray)
	{
		sortBarValue = new JLabel[SortVisualizerMain.totalNumOfElementsToSort];
		panelHoldingSortedBars.removeAll();
		
		if(SortVisualizerMain.finalizedNumCollectionToSort != null) 
		{
		for (int i = 0; i < SortVisualizerMain.totalNumOfElementsToSort; i++)
		{
			int arrayValue = SortVisualizerMain.finalizedNumCollectionToSort[i].intValue();
			String text = "" + arrayValue;

			do 
			{
				sortBarValue[i] = new JLabel(text, SwingConstants.CENTER);
			}
			while (sortBarValue[i] == null);

			Dimension calculatedDimension = new Dimension(SortVisualizerMain.computeValuesJLabelBarWidth(),
					SortVisualizerMain.computeValuesJLabelBarHeight(sortValuesArray[i]));

			sortBarValue[i].setPreferredSize(calculatedDimension);

			if (arrayValue >= 0)
				sortBarValue[i].setBackground(Color.CYAN);
			else
				sortBarValue[i].setBackground(Color.YELLOW);

			sortBarValue[i].setOpaque(true);

			panelHoldingSortedBars.add(sortBarValue[i], gridBagRestrictions);
			}	
		}

		validate();
		repaint();
	}

	public void newlyDisplayedVertBarCollection(Integer[] sortValuesArray)
	{
		newlyDisplayedVertBarCollection(sortValuesArray, -1, 0, 0);
	}

	public void newlyDisplayedVertBarCollection(Integer[] sortValuesArray, int currentlyWorkingOnArrayIndex)
	{
		newlyDisplayedVertBarCollection(sortValuesArray, currentlyWorkingOnArrayIndex, -1, 0);
	}

	public void newlyDisplayedVertBarCollection(Integer[] sortValuesArray, int currentlyWorkingOnArrayIndex, int currentlyComparingArrayIndex) 
	{
		newlyDisplayedVertBarCollection(sortValuesArray, currentlyWorkingOnArrayIndex, currentlyComparingArrayIndex, -1);
	}

	public void newlyDisplayedVertBarCollection(Integer[] sortValuesArray, int currentlyWorkingOnArrayIndex,
			int currentlyComparingArrayIndex, int currentlyScanningArrayIndex) 
	{		
		panelHoldingSortedBars.removeAll();

		for (int i = 0; i < sortBarValue.length; i++) 
		{
			int collectionValue = SortVisualizerMain.finalizedNumCollectionToSort[i].intValue();
			String contents = "" + collectionValue;

			int width = SortVisualizerMain.computeValuesJLabelBarWidth();
			int height = SortVisualizerMain.computeValuesJLabelBarHeight(sortValuesArray[i]);
			Dimension reRendDimension = new Dimension(width, height);

			do 
			{
				sortBarValue[i] = new JLabel(contents, SwingConstants.CENTER);

				if (sortBarValue[i] == null)
					System.out.println("Checkpoint #1 valueBarLabelArray[" + i + "] is NULL!");
			} 
			
			while (sortBarValue[i] == null);

			if (sortBarValue[i] == null)
			{
				System.out.println("Checkpoint #2 valueBarLabelArray[" + i + "] is NULL!");
				i--;
				continue;
			}			
			sortBarValue[i].setPreferredSize(reRendDimension);

			if (sortBarValue[i] == null) 
			{
				System.out.println("Checkpoint #3 valueBarLabelArray[" + i + "] is NULL!");
				i--;
				continue;
			}			
			sortBarValue[i].setOpaque(true);

			if (sortBarValue[i] == null)
			{
				System.out.println("Checkpoint #4 valueBarLabelArray[" + i + "] is NULL!");
				i--;
				continue;
			}

			if (i == currentlyWorkingOnArrayIndex)
				sortBarValue[i].setBackground(currentlyWorkingOnColor);

			else if (i == currentlyComparingArrayIndex)
				sortBarValue[i].setBackground(currentlyComparingColor);

			else if (i == currentlyScanningArrayIndex)
				sortBarValue[i].setBackground(currentlyScanningColor);
			
			else
				if(collectionValue >= 0)
					sortBarValue[i].setBackground(Color.CYAN);
				else
					sortBarValue[i].setBackground(Color.YELLOW);

			if (sortBarValue[i] == null) 
			{
				System.out.print("Checkpoint #5 valueBarLabelArray[" + i + "] is NULL!");
				i--;
				continue;
			}
			
			panelHoldingSortedBars.add(sortBarValue[i], gridBagRestrictions);
		}
		
		repaint();
		validate();	
	}

	private class NumberInputSection implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			
			inputtedString.add(userNumberInput.getText().trim());
			inputtedString.remove("");
			userNumberInput.setText("");
			
			String conditionedRegex = "^[0-9\\-]+";
			
			newlyAcceptedInputs = new ArrayList<>();
			
			for(int i = 0; i < inputtedString.size(); i++) 
				{
				
					if(inputtedString.get(i).matches(conditionedRegex)) 
					{
						newlyAcceptedInputs.add(inputtedString.get(i));
						
						userInstruction.setText("You have so far inputted: " + newlyAcceptedInputs.size() + "!");
						userInstruction.setFont(new Font(null, Font.BOLD, 20));
						userInstruction.setForeground(Color.YELLOW);
					}
				}
				
			}
	}
	
	private class ClearingList implements ActionListener 
	{
		public void actionPerformed(ActionEvent e)
		{
			inputtedString.clear();		
			userInstruction.setText("Please re-enter your numbers!");			
		}
	}

	private class InitializeSorting implements ActionListener 
	{
		public void actionPerformed(ActionEvent e)
		{		
			stringList = new String[newlyAcceptedInputs.size()];
			
			for(int i =0; i <newlyAcceptedInputs.size(); i++) 
			{
				stringList[i] = newlyAcceptedInputs.get(i);
			}
			
			confirmedList = new Integer[stringList.length];
				for(int s = 0; s < stringList.length; s++) 
				{
					confirmedList[s] = Integer.parseInt(stringList[s]);
				}
			
			SortVisualizerMain.executeDrawingOfBarArray();
			SortVisualizerMain.startSort((String) sortSelection.getSelectedItem());
		}
	}

	private class AnimatorSpeedControl implements ChangeListener
	{
		public void stateChanged(ChangeEvent e)
		{
			speedAnimatorLabel.setText("Current Delay: " + speedAnimatorSlider.getValue() + " ms");
			SortVisualizerMain.sleepInMS = speedAnimatorSlider.getValue();
			validate();
			repaint();
		}

	}

	private class FrameComponentListener implements ComponentListener
{
		@Override
		public void componentResized(ComponentEvent ee)
		{
			preDisplayVertBarCollection(SortVisualizerMain.finalizedNumCollectionToSort);		
		}

		@Override
		public void componentMoved(ComponentEvent e)
		{
			//Generally does nothing
		}

		@Override
		public void componentShown(ComponentEvent e)
		{
			//Generally does nothing
		}

		@Override
		public void componentHidden(ComponentEvent e) 
		{
			//Generally does nothing
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}

