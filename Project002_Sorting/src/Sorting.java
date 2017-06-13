
public class Sorting {

	public static void main(String[] args) {
	int[] arr={4,77,98,30,20,50,77,22,49,2};	
	mergeSortRec(arr,0,arr.length-1);
	//selectionSortIter(arr);	
	//insertionSortIter(arr);
	//quickSort(arr,0,arr.length-1);	
	//bubbleSortIter(arr);
	//bubbleSortRec(arr,0,arr.length-1);
		for(int i : arr){
			System.out.print(i+" ");
		}				
	}
	
	public static int[] bubbleSortIter(int[] arr){
		for(int i=0; i < arr.length; i++){
			neighborSwap(arr,i);
		}
		return arr;
	}	
	public static int[] neighborSwap(int[]arr, int i){
		for(int j=0; j<arr.length-(i+1); j++){
			if(arr[j]>arr[j+1]){
				int temp = arr[j];
				arr[j]=arr[j+1];
				arr[j+1]=temp;
			}
		}
		return arr;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////	
	public static int[] bubbleSortRec(int[] arr, int LI,int HI){
		if(LI<HI && HI>0){
			if(arr[LI]>arr[LI+1]){
				int temp=arr[LI];
				arr[LI]=arr[LI+1];
				arr[LI+1]=temp;
			}
			bubbleSortRec(arr,LI+1,HI);
			bubbleSortRec(arr,LI,HI-1);			
		}else{
			return arr;
		}
		return arr;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////		
	
	public static int[]selectionSortIter(int[] arr){
		 for(int i=0;i<arr.length;i++){
			 int min = i;
			 for(int j=i+1;j<arr.length;j++){
				 if(arr[j]<arr[min]){
					 min=j;					 
				 }
			 }
			 int temp=arr[i];
			 arr[i]=arr[min];
			 arr[min]=temp;
		 }
		 return arr;
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////		
	public static int[] insertionSortIter(int[] arr){
		for(int i=1; i<arr.length; i++){
			int tempVal = arr[i];
			int j=i;
			while(j>0 && arr[j-1]>tempVal){
				arr[j]=arr[j-1];
				j-=1;
			}
			arr[j]=tempVal;			
			}
		return arr;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////		
	public static int[] mergeSortRec(int[] arr,int l, int r){		
		if(l<r){
			int mid=(l+r)/2;			
			mergeSortRec(arr,l,mid);
			mergeSortRec(arr,mid+1,r);
			merge(arr,l,mid,mid+1,r);			
		}
		
		return arr;
	}	
	
	public static int[] merge(int[]x,int l1, int r1, int l2, int r2){
		int[] temp = new int[x.length];
		int i=l1;
		int saveF = l1;
		
		while(l1<=l2 && r1<=r2){
			if(x[l1]<x[r1]){
				temp[i] = x[l1];
				l1++;
			}else{
			temp[i] = x[r1];
			r1++;
			}
		i++;
		}
		
		while(l1<=l2){
			temp[i] = x[l1];
			l1++;
			i++;
		}
		while(r1<=r2){
			temp[i] = x[r1];
			r1++;
			i++;
		}
		for(i=saveF; i<=r2; i++){
			x[i]=temp[i];
		}
		return x;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////
	public static  int[] quickSort(int[] a, int f, int l){
		if(f<l){
			int pivotPoint = split(a,f,l);
			quickSort(a,f,pivotPoint-1);
			quickSort(a,pivotPoint + 1, l);
		}		
		return a;
	}

	public static int split(int[] a, int f, int l){
		int pivotVal = a[f];
		int pivot = f;
		boolean onCorrectSide;
		f++;	
	do{
		onCorrectSide=true;
		while(onCorrectSide){
			if(a[f]>pivotVal){
				onCorrectSide=false;
			}else{
				f++;
				onCorrectSide=(f<=l);
			}
		}				
		onCorrectSide=(f<=l);		
		while(onCorrectSide){
			if(a[l] <= pivotVal){
				onCorrectSide=false;
			}else{
				l--;
				onCorrectSide = (f<=l);//cannot last index cannot surpass first index
			}
		}
		if(f<l){
			swap(a,f,l);
			f++;
			l--;			
		}
	}while(f<=l);
	swap(a,pivot,l);
	return l;
	
	}

	public static void swap(int[] arr,int i, int other){
		int temp = arr[i];
		arr[i]=arr[other];
		arr[other]=temp;
	}
	
	
}
