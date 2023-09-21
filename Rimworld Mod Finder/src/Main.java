import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
	public static void main(String[] yargs) {
		Main main = new Main();
		String path = "C:\\Program Files (x86)\\Steam\\steamapps\\workshop\\content\\294100";
		ArrayList<Group> files = new ArrayList<>((new File(path)).listFiles().length);
		Scanner s = null;

		//Iterate over all mods in the folder
		for (File f : (new File(path)).listFiles()) {
			File about = new File(f.getPath() + "\\About\\About.xml");		
			try {
				//Scan through the about xml file until the name of the mod is found
				s = new Scanner(about);
				String string = s.nextLine();
				while(!string.contains("<name>")) {
					string = s.nextLine();
				}

				//Format the string for readability
				string = string.substring(string.indexOf(">") + 1);
				string = string.substring(0, string.indexOf("<"));

				//Add the name of the file and the numeric code assigned to it to the list of files
				files.add(main.new Group(string, f.getName()));

				s.close();
			}
			
			//If no about file was found, inform user and print the numeric code for the mod
			catch(Exception e) {
				System.out.println("No about file found: " + f.getName() + "\n");
			}
		}
		s.close();
		
		//Sort the mods by name
		files.sort(new Comparator<Group>() {
			public int compare(Group arg0, Group arg1) {
				return arg0.compareTo(arg1);
			}});
		
		//Print all mods names/codes to the console
		for (Group g : files) {
			g.print();
		}
	}
	
	private class Group implements Comparable<Group> {
		String name;
		String number;
		
		public Group(String s1, String s2) {
			name = s1;
			number = s2;
		}
		
		public void print() {
			System.out.println(name);
			System.out.println(number + "\n");
		}

		@Override
		public int compareTo(Group arg0) {
			return name.compareTo(arg0.name);
		}
	}
}
