/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Question_2;

import Question_1.LinkedList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author xhu
 */
public class Panel extends JPanel implements KeyListener, ComponentListener {

    //"example" is just an example to show you how the animation can be done
    //You need to remove the "example" before you submit your code.
    final LinkedList<Phone> phones = new LinkedList<>();
    final RepairShop repairShop = new RepairShop();

    JFrame frame;
    public Panel(JFrame frame)
    {
        this.frame = frame;        
        this.addKeyListener(this);
        this.addComponentListener(this);
        this.setFocusable(true);
    }
    

    
    @Override
    public void paint(Graphics g)
    {
        super.paintComponent(g);

        //drawing repair box in the middle
        g.setColor(Color.GRAY);
        g.fillRect(frame.getWidth() / 2, frame.getHeight() / 2, 50, 50);
        
        //repair shop text color and location
        g.setColor(Color.BLACK);
        g.drawString("Repair Shop", frame.getWidth() / 2, frame.getHeight() / 2);
        
        //drawing phones based on state
        synchronized (phones){ //synchronize for thread safety 
        for (int i = 0; i < phones.size; i++) {
            Phone phone = phones.getData(i);
            if(!phone.alive){
                continue; // skip dead phones
            }
            if (phone.isInfected) {
                if (phone.moveToRepair){
                    g.setColor(Color.PINK); // change color to pink when going to repair shop
                } else{
                    g.setColor(Color.RED); // turn red when infected
                }
            } else if(phone.isRepaired){
                g.setColor(Color.GREEN); // change colour to green when repaired
            }else {
                g.setColor(Color.BLUE); // healthy phone colors
            }

            g.fillOval(phone.x, phone.y, 10, 10);
        }
    }
        
        //currently we call run() method here. After Phone class is implemented as thread
        //you are going to remove the following code "example.run()".
        //example.run();  
        
        repaint();
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
        
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int keyCode = ke.getKeyCode();
        
        if(keyCode == KeyEvent.VK_UP){ //UP to add new phones
            
            Phone newPhone = new Phone(phones, repairShop);
            newPhone.setRange(frame.getWidth(), frame.getHeight());
            synchronized (phones) {
                phones.add(newPhone);
            }
            Thread phoneThread = new Thread(newPhone);
            phoneThread.start();
        }
        
        if (keyCode == KeyEvent.VK_V) { // V to infect random phone
            if (phones.size > 0) {
                int random = (int) (Math.random() * phones.size);
                Phone randomPhone = phones.getData(random);
                randomPhone.isInfected = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        
    }    

    @Override
    public void componentResized(ComponentEvent ce) {
        //Update phone movement when panel is resized
        for (int i = 0; i < phones.size; i++) {
            Phone phone = phones.getData(i);
            phone.setRange(frame.getWidth(), frame.getHeight());
        }
        
    }

    @Override
    public void componentMoved(ComponentEvent ce) {

    }

    @Override
    public void componentShown(ComponentEvent ce) {

    }

    @Override
    public void componentHidden(ComponentEvent ce) {

    }
}
