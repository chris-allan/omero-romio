package ome.io.nio.utests.deltavision;

import java.nio.ByteBuffer;

import org.testng.annotations.Test;

import junit.framework.TestCase;

import ome.io.nio.DeltaVision;
import ome.io.nio.utests.Helper;
import ome.model.core.OriginalFile;

public class UnsignedSixteenBitBasicIOUnitTest extends TestCase
{
	private static final String path = 
		"/Users/callan/testimages/off_by_1_b.dv";
	
	public DeltaVision getDeltaVisionPixelBuffer()
	{
    	OriginalFile file = new DeltaVisionOriginalFile(path);
    	DeltaVision dv = new DeltaVision(file.getPath(), file);
    	return dv;
	}
	
    @Test(groups={"manual"})
    public void testFirstPlaneOffset() throws Exception
    {
    	DeltaVision dv = getDeltaVisionPixelBuffer();
    	long offset = dv.getPlaneOffset(0, 0, 0);
    	assertEquals(39936, offset);
    }
    
    @Test(groups={"manual"})
    public void testFirstPlaneSize() throws Exception
    {
    	DeltaVision dv = getDeltaVisionPixelBuffer();
    	ByteBuffer buf = dv.getPlane(0, 0, 0).getData();
    	assertEquals(460800, buf.capacity());
    }
    
    @Test(groups={"manual"})
    public void testFirstPlaneMd5() throws Exception
    {
    	DeltaVision dv = getDeltaVisionPixelBuffer();
    	ByteBuffer buf = dv.getPlane(0, 0, 0).getData();
    	String md = Helper.bytesToHex(Helper.calculateMessageDigest(buf));
    	assertEquals("b495cb7c7dda3930fbf923cfbcb8c8e2", md);
    }
    
    @Test(groups={"manual"})
    public void testFirstPlaneReorderedMd5() throws Exception
    {
    	DeltaVision dv = getDeltaVisionPixelBuffer();
    	byte[] buf = new byte[460800];
    	buf = dv.getPlaneDirect(0, 0, 0, buf);
    	String md = Helper.bytesToHex(Helper.calculateMessageDigest(buf));
    	assertEquals("acc7c7a4992acd61572cbaca0bbe4d6c", md);
    }
    
    @Test(groups={"manual"})
    public void testFirstPlaneRegionMd5() throws Exception
    {
    	DeltaVision dv = getDeltaVisionPixelBuffer();
    	byte[] buf = new byte[16];
    	buf = dv.getPlaneRegionDirect(0, 0, 0, 8, 32, buf);
    	String md = Helper.bytesToHex(Helper.calculateMessageDigest(buf));
    	assertEquals("6b1145ec4d898da29b36bcd1ea22c702", md);
    }
    
    @Test(groups={"manual"})
    public void testSecondPlaneRegionMd5() throws Exception
    {
    	DeltaVision dv = getDeltaVisionPixelBuffer();
    	byte[] buf = new byte[16];
    	buf = dv.getPlaneRegionDirect(0, 0, 0, 8, 128, buf);
    	String md = Helper.bytesToHex(Helper.calculateMessageDigest(buf));
    	assertEquals("c8aff9dc50866023bed7137b2fdd602d", md);
    }
}
