package start;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.FileDialog;
import java.awt.Font;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelOption extends JPanel {

    private JavaTicks urlJavaTicks;
    private PanelTopMenu panelTop;
    private JLabel LABELFON;
    private JButton buttonDifaultDir;
    private JLabel labelDir;
    private FileDialog dialog;
    private Desktop desktop;

    public PanelOption(JavaTicks urlJavaTicks) {
        this.urlJavaTicks = urlJavaTicks;

// Диалоговое окно для выбора директории  для заметок
        dialog = new FileDialog(urlJavaTicks, "Открыв файл получишь ее директорию", FileDialog.LOAD);
        dialog.setFile("Выберите директорию и нажмите открыть");
// настройки панели        
        setLayout(null);
        setBounds(0, 0, 495, 495);
        setOpaque(false);

// Верхнее меню        
        panelTop = new PanelTopMenu(urlJavaTicks);
        panelTop.setBounds(0, 0, 477, 77);
        panelTop.setBorder(null);
        panelTop.setOpaque(false);
        add(panelTop, Integer.valueOf(10));

// отображение директории заметки
        labelDir = new JLabel(urlJavaTicks.getNameDefaultDir());
        labelDir.setBounds(120, 86, 300, 20);
        labelDir.setForeground(Color.LIGHT_GRAY);
        add(labelDir);

        // кнопка установки директории по умолчанию
        buttonDifaultDir = new JButton("директория заметок",new ImageIcon(JavaTicks.class.getResource("/image/DefaultDir.png")));
        buttonDifaultDir.setVerticalTextPosition(buttonDifaultDir.CENTER);
        buttonDifaultDir.setHorizontalTextPosition(buttonDifaultDir.CENTER);
        buttonDifaultDir.setBounds(20, 86, 92, 20);
        buttonDifaultDir.setForeground(Color.WHITE);
        buttonDifaultDir.setFont(new Font("Arial", Font.PLAIN, 9));
        buttonDifaultDir.setBorder(null);
        buttonDifaultDir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        buttonDifaultDir.setContentAreaFilled(false);
        buttonDifaultDir.addActionListener((e) -> {
            dialog.setVisible(true);
            if (dialog.getDirectory() != (null)) {
                urlJavaTicks.setDirDefault(Paths.get(dialog.getDirectory()));
                labelDir.setText(dialog.getDirectory());
            }
            
        });
        add(buttonDifaultDir, Integer.valueOf(10));

        // Обложка фона      
        LABELFON = new JLabel(new ImageIcon(JavaTicks.class.getResource("/image/StartFonImage.png")));
        LABELFON.setBounds(0, 0, 495, 495);
        add(LABELFON, Integer.valueOf(3));
    }

}
