
package redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-mvc.xml", "classpath*:spring-mybatis.xml"})
public class TestRedis {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final Logger log = LoggerFactory.getLogger(TestRedis.class);

    @Test
    public void test() {
        redisTemplate.opsForValue().set("xu", "强强123567");
        log.info("value：" + redisTemplate.opsForValue().get("xu"));
    }

}

