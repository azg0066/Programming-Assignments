import java.net.*; // for Socket, ServerSocket, and InetAddress
import java.io.*; // for IOException and Input/OutputStream

public class MyFirstTCPServer {

  private static final int BUFSIZE = 32; // Size of receive buffer

  public static void main(String[] args) throws IOException {

    if (args.length != 1) // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Port>");

    int servPort = Integer.parseInt(args[0]);

    // Create a server socket to accept client connection requests
    ServerSocket servSock = new ServerSocket(servPort);

    int recvMsgSize; // Size of received message
    byte[] byteBuffer = new byte[BUFSIZE]; // Receive buffer

    for (;;) { // Run forever, accepting and servicing connections
      Socket clntSock = servSock.accept(); // Get client connection

      System.out.println(
          "Handling client at " + clntSock.getInetAddress().getHostAddress() + " on port " + clntSock.getPort());

      InputStream in = clntSock.getInputStream();
      OutputStream out = clntSock.getOutputStream();

      // Receive until client closes connection, indicated by -1 return
      while ((recvMsgSize = in.read(byteBuffer)) != -1) {
        String message = new String(byteBuffer);
        String devowelizedMessage = devowelize(message);
        byte[] devowelizedBuffer = devowelizedMessage.getBytes();
        System.out.println("Devowelized Message: " + devowelizedMessage);

        out.write(devowelizedBuffer, 0, recvMsgSize);
      }
      byteBuffer = new byte[BUFSIZE];
      clntSock.close(); // Close the socket. We are done with this client!
    }
    /* NOT REACHED */
  }

  private static boolean isVowel(char letter) {
    if (letter == 'a' || letter == 'e' || letter == 'i' || letter == 'o' || letter == 'u') {
      return true;
    } else
      return false;
  }

  private static String devowelize(String message) {
    String out = "";
    for (int i = 0; i < message.length(); i++) {
      char letter = message.charAt(i);
      if (!isVowel(letter)) {
        out += letter;
      }
    }
    return out;
  }
}
