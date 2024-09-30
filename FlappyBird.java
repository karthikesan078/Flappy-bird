import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;//for pipes
import javax.swing.*;
import java.awt.Image;


public class FlappyBird extends JPanel implements ActionListener{
    int boardwidth=360;
    int boardheight=640;

    //images
    Image flappybirdbgImg;
    Image flappybirdImg;
    Image toppipeImg;
    Image bottompipeImg;
     
    //bird variables
    int birdx=boardwidth/8;
    int birdy=boardheight/2;
    int birdwidth=30;
    int birdheight=20;
    
    // class for bird which would have the dimensions of the bird
    class Bird{
        int x=birdx;
        int y=birdy;
        int width=birdwidth;
        int height=birdheight;
        Image img;
        Bird(Image im){
            img=im;
        }
    }
    //logic of the game
    Bird bird;//it is in local,,so we are making it into global

        //gametimer for the movement of the bird
    Timer gameLoop;


    public FlappyBird(){//constructor
       setPreferredSize(new Dimension(boardwidth,boardheight));
      //  setBackground(Color.blue);

        //loadimages
        flappybirdbgImg=new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        //here we are considering image as a data type and assignning its value with a image
        //we are getting the image from a class and resources gets the address of the image
        //getimage() converts the imageicon to image
        flappybirdImg=new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        toppipeImg=new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottompipeImg=new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
        bird =new Bird(flappybirdImg);
        gameLoop=new Timer(1000/60,this);//1000 millisecond=1 second/frame,,,this denotes flappy bird class action
            gameLoop.start();//for infninte movement,,,if we comment it ,,it would run for only one time
    }
        public void paintComponent(Graphics g) {
            super.paintComponent(g);//getting the graphics function from jpanel bascically inheritance
            draw(g);
            
    }
        public void draw(Graphics g){
            //background
           // System.out.println("moving"); it is moving
            g.drawImage(flappybirdbgImg,0,0,boardwidth,boardheight,null);
           //bird
            g.drawImage(bird.img,bird.x,bird.y,bird.width,bird.height,null);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            repaint();
            //throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
        }
}
