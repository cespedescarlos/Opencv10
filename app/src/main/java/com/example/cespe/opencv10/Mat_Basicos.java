/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.cespe.opencv10;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.media.ImageReader;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import static org.opencv.core.CvType.CV_32FC1;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

/**
 *
 * @author cespe
 */
public class Mat_Basicos {
    private final String error=" clase: "+this.getClass().getName()+" linea: ";
    Context context;
    //static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    public Mat_Basicos(Context context) {
        this.context=context;
    }

    public int get_filas(Mat m){
        if(m!=null){
            return m.rows();
        }
        return -1;
    }
    
    public int get_columnas(Mat m){
        if(m!=null){
            return m.cols();
        }
        return -1;
    }

    public Mat cargar(Uri direccion, int resource){
        Mat mat_ori;
        if(direccion!=null){
            mat_ori=cargar_mat_galeria(direccion);
        }else{
            mat_ori=cargar_mat_resource(resource);
        }
        return mat_ori;
    }

    private Mat cargar_mat_galeria(Uri direccion){
        Bitmap  mBitmap= null;
        try {
            mBitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), direccion);
            Mat m=new Mat();
            Utils.bitmapToMat(mBitmap,m);
            String men=(" filas: "+m.rows()+" columnas: "+m.cols()+" dataAddr: "+m.dataAddr()+
                    " total: "+m.total()+" size: "+m.size()+" type: "+ CvType.typeToString(m.type()));
            //mensaje(men,false);
            return m;
        } catch (IOException e) {
            mensaje("Error al cargar Imagen",true);
        }
        return null;
    }

    private Mat cargar_mat_resource(int r)  {
        try {
            Mat newImage = Utils.loadResource(context, r);
            String men=(" filas: "+newImage.rows()+" columnas: "+newImage.cols()+" dataAddr: "+newImage.dataAddr()+
                    " total: "+newImage.total()+" size: "+newImage.size()+" type: "+ CvType.typeToString(newImage.type()));
            //mensaje(men,false);
            return newImage;
        } catch (IOException e) {
            //e.printStackTrace();
            mensaje("error carga",true);
        }
        return null;

    }

    public void mensaje(String mensaje, boolean duracion_corta){
        if(duracion_corta){
            Toast.makeText(context,mensaje,Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,mensaje,Toast.LENGTH_LONG).show();
        }

    }

    private Mat escala_grises_mat(Mat imagen){
        if(imagen!=null && !imagen.empty()){
            Mat gris=new Mat(imagen.width(),imagen.height(),imagen.type());
            Imgproc.cvtColor(imagen, gris, Imgproc.COLOR_RGB2GRAY);
            return  gris;
        }
            return null;


    }

    public Bitmap escala_grises(Mat m){
        return matriz_a_Bitmap(escala_grises_mat(m));
    }
    
    // threst 0-127; valor_max 0-255, constante Imgproc.THRESH_BINARY_INV,Imgproc.THRESH_BINARY
    private Mat binarizacion_mat(Mat imagen_gauss,int threst, int valor_max,int constante_threst_binary){
        if(imagen_gauss!=null && !imagen_gauss.empty()){
                Mat binario=new Mat(imagen_gauss.width(),imagen_gauss.height(),imagen_gauss.type());
                Imgproc.threshold(imagen_gauss, binario, threst, valor_max, constante_threst_binary);
            return binario;
                //Imgproc.threshold(gris, binario, 100, 255, Imgproc.THRESH_BINARY);//Imgproc.THRESH_BINARY_INV
        }
        return null;
    }
    
    public Bitmap binarizacion(Mat m_gauss){
        return matriz_a_Bitmap(binarizacion_mat(m_gauss, 100, 255, Imgproc.THRESH_BINARY));
    }
    
     public Bitmap binarizacion_inversa(Mat m_gauss){
        return matriz_a_Bitmap(binarizacion_mat(m_gauss, 100, 255, Imgproc.THRESH_BINARY_INV));
     }
    
    private Mat filtro_Gaussiano_mat(Mat gris){
        if(gris!=null && !gris.empty()){
            Mat destino = new Mat(gris.rows(),gris.cols(),gris.type());
            Imgproc.GaussianBlur(gris, destino,new Size(45,45), 2);
            return destino;
        }
        return null;
    }
    public Bitmap filtro_Gaussiano(Mat gris){
        return matriz_a_Bitmap(filtro_Gaussiano_mat(gris));
    }
    
    private Mat Canny(Mat binaria){
        if(binaria!=null && !binaria.empty()) {
            Mat canny = new Mat(binaria.width(), binaria.height(), binaria.type());
            int min_threshold = 50;
            int ratio = 3;
            Imgproc.Canny(binaria, canny, min_threshold, min_threshold * ratio);
            return canny;
        }
        return null;
    }
    
    private List<MatOfPoint> Canny_devolver_Puntos(Mat canny){
        List<MatOfPoint> contornos=new ArrayList<>();
        Imgproc.findContours(canny, contornos, new Mat(), Imgproc.RETR_LIST,Imgproc.CHAIN_APPROX_NONE);
        return contornos;
    }
    
    private Mat Canny_con_puntos_dibujados(Mat canny){
        List<MatOfPoint> contornos=Canny_devolver_Puntos(canny);
        Imgproc.drawContours(canny, contornos, -1, new Scalar(Math.random() * 255, Math.random() * 255, Math.random() * 255));
        return canny;
    }
    
    public void escribir_imagen(String ruta_completa,Mat imagen){ //"e://procesada_gris.jpg"
        Imgcodecs.imwrite(ruta_completa, imagen);
    }
    
