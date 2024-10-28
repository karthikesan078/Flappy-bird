import java.awt.*;
import java.awt.event.*;
import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.Random;//for pipes
import javax.swing.*;
import java.awt.Image;


public class FlappyBird extends JPanel implements ActionListener,KeyListener{
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
    //pipes
    int pipex=boardwidth;
    int pipey=0;
    int pipewidth=64;  //1/6
    int pipeheight=512;

    class Pipe{
        int x=pipex;
        int y=pipey;
        int width=pipewidth;
        int height=pipeheight;
        Image img;
        boolean passed=false;

        Pipe(Image img){
              this.img=img;  
        }
    }

    //logic of the game
    Bird bird;//it is in local,,so we are making it into global
    int velocityx=-4;//for moving the pipes towards left
     int velocityy=0;//moves -6 pixels per frame
     int gravity=1;//slow down by one pixel

     ArrayList<Pipe> pipes;
        Random random=new Random();

        //gametimer for the movement of the bird
    Timer gameLoop;
    Timer placePipesTimer;

    boolean gameOver = false;
    double score = 0;


    public FlappyBird(){//constructor
       setPreferredSize(new Dimension(boardwidth,boardheight));
      //  setBackground(Color.blue);
            setFocusable(true);
            addKeyListener(this);//lokks if we have pressed any buttons

        //loadimages
        flappybirdbgImg=new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        //here we are considering image as a data type and assignning its value with a image
        //we are getting the image from a class and resources gets the address of the image
        //getimage() converts the imageicon to image
        flappybirdImg=new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        toppipeImg=new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottompipeImg=new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
        bird =new Bird(flappybirdImg);
        pipes=new ArrayList<Pipe>();
        //placePipeTimer
        placePipesTimer=new Timer(1500,new ActionListener() {
            public void actionPerformed(ActionEvent e){
                placepipes();//for every 1.5 seconds ,the placepipes would be called
            }
        });
        placePipesTimer.start();

        gameLoop=new Timer(1000/60,this);//1000 millisecond=1 second/frame,,,this denotes flappy bird class action
            gameLoop.start();//for infninte movement,,,if we comment it ,,it would run for only one time
    }

    public void placepipes(){
        //the randompipey gives random  number between 0 and 256
        int randomPipey=(int)(pipey-pipeheight/4-Math.random()*(pipeheight/2));
        int openingSpace = boardheight/4;
            
        Pipe topPipe=new Pipe(toppipeImg);
        topPipe.y=randomPipey;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottompipeImg);
        bottomPipe.y = topPipe.y + pipeheight + openingSpace;
        pipes.add(bottomPipe);

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
            //pipe
            for(int i=0;i<pipes.size();i++){
                Pipe pipe=pipes.get(i);
                g.drawImage(pipe.img,pipe.x,pipe.y,pipe.width,pipe.height,null);
            }
            // score
            g.setColor(Color.black);
            g.setFont(new Font("Showcard Gothic", Font.PLAIN,32));
            if (gameOver){
                g.drawString("Seththadaa : " +String.valueOf((int) score), 10, 35);// displaying score 
            }
             else {
                g.drawString(String.valueOf((int) score ), 10, 35);
             }
        }
        public void move(){
            velocityy+=gravity;
            bird.y+=velocityy;
           bird.y=Math.max(bird.y,0) ;//bird should not exceed the screen

                //pipes
                //for moving the pipe towards left
                for(int i=0;i<pipes.size();i++){
                    Pipe pipe=pipes.get(i);
                    pipe.x+=velocityx;
                    if (!pipe.passed && bird.x > pipe.x + pipe.width){
                       // if the bird pass the rigth side of the pipe and width of pipe 
                       pipe.passed = true;
                       score += 0.5; // for two pipes the score is increased by 1 and so the score is declared as double
                    }
                    if (collision(bird, pipe)){
                        gameOver = true;
                    }
                }
                if (bird.y > boardheight){
                    gameOver = true;
                }
               
           
        }

        public boolean collision(Bird a, Pipe b){
            return a.x < b.x + b.width && // a's top left corner doesn't reach b's top right corner 
                   a.x + a.width > b.x && // a's top right corner passes reach b's top left corner 
                   a.y < b.y + b.height &&// a's top left corner doesn't reach b's bottom left corner
                   a.y + a.height > b.y;// a's bottom left corner passes reach b's top left corner 
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            move();
            repaint();
            //throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
            if (gameOver){
                placePipesTimer.stop();
                gameLoop.stop();
            }
        }
        //pressing the enter button
        @Override
        public void keyTyped(KeyEvent e){
            
            
        }
        @Override
        public void keyPressed(KeyEvent e){
            if(e.getKeyCode()==KeyEvent.VK_SPACE ){//for getting only space button
                velocityy=-9;//when ever we press space button,velocityy is set to be -9
                

            }
            if(e.getKeyCode()==KeyEvent.VK_ENTER){//our novalty
                //velocityy=-9;
                if (gameOver){
                    // restart
                    bird.y = birdy;
                    velocityy = 0;
                    pipes.clear();
                    score =0 ;
                    gameOver = false;
                    gameLoop.start();
                    placePipesTimer.start();
                }
            }
           
        }
        @Override 
        public void keyReleased(KeyEvent e){
           
        }
}
