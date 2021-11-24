package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {

    SessionFactory sfactory = getSessionFactory();
    Transaction transaction = null;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = sfactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE User (id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                    " name VARCHAR (45), lastName VARCHAR (45), age TINYINT)")
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.err.println("Таблица не создана : " + e);
            transaction.rollback();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sfactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS User")
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.err.println("Ошибка удаления таблицы" + e);
            transaction.rollback();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sfactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (Exception e) {
            System.err.println("Пользователь не сохранён. : " + e);
            transaction.rollback();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sfactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        } catch (Exception e) {
            System.err.println("Пользователь не удалён. : " + e);
            transaction.rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = null;
        try (Session session = sfactory.openSession()) {
            transaction = session.beginTransaction();
            list = session.createQuery("FROM User").list();
            transaction.commit();
        } catch (Exception e) {
            System.err.println("Ошибка вывода пользователей. : " +e);
            transaction.rollback();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sfactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.err.println("Ошибка очищения таблицы" + e);
            transaction.rollback();
        }
    }

}
