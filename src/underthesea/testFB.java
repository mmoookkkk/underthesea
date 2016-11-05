package underthesea;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import facebook4j.conf.ConfigurationBuilder;

public class testFB {
	//
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String appId = "592725250911334";
		String appSecret = "40220c6a4a548c063bcabd0a6f346da8";
	      String accessToken = "EAAIbFJoHrGYBAIX7xFB2HGW6wqz0YFYR1isuZAUjAZAq89q4BFDnQPOZAPJ8i5gomn8Pw0cVpLPleJRnZCsUOkpLx61LLVnCNWzBZAQUSdUKfGn8SASRhSVRKoueYbC2PpnWdGtG9FEq24IbgC1pLNNZBzPbFRWdqsTMg2Vz3gZCgZDZD";
	     
	      ConfigurationBuilder cb = new ConfigurationBuilder();
	      cb.setDebugEnabled(true)
	        .setOAuthAppId(appId)
	        .setOAuthAppSecret(appSecret)
	        .setOAuthAccessToken(accessToken)
	        .setOAuthPermissions("email,publish_stream,...");
	      FacebookFactory ff = new FacebookFactory(cb.build());
	      Facebook facebook = ff.getInstance();
	      String shortLivedToken = "EAAIbFJoHrGYBAIX7xFB2HGW6wqz0YFYR1isuZAUjAZAq89q4BFDnQPOZAPJ8i5gomn8Pw0cVpLPleJRnZCsUOkpLx61LLVnCNWzBZAQUSdUKfGn8SASRhSVRKoueYbC2PpnWdGtG9FEq24IbgC1pLNNZBzPbFRWdqsTMg2Vz3gZCgZDZD";
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
			facebook.postStatusMessage("Hello World from Facebook4J."+dateFormat.format(date));
		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
