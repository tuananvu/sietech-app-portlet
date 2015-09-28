package syneren.sietech.hht;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;

import javax.faces.context.FacesContext;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ImageServlet
 */
@WebServlet("/imageservlet/*")
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private String imagePath;
    /**
     * Default constructor. 
     */
    public ImageServlet() {
        // TODO Auto-generated constructor stub
    }
    public void init() throws ServletException {
    	
        // Define base path somehow. You can define it as init-param of the servlet.
        //this.imagePath = "C:\\Users\\anguyen\\Documents\\WORKING\\test\\HHT2\\";

        // In a Windows environment with the Applicationserver running on the
        // c: volume, the above path is exactly the same as "c:\var\webapp\images".
        // In Linux/Mac/UNIX, it is just straightforward "/var/webapp/images".
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//PortletRequest pr=(PortletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
 		//PortletContext pc = pr.getPortletSession().getPortletContext();		
 		//imagePath = "C:\\Users\\anguyen\\Downloads\\liferay\\jboss-as-7.1.1.Final\\standalone\\deployments\\sietech-app-portlet.war\\app\\images\\";//pc.getRealPath("/app/images/");
 		ServletContext servletContext = request.getServletContext();
 		System.out.println("Path from servletcontext: " + servletContext.getRealPath("/app/images/"));
		imagePath = servletContext.getRealPath("/app/images/");
		
		//PortletRequest pr=(PortletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		//PortletContext pc = pr.getPortletSession().getPortletContext();
		//imagePath = pc.getRealPath("\\app\\images");
		//System.out.println("Path from portletcontext: " + pc.getRealPath("/"));
		
        // Get requested image by path info.
        String requestedImage = request.getPathInfo();

        // Check if file name is actually supplied to the request URI.
        if (requestedImage == null) {
            // Do your thing if the image is not supplied to the request URI.
            // Throw an exception, or send 404, or show default/warning image, or just ignore it.
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }
       
		
        // Decode the file name (might contain spaces and on) and prepare file object.
        File image = new File(imagePath, URLDecoder.decode(requestedImage, "UTF-8"));
        System.out.println("Servlet: " + image.getAbsolutePath());
        // Check if file actually exists in filesystem.
        if (!image.exists()) {
            // Do your thing if the file appears to be non-existing.
            // Throw an exception, or send 404, or show default/warning image, or just ignore it.
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }

        // Get content type by filename.
        String contentType = getServletContext().getMimeType(image.getName());

        // Check if file is actually an image (avoid download of other files by hackers!).
        // For all content types, see: http://www.w3schools.com/media/media_mimeref.asp
        if (contentType == null || !contentType.startsWith("image")) {
            // Do your thing if the file appears not being a real image.
            // Throw an exception, or send 404, or show default/warning image, or just ignore it.
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }

        // Init servlet response.
        response.reset();
        response.setContentType(contentType);
        response.setHeader("Content-Length", String.valueOf(image.length()));

        // Write image content to response.
        Files.copy(image.toPath(), response.getOutputStream());
        
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
