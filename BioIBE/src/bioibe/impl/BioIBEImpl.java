package bioibe.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.Scanner;

import bioibe.util.BioIBEUtil;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
/**
 * 测试系统性能
 * @author 18968
 *
 */
public class BioIBEImpl {
	/**
	 * hash函数
	 * @param str
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	public static String shaEncode(String mac_mod) throws Exception  {
		MessageDigest sha = MessageDigest.getInstance("SHA");
		byte[] mac_mod_bytes=mac_mod.getBytes("UTF-8");
		byte[] shaBytes = sha.digest(mac_mod_bytes);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < shaBytes.length; i++) {
			int val = (int)shaBytes[i]&0xff;
			if (val<16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
	//两个字符串的异或值
	public static String getXOR(String str1,String str2) throws Exception {
		StringBuffer val1 = new StringBuffer();
		StringBuffer val2 = new StringBuffer();
		StringBuffer results = new StringBuffer();
		if (str1.length()<str2.length()) {
			for (int i = 0; i < (str2.length()-str1.length()); i++) {
				val1.append("0");
			}
		} else {
			for (int i = 0; i < (str1.length()-str2.length()); i++) {
				val2.append("0");
			}
		}
		String strs1 = str1+val1.toString();
		String strs2 = str2+ val2.toString();
		byte[] bys1 = strs1.getBytes("UTF-8");
		byte[] bys2 = strs2.getBytes("UTF-8");
		//char[] chs1 = strs1.toCharArray();
		//char[] chs2 = strs2.toCharArray();
		for (int i = 0; i < bys1.length; i++) {
			//int vals = (int)bys1[i]^bys2[i];
			//System.out.println(vals);
			System.out.print((bys1[i]^bys2[i]) + ":");
			results.append((char)(bys1[i]^bys2[i]));
			//results.append((char)Integer.toHexString(bys1[i]^bys2[i]).charAt(0));
		}
		return results.toString();
	}
	public static void main(String[] args) throws Exception {
		
		long first = System.currentTimeMillis();
		long end1 = 0;
		long end2 = 0;
		BioIBEUtil bioIBEUtil = new BioIBEUtil();
		Pairing pairing = bioIBEUtil.getPairing();
		
		System.out.println("--------------物理身份验证阶段--------------");
		//MAC地址
		String macid = "A0481C9D5EA6";
		System.out.println("Mac地址:"+macid);
		//模数 N
		String modid = "9923";
		System.out.println("模数:"+modid);
		//macid||modid
		String mac_mod = macid+modid;
		//计算hash值
		//公开参数：异或值
		System.out.println("公开的异或参数:");
		String oxr_p = getXOR(modid, macid);
		//System.out.println("公开的异或参数:"+oxr_p);
		System.out.println();
		System.out.println("计算的模数值:");
		String xor_s = getXOR(macid, oxr_p);
		System.out.println();
		System.out.println("10进制模数值:"+xor_s.substring( 0, modid.length()));
		//System.out.println();
		String hash = shaEncode(mac_mod);
		System.out.println("公开的Hash值:"+hash);
		//System.out.println("hash:");
		System.out.println("输入Mac地址:");
		Scanner scanner = new Scanner(System.in);
		String in_macid = scanner.nextLine();
		String macp_mods = in_macid+xor_s.substring(0,modid.length());
		//System.out.println("macp_mods"+macp_mods);
		String hash_s = shaEncode(macp_mods);
		System.out.println("计算的Hash值:"+hash_s);
		if (hash.equals(hash_s)) {
			//本地计算的值
			end1 = System.currentTimeMillis();
			System.out.println("---------------解密阶段-----------------");
	 		/**
			System.out.println("请输入需要加密的明文：");
			Scanner in = new Scanner(System.in);
			String str = in.nextLine();
			*/
			StringBuffer strMess = new StringBuffer();
			Random random = new Random();
			for (int i = 0; i < 5; i++) {
				int max = 126;
				int min = 33;
				int ch = random.nextInt(max)%(max-min+1) + min;
				strMess.append((char)ch);
			}
			//String str = "ahdsa;dksadfiakdas";
			String str = strMess.toString();
			char[] chs = str.toCharArray();
			System.out.println("需要加密的数据:"+str);
			//明文 
			//Element mess = pairing.getGT().newRandomElement().getImmutable();
			//System.out.println("输入的明文：" + mess);
			/**
			System.out.println("请输入公钥ID:");
			int userBiopkID = in.nextInt();
			Element[] biopk = bioIBEUtil.getBiopk(userBiopkID);
			System.out.println("请输入私钥ID:");
			int userBioskID = in.nextInt();
			Element[] biosk = bioIBEUtil.getBiosk(userBioskID);
			*/
			//Element[] biopk = bioIBEUtil.getBiopk(1);
			System.out.println("请输入变换生物特征公钥ID:");
			int userBiopkID = scanner.nextInt();
			Element[] biopk = bioIBEUtil.getBiopk(userBiopkID);
			Element[] biosk = bioIBEUtil.getBiosk(2);
			//判断公私钥是否匹配
			Element product = pairing.getZr().newZeroElement().getImmutable();
			for (int i = 0; i < biopk.length; i++) {
				product = product.add(biopk[i].mul(biosk[i]));
				//System.out.println(product);
			}
			if (product.isZero()) {
				bioIBEUtil.getMsk();
				bioIBEUtil.getMpk();
				bioIBEUtil.getSk();
				Element mess;
				StringBuffer sBuffer= new StringBuffer();
				System.out.println("加密后的密文数据:");
				for (int i = 0; i < chs.length; i++) {
					System.out.println("第"+ i + "组密文数据");
					mess = pairing.getGT().newElement(chs[i]).getImmutable();
				    Element[] ctss = bioIBEUtil.getCt(mess);
					Element message = bioIBEUtil.getMessage(ctss);
					String SSL = message.toString();
					//System.out.println("解密后的明文：");
					//System.out.println(SSL);
					String[] reStrings = SSL.split("=|,");
					int reInt = Integer.parseInt(reStrings[1]);
					//System.out.println(Character.toChars(reInt));
					sBuffer.append(Character.toChars(reInt));
				}
				end2 = System.currentTimeMillis();
				System.out.println("---------------解密阶段---------------");
				System.out.println("请输入解密生物特征ID:");
				int in_sk= scanner.nextInt();
				if (userBiopkID==in_sk) {
					System.out.println("解密后的明文数据:");
				System.out.println(sBuffer.toString());
				}else {
					System.out.println("公私钥不匹配");
					System.out.println();
				}
				
				
			}else {
				System.out.println("公私钥不匹配");
			}
			/**
			bioIBEUtil.getMsk();
			bioIBEUtil.getMpk();
			bioIBEUtil.getSk();
			Element mess;
			System.out.println("解密后的明文：");
			for (int i = 0; i < chs.length; i++) {
				mess = pairing.getGT().newElement(chs[i]).getImmutable();
			    bioIBEUtil.getCt(mess);
				Element message = bioIBEUtil.getMessage();
				String SSL = message.toString();
				//System.out.println("解密后的明文：");
				//System.out.println(SSL);
				String[] reStrings = SSL.split("=|,");
				int reInt = Integer.parseInt(reStrings[1]);
				System.out.println(Character.toChars(reInt));
			}
			*/
			/**
			bioIBEUtil.getCt(mess);
			Element message = bioIBEUtil.getMessage();
			String SSL = message.toString();
			System.out.println("解密后的明文：" + message);
			System.out.println(SSL);
			*/
		} else {
			
			System.out.println("物理身份不匹配，请重新验证物理身份");
		}
		System.out.println("---------------系统加解密耗时统计---------------");
		long end = System.currentTimeMillis();
		long cost1 = end1 - first;
		long cost2 = end2 - end1;
		long cost = end - first;
		System.out.println("身份验证耗时:" + cost1 + "ms");
		System.out.println("加解密耗时:" + cost2 + "ms");
		System.out.println("系统总耗时:" + cost + "ms");
	}

}
