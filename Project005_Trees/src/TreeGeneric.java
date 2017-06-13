import java.util.ArrayList;

public class TreeGeneric<E extends Comparable<E>> {
		private Node<E> root;
		
		public TreeGeneric(){}
		
		public TreeGeneric(Node root){
			this.root = root;
		}	
		
		public Node<E> getRoot(){		
			return root;
		}	
		
		public void setRoot(Node<E> root){
			this.root=root;
		}	
			
		public Node<E> makeTree(Node<E>[]nodeArr,int LI, int HI){
			if(HI<LI){return null;}
			int midI = (LI+HI)/2;
			E mid = nodeArr[midI].getData();
			
			Node<E> root = new Node();//you are recreating your nodeList but these new nodes do not have 
		
			//here lets set all the data needed to be in current root (node)
			root.setData(mid);	
			
			root.setLeft(makeTree(nodeArr,LI,midI-1));
			root.setRight(makeTree(nodeArr,midI+1,HI));
			return root;
			
		}	

		public Node<E> find(Node<E> root, E person){
			if(root==null){
				return null;
			}
			if(root.getData().compareTo(person)==0){	
				return root;
			}			
			if(root.getData().compareTo(person)==1){
				return find(root.getLeft(),person);
			}else{
				return find(root.getRight(),person);
			}
		}
		
		

}


