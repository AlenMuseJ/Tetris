package configuration;

public class ExpressionClearer implements IConfiguration
{
	String expr;
	public ExpressionClearer(String expr, String instr)
	{
		expr = expr.replaceAll(instr+"=", "");
		expr = expr.replaceAll(";", "");
		expr = expr.replaceAll("VK_", "");
		expr = expr.replaceAll("\n", "");
		expr = expr.replaceAll("\t", "");
		expr = expr.replaceAll(" ", "");
		this.expr = expr;
	}
	
	public String getExpression() {
		return expr;
	}
	
	@Override
	public int interpret() 
	{
		FinalExpression fe = new FinalExpression(expr);
		return fe.interpret();
	}

}
