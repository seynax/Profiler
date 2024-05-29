package fr.seynax.profiler;

public class Main
{
	public static void main(String[] argsIn)
	{
		var test = new Test("coucou");
		System.out.println(test.coucou);
	}

	public final static class Test
	{
		private String coucou;

		public Test(String coucou)
		{
			this.coucou = coucou;
		}

		public String coucou()
		{
			return coucou;
		}

		public void coucou(String coucouIn)
		{
			this.coucou = coucouIn;
		}

		public boolean hasCoucou()
		{
			return coucou != null;
		}
	}
}