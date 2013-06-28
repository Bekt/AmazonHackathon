
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

public class Inventory {

	private Hashtable<String, Hashtable<String, Integer>> bins = new Hashtable<String, Hashtable<String, Integer>>();

	public Inventory() {
		BufferedReader br = null;

		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader("C:\\Users\\likevin\\Desktop\\sampledata\\data0\\inventory.dat"));

			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public Inventory(String filePath) {		
		BufferedReader br = null;

		try {
			String sCurrentLine;
			String[] binContent;
			br = new BufferedReader(new FileReader(filePath));

			while ((sCurrentLine = br.readLine()) != null) {
				binContent = sCurrentLine.split(" ");
				Hashtable<String, Integer> content = new Hashtable<String, Integer>();
				content.put(binContent[1], Integer.parseInt(binContent[2]));
				bins.put(binContent[0], content);
			}
		} catch (IOException e) {
			System.out.println("Inventory file read error");
		} catch (Exception e) {
			System.out.println("Inventory file parsing error");
		}	finally {

			try {
				if (br != null) br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public Hashtable<String, Integer> get(String binId) {
		return bins.get(binId);
	}
}
