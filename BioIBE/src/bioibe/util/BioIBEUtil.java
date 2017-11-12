package bioibe.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

public class BioIBEUtil {
	// TODO Auto-generated method stub
	//双线性映射
	Pairing pairing ;
	//生物特征私钥
	Element[] biosk = new Element[10];
	//生物特征公钥
	Element[] biopk = new Element[10];
	//公开参数
	Element[] mpk = new Element[13];
	//秘密参数
	Element[] msk = new Element[11];
			//私钥
	Element[] sk = new Element[2];
			//密文
	Element[] ct = new Element[12];
	
	public Pairing getPairing() {
		pairing = PairingFactory.getPairing("a.properties");
		return pairing;
	}
	//生物特征公钥
	@SuppressWarnings("resource")
	public Element[] getBiopk(int userBiopkID) {
		
		File file = new File("src/biopk.txt");
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			while((tempString = reader.readLine())!=null) {
				//System.out.println("line:" + line);
				if (line == userBiopkID) {
					//System.out.println(tempString);
					String[] strs = tempString.split(",");
					for (int i = 0; i < strs.length; i++) {
						biopk[i] = pairing.getZr().newElement(Integer.parseInt(strs[i].trim())).getImmutable();
						//System.out.println(biopk[i]);
					}
					return biopk;
				}
				line++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return biopk;
		/**
		biopk[0] =pairing.getZr().newElement(24).getImmutable(); 
		biopk[1] =pairing.getZr().newElement(96).getImmutable(); 
		biopk[2] =pairing.getZr().newElement(31).getImmutable(); 
		biopk[3] =pairing.getZr().newElement(43).getImmutable(); 
		biopk[4] =pairing.getZr().newElement(21).getImmutable(); 
		biopk[5] =pairing.getZr().newElement(24).getImmutable(); 
		biopk[6] =pairing.getZr().newElement(17).getImmutable(); 
		biopk[7] =pairing.getZr().newElement(29).getImmutable(); 
		biopk[8] =pairing.getZr().newElement(39).getImmutable(); 
		biopk[9] =pairing.getZr().newElement(1).getImmutable();
		return biopk;
		*/
	}
			
	//生物特征私钥
	@SuppressWarnings("resource")
	public Element[] getBiosk(int userBioskID) {
		File file = new File("src/biosk.txt");
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			while((tempString = reader.readLine())!=null) {
				//System.out.println("line:" + line);
				if (line == userBioskID) {
					//System.out.println(tempString);
					String[] strs = tempString.split(",");
					for (int i = 0; i < strs.length; i++) {
						biosk[i] = pairing.getZr().newElement(Integer.parseInt(strs[i].trim())).getImmutable();
						//System.out.println(biosk[i]);
					}
					return biosk;
				}
				line++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return biosk;
		
		/**
		biosk[0] =pairing.getZr().newElement(81).getImmutable(); 
		biosk[1] =pairing.getZr().newElement(14).getImmutable(); 
		biosk[2] =pairing.getZr().newElement(121).getImmutable(); 
		biosk[3] =pairing.getZr().newElement(106).getImmutable(); 
		biosk[4] =pairing.getZr().newElement(84).getImmutable(); 
		biosk[5] =pairing.getZr().newElement(91).getImmutable(); 
		biosk[6] =pairing.getZr().newElement(86).getImmutable(); 
		biosk[7] =pairing.getZr().newElement(58).getImmutable(); 
		biosk[8] =pairing.getZr().newElement(126).getImmutable(); 
		biosk[9] =pairing.getZr().newElement(108).getImmutable();
		return biosk;
		*/
	}
			
			/**
			 * 测试公私钥是否匹配即内积是否为0
			 
			Element product = pairing.getZr().newZeroElement();
			for (int i = 0; i < biopk.length; i++) {
				product = product.add(biopk[i].mul(biosk[i]));
			}
			System.out.println("product:"+ product);
			*/
	//秘密参数
	public Element[] getMsk() {
		for (int i = 0; i < msk.length; i++) {
			msk[i] = pairing.getZr().newRandomElement().getImmutable();
			//System.out.print("msk["+ i +"]:" + msk[i] + " ");
		}
		return msk;
	}
			
	//System.out.println();
	//公开参数
	public Element[] getMpk() {
		//生成元
		Element g = pairing.getG1().newRandomElement().getImmutable();		    
		mpk[0] = g.getImmutable();
		//System.out.print("mpk[0]:" + mpk[0] + " ");
		//大素数 131 也就是0
		Element p = pairing.getZr().newElement(131).getImmutable();
		mpk[1] = p.getImmutable();
		//System.out.print("mpk[1]:" + mpk[1] + " ");
		for (int i = 2; i < mpk.length-1; i++) {
			mpk[i] = g.powZn(msk[i-2]).getImmutable();
			//System.out.print("mpk["+ i + "]" + mpk[i] + " ");
		}
		// u
		Element u_1 = pairing.pairing(g, g).getImmutable();
		Element u = u_1.powZn(msk[msk.length-1]).getImmutable();
		mpk[mpk.length-1] = u.getImmutable();
		//System.out.println("mpk[" + (mpk.length-1) + "]"+ mpk[mpk.length-1] + " ");
		return mpk;
	}
	
	//私钥
	public Element[] getSk() {
		Element sum_a_z = pairing.getZr().newElement(0).getImmutable();
		for (int i = 0; i < biosk.length; i++) {
			sum_a_z = sum_a_z.add(msk[i].mul(biosk[i])).getImmutable();
			//System.out.print("sum:"+sum_a_z + " : ");
		}
		//随机数 t
		Element t = pairing.getZr().newRandomElement().getImmutable();
		Element sum_t = t.mul(sum_a_z).getImmutable();
		Element sum_b_t = msk[msk.length-1].add(sum_t).getImmutable();
		sk[0] = mpk[0].powZn(sum_b_t).getImmutable();
		sk[1] = mpk[0].powZn(t).getImmutable();
		return sk;
	}
			
	//加密
	public Element[] getCt(Element mess) {
		//随机数 r
		Element r = pairing.getZr().newRandomElement().getImmutable();
		//System.out.println("r:" + r);
		//System.out.println(mpk[mpk.length-1]);
		Element c_m_r = mpk[mpk.length-1].powZn(r).getImmutable();
		//Element c_m_r_t = pairing.pairing(g, g).powZn(msk[msk.length-1].mul(r));
		//明文 m
		//Element m = pairing.getGT().newRandomElement().getImmutable();
		Element m = mess.duplicate().getImmutable();
		//System.out.println("原始明文m:" + m);
		Element c_m = c_m_r.mul(m).getImmutable();
		ct[0] = c_m.getImmutable();
		Element c_0 = mpk[0].powZn(r).getImmutable();
		System.out.print("ct[0]"+ ct[0] + " , ");
		ct[1] = c_0.getImmutable();
		System.out.print("ct[1]"+ ct[1] + " , ");
		//随机数 s
		Element s = pairing.getZr().newRandomElement().getImmutable();
		Element g_r = pairing.getG1().newZeroElement().getImmutable();
		Element g_s_w = pairing.getG1().newZeroElement().getImmutable();
		for (int i = 2; i < ct.length; i++) {
			//g_r = mpk[i].powZn(r);
			//g_s_w = mpk[0].powZn(s.mulZn(biopk[i-2]));
			g_r = mpk[i].powZn(r).getImmutable();
			//g_s_w = mpk[0].powZn(s.mul(biopk[i-2]));
			g_s_w = mpk[0].powZn(s.mul(biopk[i-2])).getImmutable();
			//System.out.println("g_r" + g_r);
			//System.out.println("g_s_w" + g_s_w);
			ct[i] = g_r.mul(g_s_w).getImmutable();
			System.out.print("ct[" +i+ "]" +ct[i] + " , ");
			if (i==5) {
				System.out.println();
			}
		}
		System.out.println();
		return ct;
	}

	//解密
	public Element getMessage(Element[] ctss) {
		Element e_0_0 = pairing.getG1().newOneElement().getImmutable();
		//System.out.println("e_0_0:*******" + e_0_0);
		for (int i = 2; i < ctss.length; i++) {
			//System.out.println(ct.length);
			e_0_0 = e_0_0.mul(ctss[i].powZn(biosk[i-2]));
			//System.out.println("e_0_0:" + e_0_0);
		}
		Element e_0 = pairing.pairing(sk[1], e_0_0).getImmutable();
		Element e_1 = pairing.pairing(sk[0], ctss[1]).getImmutable();
		//Element e_1_p = e_1.powZn(pairing.getZr().newElement(130));
		Element M = ctss[0].div(e_1).mul(e_0).getImmutable();
		//Element M = ct[0].div(e_1).mul(e_1);
		//System.out.println(M_e);
		//System.out.println("解密明文M:"+M);
		//System.out.println(ct[0]);
		return M;
	}
}
