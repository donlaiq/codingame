package codinggame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


class Point {
	double x, y;
	
	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public boolean isTheSame(Point p)
	{
		if(p.x == this.x && p.y == this.y)
			return true;
		return false;
	}
	
	public String getDirection(Point p)
	{
		if(this.x == p.x && this.y < p.y)
			return "U";
		else if(this.x > p.x && this.y < p.y)
			return "UR";
		else if(this.x > p.x && this.y == p.y)
			return "R";
		else if(this.x > p.x && this.y > p.y)
			return "DR";
		else if(this.x == p.x && this.y > p.y)
			return "D";
		else if(this.x < p.x && this.y > p.y)
			return "DL";
		else if(this.x < p.x && this.y == p.y)
			return "L";
		return "UL";
	}
	
	public double getDistance(Point p)
	{
		return Math.sqrt((p.x-this.x)*(p.x-this.x) + (p.y-this.y)*(p.y-this.y));
	}
}

class StraightLine {
	double m;
	double b;
	Point middle;
	double mPerpendicular;
	double bPerpendicular;
	
	public StraightLine(Point p1, Point p2)
	{
		m = (p1.y-p2.y)/(p1.x-p2.x);
		b = m*p1.x*(-1) + p1.y;
		middle = new Point(p1.x + (p2.x-p1.x)/2, p1.y + (p2.y-p1.y)/2);
		mPerpendicular = (1/m)*(-1);
		bPerpendicular = middle.y - mPerpendicular*middle.x;
	}
	
	public boolean isPotentialSolution(String signal, String dir, Point p)
	{
		double x1 = 0;
		double y1 = bPerpendicular;
		double x2 = 1;
		double y2 = mPerpendicular + bPerpendicular;
		
		double d = (p.x-x1)*(y2-y1) - (p.y-y1)*(x2-x1);
		
		if(dir.equals("UR"))
		{
			if(signal.equals("COLDER") && d < 0)
				return true;
			else if(signal.equals("WARMER") && d >= 0)
				return true;
		}
		else if(dir.equals("DR")) //
		{
			if(signal.equals("COLDER") && d > 0)
				return true;
			else if(signal.equals("WARMER") && d <= 0)
				return true;
		}
		else if(dir.equals("DL"))
		{
			if(signal.equals("COLDER") && d > 0)
				return true;
			else if(signal.equals("WARMER") && d <= 0)
				return true;
		}
		else if(dir.equals("UL")) //
		{
			if(signal.equals("COLDER") && d < 0)
				return true;
			else if(signal.equals("WARMER") && d >= 0)
				return true;
		}
	
		return false;
	}
}



public class ShadowsOfTheKnight_Episode2_Final {
	private static List<Point> points;
	private static Point bomb;
	private static Point pos1, pos2, pos3;
	private static String bombDir;


	
	
	private static Point moreFarAway(Point current)
	{
		Point farAway = null;
		double distance = 0;
		for(Point p : points)
		{
			double auxDistance = current.getDistance(p);
			if(auxDistance > distance)
			{
				distance = auxDistance;
				farAway = p;
			}
		}
		return farAway;
	}
	
	private static void reduceSpace(Point current, Point previous)
	{		
		List<Point> newPoints = new ArrayList<Point>();
		
		String direction = current.getDirection(previous);
		if(direction.equals("U"))
		{
			if(bombDir.equals("COLDER"))
			{
				int y = (int)current.y + ((int)previous.y - (int)current.y)/2;
				
				for(Point p : points)
					if(p.y > y)
						newPoints.add(p);
				points = newPoints;
			}
			else if(bombDir.equals("WARMER"))
			{
				int y = (int)current.y + ((int)previous.y - (int)current.y)/2;
				for(Point p : points)
					if(p.y <= y)
						newPoints.add(p);
				points = newPoints;
			}
			else
			{
				for(Point p : points)
					if(previous.getDistance(p) == current.getDistance(p))
						newPoints.add(p);
				points = newPoints;
			}
		}
		else if(direction.equals("R")) 
		{
			if(bombDir.equals("COLDER"))
			{
				int x = (int)previous.x + ((int)current.x - (int)previous.x)/2;
				for(Point p : points)
					if(p.x < x)
						newPoints.add(p);
				points = newPoints;
			}
			else if(bombDir.equals("WARMER"))
			{
				int x = (int)previous.x + ((int)current.x - (int)previous.x)/2;
				for(Point p : points)
					if(p.x >= x)
						newPoints.add(p);
				points = newPoints;
			}
			else
			{
				for(Point p : points)
					if(previous.getDistance(p) == current.getDistance(p))
						newPoints.add(p);
				points = newPoints;
			}
		}
		else if(direction.equals("D")) 
		{
			if(bombDir.equals("COLDER"))
			{
				int y = (int)previous.y + ((int)current.y - (int)previous.y)/2;
				for(Point p : points)
					if(p.y < y)
						newPoints.add(p);
				points = newPoints;
			}
			else if(bombDir.equals("WARMER"))
			{
				int y = (int)previous.y + ((int)current.y - (int)previous.y)/2;
				for(Point p : points)
					if(p.y >= y)
						newPoints.add(p);
				points = newPoints;
			}
			else
			{
				for(Point p : points)
					if(previous.getDistance(p) == current.getDistance(p))
						newPoints.add(p);
				points = newPoints;
			}
		}
		else if(direction.equals("L")) 
		{
			if(bombDir.equals("COLDER"))
			{
				int x = (int)current.x + ((int)previous.x - (int)current.x)/2;
				for(Point p : points)
					if(p.x > x)
						newPoints.add(p);
				points = newPoints;
			}
			else if(bombDir.equals("WARMER"))
			{
				int x = (int)current.x + ((int)previous.x - (int)current.x)/2;
				for(Point p : points)
					if(p.x <= x)
						newPoints.add(p);
				points = newPoints;
			}
			else
			{
				for(Point p : points)
					if(previous.getDistance(p) == current.getDistance(p))
						newPoints.add(p);
				points = newPoints;
			}
		}
		else
		{
			StraightLine sl = new StraightLine(previous, current);
			if(!bombDir.equals("SAME"))
			{
				for(int i = 0; i < points.size(); i++)
				{
					if(sl.isPotentialSolution(bombDir, direction, points.get(i)))
						newPoints.add(points.get(i));
				}
				points = newPoints;
			}
			else
			{
				for(Point p : points)
					if(previous.getDistance(p) == current.getDistance(p))
						newPoints.add(p);
				points = newPoints;
			}
				
		}
	}
	
	
	private static void initPoints(int W, int H, Point batman)
	{
		for(int i = 0; i < W; i++)
		{
			for(int j = 0; j < H; j++)
			{
				Point p = new Point(Double.valueOf(i), Double.valueOf(j));
				if(!batman.isTheSame(p))
					points.add(p);
			}
		}
	}
	
	
	
