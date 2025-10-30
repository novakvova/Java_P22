package org.example;

import org.example.entities.CategoryEntity;
import org.example.utils.HibernateHelper;

public class Main {
    public static void main(String[] args) {
        System.out.println( "Привіт козаки!");

        //SimpleInsert();
        //SimpleInsertFactory();

        var session = HibernateHelper.getSession();
        try {
            var result = session.createSelectionQuery("from CategoryEntity", CategoryEntity.class)
                    .getResultList();
            result.forEach(System.out::println);
        }catch (Exception e) {
            System.out.println("Хюсто у нас проблеми "+e);
        }
    }

    private static void SimpleInsert() {
        var session = HibernateHelper.getSession();
        try {
            session.beginTransaction();
            CategoryEntity [] list = new CategoryEntity[2];
            list[0] = new CategoryEntity();
            list[0].setName("Калабуджа");
            //session.save(category);
            session.persist(list[0]);

            list[1] = new CategoryEntity();
            list[1].setName("Пельмені");
            session.persist(list[1]);
            session.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("У нас проблеми Хюстон :("+e);
        }
        finally {
            session.close();
        }
    }

    private static void SimpleInsertFactory() {
        var sessionFactory = HibernateHelper.getSessionFactory();
        sessionFactory.inTransaction(session -> {
            session.persist(new CategoryEntity("Кабачок"));
            session.persist(new CategoryEntity("Диня"));
        });
        sessionFactory.close();
    }
}