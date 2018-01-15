package configuration;

public class Context implements IConfiguration{
	String start_instructions0 = "rotate=VK_UP;";
	String start_instructions1 = "left=VK_LEFT;";
	String start_instructions2 = "right=VK_RIGHT;";
	String start_instructions3 = "down=VK_DOWN;";
	String rotate = "rotate";
	String left = "left";
	String right = "right";
	String down = "down";
	
	String expr;
	public Context()
	{
	}
	
	public void setExpr(String expr)
	{
		this.expr = expr;
	}
	
	public int interpret() {
		if (expr == null)
			return -1;
		// TODO Auto-generated method stub
		if (expr.equals(start_instructions0) || expr.equals(start_instructions1) 
				|| expr.equals(start_instructions2) || expr.equals(start_instructions3))
			return 0;
		else
		{

			if (expr.contains(rotate))
			{
				ExpressionClearer re = new ExpressionClearer(expr, rotate);
				return re.interpret();				
			}
			else if (expr.contains(left))
			{
				ExpressionClearer re = new ExpressionClearer(expr, left);
				return re.interpret();	
			}
			else if (expr.contains(right))
			{
				ExpressionClearer re = new ExpressionClearer(expr, right);
				return re.interpret();	
			}
			else if (expr.contains(down)) 
			{
				ExpressionClearer re = new ExpressionClearer(expr, down);
				return re.interpret();	
			}
			else return -1;
		}
	}	
}