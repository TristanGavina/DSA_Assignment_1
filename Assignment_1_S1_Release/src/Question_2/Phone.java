package Question_2;

import Question_1.LinkedList;
import Question_1.Node;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * 
 * @author trist
 * 
 * Question: “Which object(s) have you chosen for the synchronize? Why?”
 * 
 * ANSWER: phones, repairLock, repairShop
 * synchronized(phones) prevents concurrent modifications error when handling and accessing phones list (infecting, removing, iterating).
 * synchronized(repairLock) makes sure only 1 phone can claim the repairShop spot avoiding repairShop to be cluttered by infected phones.
 * synchronized(repairShop) ensures only 1 phone gets repaired at a time when infected phone is in repair box/shop.
 * 
 * These 3 synchronize objects prevents race conditions, ensuring a multi-threaded safe code. 
 * Allowing multiple changes and access in the phones lists simultaneously.
 */
public class Phone implements Runnable, Comparable<Phone>{

    int x = 0;
    int y = 0;
    int vx = 0;
    int vy = 0;
    int delay = 10;
    int width;
    int height;

    boolean isInfected = false;
    boolean moveToRepair = false;
    boolean alive = true;
    boolean isRepaired = false;
    int phoneLife = 500;

    final LinkedList<Phone> phones;
    final RepairShop repairShop;

    static boolean canGoRepair = false;
    static final Object repairLock = new Object(); // used for synchonize lock to avoid multiple repairs
    long immuneDuration = 60000; //phone is immune to infection for 60sec after repair
    long endImmunity = 0;

    public Phone(LinkedList<Phone> phones, RepairShop repairShop) {
        this.phones = phones;
        this.repairShop = repairShop;
        //randomized phone movements
        this.x = (int) (Math.random() * 800);
        this.y = (int) (Math.random() * 600);
        this.vx = (int) (Math.random() * 3 + 1);
        this.vy = (int) (Math.random() * 3 + 1);

    }

    public void setRange(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void run() {
        while (alive) {
            move();

            if (isInfected) {
                phoneLife--;

                if (phoneLife <= 0) {
                    if (moveToRepair) {
                        synchronized (repairLock) {
                            canGoRepair = false;
                        }
                    }
                    alive = false;
                    break;
                }

                spreadInfection();

                if (!isRepaired && !moveToRepair && phoneCanRepair()) {
                    moveToRepair = true;
                }
            }

            if (moveToRepair) {
                goToRepairBox();
            }

            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //removing from list when phone is dead
        synchronized (phones) {
            boolean dead = false;
            for (int i = 0; i < phones.size; i++) {
                Phone currentPhone = phones.getData(i);
                if (currentPhone == this) {
                    phones.remove(i);
                    dead = true;
                    break;
                }
            }
        }
    }
    public void move() {
        if (x > width || x < 0) {
            vx *= -1;
        }
        if (y > height || y < 0) {
            vy *= -1;
        }

        x += vx;
        y += vy;
    }

    public void spreadInfection() {
        if (!isInfected) {
            return;
        }

        //list of infected phones
        LinkedList<Phone> infectedPhones = new LinkedList<>();
        //synchronize to avoid modifying same list (prevents race conditions)
        synchronized (phones) {
            //iterate phones using LinkedList methods
            for (int i = 0; i < phones.size; i++) {
                Phone phone = phones.getData(i);
                boolean immune = System.currentTimeMillis() < phone.endImmunity;
                double distance = Math.hypot(this.x - phone.x, this.y - phone.y);
                if (!phone.isInfected && !immune &&  distance <= 20) {
                    infectedPhones.add(phone);
                }
            }
        }
        //infecting phones
        Node<Phone> current = infectedPhones.getHead();
        while (current != null) {
            current.data.isInfected = true;
            current = current.next;
        }
    }

    public void goToRepairBox() {
        int repairBoxX = width / 2;
        int repairBoxY = height / 2;
        int speed = 2;

        // Move to repair box
        if (x < repairBoxX) {
            x += speed;
        } else if (x > repairBoxX) {
            x -= speed;
        }

        if (y < repairBoxY) {
            y += speed;
        } else if (y > repairBoxY) {
            y -= speed;
        }

        if (Math.abs(x - repairBoxX) <= speed && Math.abs(y - repairBoxY) <= speed) {

            //fixed x and y to avoid phone wandering
            x = repairBoxX;
            y = repairBoxY;

            try {
                synchronized (repairShop) {
                    repairShop.repairShop(this);
                }
                synchronized (repairLock) {
                    canGoRepair = false;  // release the lock after repair
                }
            } catch (InterruptedException e) {
                
            }
        } 
        else { // reduncancy just to avoid phones from getting stuck or wandering off 
            if (x < repairBoxX) {
                x += speed;
            }
            if (x > repairBoxX) {
                x -= speed;
            }
            if (y < repairBoxY) {
                y += speed;
            }
            if (y > repairBoxY) {
                y -= speed;
            }
        }
    }

    private boolean phoneCanRepair() {

        synchronized (repairLock) {
            if (!canGoRepair) {
                canGoRepair = true;
                return true; // this phone claims the spot
            }
        }
        return false;
    }

    @Override
    public int compareTo(Phone o) {
        
        //if addInOrder() method is used from LinkedList class
        // sort phone by infected state 
        if(this.isInfected != o.isInfected){
            return Boolean.compare(this.isInfected, o.isInfected);
        }
        return Integer.compare(this.phoneLife, o.phoneLife); // sort phone by life
    }

}
