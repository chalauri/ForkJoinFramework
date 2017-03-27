package main.creating_fork_join_pool;

import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * Created by G.Chalauri on 03/27/17.
 */
public class Task extends RecursiveAction {

    /*
    This element is necessary, because the
    parent class of the RecursiveAction class, the ForkJoinTask class, implements
    the Serializable interface.
     */
    private static final long serialVersionUID = 1L;

    private List<Product> products;

    private int first;
    private int last;

    private double increment;

    public Task(List<Product> products, int first, int last, double
            increment) {
        this.products = products;
        this.first = first;
        this.last = last;
        this.increment = increment;
    }

    @Override
    protected void compute() {
        if (last - first < 10) {
            updatePrices();
        } else {
            int middle = (last + first) / 2;
            System.out.printf("Task: Pending tasks: %s\n", getQueuedTaskCount());
            Task t1 = new Task(products, first, middle + 1, increment);
            Task t2 = new Task(products, middle + 1, last, increment);
            invokeAll(t1, t2);
        }
    }

    private void updatePrices() {
        for (int i = first; i < last; i++) {
            Product product = products.get(i);
            product.setPrice(product.getPrice() * (1 + increment));
        }
    }
}
