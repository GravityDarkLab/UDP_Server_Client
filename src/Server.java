import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server {

	public final static int PORT = 1234;
	private final int BUFFER_SIZE = 256;
	private final DatagramSocket datagramSocket;

	private final byte[] buffer = new byte[BUFFER_SIZE];

	public Server(DatagramSocket datagramSocket) {
		this.datagramSocket = datagramSocket;
	}

	public void receiveAndSend() {
		while (true) {
			try {
				DatagramPacket datagramPacket = new DatagramPacket(buffer, BUFFER_SIZE);
				datagramSocket.receive(datagramPacket);
				InetAddress address = datagramPacket.getAddress();
				int port = datagramPacket.getPort();
				String message = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
				System.out.println(message + " received!  on Port:" + datagramPacket.getPort());
				datagramPacket = new DatagramPacket(buffer, BUFFER_SIZE, address, port);
				datagramSocket.send(datagramPacket);
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}

		}
	}

	public static void main(String[] args) {
		try {
			Server server = new Server(new DatagramSocket(PORT));
			System.out.println("Server is ready!");
			server.receiveAndSend();
		} catch (SocketException e) {
			throw new RuntimeException(e);
		}
	}
}
