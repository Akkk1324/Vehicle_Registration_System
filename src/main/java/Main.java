
package com.vehicleregistration;

import com.vehicleregistration.servlet.RegistrationServlet;
import com.vehicleregistration.dao.HibernateUtil;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.DefaultServlet;

public class Main {
    public static void main(String[] args) {
        Server server = null;
        
        try {
            // Add shutdown hook for proper cleanup
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Shutting down application...");
                HibernateUtil.shutdown();
            }));
            
            // Create Jetty server
            server = new Server(5000);
            
            // Create servlet context
            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            context.setContextPath("/");
            server.setHandler(context);
            
            // Add servlets
            context.addServlet(new ServletHolder(new RegistrationServlet()), "/api/registrations/*");
            
            // Serve static files
            ServletHolder staticServlet = new ServletHolder("default", DefaultServlet.class);
            staticServlet.setInitParameter("resourceBase", "./src/main/webapp");
            staticServlet.setInitParameter("dirAllowed", "true");
            context.addServlet(staticServlet, "/*");
            
            // Start server
            server.start();
            System.out.println("Vehicle Registration System started on http://0.0.0.0:5000");
            System.out.println("Access the application at: http://0.0.0.0:5000");
            server.join();
            
        } catch (Exception e) {
            System.err.println("Failed to start server: " + e.getMessage());
            e.printStackTrace();
            if (server != null) {
                try {
                    server.stop();
                } catch (Exception stopException) {
                    System.err.println("Error stopping server: " + stopException.getMessage());
                }
            }
            HibernateUtil.shutdown();
            System.exit(1);
        }
    }
}

