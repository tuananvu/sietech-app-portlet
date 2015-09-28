package syneren.sietech.hht;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.faces.bridge.component.HtmlInputFile;
import com.liferay.faces.bridge.model.UploadedFile;

@ManagedBean(name = "hhtinputBean")
@RequestScoped
public class HHTinputBean{
	private transient HtmlInputFile attachment;
	public HtmlInputFile getAttachment() {
		return attachment;
	}

	public void setAttachment(HtmlInputFile attachment) {
		this.attachment = attachment;
	}

	private String fileContent;
	private String colorChannel;
	private String emd;
	private String iterationNo;
	private String windowSize;
	private String threshold;
	private String hsa;
	private String chip;
	private final static Logger LOGGER = Logger.getLogger(HHTinputBean.class
			.getCanonicalName());
	private String fileName;
	private List<Integer> iterationList = new ArrayList<Integer>();
	private String filePath;	

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public List<Integer> getIterationList() {
		return iterationList;
	}

	public void setIterationList(List<Integer> iterationList) {
		this.iterationList = iterationList;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getColorChannel() {
		return colorChannel;
	}

	public void setColorChannel(String colorChannel) {
		this.colorChannel = colorChannel;
	}

	public String getEmd() {
		return emd;
	}

	public void setEmd(String emd) {
		this.emd = emd;
	}

	public String getIterationNo() {
		return iterationNo;
	}

	public void setIterationNo(String iterationNo) {
		this.iterationNo = iterationNo;
		for (int i = 1; i <= Integer.parseInt(iterationNo); i++) {
			iterationList.add(i);
		}
	}

	public String getWindowSize() {
		return windowSize;
	}

	public void setWindowSize(String windowSize) {
		this.windowSize = windowSize;
	}

	public String getThreshold() {
		return threshold;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}

	public boolean isHsa() {
		return "true".equals(hsa);
	}

	public void setHsa(boolean hsa) {
		this.hsa = hsa ? "1" : "0";
	}

	public String getChip() {
		return chip;
	}

	public void setChip(String chip) {
		this.chip = chip;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public HHTinputBean() {
		colorChannel = "0";
		emd = "1";
		iterationNo = "1";
		windowSize = "3";
		threshold = "1.0";
		hsa = "0";
		chip = "1";
		
	}
	@PostConstruct
	public void init(){
		colorChannel = "0";
		emd = "1";
		iterationNo = "1";
		windowSize = "3";
		threshold = "1.0";
		hsa = "0";
		chip = "1";
		
	}
//	public void uploadCase(ActionRequest actionRequest,
//			ActionResponse actionRresponse) throws PortletException,
//			IOException {
//
//		String folder = getInitParameter("uploadFolder");
//		realPath = getPortletContext().getRealPath("/");
//
//		logger.info("RealPath" + realPath + " UploadFolder :" + folder);
//		try {
//			logger.info("Siamo nel try");
//			UploadPortletRequest uploadRequest = PortalUtil
//					.getUploadPortletRequest(actionRequest);
//			System.out.println("Size: " + uploadRequest.getSize("fileName"));
//
//			if (uploadRequest.getSize("fileName") == 0) {
//				SessionErrors.add(actionRequest, "error");
//			}
//
//			String sourceFileName = uploadRequest.getFileName("fileName");
//			File file = uploadRequest.getFile("fileName");
//
//			logger.info("Nome file:" + uploadRequest.getFileName("fileName"));
//			File newFile = null;
//			newFile = new File(folder + sourceFileName);
//			logger.info("New file name: " + newFile.getName());
//			logger.info("New file path: " + newFile.getPath());
//
//			InputStream in = new BufferedInputStream(
//					uploadRequest.getFileAsStream("fileName"));
//			FileInputStream fis = new FileInputStream(file);
//			FileOutputStream fos = new FileOutputStream(newFile);
//
//			byte[] bytes_ = FileUtil.getBytes(in);
//			int i = fis.read(bytes_);
//
//			while (i != -1) {
//				fos.write(bytes_, 0, i);
//				i = fis.read(bytes_);
//			}
//			fis.close();
//			fos.close();
//			Float size = (float) newFile.length();
//			System.out.println("file size bytes:" + size);
//			System.out.println("file size Mb:" + size / 1048576);
//
//			logger.info("File created: " + newFile.getName());
//			SessionMessages.add(actionRequest, "success");
//
//		} catch (FileNotFoundException e) {
//			System.out.println("File Not Found.");
//			e.printStackTrace();
//			SessionMessages.add(actionRequest, "error");
//		} catch (NullPointerException e) {
//			System.out.println("File Not Found");
//			e.printStackTrace();
//			SessionMessages.add(actionRequest, "error");
//		}
//
//		catch (IOException e1) {
//			System.out.println("Error Reading The File.");
//			SessionMessages.add(actionRequest, "error");
//			e1.printStackTrace();
//		}
//
//	}

	public String processHHT() {
		@SuppressWarnings("deprecation")
		UploadedFile uploadFile = attachment.getUploadedFile();
		
		fileName =  uploadFile.getName();//getFileNameWithoutExtension(file);
		//RenderRequest renderRequest= (RenderRequest)(FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("javax.portlet.request"));
		PortletRequest pr=(PortletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		PortletContext pc = pr.getPortletSession().getPortletContext();
		System.out.println("TESTTTTTTTTT: " + pr.getContextPath());
		System.out.println("TESTTTTTTTTT: " + pc.getRealPath(""));		
		String batPath = pc.getRealPath("") + "/app";
		
		 final String path = batPath + "\\images";//"C:\\Users\\anguyen\\Documents\\WORKING\\test\\HHT2\\";
		 OutputStream out = null;
			InputStream fileContent = null;
			// final PrintWriter writer = response.getWriter();
			try {
				out = new FileOutputStream(new File(path + File.separator
						+ fileName));
				fileContent = uploadFile.getInputStream();//file.getInputStream();
				int read = 0;
				final byte[] bytes = new byte[1024];
				while ((read = fileContent.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
				// writer.println("New file " + fileName + " created at " + path);
				
				LOGGER.log(Level.INFO, "File {0} being uploaded to {1}",
						new Object[] { fileName, path });
				if (out != null) {
					out.flush();
					out.close();
				}
				if (fileContent != null) {
					fileContent.close();
				}
			} catch (FileNotFoundException fne) {
				// writer.println("You either did not specify a file to upload or are trying to upload a file "
				// + "to a protected or nonexistent location.");
				// writer.println("<br/> ERROR: " + fne.getMessage());
				LOGGER.log(Level.SEVERE, "Problems during file upload. Error: {0}",
						new Object[] { fne.getMessage() });
				return "failure";
			} catch(IOException ioe){
				LOGGER.log(Level.SEVERE, "Problems with file I/O. Error: {0}",
						new Object[] { ioe.getMessage() });
				return "failure";
			}			
			
			String batFile = batPath + "\\run_EXE_GPU_OpenCV_HHT2.bat " // "C:\\Users\\anguyen\\Documents\\WORKING\\test\\HHT2\\run_EXE_GPU_OpenCV_HHT2.bat "//"/home/synuser/HHT2/run_CPU_HHT2_EMD2_HSA2.sh " //"C:\\Users\\anguyen\\Documents\\WORKING\\test\\HHT2\\run_CPU_HHT2_EMD2_HSA2.bat "
					+ batPath + " "
					+ "images\\" + fileName + " "
					+ "images\\" + fileName + "_folder "
					+ colorChannel + " "
					+ emd + " "
					+ iterationNo + " "
					+ windowSize + " "
					+ threshold + " "
					+ hsa + " "
					+ chip + " "
					;
					
					
			java.lang.Process process;			
			
			try {
				process = Runtime.getRuntime().exec(batFile);
			// any error message?
	        StreamGobbler errorGobbler = new 
	            StreamGobbler(process.getErrorStream(), "ERROR");            
	        
	        // any output?
	        StreamGobbler outputGobbler = new 
	            StreamGobbler(process.getInputStream(), "OUTPUT");
	            
	        // kick them off
	        errorGobbler.start();
	        outputGobbler.start();
				process.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "failure";
			} catch(IOException ioe){
				LOGGER.log(Level.SEVERE, "Problems with file I/O. Error: {0}",
						new Object[] { ioe.getMessage() });
				return "failure";
			}	
			System.out.println(batFile);
			//filePath = path + fileName + "_folder";
			filePath = pc.getRealPath("/images/") + "\\" + fileName + "_folder";
			System.out.println("PATHHHH:" + filePath);
		 return "success";
	}
	
	public void generateImage() throws IOException{
		//RenderRequest renderRequest= (RenderRequest)(FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("javax.portlet.request"));
		
		HttpServletRequest request=(HttpServletRequest)(FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("com.liferay.portal.kernel.servlet.PortletServletRequest"));
		HttpServletResponse response=(HttpServletResponse)(FacesContext.getCurrentInstance().getExternalContext().getResponse());
				
		// Get requested image by path info.
        String requestedImage = request.getPathInfo();

        // Check if file name is actually supplied to the request URI.
        if (requestedImage == null) {
            // Do your thing if the image is not supplied to the request URI.
            // Throw an exception, or send 404, or show default/warning image, or just ignore it.
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }
        String imagePath = "C:\\Users\\anguyen\\Documents\\WORKING\\test\\HHT2\\";
        // Decode the file name (might contain spaces and on) and prepare file object.
        File image = new File(imagePath, URLDecoder.decode(requestedImage, "UTF-8"));

        // Check if file actually exists in filesystem.
        if (!image.exists()) {
            // Do your thing if the file appears to be non-existing.
            // Throw an exception, or send 404, or show default/warning image, or just ignore it.
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }

        // Get content type by filename.
        String contentType =  request.getServletContext().getMimeType(image.getName());

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
	}
}

class StreamGobbler extends Thread {
	InputStream is;
	String type;

	StreamGobbler(InputStream is, String type) {
		this.is = is;
		this.type = type;
	}

	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null)
				System.out.println(type + ">" + line);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
