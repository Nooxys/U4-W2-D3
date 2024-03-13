package exercices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Supplier;

public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        //       SUPPLIER

        Supplier<Product> productSupplier = () -> {

            Random random = new Random();
            long randomId = random.nextLong(10000, 100000);
            double randomPrice = random.nextDouble(1, 500.00);
            int randomName = random.nextInt(0, 4);
            int randomCategory = random.nextInt(0, 4);
            List<String> names = new ArrayList<>();
            names.add("Product1");
            names.add("Product2");
            names.add("Product3");
            names.add("Product4");
            List<String> categories = new ArrayList<>();
            categories.add("Baby");
            categories.add("Boys");
            categories.add("Books");
            categories.add("Girls");

            return new Product(randomId, names.get(randomName), categories.get(randomCategory), randomPrice);

        };


        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            productList.add(productSupplier.get());

        }


        Supplier<Customer> customerSupplier = () -> {
            Random random = new Random();
            long randomId = random.nextLong(10000, 100000);
            int randomTier = random.nextInt(1, 3);
            int randomName = random.nextInt(0, 4);
            List<String> nameList = new ArrayList<>();
            nameList.add("firstGuy");
            nameList.add("secondGuy");
            nameList.add("thirdGuy");
            nameList.add("fourthGuy");
            return new Customer(randomId, nameList.get(randomName), randomTier);
        };

        Supplier<Order> orderSupplier = () -> {
            Random random = new Random();
            long randomId = random.nextLong(10000, 100000);
            int randomProducts = random.nextInt(1, 3);
            int randomStatus = random.nextInt(0, 2);
            int randomDate = random.nextInt(0, 4);
            List<String> statusList = new ArrayList<>();
            statusList.add("pending");
            statusList.add("not in charge yet");
            statusList.add("charged");
            List<LocalDate> orderDate = new ArrayList<>();
            orderDate.add(LocalDate.now());
            orderDate.add(LocalDate.now().plusDays(1));
            orderDate.add(LocalDate.now().minusDays(1));
            orderDate.add(LocalDate.parse("2024-02-11"));

            Customer randomCustomer = customerSupplier.get();
            List<Product> randomProduct = new ArrayList<>();
            for (int i = 0; i < randomProducts; i++) {
                randomProduct.add(productSupplier.get());

            }
            return new Order(randomId, statusList.get(randomStatus), orderDate.get(randomDate), randomProduct, randomCustomer);


        };
        List<Order> orderList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            orderList.add(orderSupplier.get());
        }


//        productList.forEach(product -> logger.info(String.valueOf(product)));

        //        EXERCISE 1

        List<Product> booksProducts = productList.stream().filter(product -> product.getPrice() > 100 && product.getCategory().equals("Books")).toList();
        booksProducts.forEach(product -> logger.info(String.valueOf(product)));

        //       EXERCISE 2

        Set<Order> babyOrders = new HashSet<>();
        orderList.forEach(order -> order.getProducts().forEach(product -> {
            if (product.getCategory().equals("Baby")) {
                babyOrders.add(order);
            }
        }));
        babyOrders.forEach(order -> logger.info(String.valueOf(order)));


        //       EXERCISE 3

        List<Product> boysDiscountedProducts = productList.stream().filter(product -> product.getCategory().equals("Boys")).toList();
        boysDiscountedProducts.forEach(product -> {
            product.setPrice(product.getPrice() * 0.9);
//            logger.info(String.valueOf(product));
        });


        //      EXERCISE 4

        List<Order> ordersByDateAndTier = orderList.stream().filter(order -> order.getCustomer().getTier() == 2).filter(order -> order.getOrderDate().isAfter(LocalDate.parse("2024-02-01")) && order.getOrderDate().isBefore(LocalDate.parse("2024-02-28"))).toList();
        List<Product> finalproducts = new ArrayList<>();
        ordersByDateAndTier.forEach(order -> order.getProducts().forEach(product -> finalproducts.add(product)));
        finalproducts.forEach(product -> logger.info(String.valueOf(product)));

    }


}