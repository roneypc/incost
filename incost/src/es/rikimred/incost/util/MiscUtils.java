/**
 * 
 */
package es.rikimred.incost.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Utilidades miscelaneas
 * @author jrneyra
 */
public class MiscUtils {

	/**
	 * Retorna 'string' codificada en AES (SHA-256)
	 * @param string
	 * @return
	 */
	public static String encode(final String string) {
		try {
			// Create MessageDigest and encoding for input String
			final MessageDigest digest = MessageDigest.getInstance("SHA-256");
			final byte[] hash = digest.digest(string.getBytes("UTF-8"));

			// Hash the Input String
			final StringBuffer sb = new StringBuffer();
			for (final byte element : hash) {
				sb.append(Integer.toString((element & 0xff) + 0x100, 16).substring(1));
			}

			return sb.toString();
		}
		catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * This method verifies the content of a list.
	 * @param clazz
	 * @param c
	 * @return @throws ClassCastException
	 */
	public static <T> List<T> castList(final Class<? extends T> clazz, final Collection<?> c)
			throws ClassCastException {
		final List<T> r = new ArrayList<T>(c.size());
		for (final Object o : c) {
			r.add(clazz.cast(o));
		}
		return r;
	}

	/**
	 * Convierte el texto 'mes' en su valor numérico
	 * @param month
	 * @return
	 */
	public static int getNumberOfMonth(final String month) {
		switch (month) {
			case "Enero":case "Ene":
				return 1;
			case "Febrero":case "Feb":
				return 2;
			case "Marzo":case "Mar":
				return 3;
			case "Abril":case "Abr":
				return 4;
			case "Mayo":case "May":
				return 5;
			case "Junio":case "Jun":
				return 6;
			case "Julio":case "Jul":
				return 7;
			case "Agosto":case "Ago":
				return 8;
			case "Septiembre":case "Sep":
				return 9;
			case "Octubre":case "Oct":
				return 10;
			case "Noviembre":case "Nov":
				return 11;
			default:
				return 12;
		}
	}

	/**
	 * 
	 * @param month
	 * @return
	 */
	public static String getNameOfMonth(final int month, boolean large) {
		switch (month) {
			case 1:
				return large==true? "Enero":"Ene";
			case 2:
				return large==true? "Febrero":"Feb";
			case 3:
				return large==true? "Marzo":"Mar";
			case 4:
				return large==true? "Abril":"Abr";
			case 5:
				return large==true? "Mayo":"May";
			case 6:
				return large==true? "Junio":"Jun";
			case 7:
				return large==true? "Julio":"Jul";
			case 8:
				return large==true? "Agosto":"Ago";
			case 9:
				return large==true? "Septiembre":"Sep";
			case 10:
				return large==true? "Octubre":"Oct";
			case 11:
				return large==true? "Noviembre":"Nov";
			default:
				return large==true? "Diciembre":"Dic";
		}
	}
}
