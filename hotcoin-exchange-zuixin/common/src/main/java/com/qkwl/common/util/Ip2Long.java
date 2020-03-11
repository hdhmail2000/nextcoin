package com.qkwl.common.util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Ip2Long {

	/**
	 * IP地址转化为长整型
	 * @param ip
	 * @return IP转化为长整形数值
	 */
	public static long ip2Long(String ip) {
		long result = 0;
		StringTokenizer token = null;
		if (ip.indexOf(".") != -1) {
			token = new StringTokenizer(ip, ".");
			result += Long.parseLong(token.nextToken()) << 24;
			result += Long.parseLong(token.nextToken()) << 16;
			result += Long.parseLong(token.nextToken()) << 8;
			result += Long.parseLong(token.nextToken());
			return result;
		} else{
			token = new StringTokenizer(ip, ":");
			return ipv6toInt(ip).longValue();
		}
	}

	/**
	 * 长整型解析为IP地址
	 * @param num 待解析的长整形数值
	 * @return ip串
	 */
	public static String long2ip(long ipLong){  
		
	      StringBuilder sb = new StringBuilder();  
	      
	      sb.append(ipLong>>>24);sb.append("."); 
	      sb.append(String.valueOf((ipLong&0x00FFFFFF)>>>16));sb.append(".");  
	      sb.append(String.valueOf((ipLong&0x0000FFFF)>>>8));sb.append(".");  
	      sb.append(String.valueOf(ipLong&0x000000FF));  
	      
	      return sb.toString();  
	}  
	
	public static void main(String[] args) {
		/*String ip = "171.111.45.162";
		System.out.println("测试ip:"+ip);
		long time = System.currentTimeMillis();
		Long num = ip2Long(ip);
		time = System.currentTimeMillis() - time;
		System.out.println("转化为Long:"+ num);
		System.out.println("耗时："+time);
		time = System.currentTimeMillis();
		ip = long2ip(num);
		time = System.currentTimeMillis() - time;
		System.out.println("解析为ip:"+ ip);
		System.out.println("耗时："+time);*/
		String ip = "2409:8962:21e:2094:8404:ed1a:8141:3080";
		System.out.println(ipv6toInt(ip).longValue());
		List<Integer> items = new ArrayList<>();
		items.add(1);
	}

	public static BigInteger ipv6toInt(String ipv6)
	{

		int compressIndex = ipv6.indexOf("::");
		if (compressIndex != -1)
		{
			String part1s = ipv6.substring(0, compressIndex);
			String part2s = ipv6.substring(compressIndex + 1);
			BigInteger part1 = ipv6toInt(part1s);
			BigInteger part2 = ipv6toInt(part2s);
			int part1hasDot = 0;
			char ch[] = part1s.toCharArray();
			for (char c : ch)
			{
				if (c == ':')
				{
					part1hasDot++;
				}
			}
			// ipv6 has most 7 dot
			return part1.shiftLeft(16 * (7 - part1hasDot )).add(part2);
		}
		String[] str = ipv6.split(":");
		BigInteger big = BigInteger.ZERO;
		for (int i = 0; i < str.length; i++)
		{
			//::1
			if (str[i].isEmpty())
			{
				str[i] = "0";
			}
			big = big.add(BigInteger.valueOf(Long.valueOf(str[i], 16))
					.shiftLeft(16 * (str.length - i - 1)));
		}
		return big;
	}


}
