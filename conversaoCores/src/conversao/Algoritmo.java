package conversao;

import java.io.IOException;

import static jdk.nashorn.internal.objects.NativeMath.min;

public class Algoritmo {

    public String modeloIn;
    public String modeloOut;

    public int[] resultadoInt;
    public double[] resultadoDouble;


    public Algoritmo(String modeloPixel, String modeloConversao, double cor1, double cor2, double cor3) throws IOException {

        modeloIn = modeloPixel;
        modeloOut = modeloConversao;

        aplicarConversoes(cor1, cor2, cor3);
    }

    public void aplicarConversoes(double cor1, double cor2, double cor3) {
        if (modeloIn == "RGB") {
            if (modeloOut == "CMY") {
                resultadoDouble = pixelRGBparaCMY((int) cor1, (int) cor2, (int) cor3);
            } else {
                resultadoDouble = pixelRGBparaHSV((int) cor1, (int) cor2, (int) cor3);
            }
        } else if (modeloIn == "CMY") {
            if (modeloOut == "RGB") {
                resultadoInt = pixelCMYparaRGB(cor1, cor2, cor3);
            } else {
                int[] rgb = pixelCMYparaRGB(cor1, cor2, cor3);
                resultadoDouble = pixelRGBparaHSV(rgb[0], rgb[1], rgb[2]);
            }
        } else {
            if (modeloOut == "CMY") {
                int[] rgb = pixelHSVparaRGB(cor1, cor2, cor3);
                resultadoDouble = pixelRGBparaCMY(rgb[0], rgb[1], rgb[2]);
            } else {
                resultadoInt = pixelHSVparaRGB(cor1, cor2, cor3);
            }
        }
    }

    private double[] pixelRGBparaHSV(int cor1, int cor2, int cor3) {
        double dois_55 = 255;

        double R = cor1 / 255.0;
        double G = cor2 / 255.0;
        double B = cor3 / 255.0;

        System.out.println(R);
        System.out.println(G);
        System.out.println(B);

        System.out.println();

        double Cmax = Math.max(Math.max(R, G), B);
        double Cmin = Math.min(Math.min(R, G), B);

        double delta = Cmax - Cmin;

        System.out.println(Cmax+" - "+Cmin+" = " +delta);

        double h = 0.0;

        if (delta == 0) {
            h = 0;
        } else if (Cmax == R) {
            h = 60 * (( (G - B) / delta) % 6);
        } else if (Cmax == G) {
            h = 60 * (((B - R) / delta) + 2);
        } else  if (Cmax == B){
            h = 60 * (((R - G) / delta) + 4);
        }
        System.out.println();
        System.out.println(360+h);

        h = 360 + h;

        double s;

        if (Cmax == 0) {
            s = 0;
        } else {
            s = delta / Cmax;
        }

        double v = Cmax;

        double[] corHSV = new double[]{h, s, v};

        return corHSV;

    }

    private double[] pixelRGBparaCMY(int cor1, int cor2, int cor3) {

        double[] corCMYK;

        double red = cor1;
        double green = cor2;
        double blue = cor3;

        double cyan = 1 - (red / 255);
        double magenta = 1 - (green / 255);
        double yellow = 1 - (blue / 255);
        corCMYK = new double[]{cyan, magenta, yellow};

        return corCMYK;
    }

    private int[] pixelCMYparaRGB(double cor1, double cor2, double cor3) {

        int[] corRGB;

        double cyan = cor1;
        double magenta = cor2;
        double yellow = cor3;

        int red = (int) ((1 - cyan) * 255);
        int green = (int) ((1 - magenta) * 255);
        int blue = (int) ((1 - yellow) * 255);

//        if(red > 255){
//            red = 255;
//        }
//        if(green > 255){
//            green = 255;
//        }
//        if(blue > 255){
//            blue = 255;
//        }
//
//        if(red < 0){
//            red = 0;
//        }
//        if(green < 0){
//            green = 0;
//        }
//        if(blue < 0){
//            blue = 0;
//        }

        corRGB = new int[]{red, green, blue};

        return corRGB;
    }

    private int[] pixelHSVparaRGB(double cor1, double cor2, double cor3) {
        int[] corRGB;

        double C = cor3 * cor2;
        double X = C * (1 - Math.abs((cor1 / 60) % (2 - 1)));
        double m = cor3 - C;

        int r, g, b;

        double H = cor1;

        if (H >= 0 && H < 60) {
            r = (int) ((C + m) * 255);
            g = (int) ((X + m) * 255);
            b = (int) ((0 + m) * 255);
        } else if (H >= 60 && H < 120) {
            r = (int) ((X + m) * 255);
            g = (int) ((C + m) * 255);
            b = (int) ((0 + m) * 255);
        } else if (H >= 120 && H < 180) {
            r = (int) ((0 + m) * 255);
            g = (int) ((C + m) * 255);
            b = (int) ((X + m) * 255);
        } else if (H >= 180 && H < 240) {
            r = (int) ((0 + m) * 255);
            g = (int) ((X + m) * 255);
            b = (int) ((C + m) * 255);
        } else if (H >= 240 && H < 300) {
            r = (int) ((X + m) * 255);
            g = (int) ((0 + m) * 255);
            b = (int) ((C + m) * 255);
        } else {
            r = (int) ((C + m) * 255);
            g = (int) ((0 + m) * 255);
            b = (int) ((X + m) * 255);
        }

        corRGB = new int[]{r, g, b};
        return corRGB;
    }


}
