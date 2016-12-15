package guiview;
import java.awt.EventQueue;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import taskmodel.*;
import controller.TaskManager;
import java.awt.*;
public class AddTaskWindow extends JFrame{
	private DateChooserJButton mDCBS;
	private DateChooserJButton mDCBE;
	private JTextField mText;
	private JComboBox<String> mJCB;
	private JButton mCreate;
	private JFrame mParent;
	public AddTaskWindow(JFrame jf){
		super();
		mParent=jf;
		this.setTitle("�������");
		this.setSize(500,500);
		this.setLayout(new GridLayout(0,1));
		this.add(new JLabel("��ʼʱ��"));
		mDCBS=new DateChooserJButton();
		this.add(mDCBS);
		this.add(new JLabel("����ʱ��"));
		mDCBE=new DateChooserJButton();
		this.add(mDCBE);
		this.add(new JLabel("��������"));
		mText=new JTextField("");
		this.add(mText);
		this.add(new JLabel("��������"));
		mJCB=new JComboBox<>();
		mJCB.addItem("һ������");
		mJCB.addItem("ÿ������");
		mJCB.addItem("��������");
		this.add(mJCB);
		mCreate=new JButton("����");
		mCreate.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				Task t=null;
				Date s=mDCBS.getDate();
				Date e=mDCBE.getDate();
				switch(mJCB.getSelectedIndex()){
				case 0:
					if(s.compareTo(e)>0)return;
					t=new NormalTask(mText.getText(),s,e);
					break;
				case 1:
					try{
						Calendar c1=Calendar.getInstance();
						c1.setTime(s);
						t=new EverydayTask(mText.getText(),c1.get(Calendar.HOUR),c1.get(Calendar.MINUTE),(int)(e.getTime()-s.getTime())/(1000*60));
					}catch(Exception exc){
						return;
					}
					break;
				case 2:
					t=new UnlimitedTask(mText.getText(),s);
					break;
				}
				if(t==null)return;
				TaskManager.getInstance().addTask(t);
				AddTaskWindow.this.setVisible(false);
				EventQueue.invokeLater(new Runnable(){
					public void run(){
						((MainWindow)mParent).refresh();
					}
				});
			}
		});
		this.add(mCreate);
	}
}
