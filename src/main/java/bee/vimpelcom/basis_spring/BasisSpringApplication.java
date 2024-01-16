package bee.vimpelcom.basis_spring;

import bee.vimpelcom.basis_spring.controller.MainController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BasisSpringApplication {

    final static Logger logger = LoggerFactory.getLogger(MainController.class);

    public static void main(String[] args) {
        SpringApplication.run(BasisSpringApplication.class, args);

    }

}
