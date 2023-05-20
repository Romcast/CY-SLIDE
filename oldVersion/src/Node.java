import java.util.ArrayList;
import java.util.*;
    
    public class Node {

        // je mettrais les var en prive et je ferais les getter/setter ulterieurement
        private Grid statue; // statue du taquin à une certain etape
        private int cost; // Combien de tour j'ai joue
        private Node parent; 
        
        public Node(Grid statue, int cost, Node parent) {
            this.statue = statue;
            this.cost = cost;
            this.parent = parent;
        }
    
public Grid getStatue(){return this.statue;}
public int getCost(){return this.cost;}
public Node getParent(){return this.parent;}

     public void print() 
	{
        List<Node> path = new ArrayList<>();
        Node current = this;
        while (current != null) 
		{
            path.add(current);
            current = current.parent;
        }
        
        Collections.reverse(path);
        
        for (Node n : path) 
		{
            n.statue.print();
            System.out.println();
        }
    }

        public int[] findpositionEmpty() 
	{
        return(this.statue.findpositionEmpty());

    }

    public boolean isSolved(Grid goal) 
	{
        return Arrays.deepEquals(this.statue.getGrid(), goal.getGrid());
    }

    }