package es.rikimred.incost.util;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.apache.log4j.Logger;

/**
 * Productor de Logging para inyeccion del logger log4j
 * @author roberto
 */
@Dependent
public class LoggerProducer {

	/**
	 * Construccion del productor
	 * @param injectionPoint
	 * @return logger
	 */
	@Produces
	@Dependent
	public Logger produceLogger(final InjectionPoint injectionPoint) {
		return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
	}
}