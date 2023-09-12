
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerParallel {
	private static final int PORT = 2301;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                executorService.execute(clientHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


class ClientHandler implements Runnable {
    private Socket clientSocket;
    private boolean isStop = false;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        	BufferedReader reader;
        	DataOutputStream dos;
			try {
				reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				dos = new DataOutputStream(clientSocket.getOutputStream());
			
        	
            Timer timer = new Timer();
            
            
            while (true) {
                String input = reader.readLine();
                
                if (input.equals("exit")) {
                	isStop = true;
                    break;
                }
                if (input.equals("stop")) {
                    isStop = true;
                }
                if (input.equals("run")) {
					isStop = false;
				}
                
	            TimerTask task = new TimerTask() {
	                @Override
	                public void run() {
                        String time = new Date().toString();
                        if (!isStop) {
                        	try {
                                dos.writeUTF(time);
                                dos.flush(); // Đảm bảo dữ liệu được gửi đi ngay lập tức
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
						}
                        
	                }
	            };
	
	            // Xét interval sau mỗi 1 giây (1000 milliseconds)
	            timer.scheduleAtFixedRate(task, 0, 1000);
	            
            }
            } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
}

