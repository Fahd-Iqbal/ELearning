package AuthenticationFilter;



import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// the point of this page is to implement the fact that nonce you log out and you click back it does not go back to the user portal 
public class AuthenticationFilter implements Filter {

    private ServletContext context;
 // crrating the filter
    public void init(FilterConfig fConfig) throws ServletException {
        this.context = fConfig.getServletContext();
        this.context.log("AuthenticationFilter initialized");
    }
          // checking wether the session exists or not 
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false); // we do that to see if there is a current session or not

        if (session == null) {   //checking whether the session exists
            this.context.log("Unauthorized access request"); 
            // keeping req
            res.sendRedirect("UserLogin.html");
        } else {
            // pass the request along the filter chain
            chain.doFilter(request, response);
        }
    }
// have to add filter to web xml it only added the servlet but not the filter
    public void destroy() {
        //close any resources here
    }
}
