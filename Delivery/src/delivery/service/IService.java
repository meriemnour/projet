/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery.service;

import delivery.entity.User;
import java.util.List;

/**
 *
 * @author asus
 */
public interface IService<T> {
    public void ajouter(T t);
    public void modifier(int id_amodifier, T t);
    public void supprimer(int id);
    public List<T> afficher();

}
