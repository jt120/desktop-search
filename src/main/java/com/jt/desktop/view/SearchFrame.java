package com.jt.desktop.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.jt.desktop.model.SearchFile;
import com.jt.desktop.util.IndexUtil;
import com.jt.desktop.util.SearchUtil;

public class SearchFrame extends JFrame {
	
    private static final long serialVersionUID = -4080796207814084772L;
    
	public static void main(String[] args) {
	    new SearchFrame();
    }
	
	private JTextField searchInput;
	
	private JPanel contentPanel;
	private JMenu menu;
	private JMenuBar menuBar;
	private JMenuItem createIndex;
	private JTable table;
	private JScrollPane scrollPane;
	private	String[] columnNames = {"Name",
        "Path"};
	
	private MyMenuListener myMenuListener;
	
	public SearchFrame() {
		this.setTitle("desktop search");
		this.setSize(800,600);
		this.setLocation(300,200);
		this.addWindowListener(new MyWindowsEvent());
		
		
		menuBar = new JMenuBar();
		
		menu = new JMenu("Index");
		createIndex = new JMenuItem("create");
		menu.add(createIndex);
		
		myMenuListener = new MyMenuListener();
		createIndex.addActionListener(myMenuListener);
		
		menuBar.add(menu);
		this.add(menuBar,BorderLayout.NORTH);
		contentPanel = new JPanel(new BorderLayout());
		
		searchInput = new JTextField(20);
		searchInput.addKeyListener(new MyKeyEvent());

		//初始化，查询所有数据
		Object[][] objs = listFile("*");
		table = new JTable(objs, columnNames);
		table.setRowHeight(20);
		table.getColumn("Name").setMaxWidth(150);
		table.getColumn("Name").setMinWidth(150);
		table.getColumn("Name").setResizable(false);
		table.addMouseListener(new MyTableListener());
		scrollPane = new JScrollPane(table);
		contentPanel.add(searchInput,BorderLayout.NORTH);
		contentPanel.add(scrollPane,BorderLayout.CENTER);
		
		this.add(contentPanel,BorderLayout.CENTER);
		this.setVisible(true);
	}
	//搜索输入框监听器
	class MyKeyEvent extends KeyAdapter {
		
		@Override
        public void keyReleased(KeyEvent e) {
	        String input = searchInput.getText();
	        Object[][] objs = listFile(input);
	        DefaultTableModel dtm = new javax.swing.table.DefaultTableModel(objs,columnNames);
	        table.setModel(dtm);
	        table.repaint();
        }
	}
	//窗口监听器
	class MyWindowsEvent extends WindowAdapter {
		
		@Override
        public void windowClosing(WindowEvent e) {
			IndexUtil.close();
			SearchUtil.close();
			System.exit(0);
        }
		
	}
	//菜单监听器
	class MyMenuListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			if(command.equals("create")) {
				try {
	                IndexUtil.getIndexWriter().deleteAll();
                } catch (IOException e1) {
	                e1.printStackTrace();
                }
				IndexUtil.index("D:\\develop\\java\\docs\\tutorial\\uiswing");
			}
        }
		
	}
	
	class MyTableListener extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			int col = table.getSelectedColumn();
			int row = table.getSelectedRow();
			if(col==0) {
				col++;
			}
			javax.swing.table.TableModel model = table.getModel();
			String path = model.getValueAt(row, col).toString();
			String pPath = path.substring(0,path.lastIndexOf("\\"));
			try {
	            Runtime.getRuntime().exec("explorer " + pPath);
            } catch (IOException e1) {
	            e1.printStackTrace();
            }
        }
	}
	
	public Object[][] listFile(String str) {
		List<SearchFile> list = SearchUtil.search(str);
		int size = list.size();
		Object[][] objs = new Object[size][2];
		for (int i = 0; i < size; i++) {
	        objs[i][0] = list.get(i).getName();
	        objs[i][1] = list.get(i).getPath();
        }
		return objs;
	}
}
