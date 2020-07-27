package board.common;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

// 예외처리 클래스임을 알리는 어노테이션
@ControllerAdvice
public class ExceptionHandler {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	// 실제 프로젝트에서는 Exception.class 말고 세부 예외 클래스를 쓸 것.
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public ModelAndView defaultExceptionHandler(HttpServletRequest request, Exception exception) {
		//예외 발생시 보여주는 화면
		ModelAndView mv = new ModelAndView("/error/error_default");
		mv.addObject("exception", exception);
		log.error("exception", exception);
		return mv;
	}
}
