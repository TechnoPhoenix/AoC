import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Y2024_D25_P1 {

	static ArrayList<int[]> locks;
	static ArrayList<int[]> keys;

	public static void main(String[] args) {
		String input = inputToString("/home/user/AoC_Inputs/2024/25");

		//parsing input
		String[] sections = input.split("\n\n");
		locks = new ArrayList<int[]>();
		keys = new ArrayList<int[]>();
		for(String section : sections) {
			String[] lines = section.split("\n");
			if(section.charAt(0) == '#') {
				//this section is a lock
				int[] lock = new int[5];
				for(int i = 1; i < 6; i++) {
					for(int j = 0; j < 5; j++) {
						if(lines[i].charAt(j) == '#') {
							lock[j]++;
						}
					}
				}
				locks.add(lock);
			} else if(section.charAt(0) == '.') {
				//this section is a key
				int[] key = new int[5];
				for(int i = 1; i < 6; i++) {
					for(int j = 0; j < 5; j++) {
						if(lines[i].charAt(j) == '#') {
							key[j]++;
						}
					}
				}
				keys.add(key);
			} else {
				//something went wrong
				System.out.println("Problem: \n" + section);
			}
		}

		int result = 0;
		//test every key against every lock
		for(int[] lock : locks) {
			for(int[] key : keys) {
				int[] sum = new int[5];
				for(int i = 0; i < 5; i++) {
					sum[i] = key[i] + lock[i];
				}
				if(sum[0] < 6 && sum[1] < 6 && sum[2] < 6 && sum[3] < 6 && sum[4] < 6) {
					result++;
				}
			}
		}

		System.out.println(result);
	}

	private static String inputToString(String filename) {
    	String result = "";
    	try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
        	StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while(line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return result;
    }
}
