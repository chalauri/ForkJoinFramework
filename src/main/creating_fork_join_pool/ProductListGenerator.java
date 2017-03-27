package main.creating_fork_join_pool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by G.Chalauri on 03/27/17.
 */
public class ProductListGenerator {

    /*
    Generate the list of products. Assign the same price to all of the products, for
    example, 10 to check that the program works well.
     */
    public List<Product> generate(int size) {
        List<Product> ret = new ArrayList<Product>();

        for (int i = 0; i < size; i++) {
            Product product = new Product();
            product.setName("Product " + i);
            product.setPrice(10);
            ret.add(product);
        }

        return ret;
    }

}
