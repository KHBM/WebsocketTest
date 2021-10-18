package com.foxrain.sheep.whileblack.util;

import lombok.Getter;
import org.apache.commons.lang3.tuple.Triple;
import reactor.core.publisher.Flux;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.function.Function;

/**
 * Created with intellij IDEA.
 * by 2021 03 2021/03/19 10:34 오전 19
 * User we at 10 34
 * To change this template use File | Settings | File Templates.
 *
 * @author foxrain
 */
public class Testers
{
    public static void main(String[] args)
    {
        try {
//            final Flux<Integer> fss = _(range(1, 10));
//            final java.lang.reflect.Method ms = findFlatMap(fss);
//            final Class<?> parameterType = ms.getParameterTypes()[0];
//            final Type[] genericInterfaces = parameterType.getTypeParameters();
//            for ( Type t : genericInterfaces)
//            {
//                System.out.println(t.getTypeName());
//            }
//            final Object invoke = ms.invoke(fss, new Function[]{x ->
//            {
//                System.out.println("in x " + x);
//                return Flux.just(x);
//            }});
//            System.out.println("READY?");
//            final java.lang.reflect.Method subMethod = findSubscribe(fss);
//            subMethod.invoke(invoke);
//            System.out.println("Done");
//            _(range(1, 10)).flatMap(x -> {
//                System.out.println("in xx " + x);
//                return Flux.just(x);
//            })
//            .subscribe();
            final Flux<Triple<Integer, Integer, Integer>> tripleFlux = Flux.just(3)
                .flatMap(x -> Flux.just(4)
                    .flatMap(y -> Flux.just(5)
                        .flatMap(z -> Flux.just(Triple.of(x, y, z)))
                    )
                );

                tripleFlux.subscribe(t ->
                {
                    System.out.println(String.format("TS : [(%d, %d, %d)]"
                        , t.getLeft(), t.getMiddle(), t.getRight()));
                });

            _(range(1, 101))
                .flatMap( c -> _(range(1, c))
                    .flatMap( a -> _(range(a, c))
                        .flatMap(b -> a*a + b*b == c*c ? Flux.just(new Triple[]{Triple.of(a, b, c)}) :
                            Flux.just(Triple.emptyArray()))
                    )
                )
                .subscribe(t -> {
                    System.out.println(String.format("T : [(%d, %d, %d)]"
                        , t.getLeft(), t.getMiddle(), t.getRight()));
                })
            ;
        } catch (
            Exception e
        )
        {
            e.printStackTrace();
        }
        System.out.println("Done");
    }

    public class Do<T, MT> {

        private final Function<T, MT> method;
        private final Generator<MT, Return<MT>, T> generator;
        final Function<T, MT> doRec;

        public Do(Function<T, MT> method, Generator<MT, Return<MT>, T> generator)
        {
            this.generator = generator;
            this.method = method;
            this.doRec = new Function<T, MT>()
            {
                @Override
                public MT apply(T v)
                {
                    Return<MT> mtReturn = generator.next(v);
                    return mtReturn.isDone() ? mtReturn.getValue() :
                        Do.this.resolveFlatMap(mtReturn.getValue(), this::apply); //TODO
                }
            };
        }

        public <R> MT do_(Function<Generator<MT, Return<MT>, T>, R> yieldFunctor)
        {
            //yieldFunctor.apply(generator);

            return doRec.apply(null);
        }

