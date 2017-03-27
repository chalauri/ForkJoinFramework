package main;

import main.creating_fork_join_pool.Product;
import main.creating_fork_join_pool.ProductListGenerator;
import main.creating_fork_join_pool.Task;
import main.joining_results_of_task.Document;
import main.joining_results_of_task.DocumentTask;
import main.running_tasks_asynchronously.FolderProcessor;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * Created by G.Chalauri on 03/27/17.
 */
public class Main {

    public static void main(String[] args) {
        //creatingForkJoinPoolExample();
        //joiningResultsOfTaskExample();
        runningTasksAsynchronouslyExample();
    }

    private static void runningTasksAsynchronouslyExample() {
        ForkJoinPool pool = new ForkJoinPool();

        FolderProcessor system = new FolderProcessor("C:\\Windows",
                "log");
        FolderProcessor apps = new
                FolderProcessor("C:\\Program Files", "log");
        FolderProcessor studying = new FolderProcessor("C:\\dev\\studying", "java");

        pool.execute(system);
        pool.execute(apps);
        pool.execute(studying);


        do {
            System.out.printf("******************************************\n");
            System.out.printf("Main: Parallelism: %d\n", pool.
                    getParallelism());
            System.out.printf("Main: Active Threads: %d\n", pool.
                    getActiveThreadCount());
            System.out.printf("Main: Task Count: %d\n", pool.
                    getQueuedTaskCount());
            System.out.printf("Main: Steal Count: %d\n", pool.
                    getStealCount());
            System.out.printf("******************************************\n");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while ((!system.isDone()) || (!apps.isDone()) || (!studying.
                isDone()));

        pool.shutdown();

        List<String> results;
        results = system.join();
        System.out.printf("System: %d files found.\n", results.size());
        results = apps.join();
        System.out.printf("Apps: %d files found.\n", results.size());
        results = studying.join();
        System.out.printf("Documents: %d files found.\n", results.
                size());
    }


    private static void joiningResultsOfTaskExample() {
        Document mock = new Document();
        String[][] document = mock.generateDocument(100, 1000, "the");

        DocumentTask task = new DocumentTask(document, 0, 100, "the");

        ForkJoinPool pool = new ForkJoinPool();
        pool.execute(task);

        do {
            System.out.printf("***************************************** *\n");
            System.out.printf("Main: Parallelism: %d\n", pool.
                    getParallelism());
            System.out.printf("Main: Active Threads: %d\n", pool.
                    getActiveThreadCount());
            System.out.printf("Main: Task Count: %d\n", pool.
                    getQueuedTaskCount());
            System.out.printf("Main: Steal Count: %d\n", pool.
                    getStealCount());
            System.out.printf("******************************************\n");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!task.isDone());

        pool.shutdown();

        try {
            pool.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            System.out.printf("Main: The word appears %d in the document", task.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void creatingForkJoinPoolExample() {
        ProductListGenerator generator = new ProductListGenerator();
        List<Product> products = generator.generate(10000);

        Task task = new Task(products, 0, products.size(), 0.20);
        ForkJoinPool pool = new ForkJoinPool();

        pool.execute(task);

        do {
            System.out.printf("Main: Thread Count: %d\n", pool.
                    getActiveThreadCount());
            System.out.printf("Main: Thread Steal: %d\n", pool.
                    getStealCount());
            System.out.printf("Main: Parallelism: %d\n", pool.
                    getParallelism());
            try {
                TimeUnit.MILLISECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!task.isDone());

        pool.shutdown();

        if (task.isCompletedNormally()) {
            System.out.printf("Main: The process has completed normally.\n");
        }

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            if (product.getPrice() != 12) {
                System.out.printf("Product %s: %f\n", product.
                        getName(), product.getPrice());
            }
        }

        System.out.println("Main: End of the program.\n");
    }
}
