package configuration;


public class FinalExpression implements IConfiguration{

	String expr;
	public FinalExpression(String expr)
	{
		this.expr = expr;
	}
	
	private String convert(char ch)
	{
		String str = "" + ch;
		return str;	
	}
	
	@Override
	public int interpret() {
		if (expr == null)
			return -1;
		for (char ch = 48; ch < 58; ++ch)
		{
			if (expr.equals(convert(ch)))
				{
					return ch;
				}		
		}
		
		for (char ch = 65; ch < 91; ++ch)
		{
			if (expr.equals(convert(ch)))
				{
					return ch;
				}
		}
		return -1;
	}

}

