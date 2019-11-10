/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import model.Categories;
import model.Products;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author Dealer
 */
@Named(value = "productController")
@ManagedBean(eager=true)
@RequestScoped

public class ProductController implements Serializable {

    private List<Products> productNames;

    public List<Products> getProductNames() {
        return productNames;
    }
    
    public ProductController() {
        productNames=null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
//        Categories p = new Categories();
//        p.setCategory("ABC");
//        session.save(p);
//        session.getTransaction().commit();
        Query q = session.createQuery("From Products");
        productNames = q.list();
    }
    
}
