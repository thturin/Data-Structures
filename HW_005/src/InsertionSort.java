
public class InsertionSort{
	public InsertionSort(){}
		
	public int[] insertionSort(int[] arr){		
		for(int i=1;i<arr.length;i++){
			int tempVal = arr[i];
			int j=i;
			while(j>0 && arr[j-1]>tempVal){
				arr[j]=arr[j-1];
				j=j-1;
			}
			arr[j]=tempVal;	
		}	
		

		return arr;		
	}			
}
