package yaindtask.ui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.mozilla.universalchardet.UniversalDetector;

public class IOUtils {

	public static String readFile(String path) throws FileNotFoundException {
		String fileName = path;
		FileInputStream fis = new FileInputStream(fileName);

		byte[] encoded = new byte[10];
		try {
			encoded = Files.readAllBytes(Paths.get(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String encoding = detectEncoding(fis);
		if(encoding == null) {
			return Charset.forName("utf8").decode(ByteBuffer.wrap(encoded))
					.toString();
		}
		if (Charset.forName(encoding).equals(Charset.forName("cp1251"))
				|| Charset.forName(encoding).equals(Charset.forName("utf8"))
				|| Charset.forName(encoding).equals(Charset.forName("UTF-16LE"))) {
			return Charset.forName(encoding).decode(ByteBuffer.wrap(encoded))
					.toString();
		} else {
			return "";
		}
	}

	public static String detectEncoding(String text) {
		byte[] encoded = text.getBytes();
		byte[] buf = new byte[4096];
		// (1)
		UniversalDetector detector = new UniversalDetector(null);

		// (2)
		int i = 0;
		for (i = 0; i < encoded.length && !detector.isDone(); i++) {
			detector.handleData(buf, 0, encoded[i]);
		}
		// (3)
		detector.dataEnd();

		// (4)
		String encoding = detector.getDetectedCharset();

		// (5)
		detector.reset();
		if (encoding == null) {
			encoding = "UTF-8";
		}
		return encoding;
	}

	public static String detectEncoding(FileInputStream fis) {
		byte[] buf = new byte[4096];
		// (1)
		UniversalDetector detector = new UniversalDetector(null);

		// (2)
		int nread;
		try {
			while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
				detector.handleData(buf, 0, nread);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// (3)
		detector.dataEnd();

		// (4)
		String encoding = detector.getDetectedCharset();
		// if (encoding != null) {
		// System.out.println("Detected encoding = " + encoding);
		// } else {
		// System.out.println("No encoding detected.");
		// }

		// (5)
		detector.reset();
		return encoding;
	}

	public static void writeFile(String text, String fileFullName) {
		BufferedWriter writer = null;
		String encoding = detectEncoding(text);
		try {
			File file = new File(fileFullName);
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file), encoding));
			writer.write(text);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
			}
		}
	}
}
