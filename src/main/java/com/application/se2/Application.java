package com.application.se2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;


/**
 * SpringBoot's main Application class containing Java's main() method with invocation
 * and launch of Spring's run time.
 * 
 * @author sgra64
 *
 */
@SpringBootApplication	// same as @Configuration @EnableAutoConfiguration @ComponentScan
public class Application {


	/**
	 * Protected constructor for Spring Boot to create an Application instance.
	 * Application instances must be created by Spring, never by "new".
	 * @param args arguments passed from main()
	 */
	Application( String[] args ) {
		System.out.println( "2. Hello SpringApplication, Constructor called." );
	}


	/**
	 * Entry point after Spring Boot has initialized the application instance.
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		System.out.println( "3. Hello SpringApplication, doSomethingAfterStartup() called." );
	}


	/**
	 * Java's main() method.
	 * 
	 * @param args arguments passed from invoking command.
	 */
	public static void main( final String ... args ) {
		System.out.println( "1. Hello SpringApplication!" );
		System.out.print( "   Initialize Spring Boot and create Application instance." );

		// Initialize Spring Boot and create Application instance.
		ApplicationContext applicationContext = SpringApplication.run( Application.class, args );

		System.out.println( "4. Bye, SpringApplication, " + applicationContext.getId() + "!" );
	}


	/**
	 * Return the Application name.
	 * 
	 * @return Application name.
	 */
	public String getName() {
		return "se2-application";
	}

}
