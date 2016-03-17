package algo.helper;

public class Result 
{
	private int[] expected;
	private int[] actual;
	
	public Result(int[] expected, int[] actual)
	{
		this.expected = expected;
		this.actual = actual;
	}
	
	public int size()
	{
		return expected.length;
	}
	
	public float getAccuracy()
	{
		return 100*(float)getScore()/size();
	}
	
	public int getExpected(int index)
	{
		return expected[index];
	}
	
	public int getActual(int index)
	{
		return actual[index];
	}

	public int getScore() 
	{
		int score = 0;
		for( int i = 0; i < size(); i++ ) {
			if(  expected[i] == actual[i] )
				score++;
			else {
				addError(expected[i]);
			}
		}
		return score;
	}
	
	private void addError(int expected)
	{
		if(expected == 0)
			pollenA++;
		else if(expected == 1)
			pollenB++;
		else if(expected == 2)
			pollenC++;
		else if(expected == 3)
			pollenD++;
	}
	
	public void printErrors()
	{
		System.out.println(pollenA+"\t"+pollenB+"\t"+pollenC+"\t"+pollenD);
	}
	
	private int pollenA = 0, pollenB = 0, pollenC = 0, pollenD = 0;

}
