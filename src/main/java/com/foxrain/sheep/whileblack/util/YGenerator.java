package com.foxrain.sheep.whileblack.util;

/**
 * Created with intellij IDEA.
 * by 2021 03 2021/03/20 1:41 오후 20
 * User we at 13 41
 * To change this template use File | Settings | File Templates.
 * https://github.com/mherrmann/java-generator-functions/blob/master/src/main/java/io/herrmann/generator/Generator.java
 * @author foxrain
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class allows specifying Python generator-like sequences. For examples,
 * see the JUnit test case.
 *
 * The implementation uses a separate Thread to produce the sequence items. This
 * is certainly not as fast as eg. a for-loop, but not horribly slow either. On
 * a machine with a dual core i5 CPU @ 2.67 GHz, 1000 items can be produced in
 * &lt; 0.03s.
 *
 * By overriding finalize(), the class takes care not to leave any Threads
 * running longer than necessary.
 */
public abstract class YGenerator<T> implements Iterable<T> {

    private class Condition {
        private boolean isSet;
        public synchronized void set() {
            isSet = true;
            notify();
        }
        public synchronized void await() throws InterruptedException {
            try {
                if (isSet)
                    return;
                wait();
            } finally {
                isSet = false;
            }
        }
    }

    static ThreadGroup THREAD_GROUP;

    Thread producer;
    private boolean hasFinished;
    private final Condition itemAvailableOrHasFinished = new Condition();
    private final Condition itemRequested = new Condition();
    private T nextItem;
    private boolean nextItemAvailable;
    private RuntimeException exceptionRaisedByProducer;

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return waitForNext();
            }
            @Override
            public T next() {
                if (!waitForNext())
                    throw new NoSuchElementException();
                nextItemAvailable = false;
                return nextItem;
            }
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
            private boolean waitForNext() {
                if (nextItemAvailable)
                    return true;
                if (hasFinished)
                    return false;
                if (producer == null)
                    startProducer();
                itemRequested.set();
                try {
                    itemAvailableOrHasFinished.await();
                } catch (InterruptedException e) {
                    hasFinished = true;
                }
                if (exceptionRaisedByProducer != null)
                    throw exceptionRaisedByProducer;
                return !hasFinished;
            }
        };
    }

    protected abstract T run() throws InterruptedException;

    protected void yield(T element) throws InterruptedException {
        nextItem = element;
        nextItemAvailable = true;
        itemAvailableOrHasFinished.set();
        itemRequested.await();
    }

    protected T returns(T element) throws InterruptedException {
        yield(element);
        return element;
    }

    private void startProducer() {
        assert producer == null;
        if (THREAD_GROUP == null)
            THREAD_GROUP = new ThreadGroup("generatorfunctions");
        producer = new Thread(THREAD_GROUP, () ->
        {
            try {
                itemRequested.await();
                YGenerator.this.run();
            } catch (InterruptedException e) {
                // No need to do anything here; Remaining steps in run()
                // will cleanly shut down the thread.
            } catch (RuntimeException e) {
                exceptionRaisedByProducer = e;
            }
            hasFinished = true;
            itemAvailableOrHasFinished.set();
        });
        producer.setDaemon(true);
        producer.start();
    }

    @Override
    protected void finalize() throws Throwable {
        producer.interrupt();
        producer.join();
        super.finalize();
    }

    public static void main(String[] args)
    {
        YGenerator<Integer> simpleGenerator = new YGenerator<Integer>() {
            public Integer run() throws InterruptedException {
                yield(1);
                // Some logic here...
                yield(2);

                return returns(3);
            }
        };

        while (simpleGenerator.iterator().hasNext())
        {
            System.out.println(simpleGenerator.iterator().next());
        }
//        for (Integer element : simpleGenerator)
//            System.out.println(element);
    }
}