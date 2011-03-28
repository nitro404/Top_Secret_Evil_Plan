package imaging;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.media.Buffer;
import javax.media.Controller;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.ResourceUnavailableEvent;
import javax.media.StartEvent;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;

public class Webcam implements ControllerListener {
	
	private Dimension m_resolution;
	private MediaLocator m_mediaLocator;
	private Player m_webcam;
	private boolean m_active;
	private boolean m_deallocated;
	private FrameGrabbingControl m_frameGrabber;
	private Object m_waitSync;
	private boolean m_transition;
	
	final public static int DEFAULT_WIDTH = 640;
	final public static int DEFAULT_HEIGHT = 480;
	final public static Dimension DEFAULT_RESOLUTION = new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	
	public Webcam(int width, int height) {
		this(new Dimension(width, height));
	}
	
	public Webcam(Dimension resolution) {
		m_resolution = (resolution == null) ? DEFAULT_RESOLUTION : resolution;
		if(m_resolution.width < 16 || m_resolution.height < 16 || m_resolution.width > 4096 || m_resolution.height > 4096) {
			m_resolution = DEFAULT_RESOLUTION;
		}
		
		m_active = false;
		m_deallocated = false;
		m_mediaLocator = new MediaLocator("vfw://0");
		m_waitSync = new Object();
		m_transition = true;
	}
	
	public boolean initialize() {
		if(m_active && !m_deallocated) { return false; }
		
		try { m_webcam = Manager.createRealizedPlayer(m_mediaLocator); }
		catch(Exception e) { return false; }
		
		m_webcam.addControllerListener(this);
		
		try { m_frameGrabber = (FrameGrabbingControl) m_webcam.getControl("javax.media.control.FrameGrabbingControl"); }
		catch(Exception e) { m_frameGrabber = null; }
		if(m_frameGrabber == null) { return false; }
		
		m_webcam.start();
		
		synchronized(m_waitSync) {
			try {
				while(m_webcam.getState() != Controller.Started && m_transition) {
					m_waitSync.wait();
				}
			}
			catch(Exception e) { }
		}
		
		if(m_transition) {
			m_active = true;
		}
		
		return m_transition;
	}
	
	public boolean active() {
		return m_active;
	}
	
	synchronized public void stop() {
		m_active = false;
		m_webcam.stop();
	}
	
	synchronized public void deallocate() {
		stop();
		m_deallocated = true;
		m_frameGrabber = null;
		m_webcam.removeControllerListener(this);
		m_webcam.close();
		m_webcam.deallocate();
		m_webcam = null;
	}
	
	public boolean setWidth(int width) {
		if(width < 16 || width > 4096) { return false; }
		m_resolution.width = width;
		return true;
	}
	
	public boolean setHeight(int height) {
		if(height < 16 || height > 4096) { return false; }
		m_resolution.height = height;
		return true;
	}
	
	public boolean setResolution(Dimension resolution) {
		if(resolution == null ||
		   resolution.width < 16 ||
		   resolution.height < 16 ||
		   resolution.width > 4096 ||
		   resolution.height > 4096) { return false; }
		
		m_resolution = new Dimension(resolution.width, resolution.height);
		return true;
	}
	
	synchronized public BufferedImage capture() {
		if(!m_active) { return null; }
		
		Buffer frame = m_frameGrabber.grabFrame();
		if(frame == null) { return null; }
		
		VideoFormat format = (VideoFormat) frame.getFormat();
		if(format == null) { return null; }
		
		Dimension sourceResolution = format.getSize();
		if(sourceResolution == null || sourceResolution.width < 0 || sourceResolution.height < 0) { return null; }
		
		BufferToImage bufferToImage = new BufferToImage(format);
		
		Image image = bufferToImage.createImage(frame);
		
		BufferedImage snapshot = new BufferedImage(m_resolution.width, m_resolution.height, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g = snapshot.createGraphics();
		g.scale((double) m_resolution.width / (double) sourceResolution.width, (double) m_resolution.height / (double) sourceResolution.height);
		g.drawImage(image, 0, 0, null);
		g.dispose();
		
		return snapshot;
	}
	
	public void controllerUpdate(ControllerEvent e) {
		if(e instanceof StartEvent) {
			synchronized(m_waitSync) {
				m_transition = true;
				m_waitSync.notifyAll();
			}
		}
		else if(e instanceof ResourceUnavailableEvent) {
			synchronized(m_waitSync) {
				m_transition = false;
				m_waitSync.notifyAll();
			}
		}
	}
	
}
