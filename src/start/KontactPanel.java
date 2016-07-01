 
package start;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

 
public class KontactPanel extends JPanel implements MouseListener {
    
    private PanelTopMenu urlPanelTopMenu;
    private JavaTicks urlJavaTicks;
    private JLabel LABELFON;

    
    private JLabel labelGit,labelEmail,labelVk;

    public KontactPanel(JavaTicks urlJavaTicks) {
        this.urlJavaTicks = urlJavaTicks;
        
         setSize(495, 495);
        setLayout(null);
        setBorder(null);
        setBounds(0, 0, 495, 495);
        setOpaque(false);
        
        labelGit = new JLabel("Проект на GitHub");
        labelGit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        labelGit.addMouseListener(this);
        labelGit.setBounds(200, 150, 100, 20);
        labelGit.setForeground(Color.GREEN);
        add(labelGit,Integer.valueOf(8));
        
      
        
        labelEmail = new JLabel("Email: jarnik@list.ru");
        labelEmail.setBounds(200, 175, 150, 20);
        labelEmail.setForeground(Color.GREEN);
        add(labelEmail,Integer.valueOf(8));
        
        
        labelVk = new JLabel("Я в VK");
        labelVk.addMouseListener(this);
        labelVk.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        labelVk.setBounds(230, 195, 100, 20);
        labelVk.setForeground(Color.GREEN);
        add(labelVk,Integer.valueOf(8));
        
        
        // верхняя панель меню
        urlPanelTopMenu = new PanelTopMenu(urlJavaTicks);
        urlPanelTopMenu.setBounds(0, 0, 477, 77);
        urlPanelTopMenu.setBorder(null);
        urlPanelTopMenu.setOpaque(false);
        add(urlPanelTopMenu, Integer.valueOf(7));
        
        
        
                // Обложка фона      
        LABELFON = new JLabel(new ImageIcon(JavaTicks.class.getResource("/image/StartFonImage.png")));
        LABELFON.setBounds(0, 0, 495, 495);
        add(LABELFON, Integer.valueOf(3));
        
        
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Runtime r = Runtime.getRuntime();
        Process p = null;
        
        if(e.getSource().equals(labelGit)){
            try {
                p = r.exec("explorer.exe https://github.com/javaSair/JavaTicks");
            } catch (IOException ex) {
                Logger.getLogger(KontactPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(e.getSource().equals(labelVk)){
                try {
                p = r.exec("explorer.exe https://new.vk.com/wayjava");
            } catch (IOException ex) {
                Logger.getLogger(KontactPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    
    
    

}
