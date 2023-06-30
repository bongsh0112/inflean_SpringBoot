package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component  // 만약 springconfig에 @Bean으로 TimeTracAop를 등록해놓았다면, @Component나 @Bean 둘중에 하나는 지워야한다
public class TimeTraceAop {

    @Around("execution(* hello.hellospring..*(..))") // 이 패키지 밑에 있는거에는 모두 적용
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();

        System.out.println("START: " + joinPoint.toString());

        try {
            return joinPoint.proceed(); // 다음 프로세스로 넘어
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;

            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}
