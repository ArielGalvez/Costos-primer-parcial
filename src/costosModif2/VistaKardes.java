package costosModif2;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOError;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;




/*importaciones extras para la fecha*/
import com.alee.extended.date.WebDateField;
import com.alee.laf.WebLookAndFeel;

public class VistaKardes extends JPanel
{
	/**/private JSpinner spinnerNroDiscos;
	///**/private int contenedor=1;
	
    public VistaKardes(final JFrame ff)
    { this.setLayout(null);
      this.setSize(1000,500);
      final JTabbedPane jtp=new JTabbedPane();
      jtp.setBounds(0,45,1000,470);
      this.add(jtp);
      JPanel jp1=new JPanel();
      jp1.setBounds(0,0,920,40);
      JButton jb=new JButton("Crear Kardex");
      jb.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
             crearKardex(jtp,ff);
            }
        });
      crearKardex(jtp,ff);
      jp1.add(jb);
      this.add(jp1);
      jp1.setVisible(true);
    }
    
    static public void main(String arg[]){
     /**try {
      * 
         UIManager. setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"); 
         SwingUtilities. updateComponentTreeUI(null) ;
      } catch (Exception eX) {}*/ 
     
     try{
    	 WebLookAndFeel.install ();
    	 //WebLookAndFeel.setDecorateAllWindows ( true );
         //WebLookAndFeel.setDecorateFrames ( true );
     }catch (Exception eX) {}
     JFrame jf=new JFrame();
     jf.setContentPane(new VistaKardes(jf));
     jf.setSize(925,500);
     
     //setIconImage(new ImageIcon(getClass().getResource("/img/imagen.png")).getImage());
     /**crei q el icono entraba aqui pero no*/
     
     jf.setLocationRelativeTo(null);
     jf.setResizable(false);
     jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void crearKardex(final JTabbedPane jtp,final JFrame ff){
          final JFrame jf=new JFrame("Nuevo Kardex");
             jf.setSize(320,300);
             jf.setResizable(false);
             jf.setLayout(new GridLayout(9,2,5,5));
             jf.setUndecorated(true);
             jf.setOpacity(0.8F);
             jf.getContentPane().setBackground(new java.awt.Color(0, 244, 244));
                         
             jf.setLocationRelativeTo(null);
             jf.add(new JLabel(" Tipo Kardex"));
             jf.setIconImage(new ImageIcon("images/icono2.png").getImage());
             final JComboBox jcb=new JComboBox(new String[]{"PEPS","UEPS"});
             jf.add(jcb);
             jf.add(new JLabel(" Fecha  dia/mes/año"));
             final WebDateField fecha = new WebDateField();
             fecha.setEditable(false);
             jf.add(fecha);
             jf.add(new JLabel(" Nombre Producto"));
             final JTextField jt00=new JTextField();
             jf.add(jt00);
             jf.add(new JLabel(" Unidad  ejm: Kg, Lts"));
             final JTextField jt000=new JTextField();
             jf.add(jt000);
             final JLabel jl=new JLabel(" Inventario Inicial");
             jf.add(jl);
             final JTextField jt=new JTextField();
             jf.add(jt);
             final JLabel jl1=new JLabel(" Costo Unitario");
             jf.add(jl1);    
             final JTextField jt1=new JTextField();
             jf.add(jt1);
             jf.add(new JLabel(" Costo Total"));
             final JTextField jt2=new JTextField();
             jf.add(jt2);
             jt2.setEnabled(false);
             jt1.setEnabled(false);
             final JLabel jl2=new JLabel(" Nº de Ordenes de P.");
             jf.add(jl2);
     		 spinnerNroDiscos = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
             jf.add(spinnerNroDiscos);
             jt1.addKeyListener(new KeyListener() {
                    public void keyTyped(KeyEvent e) {}
                    public void keyPressed(KeyEvent e) {}
                    public void keyReleased(KeyEvent e) {
                     try {  double can=Integer.parseInt(jt.getText());
                            double costo_uni=VistaKardes.convertir(jt1.getText());
                            jt2.setText(""+(can*costo_uni));
                        } catch (Exception ex) {}
                    }
                });
             /**/
             /**/
             jt2.addKeyListener(new KeyListener(){
                    public void keyTyped(KeyEvent e) {}
                    public void keyPressed(KeyEvent e){}
                    public void keyReleased(KeyEvent e) {
                       try {double can=Integer.parseInt(jt.getText());
                            double costo_t=VistaKardes.convertir(jt2.getText());
                            jt1.setText(""+(costo_t/can));
                        } catch (Exception ex) {}
                    }
                });
             jt.addKeyListener(new KeyListener() {
                    public void keyTyped(KeyEvent e) {}
                    public void keyPressed(KeyEvent e) {}
                    public void keyReleased(KeyEvent e) {
                     try {  
                    	 	//double can=Integer.parseInt(jt.getText());
                            jt2.setEnabled(true);
                            jt1.setEnabled(true);
                            /*desde*/double ca=Integer.parseInt(jt.getText());
                     		double costo_uni=VistaKardes.convertir(jt1.getText());
                     		jt2.setText(""+(ca*costo_uni));/*hasta*/
                        } catch (Exception ex) {
                            //jt2.setEnabled(false);
                            //jt1.setEnabled(false);
                        }
                    }
                });
             jt000.addKeyListener(new KeyListener() {
                    public void keyTyped(KeyEvent e) {}
                    public void keyPressed(KeyEvent e) {}
                    public void keyReleased(KeyEvent e) {
                        jl.setText ("Inventario Inicial en ["+jt000.getText()+"]");
                        jl1.setText("Costo Unitario por ["+jt000.getText()+"]");
                    }
                });
             final JButton jb=new JButton("Aceptar");
             jf.add(jb);
             jb.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ex) {
                        try {
                        	int tam= Integer.parseInt(spinnerNroDiscos.getValue().toString());	
                            String pro=jt00.getText();
                            boolean pes=jcb.getSelectedIndex()==0;
                            String nom="UEPS";
                            if(pes){nom="PEPS";}
                            //String  fec=fecha.toString();
                            String  fec=fecha.getText().replace('.', '/');
                            int ii=Integer.parseInt(jt.getText());
                            double cu=VistaKardes.convertir(jt1.getText());
                            Kardex k=new Kardex(ii,cu,pes,fec,tam);
                            k. unidades=jt000.getText();
                            JPanel JPa=new JPanel ();
                            JPa.setLayout(null);
                            JLabel JL=new JLabel("Kardex "+nom+" Producto ( "+pro+" )"+" Gestion "+
                                             DateFormat.getDateInstance().format(new Date())+
                                             " /                CANTIDAD [ "+jt000.getText()+" ]                     VALOR [ Bs ]");
                            JL.setFont(new Font("Arial",2,15));
                            JL.setBounds(0,0,900,50);
                            JPa.add(JL);
                            k.setBounds(0,50,900,380);
                            JPa.add(k);
                            jtp.addTab("Kardex "+nom+" Producto ( "+pro+" )",JPa);
                            jf.dispose();       
                            ff.setIconImage(new ImageIcon("images/icono2.png").getImage());
                            ff.setVisible(true);
                        } catch (Exception e) {}
                    }
                });
             final JButton jb1=new JButton("Cancelar");
             jb1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ex) {
                            jf.dispose();
                            ff.setVisible(true);
                    }
                });
             ff.setVisible(false);
             jf.add(jb1);
             jf.toFront();
             jf.setVisible(true);
    }
    /**ultimo para ver error*/
    static public double convertir(String nu){
    	//System.out.println(nu);
    	double res=0;
    	//try {
        res=Double.parseDouble(nu);
        int canDecimales=(nu.substring(nu.indexOf(".")+1)).length(); 
        if(canDecimales>3){
            res=redondea(res);
        }
        
    	//} catch (Exception ex) {res=-1;}
    	return res;
    }
    /*public static double redondea(double numero){ 
      double resultado=numero; int decimales=3;
      BigDecimal res;
      res = new BigDecimal(numero).setScale(decimales, BigDecimal.ROUND_UP);
      resultado = res.doubleValue();
      return resultado; 
    }*/
    static public double redondea(double n){
		String nu=""+n;
        double res=Double.parseDouble(nu);
        int canDecimales=(nu.substring(nu.indexOf(".")+1)).length(); 
        //System.out.println(""+canDecimales);
        if(canDecimales>3){
            nu=nu.substring(0, nu.indexOf(".")+5);
            res=Double.parseDouble(nu);
            res=redondeaB(res);
        }
        return res;
    }
	static public double redondeaB(double numero){
	      String ver=""+numero;
	      double resultado=numero; int decimales=3;
	      int canDecimales=(ver.substring(ver.indexOf(".")+1)).length(); 
	      if(canDecimales>decimales){
	    	  BigDecimal res;
	    	  res = new BigDecimal(numero).setScale(decimales, BigDecimal.ROUND_UP);
	    	  resultado = res.doubleValue();
	      }
	      return resultado; 
	}
}

