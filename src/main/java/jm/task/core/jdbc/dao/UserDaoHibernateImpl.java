package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.HibernateUtil.getSessionFactory;
import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoHibernateImpl implements UserDao {
//    private Connection connection = getConnection();
    private SessionFactory sessionFactory = getSessionFactory();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student object
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS USERS" +
                    "(ID int not null auto_increment," +
                    "NAME VARCHAR(50)," +
                    "LASTNAME VARCHAR(50)," +
                    "AGE TINYINT," +
                    "PRIMARY KEY (ID))").executeUpdate();
            // commit transaction
            transaction.commit();
            System.out.println("Создана таблица USERS с колонками ID, NAME, LAST NAME, AGE.");
            System.out.println("-------------------------------------------------");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }


    }
    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student object
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            // commit transaction
            transaction.commit();
            System.out.println("Таблица USERS удалена");
            System.out.println("-------------------------------------------------");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student object
            session.save(user);
            // commit transaction
            transaction.commit();
            System.out.println("Пользователь " + name + " добавлен в таблицу USERS");
            System.out.println(user);
            System.out.println("-------------------------------------------------");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student object
            User user = session.get(User.class, id);
            session.delete(user);
            // commit transaction
            transaction.commit();
            System.out.println("Пользователь c ID = " + id + " удален из таблицы USERS");
            System.out.println("-------------------------------------------------");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> usersArrayList = new ArrayList<>();
        System.out.println("Таблица содержит следующих пользователей:");
        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();
            List<User> userList = session.createQuery("from User").getResultList();
            for (User e: userList){
                System.out.println(e);
            }
            System.out.println("-------------------------------------------------");
            return userList;
        }
        catch (Exception e) {
            e.printStackTrace();
            return usersArrayList;
        }
    }



    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student object
            session.createSQLQuery("DELETE FROM USERS").executeUpdate();
            // commit transaction
            transaction.commit();
            System.out.println("Таблица USERS очищена");
            System.out.println("-------------------------------------------------");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
