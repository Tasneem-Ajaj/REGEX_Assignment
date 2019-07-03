import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Driver {
	/**
	 * Regular expressions
	 */
	// Regex to check if the line starts without any space or not.
	final static String REGEX_NO_WHITESPACES = "^\\S";
	// Regex to get the number after the MTU word.
	final static String REGEX_EXTRACT_MTU = "(?<=MTU )\\d+";
	// Regex to get the first word in the line.
	final static String REGEX_FIRST_WORD = "^([\\w[/]]+)";
	// Regex to get the last word in line.
	final static String REGEX_LAST_WORD = "\\s(\\w+)$";
	// Regex to get the MAC address after 'address is'.
	final static String REGEX_MAC_ADDRESS = "(?<=address is ).*?(?=\\s)";
	// Regex to get the description after 'Description: '.
	final static String REGEX_EXTRACT_DESCRIPTION = "(?<=Description: ).*";
	// Regex to get the IP address after 'Internet address is '.
	final static String REGEX_EXTRACT_IP = "(?<=Internet address is )[^\\//]+";
	// Regex to get the admin and operation status.
	final static String REGEX_MODE = "^[A-Za-z].+ ";
	// Regex to check if the line contains 'timeout' word.
	final static String REGEX_CHECK_END = "(?i)\\bTimeout";
	// Regex to check if the line contains 'reliability' word.
	final static String REGEX_RELIABILITY_WORD = "(?i)\\breliability";
	// Regex to check if the line contains 'Encapsulation' word.
	final static String REGEX_ENCAPSULATION_WORD = "(?i)\\bEncapsulation";
	// Regex to check if the line contains 'Keepalive' word.
	final static String REGEX_KEEPLIVE_WORD = "(?i)\\bKeepalive";
	// Regex to check if the line contains 'duplex' word.
	final static String REGEX_DUPLEX = "(?i)\\bduplex";

	static StringBuilder builder;

	public static void main(String[] args) throws IOException {
		// Array List for all interfacess.
		ArrayList<Interface> interfacesArray = new ArrayList<>();
		readInterfacesFile(); // Read interfaces from file and save the needed
								// line in string builder.
		// Split the string based on '\n' new line
		String[] interfacesInfo = builder.toString().split("\n");
		Interface temp = null;
		String tempRegexResult;

		for (int i = 0; i < interfacesInfo.length; i++) {

			// check if the line does't contain any space --> get the interface
			// name , operation status , and admin status
			if (applyRegex(interfacesInfo[i], REGEX_NO_WHITESPACES) != "") {
				temp = new Interface();
				// first word in line --> interface name
				temp.setInterfaceName(applyRegex(interfacesInfo[i],
						REGEX_FIRST_WORD).trim());
				// last word in line --> operation status
				temp.setOperationStatus(applyRegex(interfacesInfo[i].trim(),
						REGEX_LAST_WORD).trim());
				// word before ',' --> admin status
				temp.setAdminStatus(applyRegex(interfacesInfo[i].split(",")[0],
						REGEX_LAST_WORD).trim());
				continue;
			}

			else {
				// get the MAC address.
				tempRegexResult = applyRegex(interfacesInfo[i],
						REGEX_MAC_ADDRESS);
				// check if the interface have MAC address or not.
				if (tempRegexResult != "") {
					temp.setMacAddress(tempRegexResult);
					continue;
				}

				// get the description.
				tempRegexResult = applyRegex(interfacesInfo[i],
						REGEX_EXTRACT_DESCRIPTION);
				// check if the interface have description or not.
				if (tempRegexResult != "") {
					temp.setDescription(tempRegexResult);
					continue;
				}
				// get the IP address
				tempRegexResult = applyRegex(interfacesInfo[i],
						REGEX_EXTRACT_IP);
				// check if the interface have IP address or not.
				if (tempRegexResult != "") {
					temp.setIpAddress(tempRegexResult);
					continue;
				}
				// get the MAC address
				tempRegexResult = applyRegex(interfacesInfo[i],
						REGEX_EXTRACT_MTU);
				// check if the interface have MAC address or not.
				if (tempRegexResult != "") {
					temp.setMtu(new Short(tempRegexResult));
					continue;
				}
				// get the admin and operation status
				tempRegexResult = applyRegex(interfacesInfo[i].trim(),
						REGEX_MODE);
				// check if the interface have admin and operation status
				if (tempRegexResult != "") {
					String mode[] = tempRegexResult.split(",");
					if (applyRegex(mode[0], REGEX_DUPLEX) != "") {
						temp.setDuplexMode(tempRegexResult.split(",")[0]);
					}

					if (tempRegexResult.split(",").length != 1) {
						temp.setIfSpeed(tempRegexResult.split(",")[1]);
					}

				}
				// add the interface to interface array
				interfacesArray.add(temp);

			}

		}
		// Print the result.
		System.out.println("Matched interfaces are "
				+ (interfacesArray.size() - 1) + "\n");
		for (int i = 0; i < interfacesArray.size() - 1; i++) {
			System.out.println("Interface #" + i + ":");
			System.out.println(interfacesArray.get(i).toString());
		}

	}

	/**
	 * Read Interfaces from interface file and save it in String Builder.
	 */
	public static void readInterfacesFile() {
		// flag to check if the all information of interface extract.
		int flag = 0;
		String file = "..\\Regex-Assignment\\src\\interfaces"; // file path.
		try {

			BufferedReader reader = new BufferedReader(new FileReader(file));
			builder = new StringBuilder();
			// read the first line in file.
			String currentLine = reader.readLine();
			// read file line by line any save the line in string builder.
			while (currentLine != null) {
				// check if the line have timeout we should skip all other line
				// until line without any space ( next interface)
				String interfaceEnd = applyRegex(currentLine, REGEX_CHECK_END);
				String noWhitespace = applyRegex(currentLine,
						REGEX_NO_WHITESPACES);
				// check if the line have repliabilty we should skip it.
				String checkRepliabiltyWord = applyRegex(currentLine,
						REGEX_RELIABILITY_WORD);
				// check if the line have encapsulation we should skip it.
				String checkEncapsulationWord = applyRegex(currentLine,
						REGEX_ENCAPSULATION_WORD);
				// check if the line have keeplive we should skip it.
				String checkKeepliveWord = applyRegex(currentLine,
						REGEX_KEEPLIVE_WORD);
				if (interfaceEnd != "") {
					currentLine = reader.readLine();
					flag = 1;
					continue;
				} else if (flag == 1 && noWhitespace == "") {

					currentLine = reader.readLine();

					continue;
				} else if (checkRepliabiltyWord != ""
						|| checkEncapsulationWord != ""
						|| checkKeepliveWord != "") {
					currentLine = reader.readLine();

					continue;
				} else {
					flag = 0;
					// append the line to string builder.
					builder.append(currentLine);
					// separate the line with '\n'
					builder.append('\n');
					currentLine = reader.readLine();
				}
			}
			// close the file.
			reader.close();

		} catch (IOException e) {
			System.out.println("Error in Reading File" + e);
			System.exit(0);
		}
	}

	/**
	 * 
	 * @param input
	 *            string
	 * @param regex
	 * @return result of regex
	 */
	private static String applyRegex(String input, String regex) {

		Pattern p = Pattern.compile(regex);
		Matcher m1 = p.matcher(input);
		if (m1.find()) {
			return m1.group();
		} else {
			return "";
		}
	}

}
