package org.itcgae.siga.logger;

import java.lang.reflect.Method;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

@Aspect
@Component
public class MethodLoggingAspect {

	@Pointcut("@annotation(MethodLogging) && execution(* *(..))")
	public void methodAnotation() {
	}

	@Pointcut("execution(* org.itcgae.siga.*.controllers..*.*(..))")
	public void controllers() {
	}

	@Pointcut("execution(* org.itcgae.siga.*.endpoints..*.*(..))")
	public void webservices() {
	}

	@Around("webservices()")
	public Object paintWsLog(ProceedingJoinPoint pjp) throws Throwable {

		Logger logger = LoggerFactory.getLogger(pjp.getTarget().getClass());

		preWsLogging(logger, pjp);

		long startTime = System.nanoTime();
		Object result = pjp.proceed();
		long finalTime = System.nanoTime();

		postControllerLogging(logger, pjp, result, startTime, finalTime);

		return result;

	}

	@Around("controllers()")
	public Object paintControllerLog(ProceedingJoinPoint pjp) throws Throwable {

		Logger logger = LoggerFactory.getLogger(pjp.getTarget().getClass());

		preControllerLogging(logger, pjp);

		long startTime = System.nanoTime();
		Object result = pjp.proceed();
		long finalTime = System.nanoTime();

		postControllerLogging(logger, pjp, result, startTime, finalTime);

		return result;

	}

	@Around("methodAnotation()")
	public Object paintMethodLog(ProceedingJoinPoint pjp) throws Throwable {

		Logger logger = LoggerFactory.getLogger(pjp.getTarget().getClass());

		preMethodLogging(logger, pjp);

		long startTime = System.nanoTime();
		Object result = pjp.proceed();
		long finalTime = System.nanoTime();

		postMethodLogging(logger, pjp, result, startTime, finalTime);

		return result;

	}

	private void preControllerLogging(Logger logger, ProceedingJoinPoint pjp) {
		try {
			StringBuilder sb = new StringBuilder();
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
					.getRequest();

			MethodSignature signature = (MethodSignature) pjp.getSignature();

			sb.append(String.format("%s ---> %s %s", pjp.getSignature().getName(), request.getMethod(),
					request.getRequestURI().toString()));
			if (signature.getParameterNames().length > 0) {
				for (int i = 0; signature.getParameterNames().length > i; i++) {
					sb.append(String.format(" [%s", signature.getParameterNames()[i]));
					try {
						sb.append(String.format(" = %s]", new ObjectMapper().writeValueAsString(pjp.getArgs()[i])));
					} catch (Exception e) {
						sb.append(String.format(" = %s]", pjp.getArgs()[i].getClass()));
					}
				}
			}
			logger.info(sb.toString());
		} catch (Exception e) {
			logger.error("Error al llamar al metodo preControllerLogging del aspecto de logado", e);
		}
	}

	private void preWsLogging(Logger logger, ProceedingJoinPoint pjp) {
		try {
			StringBuilder sb = new StringBuilder();
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
					.getRequest();

			MethodSignature signature = (MethodSignature) pjp.getSignature();

			sb.append(String.format("%s ---> %s %s", pjp.getSignature().getName(), request.getMethod(),
					request.getRequestURI().toString()));
			if (signature.getParameterNames().length > 0) {
				for (int i = 0; signature.getParameterNames().length > i; i++) {
					sb.append(String.format(" [%s = %s]", signature.getParameterNames()[i],
							pjp.getArgs()[i].toString().replaceAll("\r|\t|\n", "")));
				}
			}
			logger.info(sb.toString());
		} catch (Exception e) {
			logger.error("Error al llamar al metodo preWsLogging del aspecto de logado", e);
		}
	}

	private void postControllerLogging(Logger logger, ProceedingJoinPoint pjp, Object result, long startTime,
			long finalTime) {
		try {
			//HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				//	.getResponse();
			String methodTime = String.format("%.3f", (finalTime - startTime) / 1000000000.0);

			logger.info("{} <--- {} [{}s]", pjp.getSignature().getName(),  methodTime);

		} catch (Exception e) {
			logger.error("Error al llamar al metodo postControllerLogging del aspecto de logado", e);
		}

	}

	@SuppressWarnings("unchecked")
	private void preMethodLogging(Logger logger, ProceedingJoinPoint pjp) {

		try {
			StringBuilder sb = new StringBuilder();
			MethodSignature signature = (MethodSignature) pjp.getSignature();

			Method method = signature.getMethod();
			MethodLogging methodLoggingAnnotation = method.getAnnotation(MethodLogging.class);

			if (methodLoggingAnnotation == null) {
				methodLoggingAnnotation = (MethodLogging) signature.getDeclaringType()
						.getAnnotation(MethodLogging.class);
			}

			sb.append(String.format("Empieza %s", pjp.getSignature().getName()));
			sb.append("(");
			if (methodLoggingAnnotation != null && !methodLoggingAnnotation.showArgs()) {
				sb.append(Arrays.toString(signature.getParameterNames()).replace("[", "").replace("]", ""));
			} else {
				if (signature.getParameterNames().length > 0) {
					for (int i = 0; signature.getParameterNames().length > i; i++) {
						sb.append(String.format("%s", signature.getParameterNames()[i]));
						try {
							sb.append(String.format(" = %s", new ObjectMapper().writeValueAsString(pjp.getArgs()[i])));
						} catch (Exception e) {
							sb.append(String.format(" = %s", pjp.getArgs()[i].getClass()));
						}
						if (i < (signature.getParameterNames().length - 1)) {
							sb.append(", ");
						}
					}
				}
			}
			sb.append(")");
			logger.info(sb.toString());
		} catch (Exception e) {
			logger.error("Error al llamar al metodo preCallLogging del aspecto", e);
		}

	}

	@SuppressWarnings("unchecked")
	private void postMethodLogging(Logger logger, ProceedingJoinPoint pjp, Object result, long startTime,
			long finalTime) {
		try {
			StringBuilder sb = new StringBuilder();
			String methodTime = String.format("%.3f", (finalTime - startTime) / 1000000000.0);

			MethodSignature signature = (MethodSignature) pjp.getSignature();
			@SuppressWarnings("rawtypes")
			Class returnType = ((MethodSignature) signature).getReturnType();

			sb.append(String.format("Termina %s [%s]", pjp.getSignature().getName(), methodTime));

			Method method = signature.getMethod();
			MethodLogging methodLoggingAnnotation = method.getAnnotation(MethodLogging.class);

			if (methodLoggingAnnotation == null) {
				methodLoggingAnnotation = (MethodLogging) signature.getDeclaringType()
						.getAnnotation(MethodLogging.class);
			}
			if (methodLoggingAnnotation.showReturnValue()) {
				sb.append(String.format(" return %s", returnType.getName()));
				try {
					sb.append(String.format(" = %s", new ObjectMapper().writeValueAsString(result)));
				} catch (Exception e) {
					sb.append(String.format(" = %s", result.getClass()));
				}
			}
			logger.info(sb.toString());
		} catch (Exception e) {
			logger.error("Error al llamar al metodo postCallLogging del aspecto", e);
		}

	}

}
