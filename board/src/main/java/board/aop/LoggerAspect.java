package board.aop;

// AOP : 컨트롤러, 서비스, 매퍼 실행 시 로그 출력 공통 로직

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
@Aspect // AOP 설정 어노테이션
public class LoggerAspect {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	/*
	 *  @Around : 메서드 실행 전후 또는, 예외 발생 시점에 사용 가능
	 *  excution : 포인트컷 표현식으로 적용할 메서드를 명시할 때 사용됨.
	 */
	@Around("execution(* board..controller.*Controller.*(..)) or execution(* board..service.*Impl.*(..)) or execution(* board..mapper.*Mapper.*(..))")
	public Object logPrint(ProceedingJoinPoint joinPoint) throws Throwable{
		String type = "";
		String name = joinPoint.getSignature().getDeclaringTypeName();
		if(name.indexOf("Controller") > -1) {
			type = "Controller \t: ";
		}
		else if(name.indexOf("Service") > -1){
			type = "ServiceImpl \t: ";
		}
		else if(name.indexOf("Mapper") > -1) {
			type = "Mapper \t\t: ";
		}
		log.debug(type + name + "." + joinPoint.getSignature().getName() + "()");
		return joinPoint.proceed();
	}
}
