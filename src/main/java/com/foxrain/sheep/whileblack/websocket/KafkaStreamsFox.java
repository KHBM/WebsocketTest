package com.foxrain.sheep.whileblack.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.ForeachAction;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created with intellij IDEA.
 * by 2021 07 2021/07/20 11:48 오전 20
 * User we at 11 48
 * To change this template use File | Settings | File Templates.
 *
 * @author foxrain
 */
@Log4j2
public class KafkaStreamsFox
{
    public static void main(String[] args)
    {
        try
        {
            System.out.println("Hello world");
            Properties props = new Properties();
            props.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-application");
            props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
            props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
            props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.ByteArray().getClass());


            StreamsBuilder builder = new StreamsBuilder();
//            KStream<String, String> textLines =
                builder.<String, Data>stream("wordCount", Consumed.with(Serdes.String(), CustomSerdes.BookSold()))
                    .map((k, v) -> {
                        log.info("FOXKEY : {}, {}", k, v);
                        return new KeyValue<>("key_"+v, v);
                    })
                .mapValues(textLine ->
                {
                     return Arrays.asList(textLine.toLowerCase().split("\\W+"));
                })
                    .map((k, v) -> {
                        log.info("FOXKEY2 : {}, {}", k, v);
                        return new KeyValue<>("key_"+v, v);
                    })
                .foreach((e, a) -> log.info("FOXWhat is e ={}, a ={}", a, e))
//                .flatMap((k, v) -> Arrays.asList(k.toLowerCase().split("\\W+")))
//                .foreach((e, a) -> {});
                ;
                ;

//            KTable<String, Long> wordCounts = textLines
//                .flatMapValues(textLine -> Arrays.asList(textLine.toLowerCase().split("\\W+")))
//                .groupBy((key, word) -> word)
//                .count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as("counts-store"))
//            ;
//            textLines.foreach((e, a) ->
//                log.info("FOXWhat is e ={}, a ={}", a, e));

//        wordCounts.toStream().to("WordsWithCountsTopic", Produced.with(Serdes.String(), Serdes.Long()));

            KafkaStreams streams = new KafkaStreams(builder.build(), props);
            streams.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Map a = new HashMap();;
        }
        finally
        {
            log.error("What happens here?");
        }
    }

    @lombok.Data
    @ToString
    static class Data {
        String data;
        public String toLowerCase()
        {
            return data.toLowerCase();
        }
    }

    public static class JsonDeserializer<T> implements Deserializer<T>
    {
        private final ObjectMapper objectMapper = new ObjectMapper();

        private Class<T> tClass;

        public JsonDeserializer() {
        }

        public JsonDeserializer(Class<T> tClass) {
            this.tClass = tClass;
        }

        @Override
        public void configure(Map<String, ?> props, boolean isKey) {
            // nothing to do
        }

        @Override
        public T deserialize(String topic, byte[] bytes) {
            if (bytes == null)
                return null;

            T data;
            try {
                data = objectMapper.readValue(bytes, tClass);
            } catch (Exception e) {
                throw new SerializationException(e);
            }

            return data;
        }

        @Override
        public void close() {
            // nothing to do
        }
    }

}

final class CustomSerdes {

    public static final class BookSoldSerde extends Serdes.WrapperSerde<KafkaStreamsFox.Data> {
        public BookSoldSerde() {
            super(new JsonSerializer<>(),
                new KafkaStreamsFox.JsonDeserializer<>(KafkaStreamsFox.Data.class));
        }
    }

//        static public final class GenreCountSerde
//            extends Serdes.WrapperSerde<GenreCount> {
//            public GenreCountSerde() {
//                super(new JsonSerializer<>(),
//                    new JsonDeserializer<>(GenreCount.class));
//            }
//        }

    public static Serde<KafkaStreamsFox.Data> BookSold() {
        return new CustomSerdes.BookSoldSerde();
    }

//        public static Serde<GenreCount> GenreCount() {
//            return new CustomSerdes.GenreCountSerde();
//        }
}
 class JsonSerializer<T> implements Serializer<T>
 {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> props, boolean isKey) {
        // nothing to do
    }

    @Override
    public byte[] serialize(String topic, T data) {
        if (data == null)
            return null;

        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error serializing JSON message", e);
        }
    }

    @Override
    public void close() {
        // nothing to do
    }

}