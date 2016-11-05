package userInterface;

import java.awt.*;
import java.awt.geom.Ellipse2D;

import javax.swing.*;

public class LoserEnd extends JPanel{
   
    private Ellipse2D.Float[] ellipses;
    private double esize[];
    private float estroke[];
    private final double MAX_SIZE=40;
    
    public LoserEnd() {
        ellipses = new Ellipse2D.Float[60];
        esize    = new double[ellipses.length];
        estroke  = new  float[ellipses.length];
        for (int i = 0; i < ellipses.length; i++) {
            ellipses[i] = new Ellipse2D.Float();
            getRandomXY(i, 20 * Math.random(),getWidth()-60,getHeight()-60);
        }
    }
    public void build(){
        while(true){
           for (int i = 0; i < ellipses.length; i++) {
            estroke[i] += 0.025f; //increasing stroke
            esize[i]++; //increasing size
            if (esize[i] > MAX_SIZE) //new values if reaches maximum
                getRandomXY(i, 20 * Math.random(),getWidth()-60,getHeight()-60);
            else 
                ellipses[i].setFrame(ellipses[i].getX(), ellipses[i].getY(),
                                     esize[i], esize[i]);
           }
           repaint(); //repainting
            try {
               Thread.sleep(50); //sleeping
             }catch (Exception ex) {}
        }
    }
    public void getRandomXY(int i, double size, int w, int h) {
        esize[i] = size;
        estroke[i] = 1.0f; //setting size.strokes,coordinates
        double x = Math.random() * (getWidth()-(MAX_SIZE/2));
        double y = Math.random() * (getHeight()-(MAX_SIZE/2));
        ellipses[i].setFrame(x, y, size, size); //setting ellipse
    }
    
    
    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2d=(Graphics2D)g;
        //clearing previous trails
        Color blue1 = new Color(65,179,247);
        g2d.setColor(blue1);
        g2d.fillRect(0,0,this.getWidth(),this.getHeight());
        for (int i = 0; i < 7; i++) {               
               g2d.setStroke(new BasicStroke(estroke[i]));
           
               int white=(int)(Math.random()*256);
               g2d.setColor(Color.WHITE);
               g2d.draw(ellipses[i]); //drawing ellipses
               
        }
    }
    public static void main(String[] args) {
     JFrame f=new JFrame("Bubbles");
    
      
        JPanel panel = new JPanel();
       panel.setPreferredSize(new Dimension(200, 200));

       LoserEnd b=new LoserEnd();
       
       
       JLabel label = new JLabel("YOU LOSE!!!");
       label.setFont(new Font("Default",Font.BOLD,70));
       label.setPreferredSize(new Dimension(500, 500));
       b.add(label,BorderLayout.CENTER);
       
      
       

       f.getContentPane().add(b);

     
       f.setTitle("YOU LOSE!!");
       
    
 
       
        f.setSize(1000,1000);
       f.setVisible(true);
        f.setLocationRelativeTo(null);
     f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
        b.build();
    }
    
	@Override
	public void launch() {
		System.out.println(Thread.currentThread().getName() + ": entered " + page);
		main.replaceCurrentPanel(mainPanel);
		main.setEnabled(true);
	}

	@Override
	public void leave() {
		System.out.println(Thread.currentThread().getName() + ": leaving " + page);
		main.setEnabled(false);
	}
}