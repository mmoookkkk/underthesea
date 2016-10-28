//package underthesea;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import facebook4j.Facebook;
//import facebook4j.FacebookException;
//import facebook4j.FacebookFactory;
//import facebook4j.auth.AccessToken;
//import facebook4j.conf.ConfigurationBuilder;
//
//public class testFB {
//
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		
//		String appId = "592725250911334";
//		String appSecret = "40220c6a4a548c063bcabd0a6f346da8";
//	      String accessToken = "EAAIbFJoHrGYBAIcsJ95BWJ8DfEAPjoNXRSnZB6CO72dc3AeUM9m68tAmmvuDd0NHxDZAwftVtz05XV0eiZAeUHXR9ahnofyg5L4jmZBIimaZBIDlND2SMCiSs5tc0BT4BfoCDuD4LALyyaIELXJ3oEsmYl1eZAfS6i0bcRid7VFQZDZD";
//	     
//	      ConfigurationBuilder cb = new ConfigurationBuilder();
//	      cb.setDebugEnabled(true)
//	        .setOAuthAppId(appId)
//	        .setOAuthAppSecret(appSecret)
//	        .setOAuthAccessToken(accessToken)
//	        .setOAuthPermissions("email,publish_stream,...");
//	      FacebookFactory ff = new FacebookFactory(cb.build());
//	      Facebook facebook = ff.getInstance();
//	      String shortLivedToken = "EAAIbFJoHrGYBAImYTt3aAslbCAS5IoDbvoWoC8zb5YmJPcsoIY0ypClwdbC08VbwzsZCv9X9PLQmZC2fZCnDBsFNmAVRxoCUobNDlbT3jxRZBlAA00TZBzF5wxfhnEfMcJZBH2fKD1ZADPhsrkQgsPK55lBQNSMSZAFIhXXlRIFecgZDZD";
//	      try {
//			AccessToken extendedToken = facebook.extendTokenExpiration(shortLivedToken);
//		} catch (FacebookException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//	      try {
//	    	  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//	    	  Date date = new Date();
//	    	  System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48
//			facebook.postStatusMessage("Hello World from Facebook6J."+dateFormat.format(date));
//		} catch (FacebookException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
//
//}
