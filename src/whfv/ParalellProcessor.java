/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Uzytkownik
 */
public class ParalellProcessor implements Runnable {

    private final ConcurrentLinkedQueue<Processable> mProcessables = new ConcurrentLinkedQueue<>();

    @Override
    public void run() {
        double step = 1;
        while (true) {
            long start = System.currentTimeMillis();
            for (Processable p : mProcessables) {
                p.process(step);

            }
            long end = System.currentTimeMillis();
            step = (end - start) / 10e3;
        }
    }

    public void addProcessable(Processable p) {
        mProcessables.add(p);
    }

}
