package userInterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import facebook4j.conf.ConfigurationBuilder;
import game.Main;
import gameState.StackPage;
import gameState.StackFunction;

public class FacebookPost extends UI {
	JTextField tokenTextField;
	public FacebookPost(Main main) {
		super(main);
		dialog = new JDialog(main, "Enter IP Address");
		
		dialog.getContentPane().setLayout(new BorderLayout());
		JPanel fbPanel = new JPanel();
		fbPanel.setPreferredSize(new Dimension(300, 60));
		dialog.getContentPane().add(fbPanel);
		fbPanel.setLayout(new BorderLayout());
		tokenTextField = new JTextField();

		fbPanel.setLayout(new BorderLayout());
		tokenTextField = new JTextField("");
		tokenTextField.setColumns(1);
		JLabel tokenTextLabel = new JLabel("Facebook Token:");
		JPanel gapNorth = new JPanel();
		gapNorth.setPreferredSize(new Dimension(0, 0));
		fbPanel.add(gapNorth, BorderLayout.NORTH);
		JPanel tokenLabel = new JPanel();
		tokenLabel.setPreferredSize(new Dimension(100, 30));
		JPanel tokenField = new JPanel();
		tokenField.setPreferredSize(new Dimension(300, 30));
		tokenLabel.setLayout(new BorderLayout());
		tokenField.setLayout(new BorderLayout());
		tokenLabel.add(tokenTextLabel, BorderLayout.EAST);
		tokenField.add(tokenTextField, BorderLayout.CENTER);
		fbPanel.add(tokenLabel, BorderLayout.WEST);
		fbPanel.add(tokenField, BorderLayout.CENTER);
		JButton okbutton = new JButton("OK");
		okbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String appId = "592725250911334";
				String appSecret = "40220c6a4a548c063bcabd0a6f346da8";
			      String accessToken = tokenTextField.getText();
			      ConfigurationBuilder cb = new ConfigurationBuilder();
			      cb.setDebugEnabled(true)
			        .setOAuthAppId(appId)
			        .setOAuthAppSecret(appSecret)
			        .setOAuthAccessToken(accessToken)
			        .setOAuthPermissions("email,publish_stream,...");
			      FacebookFactory ff = new FacebookFactory(cb.build());
			      Facebook facebook = ff.getInstance();
			      String shortLivedToken = tokenTextField.getText();
			      try {
					AccessToken extendedToken = facebook.extendTokenExpiration(shortLivedToken);
				} catch (FacebookException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			      try {
			    	  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			    	  Date date = new Date();
			    	  System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48
					facebook.postStatusMessage("I'm the winner of underthesea game!!! "+dateFormat.format(date));
				} catch (FacebookException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		fbPanel.add(okbutton, BorderLayout.EAST);

		dialog.pack();
		dialog.setVisible(true);
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void leave() {
		// TODO Auto-generated method stub
		
	}

	 
	}