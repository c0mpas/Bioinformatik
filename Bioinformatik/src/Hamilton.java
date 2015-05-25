
public class Hamilton {

	private int[][] matrix;
	
	public Hamilton(int[][] matrix){
		this.matrix = matrix;
	}
	
	public int[] compute() throws Exception{
		return hamCycle(matrix);
	}
	
	private boolean isSafe(int v, int[][] graph, int[] path, int pos)
	{
	    if (graph [path[pos-1]][v] == 0)
	        return false;
	   for (int i = 0; i < pos; i++)
	        if (path[i] == v)
	            return false;
	    return true;
	}
	 
	/* solve hamiltonian cycle problem */
	private boolean hamCycleUtil(int[][] graph, int[] path, int pos)
	{
		if(graph.length == pos){
	        if (graph[ path[pos-1] ][ path[0] ] == 1)
	            return true;
	        else
	            return false;
		}
	    for (int v = 1; v < graph.length; v++)
	    {
	        if (isSafe(v, graph, path, pos))
	        {
	            path[pos] = v;
	            if (hamCycleUtil (graph, path, pos+1) == true)
	                return true;
	            path[pos] = -1;
	        }
	    }
	    return false;
	}
	 
	/* solves the Hamiltonian Cycle problem using Backtracking.*/
	private int[] hamCycle(int graph[][]) throws Exception
	{
	    int[] path = new int[graph.length];
	    for (int i = 0; i < graph.length; i++)
	        path[i] = -1;
	    path[0] = 0;
	    if (!hamCycleUtil(graph, path, 1))
	    {
	        throw new Exception("No hamilton path exists");
	    }
	    return path;
	}
	 
	
}
