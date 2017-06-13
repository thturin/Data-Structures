
public class Recursion {

	public static void main(String[] args) {
		int n = 133;
		String x=Integer.toBinaryString(n);
		System.out.println(x);	

		System.out.println(binaryRepresentation(x,0,x.length()-1));
		
//		String s = "ata";
//		System.out.println(checkPal(s,0,s.length()-1));
//		
		
	}
	
	public static int max(int []a, int start, int end){
		if(end==start){//base case 
			return a[start];
		}
		int mid =(start+end)/2;
		int max1=max(a,start,mid);
		int max2=max(a,mid+1,end);
		if(max1>max2){
			return max1;
		}else{
			return max2;
		}		
	}
	
	public static int binaryRepresentation(String s,int low,int high){
		if(low==high){
			if(s.charAt(low)=='0'){return 1;}
			else{return 0;}
		}else{
		int mid = (low+high)/2;
		return binaryRepresentation(s,low,mid)+binaryRepresentation(s,mid+1,high);		
		}			
	}
	
	public static boolean checkPal(String s, int LI, int HI){
		boolean flag=true;
		if(LI>=HI){
			return flag;
		}if(s.charAt(LI)!=(s.charAt(HI))){
			System.out.println("Not a palindrome");
			return flag=false;
		}else{
			return checkPal(s,LI+1,HI-1);
		}
		
		
	}
	
	

}
