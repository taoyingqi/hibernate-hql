package com.qed.example;

import com.qed.entity.Address;
import com.qed.entity.Person;
import com.qed.utils.HibernateUtil;
import com.qed.utils.Identify;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2015/11/7.
 */
public class QueryClient {
    SessionFactory sessionFactory = null;
    Session session = null;
    Transaction tx = null;

    @Before
    public void setUp() throws Exception {
        System.out.println("------initialize-----");
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("------free-----");
        if (session != null && session.isOpen()) {
            session.close();
        }
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }

    /**
     * batch Insert
     */
    @Test
    public void testInsert() {
        tx = session.beginTransaction();
        for (int i = 0; i < 50; i++) {
            Person person = new Person(UUID.randomUUID().toString(), "apple-" + i, "123" + i, Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Timestamp((new Date()).getTime()))));
            Set<Address> addressSet = new HashSet<Address>();
            Address address1 = new Address(Identify.numberOnly(), "New York", i);
            Address address2 = new Address(Identify.numberOnly(), "Winston", 50 - i);

            addressSet.add(address1);
            addressSet.add(address2);
            // Associate
            person.setAddressSet(addressSet);

            address1.setPerson(person);
            address2.setPerson(person);

            session.persist(person);
            if (i % 10 == 0) {
                session.flush();
                session.clear();
            }
        }
        tx.commit();
    }

    // Join query
    @Test
    public void testQueryJoin() {
        String hql = "from Person p, Address a where p.id = a.person and p.name = :name";
        Query query = session.createQuery(hql);
        query.setString("name", "apple-1");
        List<Object[]> objs = query.list();
        for (Object[] obj : objs) {
            System.out.println(Arrays.toString(obj));
        }
    }

    //Inner Join query
    @Test
    public void testQueryInnerJoin() {
        String sql = "select new List(p.name, a.descs, a.code) from Person p inner join p.addressSet a where p.name = 'apple-1'";
        Query query = session.createQuery(sql);
        List<List> objs = query.list();
        for (List l : objs) {
            System.out.println(l);
        }
    }

    //Left Outer Join query
    @Test
    public void testQueryOuterJoin() {
        String sql = "from Person p left outer join p.addressSet a where p.name = 'apple-1'";
        Query query = session.createQuery(sql);
        List<Object[]> objs = query.list();
        for (Object[] obj : objs) {
            System.out.println(Arrays.toString(obj));
        }
    }

}
