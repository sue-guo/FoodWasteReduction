/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;

import java.util.List;
import org.cst8288.foodwastereduction.model.Subscription;

/**
 *
 * @author ryany
 */
public interface SurplusObserver {
    void update(Inventory surplusItem);
}
