    public class Node {

        // je mettrais les var en prive et je ferais les getter/setter ulterieurement
        public int[][] statue; // statue du taquin à une certain etape
        public int cost; // Combien de tour j'ai joue
        public Node parent; 
        
        public Node(int[][] statue, int cost, Node parent) {
            this.statue = statue;
            this.cost = cost;
            this.parent = parent;
        }
    

 


    }