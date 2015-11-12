package ecp.servlets.dao;

import org.hibernate.HibernateException;
import org.hibernate.ObjectDeletedException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.stat.Statistics;
import org.hibernate.stat.SecondLevelCacheStatistics;
import ecp.servlets.model.Person;
import org.hibernate.Hibernate;

public class HibernateUtil{
	private static SessionFactory factory;
    private static Session session;
    private static Transaction transaction;

    static {
        try {
            factory = new Configuration().configure("hibernate.cfg.xml")
                    .buildSessionFactory();
        } catch (Throwable e) {
            System.out.println("Failed to create session factory object.");
            e.printStackTrace();
        }
    }
    
    public static <T> T perform(Command command, Class<T> returnClass) {
        session = factory.openSession();
        transaction = session.beginTransaction();
        Object returnObject = null;

        try {
            command.setSession(session);
            returnObject = command.execute();
            transaction.commit();
		} catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
             }
            if(returnObject instanceof Boolean){
                returnObject = new Boolean(false);
            }
            System.out.println("Unable to perform transaction.");
            e.printStackTrace();
        } finally {
            session.close();
        }

        return returnClass.cast(returnObject);
    }
}