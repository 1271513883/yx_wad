package com.baizhi.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CacheAspect {
    @Autowired
    private RedisTemplate redisTemplate;

    @Around("execution(* com.baizhi.service.*Impl.query*(..))")
    public Object addCache(ProceedingJoinPoint joinPoint){
        System.out.println("进入环绕通知");

        StringBuilder sb = new StringBuilder();
        //获取路径
        String className = joinPoint.getTarget().getClass().getName();
        //获取方法名
        String methodName = joinPoint.getSignature().getName();

        sb.append(className).append(methodName);
        //获取实参值
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            sb.append(arg);
        }
        System.out.println(sb);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        HashOperations hashOperations = redisTemplate.opsForHash();
        Object proceed=null;
        //判断redis中是否存在键
        if(hashOperations.hasKey(className, sb.toString())){
            proceed = hashOperations.get(className, sb.toString());
        }else{
            try {
                proceed = joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            hashOperations.put(className,sb.toString(), proceed);
        }
        return proceed;
    }


    @After("@annotation(com.baizhi.annotation.DeleteCache)")
    public void delCache(JoinPoint joinPoint){
        System.out.println("后置通知");

        //类的全限定名
        String name = joinPoint.getTarget().getClass().getName();


        redisTemplate.delete(name);

    }
}
