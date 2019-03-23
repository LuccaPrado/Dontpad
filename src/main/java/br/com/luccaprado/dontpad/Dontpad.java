package br.com.luccaprado.dontpad;

import com.google.gson.Gson;
import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.IOException;
import java.net.URL;

@Data
public class Dontpad {

    private String body;
    private String lastUpdate;
    private String changed;

    /**
     * @param route Rota do site Ex. pasta ou Ex. pasta/filha/neta
     * @return String Retorna o corpo da url
     * @throws IOException
     */
    public static String get(String route) throws IOException {
        Gson json = new Gson();
        return json.fromJson(Jsoup.connect("http://www.dontpad.com/" + route + ".body.json?lastUpdate=0").ignoreContentType(true).execute().body(), Dontpad.class).body;
    }

    /**
     * @param route Rota do site Ex. pasta ou Ex. pasta/filha/neta
     * @param milis Tempo em milisegundos para a conexão cortar
     * @return String Retorna o corpo da url
     * @throws IOException
     */
    public static String get(String route, Integer milis) throws IOException {
        Gson json = new Gson();

        return json.fromJson(Jsoup.connect("http://www.dontpad.com/" + route + ".body.json?lastUpdate=0").ignoreContentType(true).timeout(milis).execute().body(), Dontpad.class).body;
    }

    /**
     *
     * @param route Rota do site Ex. pasta ou Ex. pasta/filha/neta
     * @throws IOException
     */
    public static void getZip(String route) throws IOException {
        File file = new File(route + ".zip");

        FileUtils.copyURLToFile(new URL("http://www.dontpad.com/" + route + ".zip"), file);
    }

    /**
     *
     * @param route Rota do site Ex. pasta ou Ex. pasta/filha/neta
     * @param path Path do arquivo Ex. C:\\Arquivo\
     * @throws IOException
     */
    public static void getZip(String route, String path) throws IOException {
        if (path.endsWith("/")) {
            File file = new File(path + route + ".zip");
            FileUtils.copyURLToFile(new URL("http://www.dontpad.com/" + route + ".zip"), file);
        }else {
            File file = new File(path+ "/" + route + ".zip");
            FileUtils.copyURLToFile(new URL("http://www.dontpad.com/" + route + ".zip"), file);
        }


    }

    /**
     *
     * @param route Rota do site Ex. pasta ou Ex. pasta/filha/neta
     * @param data Dados enviados Ex. Hello World
     * @return Boolean Se sucesso ou não.
     * @throws IOException
     */
    public static Boolean post(String route, String data) throws IOException {
        Connection.Response res = Jsoup.connect("http://dontpad.com/"+route)
                .header("Content-Type","application/x-www-form-urlencoded;charset=UTF-8")
                .data("text", data)
                .method(Connection.Method.POST)
                .execute();

        if (res.statusCode() == 200){
            return true;
        }else {
            return false;
        }


    }


}


