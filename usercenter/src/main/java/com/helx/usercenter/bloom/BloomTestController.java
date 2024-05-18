package com.helx.usercenter.bloom;

import org.redisson.api.RBloomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BloomTestController {

    @Autowired
    private RBloomFilter<String> orderBloomFilter;

    @GetMapping("/testBloom")
    public Object testBloom(@RequestParam("msg") String msg){
        orderBloomFilter.add("何");
        orderBloomFilter.add("立");
        orderBloomFilter.add("新");
        orderBloomFilter.add("是");
        orderBloomFilter.add("我");
        orderBloomFilter.add("老");
        orderBloomFilter.add("师");

        return orderBloomFilter.contains(msg);
    }
}
