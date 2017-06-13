import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Program {
	public static void main(String[] args) {
		ArrayList<Person> personList = new <Person>ArrayList();
		String inFile = args[0];
		String outFile = args[1];
		
		//String f = "src/communityFileName.txt";
		//String queryF = "src/test.txt";
		inFile(inFile,personList);

		//if ssn numbers are not in order, quick sort personList
		QuickSort sort = new QuickSort();
		Person[] pArr = sort.ListToArr(personList);		
		pArr = sort.quickSort(pArr, 0, pArr.length-1);
		//now, you have an array of people that are sorted by their ssn number		
		pArr=addFriendTreeToPerson(pArr);	
		Node<Person>[] nodeArr=peopleToNodes(pArr);	//convert this people array into an array of nodes with data of ssn 
		
		TreeGeneric<Person> bst = new TreeGeneric<Person>();
		Node<Person> root = bst.makeTree(nodeArr, 0, nodeArr.length-1);
		bst.setRoot(root);
			
		queryFile(outFile, bst);	
}
	
	public static void queryFile(String f, TreeGeneric<Person> bst){
		BufferedReader br = null;
		String line = ""; 
		try{
			br = new BufferedReader(new FileReader(f));
			while((line=br.readLine())!=null){	
				if(line.length()>0){ //only reading the space that has text ie skipping line brekas
					line = line.replaceAll("\\s+$","");//strip all white space to the right of string
					if(line.startsWith("NAME-OF ")){ 
						String x = line.substring(8); //store ssn to be searched for
						//System.out.println(x);
						String name = nameOf(x,bst);//find the name of x
						System.out.println(line+": "+name);//print out this line + the
						if(name==null){System.out.println("no name");}
					}
					if(line.startsWith("MOTHER-OF ")){
						String x = line.substring(10);
						String momName = motherOf(x,bst);
						System.out.println(line+": "+momName);
						if(momName==null){System.out.println("no mom name");}
					}
					if(line.startsWith("FATHER-OF ")){
						String x = line.substring(10);
						String dadName = fatherOf(x,bst);
						System.out.println(line+": "+dadName);	
						if(dadName==null){System.out.println("no dad name");}
					}
					if(line.startsWith("HALF-SIBLINGS-OF ")){
						String x = line.substring(17);
						ArrayList<String> halfSiblings = halfSiblingsOf(x,bst);
						System.out.print(line+": ");
						if(halfSiblings!=null){
							for(String s: halfSiblings){System.out.print(s+",");}
						
						}else{
							System.out.print("no half siblings");
							}
						System.out.println(" ");
					
					}
					if(line.startsWith("FULL-SIBLINGS-OF ")){
						String x = line.substring(17);
						ArrayList<String> fullSiblings = fullSiblingsOf(x,bst);
						System.out.print(line+": ");
						if(!fullSiblings.isEmpty()){
							for(String s: fullSiblings){System.out.print(s+",");}
					
						}else{
							System.out.print("no full siblings");
							
						}
						System.out.println(" ");
					
					}
					if(line.startsWith("CHILDREN-OF ")){
						String x = line.substring(12);
						ArrayList<String> children = null;
						children=childrenOf(x,bst);
						System.out.print(line+": ");
						if(children.isEmpty()){
							System.out.println("no children");
						}else{
							for(String s: children){System.out.print(s+",");}
						}
						System.out.println(" ");
					
					}
					if(line.startsWith("MUTUAL-FRIENDS-OF ")){
						String x = line.substring(18);
						ArrayList<String> mutualFriends = mutualFriendsOf(x,bst);
						System.out.print(line+": ");
						if(!mutualFriends.isEmpty()){
							for(String s: mutualFriends){System.out.print(s+", ");}
						}else{
							System.out.println("no mutual friends");
						}
						System.out.println(" ");
					}
					if(line.startsWith("INVERSE-FRIENDS-OF ")){
						String x = line.substring(19);
						ArrayList<String>inverseFriends = inverseFriendsOf(x,bst);
						System.out.print(line+": ");
						if(inverseFriends==null){System.out.println("no inverse friends");}
						for(String s: inverseFriends){
							System.out.print(s+", ");
							
						}
						System.out.println(" ");
					}									
					if(line.startsWith("WHO-HAS-MOST-MUTUAL-FRIENDS")){
						String name = null;				
						ArrayList<String> maxMutualFriends = maxMutualFriends(bst);		
						System.out.print(line+": "+maxMutualFriends.get(maxMutualFriends.size()-1)+" total mutual friends\n");
						System.out.println("   ssn: "+maxMutualFriends.get(0)+"   name: "+maxMutualFriends.get(1));
					}		
				}
			}
		}catch(IOException e){
			System.out.println("There was an eror");
			e.printStackTrace();
		}		
	}
	
	public static void inFile (String f, ArrayList<Person> personList){
		BufferedReader br = null;
		String line=""; String splitBy= ":";
		ArrayList<String> first = new ArrayList<String>();
		ArrayList<String> last = new ArrayList<String>();
		ArrayList<String> ssn = new ArrayList<String>();
		ArrayList<String> father = new ArrayList<String>();
		ArrayList<String> mother = new ArrayList<String>();
		ArrayList<ArrayList<String>> friends = new ArrayList<ArrayList<String>>();
		
		try{
			br = new BufferedReader(new FileReader(f)); //read string to find file and put into buffer	
			while((line=br.readLine())!=null) {
				if(line.length()>0){ //avoid exiting out of while loop with break lines in between data
					line = line.replaceAll("\\s", "");//strip current line of all spaces				
					String[] temp = line.split(splitBy);//ex: [FIRST NAME,John]
					if(line.startsWith("FIRST")){
						first.add(temp[1]); // first[0]="John"
					}else if(line.startsWith("LAST")){		
						last.add(temp[1]); //last[0]="Smith"
					}else if(line.startsWith("SSN")){
						ssn.add(temp[1]); //ssn[0]="1"
					}else if(line.startsWith("FATHER")){
						father.add(temp[1]); //father[0]="4"
					}else if(line.startsWith("MOTHER")){
						mother.add(temp[1]);//mother[0]="5"
					}else if(line.startsWith("FRIENDS")){
						String friendLine = temp[1];     //System.out.println(friendLine);				
						String[] friendArr = friendLine.split(",");   
						ArrayList<String> friendList = new ArrayList<String>(Arrays.asList(friendArr)); 
						friends.add(friendList);
					}else{
						br.readLine();//skip line	
						} 					
				}				
			}
		}catch(IOException e){
			System.out.println("Cannot find file");
			e.printStackTrace();
		}
				
		//create people objects from information (first, last, ssn, and friend list) h
		for(int i=0; i<first.size();i++){
			String firstName=first.get(i);
			String lastName=last.get(i);
			int SSN = Integer.parseInt(ssn.get(i));	
			ArrayList<String> list = friends.get(i);
			ArrayList<Integer>friendsNum= new ArrayList<Integer>();
			for(String s: list){
				int temp = Integer.parseInt(s);
				friendsNum.add(temp);
			}		
			Person p = new Person(SSN, firstName, lastName, friendsNum);
			personList.add(p);
		}
		
		//now search to find the matching mother and father for each person 			
		for(int i=0;i<personList.size();i++){
			Person p = personList.get(i);
			int fatherNum = Integer.parseInt(father.get(i)); 
			int motherNum = Integer.parseInt(mother.get(i));
			int search = 0; int j=0;
			while(search!=2 && j<personList.size()){//loop through each person to compare the mother and father list 
				Person pp = personList.get(j);
				if(pp.getSsn()==fatherNum){ 
					p.setdad(pp);
					search++;			
				}else if(pp.getSsn()==motherNum){
					p.setmom(pp);
					search++;
				}
				j++;
			}
		}
		
		//search for the people that do not have a mother or father address and create an new mother, father person with  serial number
		//these mothers and fathers do not have a name, only a SSN
		for(int i=0;i<personList.size();i++){
			Person p = personList.get(i); 
			int fatherNum = Integer.parseInt(father.get(i)); 
			int motherNum = Integer.parseInt(mother.get(i));
			if (p.getmom()==null){ 
				String noFirst = "no first";
				String noLast = "no last";
				Person mom  = new Person(motherNum, noFirst, noLast);
				p.setmom(mom);
			}
			if(p.getdad()==null){
				String noFirst = "no first";
				String noLast = "no last";
				Person dad = new Person(fatherNum, noFirst, noLast);
				p.setdad(dad);
			}
		}		
	}
	
	public static Node<Person>[] peopleToNodes(Person[]pArr){		
		Node<Person> nodeArr[]= new Node[pArr.length];//create an empty node list of peopl
		for(int i=0;i<pArr.length;i++){
			Person p = pArr[i];
			Node <Person> n = new Node<Person>();
			n.setData(p);
			nodeArr[i]=n;
		}		
		return nodeArr;
	}
			
	public static Person[] addFriendTreeToPerson(Person[]pArr){
		
		InsertionSort sort = new InsertionSort();

		Node<Person>[] friendNodes =null;
		for(int i=0;i<pArr.length;i++){
			Person p = pArr[i];
			
			friendNodes = new Node[p.getFriends().size()];
			int count=0;
			ArrayList<Integer> friends = p.getFriends();
			int[] friendsArr =new int[friends.size()];
			for(int z=0;z<friends.size();z++){
				friendsArr[z]=friends.get(z);
				}
			
			friendsArr=sort.insertionSort(friendsArr);

			for(int k=0;k<friendsArr.length;k++){
				for(int j=0;j<pArr.length;j++){				
					if(pArr[j].getSsn()==friendsArr[k]){//if friend # equals the ssn of someone in the list			
						Node<Person> n = new Node<Person>();
						n.setData(pArr[j]);
						friendNodes[count]=n;
						count++;						
					}
				}
			}

			//make tree for each person
			TreeGeneric<Person> tree = new TreeGeneric<Person>();
			Node<Person>root = tree.makeTree(friendNodes, 0, friendNodes.length-1);
			p.setFriendTree(tree);
			p.getFriendTree().setRoot(root);
		
		
		}
	return pArr;
	}
	
	//NAME 
	public static String nameOf(String x,TreeGeneric<Person> bst){
		int ssn = Integer.parseInt(x);
		Person p = new Person(ssn);
		Node<Person> root = bst.getRoot();
		Node<Person> n = bst.find(root,p);//use key to find matching ssn in bst 
		//System.out.println(n.getData().getSsn());
		if(n!=null){//if there is a match
			//print out return the name
			String name = n.getData().getFirst()+" "+n.getData().getLast();
			return name;
		}else{return null;}
	}
	
	//MOTHER OF
	public static String motherOf(String x,TreeGeneric<Person> bst){
		int ssn = Integer.parseInt(x);
		Person p= new Person(ssn);//create a temporary person to find
		Node<Person> root = bst.getRoot();
		Node<Person> n = bst.find(root,p);
		if(n!=null){
			String name = n.getData().getmom().getFirst()+" "+n.getData().getmom().getLast();
			return name;
		}else{
			return null;
		}			
	}
	
	//FATHER OF
	public static String fatherOf(String x, TreeGeneric<Person> bst){
		int ssn = Integer.parseInt(x);
		Person p = new Person(ssn);
		Node<Person> root = bst.getRoot();
		Node<Person> n = bst.find(root,p);
		if(n!=null){
			String name = n.getData().getdad().getFirst()+" "+n.getData().getdad().getLast();
			return name;
		}else{
			return null;
		}
	}
	
	///HALF SIBLINGS
	public static void findHalfSiblings(Node<Person> root, int momId, int dadId, ArrayList<String>halfSiblings){
		if(root==null){
			return;
		}
		//scenario if root only shares mom
		if(root.getData().getmom().getSsn()==momId && root.getData().getdad().getSsn()!=dadId){
			String name = root.getData().getFirst()+" "+root.getData().getLast();
			halfSiblings.add(name);		
		}
		//scenario if root only shares father
		if (root.getData().getmom().getSsn()!=momId && root.getData().getdad().getSsn()==dadId){
			String name = root.getData().getFirst()+" "+root.getData().getLast();
			halfSiblings.add(name);		
		}
		findHalfSiblings(root.getLeft(),momId, dadId, halfSiblings);
		findHalfSiblings(root.getRight(),momId,dadId,halfSiblings);
	}
			
	public static ArrayList<String> halfSiblingsOf(String x, TreeGeneric<Person> bst){
		int ssn = Integer.parseInt(x);
		ArrayList<String> halfSiblingsList = new ArrayList<String>();
		Person temp = new Person(ssn);
		Node<Person> root = bst.getRoot();
		Node<Person> n = bst.find(root,temp);
		int momId = n.getData().getmom().getSsn();//store the ssn of current node's mom id 
		int dadId = n.getData().getdad().getSsn();//store the ssn of current nodes' dad id
		if(n!=null){
			//we want to search through bst and find the node's that have a person with one common parent, not two
			findHalfSiblings( (Node<Person>)root,momId,dadId,halfSiblingsList);
			if(!halfSiblingsList.isEmpty()){
				return halfSiblingsList; //return the list of names of half siblings	
			}			
		}else{return null;}	
		return null;
	}
	
	public static void findFullSiblings(Node<Person> root,int momId, int dadId, ArrayList<String>siblings){	
		//post order traversal of tree	
		if(root==null){//base case
			return;
			}
		//scenario for full siblings
		if(root.getData().getmom().getSsn()==momId &&root.getData().getdad().getSsn()==dadId){
			String name = root.getData().getFirst()+" "+root.getData().getLast();			
			siblings.add(name);
		}	
		findFullSiblings(root.getLeft(),momId, dadId,siblings);
		findFullSiblings(root.getRight(),momId,dadId,siblings);			
	}
	
	public static ArrayList<String> fullSiblingsOf(String x, TreeGeneric<Person> bst){
		int ssn = Integer.parseInt(x);
		
		ArrayList<String> fullSiblingsList = new ArrayList<String>();
		Person temp = new Person(ssn);
		Node<Person> root = bst.getRoot();
		Node<Person> n = bst.find(root,temp);
		int momId  = n.getData().getmom().getSsn();
		int dadId = n.getData().getdad().getSsn();
		if(n!=null){
			findFullSiblings((Node<Person>)root,momId,dadId,fullSiblingsList);		
			//loop through to remove the actual person from their own sibling list 						
			if(!fullSiblingsList.isEmpty()){	
				int saveI=0;
				for(int i=0; i<fullSiblingsList.size();i++){					
					String originalPerson = n.getData().getFirst()+" "+n.getData().getLast();
					if(fullSiblingsList.get(i).equals(originalPerson)){
						saveI=i;						
					}
				}				
				fullSiblingsList.remove(fullSiblingsList.get(saveI));
				return fullSiblingsList;					
			}else{
				return null;
				}			
		}
		return null;
	}

	
	//FIND CHILDREN
	public static void findChildren(Node<Person> root, int key, ArrayList<String>children){
		if(root==null){return;}
		//search through each node to find one that holds a person with the mom or dad with the same ssn as the key
		if(root.getData().getdad().getSsn()==key||root.getData().getmom().getSsn()==key){
			String name = root.getData().getFirst()+" "+root.getData().getLast();
			children.add(name);
		}
		findChildren(root.getLeft(),key,children);
		findChildren(root.getRight(),key,children);
	}
	
	public static ArrayList<String> childrenOf(String x, TreeGeneric<Person>bst){
		int ssn = Integer.parseInt(x);
		Person temp = new Person(ssn);//search for this temp node with the same ssn number as the node to be found in tree
		Node<Person> root = bst.getRoot();
		Node<Person> n = bst.find(root,temp);
		ArrayList<String> childrenList = new ArrayList<String>();
		if(n!=null){
			findChildren((Node<Person>)root,ssn,childrenList);
			return childrenList;
		}else{return null;}
	}
			
	//MUTUAL FRIENDS 
	public static ArrayList<String> mutualFriendsOf(String x,TreeGeneric<Person>bst){
		int ssn = Integer.parseInt(x);
		Person temp = new Person(ssn);		
		Node<Person> root = bst.getRoot();

		Node<Person> n = bst.find((Node<Person>)root,temp);
	
		if(n!=null){
			ArrayList<String> mutualFriends = new ArrayList<String>();
			TreeGeneric<Person> friendTree = n.getData().getFriendTree();	
			Node<Person> friendTreeRoot = friendTree.getRoot();//setup to traverse through n's friend tree 
			traverseFriendTree(friendTreeRoot,mutualFriends,n);
			return mutualFriends;
		}else{return null;}
	}
	
	public static void traverseFriendTree(Node<Person> friendTreeRoot,ArrayList<String>mutualFriends, Node<Person>main){
		if(friendTreeRoot==null){return;}
		Person findP = new Person(main.getData().getSsn());

		//at current node, check it's friend tree to see if it matches 'main' node
		
		//we have labeled the current node of a friend in main's tree of friends 
		TreeGeneric<Person> friendsFriendTree = friendTreeRoot.getData().getFriendTree();	
	
		Node<Person> x = friendsFriendTree.getRoot();
		Node<Person> mutualFriend = friendsFriendTree.find(x,findP);
	
		
		if(mutualFriend!=null && !friendTreeRoot.getData().getFirst().equals(main.getData().getFirst())){//if we can find the main node in the friend of friend's tree	
			String name = friendTreeRoot.getData().getFirst()+" "+friendTreeRoot.getData().getLast();
			mutualFriends.add(name);
			
		}	
		traverseFriendTree(friendTreeRoot.getLeft(),mutualFriends,main);
		traverseFriendTree(friendTreeRoot.getRight(),mutualFriends,main);
	}
						
	//INVERSE FRIENDS
	public static void traverseTree(Node<Person>root,ArrayList<String>inverseFriendList, Node<Person>main, TreeGeneric<Person> bst){
		if(root==null){return;}
		Person findP= new Person(main.getData().getSsn());
		Node<Person> friendTreeRoot = root.getData().getFriendTree().getRoot();
		Node<Person> currPerson = bst.find(friendTreeRoot,findP);//find main node in current root's friend tree list
		if(currPerson!=null){
			//we found a match!
			String name = root.getData().getFirst()+" "+root.getData().getLast();
			inverseFriendList.add(name);
		}			
		traverseTree(root.getLeft(),inverseFriendList,main,bst);
		traverseTree(root.getRight(),inverseFriendList,main,bst);		
	}
	
	public static ArrayList<String> inverseFriendsOf(String x,TreeGeneric<Person>bst){
		int ssn = Integer.parseInt(x);
		Person temp = new Person(ssn);
		Node<Person> root=bst.getRoot();
		Node<Person> n = bst.find(root,temp);
		ArrayList<String>inverseFriendList = new ArrayList<String>();
		if(n!=null){
			traverseTree((Node<Person>)root,inverseFriendList,n,bst);			
			return inverseFriendList;
		}else{return null;}		
	}
		
	//MUTUAL FRIENDS MAX
	public static ArrayList<String> maxMutualFriends(TreeGeneric<Person>bst){
		ArrayList<ArrayList<String>>allMutualFriendsList = new ArrayList<ArrayList<String>>();	
		Node<Person> root = bst.getRoot();		
		traverseTreeMax(root,allMutualFriendsList,bst);
		ArrayList<String> finalMaxPerson = new ArrayList<String>();	
		String name = null; int max=0; int size =0; String ssn=null;
		for(int i=0;i<allMutualFriendsList.size();i++){
			size=0;
			size = allMutualFriendsList.get(i).size()-2;
			if(size>max){
				max=size;	
				ssn = allMutualFriendsList.get(i).get(allMutualFriendsList.get(i).size()-2);
				name = allMutualFriendsList.get(i).get(allMutualFriendsList.get(i).size()-1); //name is last string in arraylist					
			}
		}
		

		String temp = Integer.toString(max);
		finalMaxPerson.add(temp);
		finalMaxPerson.add(name);
		finalMaxPerson.add(ssn);
			
		//if there are duplicates
		int dupSize=0;
		for(int j=0;j<allMutualFriendsList.size();j++){
			dupSize=0;
			dupSize = allMutualFriendsList.get(j).size()-2;
			if(dupSize==max){		
				if(!allMutualFriendsList.get(j).get(dupSize).equals(finalMaxPerson.get(finalMaxPerson.size()-1))){	
					finalMaxPerson.add(allMutualFriendsList.get(j).get(dupSize+1));					
					finalMaxPerson.add(allMutualFriendsList.get(j).get(dupSize));
				}
			}		
		}
		
		int chooseSSN = 1000; String finalName = null;
		ArrayList<String> finalList = new ArrayList<String>();
		for(int f=1; f<finalMaxPerson.size();f++){	
			if(f%2==0){//if even index, ssn numbers
				int u = Integer.parseInt(finalMaxPerson.get(f));
				if (u<chooseSSN){//if ssn is less than original ssn of 1000
					chooseSSN = Integer.parseInt(finalMaxPerson.get(f));
					finalName = finalMaxPerson.get(f-1);
				}			
			}
		}
		finalList.add(Integer.toString(chooseSSN));finalList.add(finalName); finalList.add(temp);
		return finalList;			
	}
	
	public static void traverseTreeMax(Node<Person>root,ArrayList<ArrayList<String>> listOfLists,TreeGeneric<Person> bst){
		if(root==null){return;}	
		ArrayList<String> maxFriendsList = new ArrayList<String>();	
		String ssn  = Integer.toString(root.getData().getSsn());			
		maxFriendsList = mutualFriendsOf(ssn,bst);
		maxFriendsList.add(ssn);//add the ssn 
		maxFriendsList.add(root.getData().getFirst()+" "+root.getData().getLast());	//add the actual persons name

		listOfLists.add(maxFriendsList);	

		traverseTreeMax(root.getLeft(),listOfLists,bst);
		traverseTreeMax(root.getRight(),listOfLists,bst);
	}
	





}
