package HW7;


import javax.persistence.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static EntityManagerFactory emf;
    public static EntityManager em;

    public static void main(String[] args) {
        emf = Persistence.createEntityManagerFactory("JPA_persistence");
        em = emf.createEntityManager();


        Product p1 = new Product("prod111", 11.11);//окей, цена будет 22.22
        Product p2 = new Product("prod222", 22.22);//окей, цена будет 22.22
        Product p3 = new Product("prod333", 33.33);//окей, цена будет 22.22
        add(p1, p2, p3);

        Client c1 = new Client("client111", "client111@mail");
        Client c2 = new Client("client222", "client222@mail");
        Client c3 = new Client("client333", "client333@mail");
        add(c1, c2, c3);

        Order o1 = new Order(900, 3, 1, 13);
        Order o2 = new Order(901, 3, 2, 14);
        Order o3 = new Order(902, 2, 3, 15);
        add(o1, o2, o3);



        delete(c3, p1);

        Client Margarett = (Client) get(Client.class, 2);
        System.out.println(Margarett);

        update(c1);

        showAll(Client.class);

        showOrderTotalPayByClientId(3);
    }

    public static void showOrderTotalPayByClientId(int clientId){
        Query query2 = em.createQuery("SELECT SUM(e.sumOfOrder) FROM Order e WHERE e.clientID = :param ");
        query2.setParameter("param", clientId);
        double payment = (Double) query2.getSingleResult();
        System.out.println("In total to pay= " + payment + " for Client with id="+ clientId);
    }

    public static void showAll(Class clazz) {
        if (clazz == Client.class) {
            Query query = em.createQuery(" FROM Client ", Client.class);
            List<Client> list = (List<Client>) query.getResultList();
            for (Client c : list)
                System.out.println(c);
        } else if (clazz == Product.class) {
            Query query = em.createQuery(" FROM Product ", Product.class);
            List<Product> list = (List<Product>) query.getResultList();
            for (Product c : list)
                System.out.println(c);
        } else if (clazz == Order.class) {
            Query query = em.createQuery(" FROM Order ", Order.class);
            List<Order> list = (List<Order>) query.getResultList();
            for (Order c : list)
                System.out.println(c);
        }
    }


    public static void update(Object o) {
        Scanner sc = new Scanner(System.in);
        em.getTransaction().begin();
        try {
            if (o.getClass() == Client.class) {
                Client c = em.getReference(Client.class, ((Client) o).getId());
                System.out.print("Enter client new name: ");
                String newName = sc.nextLine();
                System.out.print("Enter client new email: ");
                String newEmail = sc.nextLine();
                c.setName(newName);
                c.setEmail(newEmail);
                em.getTransaction().commit();
            } else if (o.getClass() == Product.class) {
                Product p = em.getReference(Product.class, ((Product) o).getId());
                System.out.print("Enter product new name: ");
                String newName = sc.nextLine();
                System.out.print("Enter product new price: ");
                String newPrice = sc.nextLine();
                Double d = Double.valueOf(newPrice);//ЧИСЛО вводить через ТОЧКУ
                p.setName(newName);
                p.setPrice(d);
                em.getTransaction().commit();
            } else if (o.getClass() == Order.class) {
                Order ord = em.getReference(Order.class, ((Order) o).getId());
                System.out.print("Enter order new SerialNumber: ");
                Integer orderSerialNumber = sc.nextInt();
                System.out.print("Enter new clientID: ");
                Integer clientID = sc.nextInt();
                System.out.print("Enter new productId: ");
                Integer productId = sc.nextInt();
                System.out.print("Enter new product quantity: ");
                Integer productQuantity = sc.nextInt();
                ord.setOrderSerialNumber(orderSerialNumber);
                ord.setClientID(clientID);
                ord.setProductId(productId);
                ord.setProductQuantity(productQuantity);
                em.getTransaction().commit();
            }
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    public static Object get(Class clazz, int id) throws NoSuchElementException{
        if(clazz == Client.class){
             return em.getReference(Client.class, id);
        } else if(clazz == Product.class){
            return em.getReference(Product.class, id);
        } else if(clazz == Order.class){
            return em.getReference(Order.class, id);
        }

        throw new NoSuchElementException();
    }
    public static void delete(Object... o) {
        for (int i = 0; i < o.length; i++) {
            if (o[i].getClass() == Client.class || o[i].getClass() == Product.class
            || o[i].getClass() == Order.class) {
                em.getTransaction().begin();
                try {
                    em.remove(o[i]);
                    em.getTransaction().commit();
                } catch (Exception ex) {
                    em.getTransaction().rollback();
                }
            }
        }

    }


    private static void add(Object... o) {
        for (int i = 0; i < o.length; i++) {
            if (o[i].getClass() == Client.class) {
                em.getTransaction().begin();
                try {
                    Client c = (Client) o[i];
                    em.persist(c);
                    em.getTransaction().commit();
                } catch (Exception ex) {
                    em.getTransaction().rollback();
                }
            } else if (o[i].getClass() == Product.class) {
                em.getTransaction().begin();
                try {
                    Product p = (Product) o[i];
                    em.persist(p);
                    em.getTransaction().commit();
                } catch (Exception ex) {
                    em.getTransaction().rollback();
                }
            } else if (o[i].getClass() == Order.class) {
                em.getTransaction().begin();
                try {
                    Order order = (Order) o[i];
                    em.persist(order);
                    em.getTransaction().commit();
                } catch (Exception ex) {
                    em.getTransaction().rollback();
                }
            }
        }
    }

}
