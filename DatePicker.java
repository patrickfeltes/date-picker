import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DatePicker extends JPanel implements MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;
	
	// fields that store the selected date
	private int month, day, year;
	
	// dimensions
	private int boxDimension = 30;
	private int rows = 5;
	private int cols = 7;
	private int bannerHeight = 30;
	
	private final int width = cols * boxDimension;
	private final int height = rows * boxDimension + bannerHeight;
	
	private int mouseX, mouseY;
	private int mouseCoorX, mouseCoorY;
	
	// default DatePicker constructor, sets selected day to current day
	public DatePicker() {
		setToCurrentDate();
	
		initialize();
	}
	
	// sets selected date to the date in parameters, but if that is invalid, set to current date
	public DatePicker(int month, int day, int year) {
		// if dates are invalid, set to current date
		if(month > 11) {
			setToCurrentDate();
		} else if(day > new GregorianCalendar(month, 1, year).getActualMaximum(Calendar.DAY_OF_MONTH)) {
			setToCurrentDate();
		} else {
			this.month = month;
			this.day = day;
			this.year = year;
		}
		initialize();
	}
	
	public void initialize() {
		System.out.println(width + " " + height);
		setPreferredSize(new Dimension(width + 1, height + 1));
		setMaximumSize(new Dimension(width + 1, height + 1));
		setMinimumSize(new Dimension(width + 1, height + 1));
		addMouseMotionListener(this);
		addMouseListener(this);
		setFocusable(true);
	}
	
	public void setToCurrentDate() {
		this.month = Calendar.getInstance().get(Calendar.MONTH);
		this.day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		this.year = Calendar.getInstance().get(Calendar.YEAR);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		
		// draw the grid
		for(int horizontalLines = 0; horizontalLines <= rows; horizontalLines++) {
			g.drawLine(0, bannerHeight + boxDimension * horizontalLines, width, bannerHeight + boxDimension * horizontalLines);
		}
		for(int verticalLines = 0; verticalLines <= cols; verticalLines++) {
			g.drawLine(verticalLines * boxDimension, bannerHeight, verticalLines * boxDimension, height);
		}
		
		// draw on the hovered place
		if(mouseCoorX >= 0 && mouseCoorX < cols && mouseCoorY >= 0 && mouseCoorY < rows) {
			g.setColor(new Color(0.1f, 0.5f, 0.5f, 0.5f));
			g.fillRect(mouseCoorX * boxDimension + 1, mouseCoorY * boxDimension + 1 + bannerHeight, boxDimension, boxDimension);
		}
		
	}

	// fill fields with mouse position
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		mouseCoorX = mouseX / boxDimension;
		if(mouseY < bannerHeight) mouseCoorY = -1;
		else mouseCoorY = (mouseY - bannerHeight) / boxDimension;
		repaint();
	}

	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		mouseCoorX = mouseX / boxDimension;
		if(mouseY < bannerHeight) mouseCoorY = -1;
		else mouseCoorY = (mouseY - bannerHeight) / boxDimension;
		repaint();
	}
	
	public void mouseClicked(MouseEvent e) {
		
	}
	
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
	public static void main(String[] args) {
		DatePicker picker = new DatePicker(11, 33, 2011);
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		frame.add(picker, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
}