/*    public void Momentos_HU_manual(Mat original){
        original=escala_grises_mat(original);
        original=filtro_Gaussiano_mat(original);
        original=binarizacion_mat(original, 100, 255, Imgproc.THRESH_BINARY);
        original=Canny(original);
        List<MatOfPoint> contornos_original=new ArrayList<>();
        Imgproc.findContours(original, contornos_original, new Mat(), Imgproc.RETR_EXTERNAL,Imgproc.CHAIN_APPROX_NONE);
        Moments p = new Moments();

        List<Moments> nu = new ArrayList<>(contornos_original.size());
        
        for(int i=0; i<contornos_original.size(); i++)
        {
             nu.add(i, Imgproc.moments(contornos_original.get(i),false));
             p=nu.get(i);
        }

        double n20 = p.get_nu20();
        double n02 = p.get_nu02();
        double n30 = p.get_nu30();
        double n12 = p.get_nu12();
        double n21 = p.get_nu21();
        double n03 = p.get_nu03();
        double n11 = p.get_nu11();
        
        double[] momentos=new double[8];
        
        //First moment
        momentos[0] = n20 + n02;

        //Second moment
        momentos[1] = Math.pow((n20 - n02), 2) + 4*Math.pow(n11, 2);
        //Third moment
        momentos[2] = Math.pow(n30 - (3 * n12), 2)
                + Math.pow((3 * n21 - n03), 2);

        //Fourth moment
        momentos[3] = Math.pow((n30 + n12), 2) 
                + Math.pow((n21 + n03), 2);

        //Fifth moment
        double a=(n30-3*n12)+(n30+n12);
        double b=Math.pow((n30+n12),2)-3*(Math.pow((n21+n03), 2));
        double c=(3*n21-n03)*(n21+n03);
        double d=3*Math.pow((n30+n12), 2)-Math.pow((n21+n03), 2);
        
        momentos[4] = a*b+c*d;

        //Sixth moment
        a=Math.pow((n30+n12), 2)-Math.pow((n21+n03), 2);
        b=4*n11*(n30+n12)*(n21+n03);
        momentos[5] = (n20 - n02)*a+b;

        //Seventh moment
        a=(3*n21-n03)*(n30+n12);
        b=Math.pow((n30+n12), 2)-3*Math.pow((n21+n03), 2);
        c=(n30-3*n12)*(n21+n03);
        d=3*Math.pow((n30+n12), 2)-Math.pow((n21+n03), 2);
        momentos[6] = a*b-c*d;

        //Eighth moment
        a=Math.pow((n30+n12), 2);
        b=(n20-n02)*(n30+n12)*(n03+n21);
        momentos[7] = n11 * a - b;
        
        for (int i = 0; i < momentos.length; i++) {
            System.out.println("i: "+momentos[i]+"");
        }
    }
    
    private double[] Moments_HU2(Mat original,boolean optimizado){
        if(original!=null && !original.empty()) {
            try {
                Mat m = escala_grises_mat(original);
                m = filtro_Gaussiano_mat(m);
                m = binarizacion_mat(m, 100, 255, Imgproc.THRESH_BINARY);
                Mat canny = Canny(m);
                List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
                Mat hierarcy = new Mat();
                Imgproc.findContours(canny, contours, hierarcy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
                Moments p = new Moments();
                List<Moments> nu2 = new ArrayList<Moments>(contours.size());
                for (int i = 0; i < contours.size(); i++) {
                    nu2.add(i, Imgproc.moments(contours.get(i), false));
                    p = nu2.get(i);
                }

                Mat t = new Mat();
                Imgproc.HuMoments(p, t);
                int row = t.rows();
                int col = t.cols();
                double temp[];
                double acu = 0;
                double respuesta[] = new double[row];


                for (int i = 0; i < row; i++) {
                    temp = t.get(i, 0);
                    double q = temp[0];
                    if (optimizado) {
                        q = Math.signum(q) * Math.log10(Math.abs(q));
                    }
                    acu = acu + Math.abs(q);
                    System.out.println(i + " = ori:" + temp[0] + " ;opt: " + q);
                    respuesta[i] = q;
                }
                acu = acu / row;
                System.out.println("promedio: " + acu);
                return respuesta;

            } catch (Exception e) {
                System.out.println("Ocurrio un Error al Calcular HU2");
            }
        }
        return null;
    }
    
    private double[] Moments_HU3_area_mayor_90(Mat original,boolean optimizado){
        try {
            Mat m=escala_grises_mat(original);
            m=filtro_Gaussiano_mat(m);
            m=binarizacion_mat(m, 100, 255, Imgproc.THRESH_BINARY);
            Mat canny=Canny(m);
            List<MatOfPoint> contours = new ArrayList<>();
            List<MatOfPoint> contours_escogidos = new ArrayList<>();
            contours=Canny_devolver_Puntos(canny);

            Moments p = new Moments();
            List<Moments> nu2 = new ArrayList<>();
            System.out.println("size list momentos contours: "+contours.size());
            int cc=0;
            for(int i=0; i<contours.size(); i++)
            {
                MatOfPoint parcial=contours.get(i);
                if((parcial.height()*parcial.width())>90){
                    nu2.add(cc, Imgproc.moments(contours.get(i),false));
                    p=nu2.get(cc);
                    cc++;
                    contours_escogidos.add(parcial);
                }
            }
           // System.out.println("size list momentos nu2: "+nu2.size());

            Mat t=new Mat();
            Imgproc.HuMoments(p, t);
            int row=t.rows();
            int col=t.cols();
            double temp[];
            double acu=0;
            double respuesta[]=new double[row];


            for (int i = 0; i < row; i++) {
                    temp=t.get(i, 0);
                    double q=temp[0];
                    if (optimizado) {
                        q=Math.signum(q)*Math.log10(Math.abs(q));
                    }
                    acu=acu+Math.abs(q);
                    //System.out.println(i+" = ori:"+temp[0]+" ;opt: "+q);
                    respuesta[i]=q;
            }
            acu=acu/row;
            mensaje("promedio: "+acu,false);
            return respuesta;
            
        } catch (Exception e) {
            mensaje("Ocurrio un Error al Calcular HU3_area",true);
        }
        return null;
    }*/

    ///////////////////////////////////////////////////////////////

    private List<Momentos_Hu> momento_HU_directo(Mat m){
        try {
            Mat canny=Canny(m);
            List<MatOfPoint> contours = new ArrayList<>();
            List<Momentos_Hu> momentos_hu_todos=new ArrayList<>();
            contours=Canny_devolver_Puntos(canny);
            List<Moments> nu2 = new ArrayList<>();
            System.out.println("size list momentos contours: "+contours.size());
            for(int i=0; i<contours.size(); i++)
            {
                MatOfPoint parcial=contours.get(i);
                if((parcial.height()*parcial.width())>90){
                    nu2.add(Imgproc.moments(contours.get(i),false));
                }
            }
            for (int jj = 0; jj < nu2.size(); jj++) {
                Moments get = nu2.get(jj);
                Mat t=new Mat();
                Imgproc.HuMoments(get, t);
                int row=t.rows();
                int col=t.cols();
                double temp[];
                double acu=0;
                Momentos_Hu momento=new Momentos_Hu();

                for (int i = 0; i < row; i++) {
                    temp=t.get(i, 0);
                    double q=temp[0];
                    q=Math.signum(q)*Math.log10(Math.abs(q));
                    if(Double.compare(q, Double.NaN)==0){
                        q=(double) 0;
                    }

                    momento.set(i, q);
                    acu=acu+Math.abs(q);
                }
                acu=acu/row;
                momento.setPromedio(acu);
                momento.setPosicion_momento(jj);
                momentos_hu_todos.add(momento);
            }
            return momentos_hu_todos;
        } catch (Exception e) {
            mensaje("Error al calcular los Momentos de la Imagen, "+error+" 393",false);
        }
        return null;
    }

    private List<Momentos_Hu> procesar_HU_intermediario(Mat m){
        // de todos los momentos devolveremos solo 2 el primero y ultimo
        // si es que no son 0
        List<Momentos_Hu> res=null;
        List<Momentos_Hu> lista_momentos=momento_HU_directo(m);
        lista_momentos=Momentos_Hu.limpiar_lista(lista_momentos);
        if(lista_momentos!=null && lista_momentos.size()>1){
            Momentos_Hu get_primero=lista_momentos.get(0);
            Momentos_Hu get_ultimo=lista_momentos.get(lista_momentos.size()-1);
            res=new ArrayList<>();
            res.add(get_primero);
            res.add(get_ultimo);
        }
        /*else{
            mensaje("Error la lista limpiada no es suficiente,; "+error+" 421", false);
        }*/
        return res;
    }
    public List<Momentos_Hu> procesar_HU_solo(Uri ruta,int resource){
        Mat z=cargar(ruta,resource);
        z=escala_grises_mat(z);
        z=filtro_Gaussiano_mat(z);
        z=binarizacion_mat(z, 100, 255, Imgproc.THRESH_BINARY);
        return procesar_HU_intermediario(z);

    }

    public void guardar_fotos_angulos(Uri ruta){
        procesar_grados(ruta,0);
        procesar_grados(ruta,90);
        procesar_grados(ruta,180);
        procesar_grados(ruta,270);
    }

    public List<Momentos_Hu> procesar_HU_angulos(int resource){
        // devolvera una lista con 2 momentos para cada imagen,
        //pero como procesaremos la misma imagen con diferentes angulos
        //  seran 4 imagenes en total 0°, 90°, 180°, 270°
        // por tanto la lista contendra 8 momentos
        List<Momentos_Hu> result = null;
            result=new ArrayList<>();

            String destino = context.getExternalFilesDir(Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath()+"/Foto_Scan/" ;
            String extension=".png";

            List<Momentos_Hu> lista=null;
          //  if(procesar_grados(ruta,90)){
            File image=new File(destino,"90"+extension);
            if(image.exists()){
                    lista=procesar_HU_solo(Uri.fromFile(image),0);
                    if(lista!=null){
                        result.add(lista.get(0));
                        result.add(lista.get(1));
                    }
            }
            //}else{
              //  mensaje("No se Pudo Procesar la Imagen a 90°",false);
            //}

            //if(procesar_grados(ruta,180)){
                image=new File(destino,"180"+extension);
                if(image.exists()){
                    lista=procesar_HU_solo(Uri.fromFile(image),0);
                    if(lista!=null){
                        result.add(lista.get(0));
                        result.add(lista.get(1));
                    }
                }

//            }else{
  //              mensaje("No se Pudo Procesar la Imagen a 180°",false);
    //        }

            //if(procesar_grados(ruta,270)){
                image=new File(destino,"270"+extension);
                if(image.exists()){
                    lista=procesar_HU_solo(Uri.fromFile(image),0);
                    if(lista!=null){
                        result.add(lista.get(0));
                        result.add(lista.get(1));
                    }
                }

            //}else{
            //    mensaje("No se Pudo Procesar la Imagen a 270°",false);
            //}
        if(result.isEmpty()){
            return null;
        }
        return result;
    }



    public boolean procesar_grados(Uri ruta, int grado){
        Mat z=cargar(ruta,0);
        z=rotacion_grados_flip(z,grado);
        return guardar_bitmap(matriz_a_Bitmap(z),""+grado);

    }

    public boolean guardar_bitmap(Bitmap imagen,String nombre){
        //memoria interna
        String destiino = context.getExternalFilesDir(Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath()+"/Foto_Scan/" ;
        File file =new File(destiino);
        file.mkdirs();

        String extension=".png";

        File image=new File(destiino,nombre+extension);
        FileOutputStream outputStream;
        try {
            outputStream =new FileOutputStream(image);
            //Bitmap mBitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), ruta);

            imagen.compress(Bitmap.CompressFormat.PNG,100,outputStream);
            outputStream.flush();
            outputStream.close();

            // comprobamos si se grabo el archivo
            file=new File(destiino+nombre+extension);
            return file.exists();

        } catch (FileNotFoundException e) {
            mensaje(e.getMessage(),false);
            //e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
            mensaje(e.getMessage(),false);
        }

        return false;

    }


    /*public void guardar(Uri ruta){
        Mat z=cargar(ruta,0);
        // este metodo sirve para cualquier version

        //memoria interna
        String destiino = context.getExternalFilesDir(Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath()+"/Foto_Scan/" ;
        // memoria externa
        //String destiino = getFilesDir().getAbsolutePath()+"/Foto_Scan/" ;
        mensaje(destiino,false);
        File file =new File(destiino);
        file.mkdirs();

        // este metodo solo sirve para version < android 5.0
        //File localFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/FotosRafa/");
        //localFile.mkdirs();

        File image=new File(destiino,"prueba.png");
        FileOutputStream outputStream;
        try {
            outputStream =new FileOutputStream(image);
            Bitmap mBitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), ruta);
            mBitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
            outputStream.flush();
            outputStream.close();

        } catch (FileNotFoundException e) {
            mensaje(e.getMessage(),false);
            //e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
            mensaje(e.getMessage(),false);
        }

    }*/


    /////////////////////////////////////////////////////




    
    /*public double[] procesar_HU(Mat original){
        return Moments_HU3_area_mayor_90(original, true);
    }*/

    public Mat rotacion_grados_flip(Mat src,int grados){
        Mat dst=new Mat();
        switch(grados){
            case 0: dst=src;break;
            case 90: Core.flip(src.t(), dst, 1);break;
            //case 180: Core.flip(src.t(), dst, -1);break;
            case 180: Core.flip(src.t(), dst, 1);
                      Core.flip(dst.t(), dst, 1);break;
            case 270: Core.flip(src.t(), dst, 0);break;
            // para el 360 se hace asi porque haciendo 2 veces 180 la imagen se
            // voltea con el texto mas, de derecha a izquierda
            case 360:
                Core.flip(src.t(), dst, 1);
                Core.flip(dst.t(), dst, 0);break;
        }
        return dst;
    }
    
    public void convertir(Mat binaria){
        if(binaria!=null && !binaria.empty()){
            mensaje("old: "+CvType.typeToString(binaria.type()),true);
            binaria.convertTo(binaria,CV_32FC1, 1.0/255.0);
            mensaje("new: "+CvType.typeToString(binaria.type()),true);
        }
    }
    
    public Bitmap matriz_a_Bitmap(Mat matriz){
        if(matriz!=null && !matriz.empty()){
            Bitmap b =Bitmap.createBitmap(matriz.width(),matriz.height(),Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(matriz,b);
            return b;
        }
            return null;


    }
    
    public boolean comparar_exactas(Uri ruta_orig, Uri ruta_copia){
        boolean resultado=false;
        long inicio=System.currentTimeMillis();
        Mat original=cargar_mat_galeria(ruta_orig);
        Mat copia=cargar_mat_galeria(ruta_copia);
        
        Mat total=new Mat();
        
            if(tamaño_imagen(original, copia)){
                try {
                    Core.absdiff(original,copia, total);

                    Mat gris=escala_grises_mat(total);
                    List<MatOfPoint> contornos=new ArrayList<>();
                    Imgproc.findContours(gris, contornos, new Mat(), Imgproc.RETR_LIST,Imgproc.CHAIN_APPROX_SIMPLE);

                    if(contornos.isEmpty()){
                        long diferencia=System.currentTimeMillis()-inicio;
                        resultado=true;
                    }
                } catch (Exception e) {
                    mensaje("tamaños distintos",true);
                }
                
            }else{
                mensaje("tamaños distintos",true);
           }
            
        
                    long diferencia=System.currentTimeMillis()-inicio;
                  
                    String tardo=" Tiempo: "+(diferencia/60000)+" minutos, "+
                                    (diferencia/1000)+" segundos, "+
                                    " milisegundos "+diferencia%1000;
                    mensaje("comparacion exacta: "+tardo,true);
        
        return resultado;
    }
   
    public void escribir_texto(Mat imagen,String ruta){
        long inicio=System.currentTimeMillis();
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter(ruta);
            pw = new PrintWriter(fichero);

            if(imagen.dataAddr()!=0){
                int row=imagen.rows();
                int col=imagen.cols();
                double temp[];
                
                for (int i = 0; i < row; i++) {
                    String s_fil="";
                    for (int j = 0; j < col; j++) {
                        temp=imagen.get(i, j);
                        s_fil=s_fil+" [";
                        for (int k = 0; k < temp.length; k++) {
                             s_fil=s_fil+String.valueOf(temp[k])+"; ";
                        }
                        s_fil=s_fil+"]";
                    }
                    pw.println(s_fil);
                }
            }

        } catch (Exception e) {
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
                    long diferencia=System.currentTimeMillis()-inicio;
                    String tardo=" Tiempo: "+(diferencia/60000)+" minutos, "+
                                    (diferencia/1000)+" segundos, "+
                                    " milisegundos "+diferencia%1000;
                    System.out.println("escritura archivo: "+ruta+" ; "+tardo);
           } catch (Exception e2) {
           }
        }
    }
    
    public int canales(Mat imagen){
        return imagen.channels();
    }
    
    public int typo_imagen(Mat imagen){
        return imagen.type();
    }
    
    public boolean tamaño_imagen(Mat original, Mat copia){
        return (original.rows()==copia.rows() && original.cols()==copia.cols());
    }
    
}
