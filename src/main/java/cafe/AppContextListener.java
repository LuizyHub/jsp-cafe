package cafe;

import cafe.users.UserRegisterServlet;
import cafe.users.UsersProfileServlet;
import cafe.users.UsersServlet;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.function.Supplier;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Factory factory = new Factory();

        addServlet(sce, "/user/register", UserRegisterServlet::new);
        addServlet(sce, "/users", () -> new UsersServlet(factory.userRepository()));
        addServlet(sce, "/users/*", () -> new UsersProfileServlet(factory.userRepository()));
    }
    private void addServlet(ServletContextEvent sce, String mapping, Supplier<Servlet> servletSupplier) {
        Servlet servlet = servletSupplier.get();
        sce.getServletContext().addServlet(servlet.getClass().getName().substring(
                0, servlet.getClass().getName().lastIndexOf('.')
        ), servlet).addMapping(mapping);
    }
}
