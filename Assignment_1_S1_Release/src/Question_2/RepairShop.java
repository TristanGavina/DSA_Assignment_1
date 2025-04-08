/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Question_2;

/**
 *
 * @author xhu
 */
public class RepairShop {
    
    boolean repairing = false;
       
    public synchronized void repairShop(Phone phone)throws InterruptedException{
        try{
            repairing = true;
            
            Thread.sleep(1000);
            
            phone.phoneLife = 500;
            phone.isInfected = false;
            phone.isRepaired = true;
            phone.moveToRepair = false;
            
            //make phone is immune to virus for 60 seconds
            phone.endImmunity = System.currentTimeMillis() + phone.immuneDuration;
            
            repairing = false;
        } catch (InterruptedException e){
        }
        
    }
}