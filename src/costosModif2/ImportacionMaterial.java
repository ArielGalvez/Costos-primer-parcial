package costosModif2;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.alee.laf.WebLookAndFeel;

public class ImportacionMaterial extends JFrame{
	
	Vista vista;
	java.awt.Container c;
	private Kardex kardex;
	
	public ImportacionMaterial(int nProd, String fecha, double tipoCambio, ArrayList<ArrayList<String>> productos, Kardex k) {
		// TODO Auto-generated constructor stub
		c = getContentPane();
		c.setLayout(null);
		try{
	    	 WebLookAndFeel.install ();
	     }catch (Exception eX) {}
		setIconImage(new ImageIcon("images/icono2.png").getImage());
		setTitle("Importacion de Materiales");
		setSize(900,500);
		kardex=k;
		vista= new Vista(897, 497, nProd, fecha, tipoCambio, productos, kardex);
		c.add(vista);	
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		setVisible(true);
	}
	public class Vista extends JPanel{
		Kardex kardex; 
		private int nroProductos;
		private String fecha;
		private double tipoCambio;
		private double dA, fYs, sumBs=0;
		private ArrayList<ArrayList<String>> prod;
		///private ArrayList <ArrayList <String>> kardex;
		private JTable table;
		java.awt.Container c;
		JLabel jl1, jl2, jl3, jl4, jl5, jl6, jl7;
		JTextField jt1, jt2, jt3, jt4, jt5, jt6, jt7, jt8;
		JTextField jtTc, jtCf, jtDa,  jtP;
		JComboBox jcb;
		
		private String[] preciosUf;
		
