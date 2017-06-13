
public class QuickSort {

	public static void main(String[] args) {
		
		QuickSort s = new QuickSort(); 
		int[] num = {4,77,98,30,20,77,22,49,2};
		int start = 0; 
		int end = num.length - 1;
		s.sort(num, start, end);
		for(int i=0; i<num.length; i++){
		System.out.print(num[i] + " ");
		}

	}
	     
	      
	public static void sort(int[] num, int start, int end){

	   //base case
	   if(start == end-1){
	   	int temp;
	   	if(num[start]> num[end]){
	   		temp = num[start];
                num[start] = num[end];
                num[end] = temp;
	   	}
	   }
	   //base case
	   else if(start == end){
	   	
	   }


	   else{ 
	       int pivot = num[(start+end)/2]; 
	       int length = num.length - 1;
	       int i = start;
	       int j = end;
	       int temp; 

	 
	       while (i <= j) {
	           while (num[i] < pivot)
	                 i++;
	           while (num[j] > pivot)
	                 j--;
	           if (i <= j) {
	                 temp = num[i];
	                 num[i] = num[j];
	                 num[j] = temp;
	                 i++;
	                 j--;
	           }
	     
	       }

	       sort(num, start, i-1); 
	       sort(num, j+1, end); 
	   }
	}
}

