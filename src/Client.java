import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {

	private final DatagramSocket datagramSocket;
	private final InetAddress address;

	public Client(DatagramSocket datagramSocket, InetAddress inetAddress) {
		this.datagramSocket = datagramSocket;
		this.address = inetAddress;
	}

	public void sendAndReceive() {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			try {
				String message = scanner.nextLine();
				byte[] buffer = message.getBytes(StandardCharsets.UTF_8);
				DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, address, Server.PORT);
				datagramSocket.send(datagramPacket);
				datagramSocket.receive(datagramPacket);
				String receivedMessage = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
				System.out.println("Server: " + receivedMessage);
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}

	public static void main(String[] args) {
		try {
			DatagramSocket datagramSocket = new DatagramSocket();
			InetAddress address = InetAddress.getByName("localhost");
			Client client = new Client(datagramSocket, address);
			System.out.println("Client Started");
			client.sendAndReceive();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
