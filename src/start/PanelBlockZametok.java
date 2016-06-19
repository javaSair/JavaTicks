 
package start;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

 
public class PanelBlockZametok extends JPanel {
    
    private JavaTicks urlJavaTicks;
    private PanelTopMenu urlPanelTopMenu;
    private JLabel LABELFON;
    private PanelBlockZametok urlPanelBlockZametok;
    private JScrollPane scrol;
    private JPanel panelZametki;
    
    private ArrayList<JPanel> listPanel = new ArrayList<>();
    
    void repaintSpisok(){
        Runnable r = new Runnable() {

            @Override
            public void run() {
                listPanel.clear();
                for(int i=0;i<urlJavaTicks.getListSize();i++){
                    System.out.println(i);
                    
                    PanelCreateZametks p = new PanelCreateZametks(urlJavaTicks, urlJavaTicks.getListZametki().get(i), i*20);
                    p.setBorder(null);
//                    p.setOpaque(false);
                    p.setBounds(0, i*20, 430, 20);
 
                    
                    
                    
                    p.addMouseListener(new MouseAdapter() {

                        @Override
                        public void mouseEntered(MouseEvent e) {
                            p.SetVisibal(true);
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                             p.SetVisibal(false);
                        }
                        
                        
                        
   }
                    
                    );
//                    p.setBorder(BorderFactory.createEtchedBorder());
                    listPanel.add(p);
                }
                for(int i=0;i<listPanel.size();i++){
                    panelZametki.add(listPanel.get(i),Integer.valueOf(8));
                  
                }
//                    System.out.println(listPanel.get(i)); 
                    
                
          
                updatePreferedSize();
                urlJavaTicks.setContentPane(urlPanelBlockZametok);
                
             scrol.revalidate();
               urlJavaTicks.revalidate();
            }
        };
        new Thread(r).start();
         
    }

 
    
    
    
    void updatePreferedSize(){
        panelZametki.setPreferredSize(new Dimension(panelZametki.getPreferredSize().width, listPanel.size()*20+20));
    }
    
    
    
    public PanelBlockZametok(JavaTicks urlJavaTicks) {
        this.urlJavaTicks=urlJavaTicks;
        
       urlPanelBlockZametok = this; 
        
      panelZametki = new JPanel(true);
   panelZametki.setLayout(null);
      panelZametki.setBounds(20, 150, 450, 330);
      panelZametki.setSize(450, 330+(listPanel.size()*20));
      panelZametki.setBackground(Color.BLACK);
     panelZametki.setBorder(null);
     panelZametki.setPreferredSize(new Dimension(panelZametki.getPreferredSize().width, 330));
//      panelZametki.setOpaque(false);
      
       scrol = new JScrollPane(panelZametki, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
       scrol.setViewportView(panelZametki);
       scrol.getVerticalScrollBar().setMinimum(0);
       scrol.getVerticalScrollBar().setMaximum((listPanel.size()*20));
       scrol.getVerticalScrollBar().setVisibleAmount(10);
       
//       scrol.add(panelZametki,Integer.valueOf(15));
//       scrol.setBorder(null);
//       scrol.setLayout(new FlowLayout());
//       scrol.setOpaque(false);
       scrol.setBounds(20, 150, 450, 330);
        add(scrol);
        
        setLayout(null);
        setBounds(0, 0, 495, 495);
        setOpaque(false);
        
        // верхняя панель меню
      urlPanelTopMenu = new PanelTopMenu(urlJavaTicks);
      urlPanelTopMenu.setBounds(0, 0, 477, 77);
      urlPanelTopMenu.setBorder(null);
      urlPanelTopMenu.setOpaque(false);
        add(urlPanelTopMenu,Integer.valueOf(9));
        
        
 // Обложка фона      
        LABELFON = new JLabel(new ImageIcon(urlJavaTicks.getS()+"src\\image\\StartFonImage.png"));
        LABELFON.setBounds(0, 0, 495, 495);
        add(LABELFON, Integer.valueOf(3));       
        
        
    }

    
    
    
    
}
