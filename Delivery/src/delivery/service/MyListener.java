/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery.service;

import delivery.entity.BonPlan;
import delivery.entity.Reclamation;



/**
 *
 * @author Mortadha
 */
public interface MyListener {
    
    public void onClickListener(BonPlan bp);
    public void onClickListener2(Reclamation r);
    
    

}
