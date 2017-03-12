package data.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

import data.serviceimpl.DataInterfaceImpl;

public class DataInterface_Driver {
	public static void main(String[] args){
		
		
		Scanner in=new Scanner(System.in);
		long start_time=System.currentTimeMillis();
		System.out.println(start_time);	
		IDataInterface data=new DataInterfaceImpl();
		long end_time=System.currentTimeMillis();
		System.out.println(end_time);
		System.out.println("runtime: "+(-start_time+end_time)+" ms");
		String input="";
		SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yy");
		while(!(input=in.nextLine()).equals("esc")){
			if(input.charAt(0)=='1'){
				input=input.substring(2);
				String[] three=input.split(",");
				Calendar cal1=Calendar.getInstance();
				Calendar cal2=Calendar.getInstance();
				try {
					cal1.setTime(sdf.parse(three[1]));
					cal2.setTime(sdf.parse(three[2]));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				long start_time1=System.currentTimeMillis();
				data.getSingleStockInfo(three[0],cal1,cal2);
				long end_time1=System.currentTimeMillis();
				System.out.println(end_time1);
				System.out.println("runtime: "+(-start_time1+end_time1)+" ms");
			}
			else if(input.charAt(0)=='2'){
				input=input.substring(2);
				Calendar cal=Calendar.getInstance();
				try {
					cal.setTime(sdf.parse(input));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				long start_time1=System.currentTimeMillis();
				data.getMarketByDate(cal);
				long end_time1=System.currentTimeMillis();
				System.out.println(end_time1);
				System.out.println("runtime: "+(-start_time1+end_time1)+" ms");
			}
			else if(input.charAt(0)=='3'){
				input=input.substring(2);
				String[] info=input.split(",");
				data.signUpCheck(info[0], info[1]);
			}
			else if(input.charAt(0)=='4'){
				input=input.substring(2);
				String[] info=input.split(",");
				data.signInCheck(info[0], info[1]);
			}
			else if(input.charAt(0)=='5'){
				input=input.substring(2);
				data.logout(input);
			}
		}
	}
}