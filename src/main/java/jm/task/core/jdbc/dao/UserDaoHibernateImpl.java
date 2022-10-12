package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        String query = "CREATE TABLE IF NOT EXISTS User(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20), last_name VARCHAR(20), age INT)";
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(query).executeUpdate();
            transaction.commit();
        } catch (HibernateException ex) {
            throw ex;
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        String query = "DROP TABLE IF EXISTS User";
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(query).executeUpdate();
            transaction.commit();
        } catch (HibernateException ex) {
            throw ex;
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (HibernateException ex) {
            throw ex;
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.load(User.class, id));
            transaction.commit();
        } catch (HibernateException ex) {
            throw ex;
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        String query = "SELECT a FROM User a";
        List<User> users = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            users = session.createQuery(query, User.class).getResultList();
            transaction.commit();
        } catch (HibernateException ex) {
            throw ex;
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        String query = "DELETE FROM User w";
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery(query).executeUpdate();
            transaction.commit();
        } catch (HibernateException ex) {
            throw ex;
        }
    }
}
