package utils;

import java.util.Base64;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class SmbUtil {

	@PersistenceContext(unitName = "smbcustsrv")
	private  EntityManager entityManager;

	public static Integer decodeInteger(String idEncoded) {
		try {
			byte[] decodedBytes = Base64.getDecoder().decode(idEncoded);
			Integer idUser = Integer.valueOf(new String(decodedBytes));
			return idUser;
		} catch (Exception e) {
			return null;
		}
	
	}
	
	public static String decodeString(String idEncoded) {
		try {
			byte[] decodedBytes = Base64.getDecoder().decode(idEncoded);
			return new String(decodedBytes);
		} catch (Exception e) {
			return null;
		}
	
	}

	public static String encodeInteger(Integer intToEncode) {
		try {
			byte[] encodedBytes = Base64.getEncoder().encode(intToEncode.toString().getBytes());
			String base64EncodedInt = new String(encodedBytes);
			return base64EncodedInt;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String encodeString(String strToEncode) {
		try {
			byte[] encodedBytes = Base64.getEncoder().encode(strToEncode.getBytes());
			String base64EncodedStr = new String(encodedBytes);
			return base64EncodedStr;
		} catch (Exception e) {
			return null;
		}
	}

}
