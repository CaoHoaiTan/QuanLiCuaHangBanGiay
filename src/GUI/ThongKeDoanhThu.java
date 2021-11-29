package GUI;

import java.awt.EventQueue;
import java.awt.Frame;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.formdev.flatlaf.FlatLightLaf;

import DAO.ConnectionUtils;
import MODEL.DonHang;
import MODEL.DonHangModel;

import javax.swing.BoxLayout;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.plaf.InternalFrameUI;
import javax.swing.plaf.basic.BasicInternalFrameUI;

public class ThongKeDoanhThu extends JInternalFrame {

	/**
	 * Launch the application.
	 * @throws ClassNotFoundException 
	 */
	
	  public static JFreeChart createChart() throws ClassNotFoundException {
	        JFreeChart barChart = ChartFactory.createBarChart(
	                "THỐNG KÊ DOANH THU SHOP THEO NGÀY",
	                "Ngày", "VNĐ",
	                createDataset(), PlotOrientation.VERTICAL, false, false, false);
	        return barChart;
	    }
	  
	  public static JFreeChart createChartMonth() throws ClassNotFoundException {
	        JFreeChart barChart = ChartFactory.createBarChart(
	                "THỐNG KÊ DOANH THU SHOP THEO THÁNG",
	                "Tháng", "VNĐ",
	                createDatasetMonth(), PlotOrientation.VERTICAL, false, false, false);
	        return barChart;
	    }

	    private static CategoryDataset createDataset() throws ClassNotFoundException {
	        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	        List<DonHang> listdHang = new ArrayList<DonHang>();
	        try {
	        	Connection conn = ConnectionUtils.getConnection();
				listdHang = DonHangModel.thongKeDoanhThuTheoNgay(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        //add data
	        for(int i=0 ; i<listdHang.size();i++) {
	        	dataset.addValue(listdHang.get(i).getCost(), "VNĐ", listdHang.get(i).getPurchaseDate());
	        	
	        }
	        
	        return dataset;
	    }
	    
	    private static CategoryDataset createDatasetMonth() throws ClassNotFoundException {
	        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	        List<DonHang> listdHang = new ArrayList<DonHang>();
	        try {
	        	Connection conn = ConnectionUtils.getConnection();
				listdHang = DonHangModel.thongKeDoanhThuTheoThang(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        //add data
	        for(int i=0 ; i<listdHang.size();i++) {
	        	dataset.addValue(listdHang.get(i).getCost(), "VNĐ", String.valueOf(listdHang.get(i).getPurchaseMonth()) );
	        	
	        }
	        
	        return dataset;
	    }
	    
	    
	//
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
								        
					ThongKeDoanhThu frame = new ThongKeDoanhThu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws ClassNotFoundException 
	 * @throws UnsupportedLookAndFeelException 
	 */
	public ThongKeDoanhThu() throws ClassNotFoundException, UnsupportedLookAndFeelException {
		setFrameIcon(null);
		setBorder(null);
		UIManager.setLookAndFeel(new FlatLightLaf());
		
		
		setBounds(100, 100, 607, 418);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane);
		
		JInternalFrame internalFrame = new JInternalFrame();
		tabbedPane.addTab("Thống kê theo ngày", null, internalFrame, null);
		//Tạo Chart theo ngày
		ChartPanel chartPanel = new ChartPanel(createChart());
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
		
		internalFrame.getContentPane().add(chartPanel);
		internalFrame.setVisible(true);
		
		
		JInternalFrame internalFrame_1 = new JInternalFrame();
		tabbedPane.addTab("Thống kê theo tháng", null, internalFrame_1, null);
		
		//Tạo Chart theo tháng
		ChartPanel chartPanelMonth = new ChartPanel(createChartMonth());
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
		
		internalFrame_1.getContentPane().add(chartPanelMonth);
		internalFrame_1.setVisible(true);
		
		
		
		
		
		//setLocationRelativeTo(null);
//		ChartPanel chartPanel = new ChartPanel(createChart());
//        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367)); 
//    
//        JFrame frameTK = new JFrame();
//        frameTK.getContentPane().add(chartPanel);
//        frameTK.setTitle("Biểu đồ JFreeChart trong Java Swing");
//        frameTK.setSize(600, 400);
//        frameTK.setLocationRelativeTo(null);
//        frameTK.setResizable(false);
//        frameTK.setVisible(true);
		
		
        
	}
	// Tắt title bar của JInternalFrame
	@Override
	public void setUI(InternalFrameUI ui) {
		super.setUI(ui);
		BasicInternalFrameUI frameUI = (BasicInternalFrameUI) getUI();
		if (frameUI != null)
			frameUI.setNorthPane(null);
	}

}
