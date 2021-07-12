
/*

A car goes on the road.Users can stop the car or can move the car again.Also,color of the car can be changed by users.When the car goes to outside of the window,the car goes to the start point.
Also,there is a clock and there is a light source.Indicator of the clock rotates 90 degree in clockwise direction in each 15 seconds.The light source increases distance of the light in each 0.2 seconds.
The light source can brighten a certain region.Assume that the animation does not stop.(pressing to "Stop Animate")

Note : This program runs for 800x800 window size

 */

package lab11;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FreeAnimate2 extends JPanel implements ActionListener
{
    // coordinates of top window of the car
	private int[] car_top_x= {75,100,125,50};
	private int[] car_top_y= {350,350,370,370};
	// coordinates of middle part of the car
	private int[] car_middle_x= {35,140,140,35};
	private int[] car_middle_y= {370,370,390,390};
	// coordinates of wheel of the car 
	private int car_wheel_x= 40;
	private int car_wheel_y= 380;
	private int radius_wheel=20; // radius of wheels of the car
	private int red=255,green=0,blue=0; // these variables will be used to change color of the car
	
	// coordinates of indicator of the clock
	private int[] clock_x= {720,725,725,720};
	private int[] clock_y= {650,650,700,700}; 
	
	// coordinates of the light 
	private int[] light_x= {72,82,72};
	private int[] light_y= {83,83,93};
	
	Timer timer; // for the car
	Timer clock; // for the clock
	Timer light; // for the light
	
	public FreeAnimate2()
	{
		// to start the animation as default
		timer=new Timer(20,null);
		timer.setActionCommand("Start"); 
		timer.addActionListener(this);
		timer.start();
		
		clock=new Timer(15000,null);
		clock.setActionCommand("Clock");
		clock.addActionListener(this);
		clock.start();
		
		light=new Timer(20,null);
		light.setActionCommand("Light");
		light.addActionListener(this);
		light.start();
		
		// Creating the panel for the buttons
		Panel p=new Panel();
		p.setBackground(Color.black);
		p.setBounds(0,0,800,40);
		add(p);
		setLayout(null);
		// Creating the buttons
		Button start_an=new Button();
		start_an.setLabel("Start Animate");
		start_an.addActionListener(this);
		Button stop_an=new Button();
		stop_an.setLabel("Stop Animate");
		stop_an.addActionListener(this);
		Button car_color=new Button();
		car_color.setLabel("Change Car Color");
		car_color.addActionListener(this);
		// Adding the buttons to the panel
		p.add(start_an);
		p.add(stop_an);
		p.add(car_color);
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2=(Graphics2D)g;
		g2.setColor(Color.yellow); // color of border of the panel
		g2.drawRect(0,0,800,40); // border of the panel
		g2.setColor(new Color(200,200,200)); // color of left and right barrier of the road
		g2.fillRect(0,250,800,50); // left barrier
		g2.fillRect(0,500,800,50); // right barrier
		g2.setColor(new Color(100,100,100)); // color of the road
		g2.fillRect(0,300,800,200); // road
		g2.setColor(new Color(0,0,0)); // color of top window of the car
		g2.fillPolygon(car_top_x,car_top_y,4); // top window of the car
		g2.setColor(new Color(red,green,blue)); // color of middle part of the car (color of the car)
		g2.fillPolygon(car_middle_x,car_middle_y,4); // middle part of the car
		g2.setColor(Color.black); // color of back wheel of the car
		g2.fillOval(car_wheel_x,car_wheel_y,radius_wheel,radius_wheel); // back wheel
		g2.setColor(Color.black); // color of front wheel of the car
		g2.fillOval(car_middle_x[2]-radius_wheel-5,car_wheel_y,radius_wheel,radius_wheel); // front wheel
		g2.setColor(Color.white); // color of the clock
		g2.fillOval(670,650,100,100); // clock
		g2.setColor(Color.black); // color of the indicator of the clock
		g2.fillPolygon(clock_x,clock_y,4); // indicator of the clock
		g2.setColor(Color.blue); // color of the light source
		g2.fillOval(50,60,30,30); // light source
		g2.setColor(Color.yellow); // color of the light
		g2.fillPolygon(light_x,light_y,3); // light
	
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("Start")) // default animation
		moveIt();
		else if(e.getActionCommand().equals("Stop Animate"))// to stop the animation
		{
		timer.stop();
		clock.stop();
		light.stop();
		}
		else if(e.getActionCommand().equals("Start Animate")) // to start the stopped animation again
		{
			timer.restart();
			clock.restart();
			light.restart();
			}
		else if(e.getActionCommand().equals("Change Car Color")) // to change color of the car
		{
			if(red==255 && green==0 && blue==0) // red
			green=255;
			else if(red==255 && green==255 && blue==0) // yellow
			red=0;
			else if(red==0 && green==255 && blue==0) // green
			blue=255;
			else if(red==0 && green==255 && blue==255) // turquoise
			green=0;
			else if(red==0 && green==0 && blue==255) // blue
			red=255;
			else if(red==255 && green==0 && blue==255) // pink
			blue=0;
		}
		else if(e.getActionCommand().equals("Clock")) // to rotate the indicator
		moveToClockQuarter();
		else if(e.getActionCommand().equals("Light")) // to increase distance of the light
		increaseLight();
		
	repaint();
	}
	
	public void moveToClockQuarter() // to rotate the indicator in each 15 seconds
	{
		// these variables will be used to prevent clock_x and clock_y change
		int[] copy_x=new int[4];
		int[] copy_y=new int[4];
		
		// Assigning to copy arrays
		for(int i=0;i<4;i++)
		{
			copy_x[i]=clock_x[i];
			copy_y[i]=clock_y[i];
		}
		
		// Converting to (x,y) coordinate system
		for(int i=0;i<4;i++)
		{
			copy_x[i]=copy_x[i]-720;
			copy_y[i]=700-copy_y[i]; 
		}
		
		// Rotating the indicator (K' = R * K,theta = 90)
		clock_x[0]=copy_y[0]; 
		clock_x[1]=copy_y[1];
		clock_x[2]=copy_y[2];
		clock_x[3]=copy_y[3];
		
		clock_y[0]=-copy_x[0];
		clock_y[1]=-copy_x[1];
		clock_y[2]=-copy_x[2];
		clock_y[3]=-copy_x[3];	
		
        copy_x[0]=clock_x[3];
        copy_x[1]=clock_x[0];
        copy_x[2]=clock_x[1];
        copy_x[3]=clock_x[2];
        
        copy_y[0]=clock_y[3];
        copy_y[1]=clock_y[0];
        copy_y[2]=clock_y[1];
        copy_y[3]=clock_y[2];	
        
        // Assigning to clock_x and clock_y arrays
        for(int i=0;i<4;i++)
        {
        clock_x[i]=copy_x[i];
        clock_y[i]=copy_y[i];
        }
		
        // Converting to coordinates in the window
		for(int i=0;i<4;i++)
		{
			clock_x[i]=720+clock_x[i];
			clock_y[i]=700-clock_y[i];
		}

	}
	
	public void increaseLight() // to increase distance that the light source will brighten
	{				
		// max distance is light_y[2] = 240 
		// light_y[2] is bottom vertex of the triangle
		
		if(light_y[2]<240)
		{
			light_x[1]++;
			light_y[2]++;
		}
	}
	
	public void moveIt() // this function makes the car move in x direction
	{
		// the car goes to 5 unit in x direction
		for(int i=0;i<4;i++)
		{
			car_top_x[i]+=5;
			car_middle_x[i]+=5;
		}
	car_wheel_x+=5;
	if(car_middle_x[2]>800) // to prevent the car go to outside the window
	{
		car_top_x[0]=75;
		car_top_x[1]=100;
		car_top_x[2]=125;
		car_top_x[3]=50;
		
		car_middle_x[0]=35;
		car_middle_x[1]=140;
		car_middle_x[2]=140;
		car_middle_x[3]=35;
		
		car_wheel_x=40;
	}
	}
	
	public static void main(String[] args)
	{
		FreeAnimate2 free2=new FreeAnimate2();
		JFrame fr=new JFrame("Car"); // title of the window
		fr.setSize(800,800); // size of the window
		fr.setVisible(true); // to see the window
		fr.getContentPane().add(free2);
		fr.setBackground(new Color(0,50,0)); // background color of the window
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // to close the window
	}

}




