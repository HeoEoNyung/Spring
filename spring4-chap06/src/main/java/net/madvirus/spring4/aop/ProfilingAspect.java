package net.madvirus.spring4.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ProfilingAspect {
	
	@Pointcut("execution(public * net.madvirus.spring4.board..*(..))")
	private void profileTarget() {
		
	}
	
	@Around("profileTarget()")
	public Object trace(ProceedingJoinPoint joinPoint) throws Throwable {
		String sig = joinPoint.getSignature().toShortString();
		System.out.println(sig + "시작");
		long start = System.currentTimeMillis();
		
		try{
			Object result = joinPoint.proceed();
			return result;
		} finally {
			long finish = System.currentTimeMillis();
			System.out.println(sig + "종료");
			System.out.println(sig + "실행 시간 : " + (finish - start) + "ms");
		}
	}
	
}
