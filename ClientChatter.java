package Chat;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import java.net.Socket;

import java.awt.Font;
import java.awt.event.*;

public class ClientChatter extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtStaff;
	private JTextField txtServerIP;
	private JTextField txtServerPort;
	
	Socket mngSocket = null;
	String mngIP = "";
	int mngPort = 0;
	String staffName = "";
	BufferedReader bf = null;
	DataOutputStream os = null;
	OutputThread t = null;
	public static void main(String[] args) {
		  try {
	            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
	                if ("Nimbus".equals(info.getName())) {
	                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
	                    break;
	                }
	            }
	        } catch (ClassNotFoundException ex) {
	            java.util.logging.Logger.getLogger(ClientChatter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (InstantiationException ex) {
	            java.util.logging.Logger.getLogger(ClientChatter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (IllegalAccessException ex) {
	            java.util.logging.Logger.getLogger(ClientChatter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
	            java.util.logging.Logger.getLogger(ClientChatter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        }
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientChatter frame = new ClientChatter();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public ClientChatter() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 823, 513);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Staff and server info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 7, 0, 0));
		
		JLabel lbStaff = new JLabel("Staff: ");
		lbStaff.setHorizontalAlignment(SwingConstants.RIGHT);
		lbStaff.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		panel.add(lbStaff);
		
		txtStaff = new JTextField();
		panel.add(txtStaff);
		txtStaff.setColumns(10);
		
		JLabel lbIp = new JLabel("IP: ");
		lbIp.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lbIp.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lbIp);
		
		txtServerIP = new JTextField();
		panel.add(txtServerIP);
		txtServerIP.setColumns(10);
		
		JLabel lbPort = new JLabel("Port: ");
		lbPort.setHorizontalAlignment(SwingConstants.RIGHT);
		lbPort.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		panel.add(lbPort);
		
		txtServerPort = new JTextField();
		panel.add(txtServerPort);
		txtServerPort.setColumns(10);
		
		JFrame thisFrame = this;		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mngIP= txtServerIP.getText();
				mngPort= Integer.parseInt(txtServerPort.getText());
				staffName= txtStaff.getText();
				try {
					mngSocket = new Socket(mngIP, mngPort);
					if(mngSocket!=null) {
						ChatPanel p = new ChatPanel(mngSocket, staffName, "Manager");
						thisFrame.getContentPane().add(p);
						p.getTxtMessages().append("Started");
						p.updateUI();
						
						bf = new BufferedReader(new InputStreamReader(mngSocket.getInputStream()));
						os = new DataOutputStream(mngSocket.getOutputStream());
						
						os.writeUTF("Staff: "+staffName);
						os.write(13); os.write(10);
						os.flush();
												
					}
				} catch (Exception e2) {					
					e2.printStackTrace();
				}
			}
		});
		btnConnect.setFont(new Font("Times New Roman", Font.BOLD, 13));
		panel.add(btnConnect);
	}

}