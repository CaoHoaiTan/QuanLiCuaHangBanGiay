package GUI;

import DAO.ConnectionUtils;
import MODEL.DonHang;
import MODEL.DonHangModel;
import com.formdev.flatlaf.FlatLightLaf;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.plaf.InternalFrameUI;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ThongKeDoanhThu extends JInternalFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Launch the application.
     *
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
        for (int i = 0; i < listdHang.size(); i++) {
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
        for (int i = 0; i < listdHang.size(); i++) {
            dataset.addValue(listdHang.get(i).getCost(), "VNĐ", String.valueOf(listdHang.get(i).getPurchaseMonth()));

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
     *
     * @throws ClassNotFoundException
     * @throws UnsupportedLookAndFeelException
     */
    public ThongKeDoanhThu() throws ClassNotFoundException, UnsupportedLookAndFeelException {
    	getContentPane().setBackground(new Color(255, 255, 255));
        setFrameIcon(null);
        setBorder(null);
        UIManager.setLookAndFeel(new FlatLightLaf());


        setBounds(100, 100, 607, 418);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBackground(new Color(255, 255, 255));
        getContentPane().add(tabbedPane);

        JInternalFrame TK_Ngay = new JInternalFrame();
        TK_Ngay.getContentPane().setBackground(new Color(255, 255, 255));
        TK_Ngay.setBackground(new Color(255, 255, 255));
        tabbedPane.addTab("Thống kê theo ngày", null, TK_Ngay, null);
        //Tạo Chart theo ngày
        ChartPanel chartPanel = new ChartPanel(createChart());
        chartPanel.setBackground(new Color(255, 255, 255));
        chartPanel.setPreferredSize(new Dimension(560, 367));

        TK_Ngay.getContentPane().add(chartPanel);
        TK_Ngay.setBorder(null);
        TK_Ngay.setFrameIcon(null);


        JInternalFrame TK_Thang = new JInternalFrame();
        TK_Thang.getContentPane().setBackground(new Color(255, 255, 255));
        TK_Thang.setBackground(new Color(255, 255, 255));
        tabbedPane.addTab("Thống kê theo tháng", null, TK_Thang, null);

        //Tạo Chart theo tháng
        ChartPanel chartPanelMonth = new ChartPanel(createChartMonth());
        chartPanel.setPreferredSize(new Dimension(560, 367));

        TK_Thang.getContentPane().add(chartPanelMonth);
        TK_Thang.setBorder(null);
        TK_Thang.setFrameIcon(null);
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
