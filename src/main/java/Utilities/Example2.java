package Utilities;

public class Example2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String s="907741 is your Amazon OTP. OTP is confidential. For security reasons, DO NOT share this OTP with anyone.";
		String s1=s.replaceAll("[^0-9]", " ");
		System.out.println(s1);
	}

}
