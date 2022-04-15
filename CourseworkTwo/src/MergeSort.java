class MergeSort implements Runnable {

	private Integer[] willBeSorted;
	private MyFrame mainFrame;

	public MergeSort(Integer[] toBeSorted, MyFrame iframed) 
	{
		this.willBeSorted = toBeSorted;
		this.mainFrame = iframed;
	}

	@Override
	public void run()
	{
		mainFrame.setResizable(false);
		mainFrame.newlyDisplayedVertBarCollection(willBeSorted);
		mainFrame.setResizable(true);
		SortVisualizerMain.isCurrentlySorting = false;
	}
	
	public void sortInPlace(Integer[] willBeSorted, int i, int j) 
	{
		sort (willBeSorted, 0, willBeSorted.length - 1);
	}
	
	private void sort(Integer[] toBeSorted, int left, int right)
	{
			int mid, lt, rt;
			int temp;
			
			if(left >= right)
				return;
			
			mid = (left + right) / 2;
			
			sortInPlace(willBeSorted, left, mid);
			sortInPlace(willBeSorted, mid + 1, right);
			
			lt = left;
			rt = mid + 1;
			
			if(willBeSorted[mid] <= willBeSorted[rt])
				return;
			
			while(lt <= mid && rt <= right) 
			{
				if(willBeSorted[lt] <= willBeSorted[rt]) 
				{
					lt++;
				}		
				else 
				{
					temp = willBeSorted[rt];
					
					for(int i = rt - lt; i > 0; i--) 
					{
						willBeSorted[lt+i] = willBeSorted[lt+i-1];
					}
					willBeSorted[lt] = temp;
					
					lt++;
					mid++;
					rt++;
				}
				
				mainFrame.newlyDisplayedVertBarCollection(willBeSorted, mid, rt, lt);
				
				try 
				{
					Thread.sleep(SortVisualizerMain.sleepInMS);
				}
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}			
	}
	
}


