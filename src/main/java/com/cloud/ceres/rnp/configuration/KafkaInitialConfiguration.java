package com.cloud.ceres.rnp.configuration;

import com.cloud.ceres.rnp.RnpApplication;
import com.cloud.ceres.rnp.kafkaDto.Foo2;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author heian
 * @create 2020-01-29-11:06 上午
 * @description
 */
//@Configuration
public class KafkaInitialConfiguration {

    Logger logger = LoggerFactory.getLogger(RnpApplication.class);

    @Bean
    public ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(ConcurrentKafkaListenerContainerFactoryConfigurer configurer,//yml配置
                                                                                       ConsumerFactory<Object, Object> kafkaConsumerFactory,//默认的消费工厂
                                                                                       KafkaTemplate<Object, Object> template) {
        //设置了字节转化器
        ConcurrentKafkaListenerContainerFactory <Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, kafkaConsumerFactory);
        // 配置一个消费的消息处理异常处理器：重试3次还不成功则将该消息发到死信topic里。
        factory.setErrorHandler(new SeekToCurrentErrorHandler(new DeadLetterPublishingRecoverer(template), 3)); // dead-letter
        return factory;
    }
    //将josn字节转为你想强转的对象
    @Bean
    public RecordMessageConverter converter() {
        return new StringJsonMessageConverter();
    }


    //注解的形式创建topic  分区数 和 副本数
    @Bean
    public NewTopic topic() {
        return new NewTopic("topic1", 1, (short) 1);
    }
    @Bean
    public NewTopic dlt() {
        return new NewTopic("topic1.DLT", 1, (short) 1);
    }

    @KafkaListener(id = "myGroup", topics = {"topic1"})
    public void listen(Foo2 foo) {//自动转化Foo2
        logger.info("Received: " + foo);
        if (foo.getFoo().startsWith("fail")) {
            throw new RuntimeException("failed");
        }
    }

    @KafkaListener(id = "dltGroup", topics = "topic1.DLT")
    public void dltListen(String in) {
        logger.info("Received from DLT: " + in);
    }
}
