package demo;

import java.io.IOException;

import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class Demo8 {

	public static void main(String[] args) throws IOException {
		
		Terminal terminal = TerminalBuilder.builder().system(true).build();
		Completer commandCompleter = new StringsCompleter("CREATE", "OPEN", "WRITE", "CLOSE");
		      
		LineReader lineReader = LineReaderBuilder.builder()
		        .terminal(terminal)
		        .completer(commandCompleter)
		        .build();
		String prompt = "fogyyyy> ";
		while (true) {
			String line;
			try {
				line = lineReader.readLine(prompt);
				System.out.println(line);
			} catch (UserInterruptException e) { // Do nothing } catch (EndOfFileException e) {
				System.out.println("\nBye.");
				return;
			}
		}
	}

}
