package com.boot.test.datasource;

import com.boot.test.datasource.annotations.MasterDataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 *  注解masterDatasource实现
 */
@Aspect
@Component
public class DataSourceAop {
	private static final Logger logger = LoggerFactory.getLogger(DataSourceAop.class);

	@Around("execution(* com.boot.test.dao.mapper.*.*(..))")
	public Object doAroundMapper(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object result = null;
		MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
		Method method = signature.getMethod();// 获取正在执行的方法
		if (DataSourceSwitcher.isManualMasterMode()) {
			// 强制要求主数据源
			DataSourceSwitcher.setMasterWithDontChangeSetMode();// 设置数据源为主数据源
		} else {
			// 根据注解选择数据源
			MasterDataSource dataSource = method.getAnnotation(MasterDataSource.class);
			if (null != dataSource) {
				DataSourceSwitcher.setMasterWithDontChangeSetMode();// 设置数据源为主数据源
			} else {
				DataSourceSwitcher.setSlaveWithDontChangeSetMode();// 设置为从数据源
			    //DFSDFAF
				
				System.out.println();
			} 
		}
		result = proceedingJoinPoint.proceed();
		return result;
	}
}
