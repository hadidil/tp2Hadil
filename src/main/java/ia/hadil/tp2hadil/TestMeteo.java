package ia.hadil.tp2hadil;

public class TestMeteo {

    public static void main(String[] args) throws Exception {
        MeteoTool outil = new MeteoTool();
        System.out.println(outil.isPluiePrevue(48.85, 2.35, 0, 1));    }
}