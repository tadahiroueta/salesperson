import java.util.LinkedList;

public class Tour 
{
	private LinkedList<Point> tour;

	
	/** create an empty tour */
	public Tour()
	{
		tour = new LinkedList<Point>();
	}
	
	/** create a four-point tour, for debugging */
	public Tour(Point a, Point b, Point c, Point d)
	{
		tour = new LinkedList<Point>();
		tour.add(a);
		tour.add(b);
		tour.add(c);
		tour.add(d);
	}
	
	/** print tour (one point per line) to std output */
	public void show()
	{
		for (Point point : tour) System.out.println(point);
	}
	
	/** draw the tour using StdDraw */
	public void draw()
	{
		if (tour.size() <= 1) return; // no lines

		for (int i = 0; i < tour.size(); i++) {
			Point polled = tour.poll();
			polled.drawTo(tour.peek());
			tour.add(polled);
		}
	}
	
	/** return number of nodes in the tour */
	public int size()
	{
		return tour.size();
	}
	
	/** return the total distance "traveled", from start to all nodes and back to start */
	public double distance()
	{
		if (tour.size() <= 1) return 0.0; // no lines

		int sum = 0;
		for (int i = 0; i < tour.size(); i++) {
			Point polled = tour.poll();
			sum += polled.distanceTo(tour.peek());
			tour.add(polled);
		}
		return sum;
		
	}
	
	/** insert p using nearest neighbor heuristic */
    public void insertNearest(Point p) 
    {
		int nearestIndex = 0;
		double nearestDistance = Double.MAX_VALUE;

		for (int i = 0; i < tour.size(); i++) {
			Point polled = tour.poll();
			double distance = polled.distanceTo(p);
			if (distance < nearestDistance) {
				nearestDistance = distance;
				nearestIndex = i;
			}
			tour.add(polled);
		}
		tour.add(nearestIndex, p);
    }

	/** insert p using smallest increase heuristic */
    public void insertSmallest(Point p) 
    {
		if (tour.size() <= 2) { // no lines
			tour.add(p);
			return;
		}

		int smallestIndex = 0;
		double smallestIncrease = Double.MAX_VALUE;

		for (int i = 0; i < tour.size(); i++) {
			Point polled = tour.poll(), peeked = tour.peek();
			double distance = polled.distanceTo(p) + p.distanceTo(peeked) - polled.distanceTo(peeked);
			if (distance < smallestIncrease) {
				smallestIncrease = distance;
				smallestIndex = i + 1;
			}
			tour.add(polled);
		}
		tour.add(smallestIndex, p);
    }

	/** Insert p in away from origin */
	public void insertAway(Point p) {
		if (tour.size() == 0) {
			tour.add(p);
			return;
		}

		Point origin = new Point(0, 0);
		double awayDistance = p.distanceTo(origin);
		int awayIndex = 0;

		for (int i = 0; i < tour.size(); i++) {
			Point polled = tour.poll();
			if (awayDistance > polled.distanceTo(origin)) awayIndex = i + 1;
			tour.add(polled);
		}
		tour.add(awayIndex, p);
	}

}