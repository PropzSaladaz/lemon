package com.tecnico.lemon;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.Security;

import com.tecnico.lemon.KeyGenerate;
import com.tecnico.lemon.database.KeysServiceFrontend;
import java.util.Base64;
import java.io.FileOutputStream;


@SpringBootApplication
public class LemonApplication {

	public static void main(String[] args) {
		try {
			KeysServiceFrontend keysServiceFrontend = new KeysServiceFrontend();
			String sharedKey = keysServiceFrontend.requestSharedKey();
			byte[] decodedSharedKey = Base64.getDecoder().decode(sharedKey);
			FileOutputStream keyOut = new FileOutputStream("src/main/credentials/shared-key.bin");
			keyOut.write(decodedSharedKey);
			keyOut.close();

			SpringApplication.run(LemonApplication.class, args);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}

