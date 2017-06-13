import java.util.ArrayList;

public class Arrays {

	public static void main(String[] args) {
		String s1="lima";
		String s2="am  il ";
		System.out.println(compareStrings(s1,s2));
		
		
		String a = "the little monkey said";
		String b = "oh wow that great";
		String c = "hm";
		String d = "meow";
		String e = "okay that is cool i dont care";
		String f = "pigs and babies pigs and babies pigs and babies";
		String g = "fffffffffffffffffffffffffffffffffffffffffffffffffffff";
		String h = "f";
		String i = "woah htat is soooooo cool";

		ArrayList<String> sList = new ArrayList<String>();
		sList.add(a);sList.add(b);sList.add(c);sList.add(d);sList.add(e);sList.add(f);sList.add(g);sList.add(h);sList.add(i);
		sList=compareThree(sList);
		for(String s:sList){
			System.out.println(s);
		}
		
		
		
	}
	
	
	public static int compareStrings(String s1,String s2){
		s1=s1.replaceAll("\\s", ""); s2=s2.replaceAll("\\s", "");
		s1=s1.toUpperCase(); s2=s2.toUpperCase();		
		Boolean match=true;
		for(int i=0,k=s2.length()-1; i<s1.length(); i++,k--){
			if( !( s1.charAt(i)==s2.charAt(k) ) ){
				match=false;
				System.out.println("Not a match");
				return 0;
			}			
		}
		if(match==true){
			System.out.println("It's a match");
			return 1;
		}
		return -1;			
	}
	
	public static ArrayList <String> compareThree(ArrayList<String>sList){
		ArrayList<String> sListNew = new ArrayList<String>();
		for(int i=2;i<sList.size();i+=3){
				if(sList.get(i).length()<sList.get(i-1).length()){ //s0 < s1
					if(sList.get(i).length()<sList.get(i-2).length()){//s0 < s2
						sListNew.add(sList.get(i)); //s0 is the smallest
						}					
				}else if(sList.get(i-1).length()<sList.get(i-2).length()){//else s1<s0 and <s2
					sListNew.add(sList.get(i-1)); //s1 is the smallest			
				}else{//else s2<s0 and <s1
					sListNew.add(sList.get(i-2));		//s2 is the smallest		}
				}
		}
		return sListNew;	
	}
}

