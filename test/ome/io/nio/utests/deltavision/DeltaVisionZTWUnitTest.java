/*
 *   $Id: HelperUnitTest.java 1489 2007-04-26 14:48:50Z david $
 *
 *   Copyright 2006 University of Dundee. All rights reserved.
 *   Use is subject to license terms supplied in LICENSE.txt
 */
package ome.io.nio.utests.deltavision;

import java.nio.ByteBuffer;

import org.testng.annotations.*;

import junit.framework.TestCase;
import ome.io.nio.DeltaVision;
import ome.io.nio.DeltaVisionHeader;
import ome.model.core.OriginalFile;
import ome.util.Utils;

public class DeltaVisionZTWUnitTest extends TestCase
{
	private DeltaVision getDeltaVisionPixelBuffer()
	{
    	OriginalFile file = new DeltaVisionOriginalFile();
    	DeltaVision dv = new DeltaVision(file.getPath(), file);
    	dv.setSequence(DeltaVisionHeader.ZTW_SEQUENCE);
    	return dv;
	}
    
	@Test(groups={"manual"})
    public void testFirstPlaneSecondTimepointMd5() throws Exception
    {
    	DeltaVision dv = getDeltaVisionPixelBuffer();
    	ByteBuffer buf = dv.getPlane(0, 0, 1).getData();
    	String md = Utils.bytesToHex(Utils.calculateMessageDigest(buf));
    	assertEquals("a2c8e1551c3a5857c005223a98330842", md);
    }

	@Test(groups={"manual"})
    public void testFirstPlaneSecondChannelMd5() throws Exception
    {
    	DeltaVision dv = getDeltaVisionPixelBuffer();
    	ByteBuffer buf = dv.getPlane(0, 1, 0).getData();
    	String md = Utils.bytesToHex(Utils.calculateMessageDigest(buf));
    	assertEquals("317d05abe4b5266562f132faa97e204f", md);
    }
    
	@Test(groups={"manual"})
    public void testSecondPlaneFirstChannelMd5() throws Exception
    {
    	DeltaVision dv = getDeltaVisionPixelBuffer();
    	ByteBuffer buf = dv.getPlane(1, 0, 0).getData();
    	String md = Utils.bytesToHex(Utils.calculateMessageDigest(buf));
    	assertEquals("73ab6431bca5f102882f956162d30d3b", md);
    }
    
	@Test(groups={"manual"})
    public void testLastPlaneMd5() throws Exception
    {
    	DeltaVision dv = getDeltaVisionPixelBuffer();
    	ByteBuffer buf = dv.getPlane(dv.getSizeZ() - 1,
    	                             dv.getSizeC() - 1,
    	                             dv.getSizeT() - 1).getData();
    	String md = Utils.bytesToHex(Utils.calculateMessageDigest(buf));
    	assertEquals("a78c365ab044e179b6cae0b6150df3a4", md);
    }
}
