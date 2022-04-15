

public class BubbleSort implements Runnable 
{
	private Integer[] willBeSorted; 
	private MyFrame mainFrame;
	
	public BubbleSort(Integer[] toBeSorted, MyFrame iframed) 
	{
		this.willBeSorted = toBeSorted;
		this.mainFrame = iframed;
	
	}
	@Override
	public void run() 
	{
		mainFrame.setResizable(false);
		sort();
		mainFrame.newlyDisplayedVertBarCollection(willBeSorted);
		mainFrame.setResizable(true);
		SortVisualizerMain.isCurrentlySorting = false;
	}
	
	public void sort() 
	{
		int temporary = 0;
		boolean swapped = false;
		
		for(int i = 0; i < willBeSorted.length - 1; i++) 
		{
			swapped = false;
			for(int j = 1; j < willBeSorted.length - i ; j++) 
			{
				if(willBeSorted[j-1] > willBeSorted[j]) 
				{
					temporary = willBeSorted[j-1];
					willBeSorted[j-1] = willBeSorted[j];
					willBeSorted[j] = temporary;
					swapped = true;
				}
				
				mainFrame.newlyDisplayedVertBarCollection(willBeSorted, j, j+1,0);
				
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

