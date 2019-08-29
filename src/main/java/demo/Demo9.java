package demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;


public class Demo9 {

	public static void main(String[] args) throws IOException {
		args= new String[] {"127.0.0.1","8859"};
        InetSocketAddress inetSocketAddress = new InetSocketAddress(args[0], Integer.valueOf(args[1]));
		final Socket socket = new Socket();
        socket.setSoTimeout(0);
        socket.connect(inetSocketAddress, 60 * 1000*10);
        socket.setKeepAlive(true);
       
	}
}
