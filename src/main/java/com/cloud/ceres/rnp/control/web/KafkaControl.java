package com.cloud.ceres.rnp.control.web;

import com.cloud.ceres.rnp.kafkaDto.Foo1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author heian
 * @create 2020-01-29-10:06 上午
 * @description
 */
@RestController
@RequestMapping("/kafka")
public class KafkaControl {

    @Autowired
    private KafkaTemplate<Object, Object> template;

    @GetMapping(path = "/send/foo/{what}")
    public void sendFoo(@PathVariable String what) {
        this.template.send("topic1", new Foo1(what));
    }

}
