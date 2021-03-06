package smbcustsrv.testimpl.unit;

import static org.junit.Assert.*;

import org.junit.Test;

import smbcustsrv.testinteface.UnitTest;
import utils.SmallSmbUtils;

public class TestSmbUtils implements UnitTest {

	@Test
	public void testDecodeInteger() {
		String base64EncodedInteger = "NTQ=";
		
		Integer decodedInteger = SmallSmbUtils.decodeInteger(base64EncodedInteger);
		assertNotNull(decodedInteger);
		assertNotEquals(decodedInteger.longValue(), -20);
		assertEquals(decodedInteger.longValue(), 54);
		
	}

	@Test
	public void testEncodeInteger() {
		Integer intToEncode = 54;
		
		String base64EncodedInteger = SmallSmbUtils.encodeInteger(intToEncode);
		
		assertNotNull(base64EncodedInteger);
		assertNotEquals(base64EncodedInteger, "");
		assertEquals(base64EncodedInteger, "NTQ=");
		
	}
	
	@Test
	public void testDecodeString() {
		String base64EncodedDecode = "NTQ=";
		String decodedSting = SmallSmbUtils.decodeString(base64EncodedDecode);
		
		assertNotNull(decodedSting);
		assertNotEquals(decodedSting, "-20");
		assertEquals(decodedSting, "54");
		
	}
	
	@Test
	public void testEncodeString() {
		String strToEncode = "54";
		
		String base64EncodedString = SmallSmbUtils.encodeString(strToEncode);
		assertNotNull(base64EncodedString);
		assertNotEquals(base64EncodedString, "");
		assertEquals(base64EncodedString,"NTQ=");
		
	}
	
}
