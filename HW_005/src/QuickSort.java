import java.util.ArrayList;

public class QuickSort {
	
	public QuickSort(){}
	
	public Person[] ListToArr(ArrayList<Person> list){
		Person[] arr = new Person[list.size()];		
		arr = list.toArray(arr);		
		
//		System.out.println("converted array of people");
//		for(Person p: arr){
//			System.out.print("  "+p.getSsn());
//		}
//		System.out.println("\n\n");
		return arr;
	}
	
	public Person[] quickSort(Person[]arr, int LI, int HI){
		if(LI<HI){
			int piv = split(arr,LI,HI);
			if(LI<piv-1){
				quickSort(arr,LI,piv-1);
			}
			if(HI>piv+1){
				quickSort(arr,piv+1,HI);
			}
		}
		return arr;
	}
	
	public int split(Person[] arr, int LI, int HI){
		int movL = LI;
		int movH = HI;
		Person piv = arr[movL];
		movL++; //ignore the first index (pivot)
		while(movL<=movH){
			while(arr[movL].getSsn()<piv.getSsn() && movL<=HI){//while high value is less than the pivot and moving left index is less than the last index
				movL++; //increment the left side index
			}
			while(arr[movH].getSsn()>piv.getSsn() && movH>=LI){
				movH--;
			}	
		
		//if dont cross, swap the moving i
			if(movL<movH){
				Person temp = arr[movL];
				arr[movL]=arr[movH];
				arr[movH]=temp;
			}
		}
		
		if(movH>LI){
			Person temp = arr[movH];
			arr[movH]=piv;
			arr[LI]=temp;
		}
	return movH;	
	}
	
	
	
	
}
