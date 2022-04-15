
public class SelectionSort implements Runnable {
	
	private Integer[] willBeSorted; 
	private MyFrame mainFrame;
	
	public SelectionSort(Integer[] toBeSorted, MyFrame iframed) 
	{
		this.willBeSorted = toBeSorted;
		this.mainFrame = iframed;
	
	}

	@Override
	public void run() {
		mainFrame.setResizable(false);
		sort();
		mainFrame.newlyDisplayedVertBarCollection(willBeSorted);
		mainFrame.setResizable(true);
		SortVisualizerMain.isCurrentlySorting = false;
		
	}
	
	public void sort() 
	{
		boolean swapped = false;
		
		for(int i = 0; i < willBeSorted.length -1; i++) 
		{
			int smallestIndex = i;
			swapped = false;

			for(int j = i+1; j < willBeSorted.length; j++) 
			{
				if(willBeSorted[j] < willBeSorted[smallestIndex]) 
				{
					smallestIndex = j;
				}
				
				int key = willBeSorted[smallestIndex];
				while (smallestIndex > i) 
				{
					willBeSorted[smallestIndex] = willBeSorted[smallestIndex - 1];
					smallestIndex--;
				}
				
				willBeSorted[i] = key;
				swapped = true;
				
				mainFrame.newlyDisplayedVertBarCollection(willBeSorted, j, j+1, 0);
				
				try 
				{
					Thread.sleep(SortVisualizerMain.sleepInMS);
				}
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
			
			if(!swapped)
				break;
		}
	}
}

