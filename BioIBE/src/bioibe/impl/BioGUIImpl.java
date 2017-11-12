package bioibe.impl;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 图形化界面
 * @author 18968
 *
 */
public class BioGUIImpl extends JFrame{
	//定义组件
	JPanel jp1 ;    //面板
	JLabel jl1;     //标签
	JButton jb1;    //按钮
	JTextField jtf1;     //文本域
	//构造器
	public BioGUIImpl() {
		// TODO Auto-generated constructor stub
		jp1 = new JPanel();
		jl1 = new JLabel("公钥");
		
	}
}