	private static void printInfoToConsole(int cont)
	{
		if(!bombDir.equals("UNKNOWN"))
    	{
			if(pos2.x == bomb.x && pos2.y == bomb.y)
        		System.out.println("Success: you defused the bombs in time. The hostages are safe. You win :)");
        	System.out.println("Batman moved from window (" + (int)pos1.x + ", " + (int)pos1.y + ") to window (" + (int)pos2.x + ", " + (int)pos2.y + ")			 			STEP " + cont);
        	if(bombDir.equals("COLDER"))
        		System.out.println("Batman is further away from the bombs");
        	else if(bombDir.equals("WARMER"))
        		System.out.println("Batman is closer to the bombs");
        	else
        		System.out.println("Batman is at the same distance to the bombs");
        	System.out.println();
    	}
    	else
    	{
    		System.out.println("Locate the bombs, save the hostages!");
    		System.out.println("Batman is on window (" + (int)pos1.x + ", " + (int)pos1.y + ")");
    		System.out.println();
    	}
	}
	
	

	public static void main(String[] args) {
		
		/*int W = 5; 
        int H = 16; 
        int N = 80; 
        int X0 = 1;
        int Y0 = 5;
        bomb = new Point(4.0, 10.0);
        pos1 = new Point(1.0, 5.0);*/
		
		int W = 18; 
        int H = 32; 
        int N = 45; 
        int X0 = 17;
        int Y0 = 31;
        bomb = new Point(2.0, 1.0);
        pos1 = new Point(X0, Y0);
        
        /*int W = 1; 
        int H = 100; 
        int N = 12; 
        int X0 = 0;
        int Y0 = 98;
        bomb = new Point(0.0, 97.0);
        pos1 = new Point(X0, Y0);*/
        
        /*int W = 15; 
        int H = 15; 
        int N = 12; 
        int X0 = 3;
        int Y0 = 6;
        bomb = new Point(0.0, 1.0);
        pos1 = new Point(X0, Y0);*/
        
        /*int W = 24; 
        int H = 24; 
        int N = 15; 
        int X0 = 22;
        int Y0 = 13;
        bomb = new Point(19.0, 21.0);
        pos1 = new Point(X0, Y0);*/
        
		
		/*int W = 8000; 
        int H = 8000; 
        int N = 31; 
        int X0 = 3200;
        int Y0 = 3200;
        bomb = new Point(0.0, 1.0);
        pos1 = new Point(X0, Y0);*/
        //pos2 = new Point(0.0, 0.0);*/
        
        /*int W = 1000; 
        int H = 1000; 
        int N = 27; 
        int X0 = 501;   
        int Y0 = 501;
        bomb = new Point(490.0, 490.0);
        pos1 = new Point(X0, Y0);*/
		
		
		
		/*int W = 50; 
        int H = 50; 
        int N = 16; 
        int X0 = 17;
        int Y0 = 29;
        bomb = new Point(0.0, 1.0);
        pos1 = new Point(X0, Y0);*/
		
        
        
        points = new ArrayList<Point>();
        initPoints(W, H, pos1);
        
        
        pos2 = moreFarAway(pos1);
        
       
        points.remove(pos1);
        points.remove(pos2);
              
        
        int cont = 0;
        
        while(!pos1.isTheSame(bomb))
        {
        	if(pos3 == null)
        		bombDir = "UNKNOWN";
        	else if(pos2 != null)
        	{   		
        		double prevDistanceToBomb = pos1.getDistance(bomb);
            	double currentDistanceToBomb = pos2.getDistance(bomb);
        		
        		if(prevDistanceToBomb < currentDistanceToBomb)
        			bombDir = "COLDER";
        		else if(prevDistanceToBomb > currentDistanceToBomb)
        			bombDir = "WARMER";
        		else
        			bombDir = "SAME";
        	
        		reduceSpace(pos2, pos1);
        	}
        	
        	
        	printInfoToConsole(cont );
        	
        		
        	if(pos3 != null)
        	{
        		pos1 = pos2;
        		pos2 = pos3;
        	}
        	
        	if(points.size() > 0)
        	{
        		pos3 = moreFarAway(pos2);//new Point(W-1, H-1);
	        	points.remove(pos3);
        	}
        	
        	cont++;
        }
        
	}

}