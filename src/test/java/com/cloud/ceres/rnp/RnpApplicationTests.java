package com.cloud.ceres.rnp;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;

import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertTrue;

@SpringBootTest
class RnpApplicationTests {

    Logger logger = LoggerFactory.getLogger(RnpApplicationTests.class);

    @Test
    public void testBeanGenerator() throws Exception{
        BeanGenerator beanGenerator = new BeanGenerator();
        beanGenerator.addProperty("value",String.class);
        Object myBean = beanGenerator.create();
        Method setter = myBean.getClass().getMethod("setValue",String.class);
        setter.invoke(myBean,"Hello cglib");
        Method getter = myBean.getClass().getMethod("getValue");
        Assert.assertEquals("Hello cglib",getter.invoke(myBean));
    }

    @Test
    public void testBeanMap() throws Exception{
        BeanGenerator generator = new BeanGenerator();
        generator.addProperty("username",String.class);
        generator.addProperty("password",String.class);
        Object bean = generator.create();
        Method setUserName = bean.getClass().getMethod("setUsername", String.class);
        Method setPassword = bean.getClass().getMethod("setPassword", String.class);
        setUserName.invoke(bean, "admin");
        setPassword.invoke(bean,"password");
        BeanMap map = BeanMap.create(bean);
        Assert.assertEquals("admin", map.get("username"));
        Assert.assertEquals("password", map.get("password"));
    }

    @Test
    public void testAutoCommit() throws Exception {
       /* logger.info("Start auto");
        ContainerProperties containerProps = new ContainerProperties("topic1", "topic2");
        final CountDownLatch latch = new CountDownLatch(4);
        containerProps.setMessageListener(new MessageListener<Integer, String>() {

            @Override
            public void onMessage(ConsumerRecord<Integer, String> message) {
                logger.info("received: " + message);
                latch.countDown();
            }

        });
        KafkaMessageListenerContainer<Integer, String> container = createContainer(containerProps);
        container.setBeanName("testAuto");
        container.start();
        Thread.sleep(1000); // wait a bit for the container to start
        KafkaTemplate<Integer, String> template = createTemplate();
        template.setDefaultTopic(topic1);
        template.sendDefault(0, "foo");
        template.sendDefault(2, "bar");
        template.sendDefault(0, "baz");
        template.sendDefault(2, "qux");
        template.flush();
        assertTrue(latch.await(60, TimeUnit.SECONDS));
        container.stop();
        logger.info("Stop auto");*/

    }

}
