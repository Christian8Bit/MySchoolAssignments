import java.util.ArrayList;
import java.util.Collections;

public class SortVisualizerMain 
{
	public static final int smallestBarHeight = 10;
	public static final int smallestBarWidth = 10;
	public static final int standardBigVal = 600;
	public static final int standardSmallVal = -600;
	
	private static Thread selectedSortLogic;
	
	public static MyFrame mainFrame;
	public static Integer[] finalizedNumCollectionToSort;

	
	public static boolean isCurrentlySorting = false;
	public static int totalNumOfElementsToSort;
	public static int sleepInMS = 100;
	
	public static boolean numRandomizer = false;

	public static void executeDrawingOfBarArray() 
	{
		if(isCurrentlySorting)
			return;
		
		finalizedNumCollectionToSort = mainFrame.confirmedList;
		totalNumOfElementsToSort = mainFrame.confirmedList.length;
		
		ArrayList<Integer> shuffleSection = new ArrayList<>();
		for(int i = 0; i < finalizedNumCollectionToSort.length; i++) 
		{
			shuffleSection.add(finalizedNumCollectionToSort[i]);
		}
		Collections.shuffle(shuffleSection);
			finalizedNumCollectionToSort = shuffleSection.toArray(finalizedNumCollectionToSort);
		
		mainFrame.preDisplayVertBarCollection(finalizedNumCollectionToSort);
	}
	
	public static void startSort (String sortingLogic) 
	{		
		if(selectedSortLogic == null || !isCurrentlySorting) 
		{
			isCurrentlySorting = true;
			
			switch(sortingLogic) 
			{
				case "Bubble":
					selectedSortLogic = new Thread(new BubbleSort(finalizedNumCollectionToSort, mainFrame));
					break;
				
					case "Selection":
					selectedSortLogic = new Thread(new SelectionSort(finalizedNumCollectionToSort, mainFrame));
					break;
				
					case "Merge":
					selectedSortLogic = new Thread(new MergeSort(finalizedNumCollectionToSort, mainFrame));
					break;
				
				default:
				isCurrentlySorting = false;
				return;
			}
			
			selectedSortLogic.start();
		}
	}
	
	public static int generateRandomIntWithinRange (int smallest, int biggest) 
	{
		int range = Math.abs(biggest - smallest);
		int randNum = (int)(range * Math.random());
		return(smallest + randNum);
	}
	
	public static int computeValuesJLabelBarWidth() 
	{
		int result = (int)Math.max(
				Math.floor
				(0.95*mainFrame.panelHoldingSortedBars.getWidth()/totalNumOfElementsToSort), smallestBarWidth);
		
		return (result);
	}
	
	public static int computeValuesJLabelBarHeight (int arrayValue) 
	{
		int minVal = smallestValueFoundInNumCollection();
		int maxVal = largestValueFoundInNumCollection();
		int valRanging = maxVal - minVal;
		double fixedValueRatio = (0.8*mainFrame.panelHoldingSortedBars.getHeight() - smallestBarHeight) / valRanging;
		
		int result = (int)((arrayValue - minVal) * fixedValueRatio) + smallestBarHeight;
		
		return result;
	}
	
	public static int smallestValueFoundInNumCollection() 
	{
		int minValue = Integer.MAX_VALUE;
		
		for(int i : finalizedNumCollectionToSort) 
		{
			if (minValue > i)
				minValue = i;
		}
		return minValue;
	}
	
	public static int largestValueFoundInNumCollection() 
	{
		int maxValue = Integer.MIN_VALUE;
		
		for(int i : finalizedNumCollectionToSort) 
		{
			if(maxValue < i)
				maxValue = i;
		}
		return maxValue;
	}
}

