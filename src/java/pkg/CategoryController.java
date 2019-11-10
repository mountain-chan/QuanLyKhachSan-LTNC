/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg;

import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import model.Categories;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author Dealer
 */
//@Named(value = "categoryController")
//@SessionScoped
public class CategoryController implements Serializable {

    /**
     * Creates a new instance of CategoryController
     */
    private List<Categories> categoryNames;

    public List<Categories> getCategoryNames() {
        return categoryNames;
    }
    
    public CategoryController() {
//        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//        session.beginTransaction();
//        Categories p = new Categories();
//        p.setCategory("ABC");
//        session.save(p);
//        session.getTransaction().commit();
//        Query q = session.createQuery("From Categories");
//        categoryNames = q.list();
         categoryNames.add(new Categories(1,"asdsad"));
    }
    
}