		public Vista(int x, int y, int nProd, String f, double tc, ArrayList<ArrayList<String>> productos, Kardex k){
			try{
		    	 WebLookAndFeel.install ();
		     }catch (Exception eX) {}
			kardex=k;
			nroProductos=nProd;
			fecha=f;
			tipoCambio=tc;
			prod=productos;
			preciosUf = new String[nroProductos];
			c = getContentPane();
			c.setLayout(null);
			setSize(x,y);
			crearComponentes();
			
	        table = new JTable();	        
	        calcular();
	        actualizar();
	       // table.setPreferredScrollableViewportSize(new Dimension(800, 150));
	        //table.setBounds(10, 200, 800, 150);
	        JScrollPane scrollPane = new JScrollPane(table);
	        scrollPane.setBounds(0, 250, 895, 116);
	        scrollPane.setVisible(true);
	        c.add(scrollPane);
		}
		public void crearComponentes(){
			jl1= crearLabel("FLETES Y SEGUROS",100,10,150,30);
			jl2= crearLabel("[$us]",275,10,90,30);
			jl3= crearLabel("[Bs]",410,10,90,30);						
			jl4= crearLabel("FyS origen-frontera",100,40,130,30);						
			jl5= crearLabel("FyS frontera-aduana",100,70,130,30);			
			jl6= crearLabel("FyS aduana-destino",100,100,130,30);			
			jl7= crearLabel("TOTAL",150,130,130,30);
			
			jt1 = crearCaja(240,40,110,26);
			jt2 = crearCaja(240,70,110,26);					
			jt3 = crearCaja(240,100,110,26);
			jt3.setText("0");
			jt4 = crearCaja(240,130,110,26);
			jt4.setEditable(false);
						
			jt5 = crearCaja(365,40,110,26);						
			jt6 = crearCaja(365,70,110,26);						
			jt7 = crearCaja(365,100,110,26);
			jt7.setText("0");
			jt8 = crearCaja(365,130,110,26);
			jt8.setEditable(false);
			
			jt1.addKeyListener(new KeyListener() {
                public void keyTyped(KeyEvent e) {}
                public void keyPressed(KeyEvent e) {}
                public void keyReleased(KeyEvent e) {
                 try {  
                	 double a=VistaKardes.convertir(jt1.getText());
                	 jt5.setText(""+(redondea(a*tipoCambio)));
                	 double b=VistaKardes.convertir(jt2.getText());
                	 double c=VistaKardes.convertir(jt3.getText());
                	 jt4.setText(""+(redondea(a+b+c)));
                 } catch (Exception ex) {}
                 try {  
                	 double d1=VistaKardes.convertir(jt5.getText());
                	 double e2=VistaKardes.convertir(jt6.getText());
                	 double f3=VistaKardes.convertir(jt7.getText());
                	 //System.out.println("jt1 "+d1+"  "+e2+"  "+f3);
                	 jt8.setText(""+(redondea(d1+e2+f3)));
                 } catch (Exception ex) {}
                 try {calcularCIFyDA();
                 } catch (Exception ex) {}
                }
            });
			jt2.addKeyListener(new KeyListener() {
                public void keyTyped(KeyEvent e) {}
                public void keyPressed(KeyEvent e) {}
                public void keyReleased(KeyEvent e) {
                 try {  
                	 double a=VistaKardes.convertir(jt1.getText());
                	 double b=VistaKardes.convertir(jt2.getText());
                	 jt6.setText(""+(redondea(b*tipoCambio)));
                	 double c=VistaKardes.convertir(jt3.getText());
                	 jt4.setText(""+(redondea(a+b+c)));
                 } catch (Exception ex) {}
                 try {  
                	 double d1=VistaKardes.convertir(jt5.getText());
                	 double e2=VistaKardes.convertir(jt6.getText());
                	 double f3=VistaKardes.convertir(jt7.getText());
                	 //System.out.println("jt2 "+d1+"  "+e2+"  "+f3);
                	 jt8.setText(""+(redondea(d1+e2+f3)));
                 } catch (Exception ex) {}
                }
            });
			jt3.addKeyListener(new KeyListener() {
                public void keyTyped(KeyEvent e) {}
                public void keyPressed(KeyEvent e) {}
                public void keyReleased(KeyEvent e) {
                 try {  
                	 double a=VistaKardes.convertir(jt1.getText());
                	 double b=VistaKardes.convertir(jt2.getText());
                	 double c=VistaKardes.convertir(jt3.getText());
                	 jt7.setText(""+(redondea(c*tipoCambio)));
                	 jt4.setText(""+(redondea(a+b+c)));
                 } catch (Exception ex) {}
                 try {  
                	 double d1=VistaKardes.convertir(jt5.getText());
                	 double e2=VistaKardes.convertir(jt6.getText());
                	 double f3=VistaKardes.convertir(jt7.getText());
                	 //System.out.println("jt3 "+d1+"  "+e2+"  "+f3);
                	 jt8.setText(""+(redondea(d1+e2+f3)));
                 } catch (Exception ex) {}
                }
            });
			/***para la otra caja*/
			jt5.addKeyListener(new KeyListener() {
                public void keyTyped(KeyEvent e) {}
                public void keyPressed(KeyEvent e) {}
                public void keyReleased(KeyEvent e) {
                 try {  
                	 double a=VistaKardes.convertir(jt5.getText());
                	 jt1.setText(""+(redondea(a/tipoCambio)));
                	 double b=VistaKardes.convertir(jt6.getText());
                	 double c=VistaKardes.convertir(jt7.getText());
                	 jt8.setText(""+(redondea(a+b+c)));
                 } catch (Exception ex) {}
                 try {  
                	 double d1=VistaKardes.convertir(jt1.getText());
                	 double e2=VistaKardes.convertir(jt2.getText());
                	 double f3=VistaKardes.convertir(jt3.getText());
                	 jt4.setText(""+(redondea(d1+e2+f3)));
                 } catch (Exception ex) {}
                 try {calcularCIFyDA();
                 } catch (Exception ex) {}
                }
            });
			jt6.addKeyListener(new KeyListener() {
                public void keyTyped(KeyEvent e) {}
                public void keyPressed(KeyEvent e) {}
                public void keyReleased(KeyEvent e) {
                 try {  
                	 double a=VistaKardes.convertir(jt5.getText());
                	 double b=VistaKardes.convertir(jt6.getText());
                	 jt2.setText(""+(redondea(b/tipoCambio)));
                	 double c=VistaKardes.convertir(jt7.getText());
                	 jt8.setText(""+(redondea(a+b+c)));
                 } catch (Exception ex) {}
                 try {  
                	 double d1=VistaKardes.convertir(jt1.getText());
                	 double e2=VistaKardes.convertir(jt2.getText());
                	 double f3=VistaKardes.convertir(jt3.getText());
                	 jt4.setText(""+(redondea(d1+e2+f3)));
                 } catch (Exception ex) {}
                }
            });
			jt7.addKeyListener(new KeyListener() {
                public void keyTyped(KeyEvent e) {}
                public void keyPressed(KeyEvent e) {}
                public void keyReleased(KeyEvent e) {
                 try {  
                	 double a=VistaKardes.convertir(jt5.getText());
                	 double b=VistaKardes.convertir(jt6.getText());
                	 double c=VistaKardes.convertir(jt7.getText());
                	 jt3.setText(""+(redondea(c/tipoCambio)));
                	 jt8.setText(""+(redondea(a+b+c)));
                 } catch (Exception ex) {}
                 try {  
                	 double d1=VistaKardes.convertir(jt1.getText());
                	 double e2=VistaKardes.convertir(jt2.getText());
                	 double f3=VistaKardes.convertir(jt3.getText());
                	 jt4.setText(""+(redondea(d1+e2+f3)));
                 } catch (Exception ex) {}
                }
            });
			/***para controlar Cambio*/
			
			JLabel tipoC= crearLabel("Tipo de Cambio Bs/$us", 575, 40, 125, 30);
			JLabel cifF= crearLabel("CIF Frontera", 600, 70, 100, 30);
			JLabel da= crearLabel("Derechos Arancelarios", 580, 100, 120, 30);
			
			jtTc = crearCaja(700, 40, 100, 26);
			jtTc.setText(""+tipoCambio);
			jtTc.setEditable(false);
			jtCf = crearCaja(700, 70, 100, 26);
			jtCf.setEditable(false);
			jtDa = crearCaja(700, 100, 100, 26);
			jtDa.setEditable(false);
			
			JLabel dA= crearLabel("D.A. = ", 580, 180, 50, 30);
			jtP = crearCaja(630, 180, 30, 26);
			jtP.setText(""+15);
			jtP.setBackground(new Color(234,234,234));
			jtP.addKeyListener(new KeyListener() {
                public void keyTyped(KeyEvent e) {}
                public void keyPressed(KeyEvent e) {}
                public void keyReleased(KeyEvent e) {
                 try { 
                	 calcularCIFyDA();
                	 //calcular();
                	 completarTabla();
                 } catch (Exception ex) {}
                }
            });
			JLabel dcif= crearLabel("% de CIF FRONTERA", 670, 180, 160, 30);
			
			/***extras para tunear la tabla*/
			JLabel g1=crearLabel("Pu FOB",230 , 226, 100, 30);
			g1.setFont(new Font("Arial",2,12));
			g1.setForeground(Color.blue);
			JLabel g2=crearLabel("VALOR FOB",375 , 226, 100, 30);
			g2.setFont(new Font("Arial",2,12));
			g2.setForeground(Color.blue);
			JLabel g3=crearLabel("COSTOS",580 , 226, 100, 30);
			g3.setFont(new Font("Arial",2,12));
			g3.setForeground(Color.blue);
			
			JButton jbCompletar= new JButton("Completar Tabla");
			jbCompletar.setBounds(400, 180, 140, 30);
			c.add(jbCompletar);
			jbCompletar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ex) {
                    completarTabla();
                }
			});
			
	    	jcb=new JComboBox(preciosUf);
	    	jcb.setBounds(400, 390, 120, 30);
	    	jcb.setVisible(true);
			c.add(jcb);
			
			JLabel jldes= crearLabel("Selecionar P.u. Final de ", 260, 390, 140, 30);
			c.add(jldes);
			
			JButton jbAceptar= new JButton("Aceptar");
			jbAceptar.setBounds(520, 390, 100, 30);
			c.add(jbAceptar);
			jbAceptar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					try {
						//System.out.println("entra");
						int identificador=jcb.getSelectedIndex();
						String cad = prod.get(identificador).get(1);
						//System.out.println(cad);
						double d2=VistaKardes.convertir(cad);
						double d=VistaKardes.convertir(prod.get(identificador).get(10));
						//System.out.println(""+identificador+" "+l+" "+d);
						kardex.importacion(fecha, d2, d);
						//dispose();
					} catch (Exception e) {System.out.println("Algo estas haciendo mal");}
					dispose();
				}
			});
		}
		public void paint(Graphics g){
			/*g.setColor(Color.green);
			g.fillRect(0, 0, 900, 500);*/

			g.setColor(new Color(130,130,130));
			g.fillRoundRect(96,6, 405, 165,20,20);
			g.setColor(new Color(226,255,143));
			g.fillRoundRect(95,5, 400, 160,20,20);
			
			g.setColor(new Color(130,130,130));
			g.fillRoundRect(566, 31, 245, 110,20,20);
			g.setColor(new Color(226,255,143));
			g.fillRoundRect(565, 30, 240, 105,20,20);
			g.setColor(new Color(230,230,230));
			/*para la tabla*/g.fillRoundRect(164,230, 565, 50,10,10);
			
			g.setColor(Color.BLACK);
			g.drawLine(95, 38, 495, 38);
			g.drawLine(95, 128, 495, 128);
			g.drawLine(235, 5, 235, 163);
			g.drawLine(355, 5, 355, 163);
			/*para la tabla*/g.drawRoundRect(164,230, 565, 50,10,10);
			g.drawLine(326, 230, 326, 256);
			g.drawLine(488, 230, 488, 256);
			/*para los cuadrados*/
			g.drawRoundRect(95,5, 400, 160,20,20);
			g.drawRoundRect(565, 30, 240, 105,20,20);
		}
		private JLabel crearLabel(String texto,int x,int y,int w,int h){
			JLabel label = new JLabel(texto);
			label.setBounds(x,y,w,h);
			c.add(label);
			return label;
		}
		private JTextField crearCaja(int x,int y,int w,int h){
			JTextField caja = new JTextField();
			caja.setBounds(x,y,w,h);
			caja.setForeground (new Color(0,0,102));
			c.add(caja);
			return caja;
		}
		public double redondea(Double n){
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
		public double redondeaB(double numero){
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
	    public void actualizar()
	    {
	    	
	        Object []   head = {"MATERIAL", "Q", "[$us]", "[Bs]", "[$us]", "[Bs]","F. y S.", "D. A.", "TOTAL", "C. TOTAL"
	        		,"P.u. [Bs]"};
	        
	        Object [][] alfa = new Object[prod.size()][11];
	        for(int i = 0; i < prod.size(); i++)
	        {
	            alfa [i] = prod.get(i).toArray();   
	        }
	        table.setModel(new javax.swing.table.DefaultTableModel(alfa, head){
	            public boolean isCellEditable(int rowIndex , int columnIndex) {
	                return false; 
	            }
	        });
	        table.getTableHeader().setForeground(Color.blue);
	        table.getTableHeader().setBackground(Color.green);
	        table.getTableHeader().setFont(new Font("Arial",2,12));      
	    }
	    public void calcularCIFyDA(){
	    	/*vars   jtTc jtCf jtDa*/
	    	double fySof=VistaKardes.convertir(prod.get(prod.size()-1).get(5));
	    	double cif=(VistaKardes.convertir(jt5.getText())+fySof);
	    	jtCf.setText(""+cif);
	    	double porciento=VistaKardes.convertir(jtP.getText())/100;
	    	dA=redondea(cif*porciento);
	    	jtDa.setText(""+dA);
	    }
	    /***var  nroProductos fecha tipoCambio prod  dA fYs */
	    public void calcular(){
	    	for(int i = 0; i < prod.size()-1; i++)
	        {
	    		String nom=prod.get(i).get(0);
	            double varQ=VistaKardes.convertir(prod.get(i).get(1));
	            double varP1=VistaKardes.convertir(prod.get(i).get(2));
	            double varP2=varP1*tipoCambio;
	            double varV1=redondea(varQ*varP1);
	            double varV2=redondea(varQ*varP2);
	            ArrayList<String> a= new ArrayList<String>();
	            a.add(nom);a.add(prod.get(i).get(1));a.add(""+varP1);a.add(""+varP2);a.add(""+varV1);a.add(""+varV2);
	            a.add("");a.add("");a.add("");a.add("");a.add("");
	            prod.set(i, a);
	        }
	    	double varSum=0;
	    	for(int j=0; j<prod.size()-1;j++){
	    		varSum+=VistaKardes.convertir(prod.get(j).get(4));
	    		sumBs+=VistaKardes.convertir(prod.get(j).get(5));
	    	}
	    	prod.get(prod.size()-1).set(4, ""+varSum);
	    	prod.get(prod.size()-1).set(5, ""+sumBs);
	    }/*vars  dA, fYs, sumBs=0*/
	    public void completarTabla(){
	    	fYs=VistaKardes.convertir(jt8.getText());
	    	for(int i=0; i<prod.size()-1;i++){
	    		double var1=VistaKardes.convertir(prod.get(i).get(5));//error
	    		double varM=fYs*var1;
	    		double varFyS=redondea((varM/sumBs));
	    		//System.out.println(varFyS+"  "+fYs+"  "+sumBs+"  var1="+var1);
	    		prod.get(i).set(6, ""+varFyS);//aqui el error
	    		
	    		double varAx2=dA*var1;
	    		double varDa=redondea(varAx2/sumBs);
	    		//System.out.println(""+varDa);
	    		prod.get(i).set(7, ""+varDa);
	    		
	    		double var2=VistaKardes.convertir(prod.get(i).get(6));
	    		double var3=VistaKardes.convertir(prod.get(i).get(7));
	    		double varAux=redondea(var2+var3);
	    		prod.get(i).set(8, ""+varAux);
	    		
	    		double var4=VistaKardes.convertir(prod.get(i).get(8));
	    		double varAux2=redondea(var4+var1);
	    		prod.get(i).set(9, ""+varAux2);
	    		
	    		double var5=VistaKardes.convertir(prod.get(i).get(9));//error
	    		double var6=VistaKardes.convertir(prod.get(i).get(1));
	    		double varPu=redondea((var5/var6));
	    		prod.get(i).set(10, ""+varPu);
	    		
	    		preciosUf[i]=prod.get(i).get(0)+" = "+varPu;
	    	}
	    	prod.get(prod.size()-1).set(6, ""+fYs);
	    	prod.get(prod.size()-1).set(7, ""+dA);
	    	prod.get(prod.size()-1).set(8, ""+(fYs+dA));
	    	actualizar();
	    	aparecerPu();
	    }
	    public void aparecerPu(){
	    	jcb.setModel(new DefaultComboBoxModel(preciosUf)); 
	    }
	}
}
