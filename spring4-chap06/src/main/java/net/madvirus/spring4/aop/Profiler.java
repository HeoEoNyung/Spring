package net.madvirus.spring4.aop;

import org.aspectj.lang.ProceedingJoinPoint;

public class Profiler {
	
	public Object trace(ProceedingJoinPoint joinPoint) throws Throwable {
		String sig = joinPoint.getSignature().toShortString();
		System.out.println(sig + "시작");
		long start = System.currentTimeMillis();
		try{
			Object result = joinPoint.proceed();
			return result;
		}finally {
			long finish = System.currentTimeMillis();
			System.out.println(sig + "종료");
			System.out.println(sig + "실행 시간"+ (finish - start)+"ms");
		}
	}
	
}
