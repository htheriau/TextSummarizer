import java.io.*;
import java.awt.*;  
import javax.swing.*;  
import java.awt.event.*;  
public class Main extends JFrame implements ActionListener{
  JMenuBar mb;  
	JMenu file;  
	JMenuItem open;  
	JTextArea ta; 
static String filePath;
Main(){  
open=new JMenuItem("Open File");  
open.addActionListener(this);  
          
file=new JMenu("File");  
file.add(open);  
          
mb=new JMenuBar();  
mb.setBounds(0,0,800,20);  
mb.add(file);  
          
ta=new JTextArea(800,800);  
ta.setBounds(0,20,800,800);  
          
add(mb);  
add(ta);  
          
}  
public void actionPerformed(ActionEvent e) {  
if(e.getSource()==open){  
openFile();  
}  
}  
      
void openFile(){  
JFileChooser fc=new JFileChooser();  
int i=fc.showOpenDialog(this);  
          
if(i==JFileChooser.APPROVE_OPTION){  
File f=fc.getSelectedFile();  
filePath=f.getPath();  
 
}  
          
}  

  public static void main(String[] args){
     Main om=new Main();  
    om.setSize(800,800);  
    om.setLayout(null);  
    om.setVisible(true);  
    om.setDefaultCloseOperation(EXIT_ON_CLOSE);  
    //String filePath = args[0];
    if(filePath == null){
      filePath = "./input.txt";
    }

    String[] keywords = null; 
    if(args.length < 2){
      keywords = new String[1];
      keywords[0] = "";
    }
    else{
      keywords = new String[args.length-1];
      for(int i=1; i<args.length; i++){
        keywords[i-1] = args[i];
      }
    }

    System.out.print("keywords:\t");
    for(String keyword : keywords){
      System.out.print(keyword+"\t");
      System.out.println();
    }

    //String[] keywords = null; 
    Generator generator = new Generator();

    generator.loadFile(filePath);
    generator.setKeywords(keywords);
//    generator.generateSignificantSentences();
    System.out.println(generator.generateSummary());
//    generator.generateSummary();
  }
}