        private MT resolveFlatMap(MT target, Function<T, MT> func)
        {
            final java.lang.reflect.Method flatMap = findFlatMap(target);
            try
            {
                return (MT) flatMap.invoke(target, new Function[]{func});
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            catch (InvocationTargetException e)
            {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    public static <T> java.lang.reflect.Method findSubscribe(T t)
    {
        Class<?> tclass = t.getClass();

        java.lang.reflect.Method[] mList = tclass.getMethods();
        for (java.lang.reflect.Method m : mList)
        {
            if (m.getName().equalsIgnoreCase("subscribe"))
            {
                Class<?>[] paramsTypes = m.getParameterTypes();
                if (paramsTypes.length == 1)
                {
                    if (paramsTypes[0].getName().equals("java.util.function.Consumer"))
                    {
                        return m;
                    }
                }
            }
        }

        throw new RuntimeException("No flatmap(Function) method");
    }

    public static <T> java.lang.reflect.Method findFlatMap(T t)
    {
        Class<?> tclass = t.getClass();

        java.lang.reflect.Method[] mList = tclass.getMethods();
        for (java.lang.reflect.Method m : mList)
        {
            if (m.getName().equalsIgnoreCase("flatmap"))
            {
                Class<?>[] paramsTypes = m.getParameterTypes();
                if (paramsTypes.length == 1)
                {
                    if (paramsTypes[0].getName().equals("java.util.function.Function"))
                    {
                        return m;
                    }
                }
            }
        }

        throw new RuntimeException("No flatmap(Function) method");
    }

    @Getter
    public static class Return<T>
    {
        private final T value;
        private final boolean done;
        public Return(T value, boolean done)
        {
            this.value = value;
            this.done = done;
        }
    }

    interface Generator<MT, RMT, T>
    {
        RMT next(T v);
        Value<T> yield(MT mt);
    }

    public static class Value<T> {

        T t;

        public Value(T t)
        {
            this.t = t;
        }

        public T v()
        {
            return t;
        }
    }

    public static class ChainGenerator<MT, T, R> implements Generator<MT, Return<MT>, T>
    {
        private boolean done = false;
        private Function<ChainGenerator<MT, T, R>, R> function;
        private Value<T> yieldValue;
        private MT returnValue;
        final Function<T, MT> doRec;

        public boolean hasNext()
        {
            return false;
        }

        public static void test()
        {
            ChainGenerator.<Flux<Integer>, Integer, Flux<Triple<Integer, Integer, Integer>>>of((m) ->
            {
                Value<Integer> c = m.yield(Flux.just(5));
                Value<Integer> a = m.yield(Flux.just(3));
                Value<Integer> b = m.yield(Flux.just(4)); // yield * 고려..

                return m.returns(a.v()*a.v() + b.v()*b.v() == c.v()*c.v() ?
                    Flux.just(new Triple[]{Triple.of(a.v(), b.v(), c.v())}) :
                    Flux.just(Triple.emptyArray()));
            });


        }

        public static <MT, T, R> ChainGenerator<MT, T, R> of(Function<ChainGenerator<MT, T, R>, R> supplier)
        {
            return new ChainGenerator(supplier);
        }

        public ChainGenerator(Function<ChainGenerator<MT, T, R>, R> supplier)
        {
            this.function = supplier;
            this.function.apply(this);

            this.doRec = new Function<T, MT>()
            {
                @Override
                public MT apply(T v)
                {
                    Return<MT> mtReturn = ChainGenerator.this.next(v);
                    return mtReturn.isDone() ? mtReturn.getValue() :
                        ChainGenerator.this.resolveFlatMap(mtReturn.getValue(), this::apply); //TODO
                }
            };

            //this.doRec.apply(null);
        }

        private MT resolveFlatMap(MT target, Function<T, MT> func)
        {
            final java.lang.reflect.Method flatMap = findFlatMap(target);
            try
            {
                return (MT) flatMap.invoke(target, new Function[]{func});
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            catch (InvocationTargetException e)
            {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public void setDoneTrue()
        {
            done = true;
        }

        public R call()
        {
            final R r = function.apply(this);
            setDoneTrue();
            return r;
        }

        @Override
        public Return<MT> next(T t)
        {
            yieldValue = new Value(t);
            return new Return(returnValue, done);
        }

        @Override
        public Value<T> yield(MT expression)
        {
            /////doRec.apply(expression);
            returnValue = expression;
            return yieldValue;
        }

        public R returns(R expression)
        {
            setDoneTrue();
            return expression;
        }
    }

//    public static <T, R> R yield(T expression)
//    {
//
//    }

    public static Flux<Integer> _(Iterable<Integer> it)
    {
        return Flux.fromIterable(it);
    }

    public static Iter range(int start, int end)
    {
        return Iter.range(start, end);
    }

    private static class Iter implements Iterator<Integer>, Iterable<Integer>
    {
        int s = 0;
        final int e;
        final int z;

        public static Iter range(int init)
        {
            return range(init, Integer.MAX_VALUE);
        }

        public static Iter range(int init, int end)
        {
            return range(init, end, 1);
        }

        public static Iter range(int init, int end, int step)
        {
            return new Iter(init, end, step);
        }

        public Iter(int init, int end, int step)
        {
            this.s = init;
            this.e = end;
            this.z = step;
        }

        public Iter(int init)
        {
            this.s = init;
            this.e = Integer.MAX_VALUE;
            this.z = 1;
        }

        @Override
        public boolean hasNext()
        {
            return s < e;
        }

        @Override
        public Integer next()
        {
            return s++;
        }

        @Override
        public Iterator<Integer> iterator()
        {
            return this;
        }
    }
}
