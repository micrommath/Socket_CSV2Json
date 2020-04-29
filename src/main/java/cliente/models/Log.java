package cliente.models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class Log {

    static String caminhoArquivoLog = System.getProperty("java.io.tmpdir") + "logCSVToJson.txt";

    public static void GravarLog(LogInformacoes log) throws IOException {

        GravarLog(log.getLeitura(), log.getParse(), log.getGravacao());

    }

    public static void GravarLog(long tempoLeitura, long tempoParse, long tempoGravacao) throws IOException {

        if (!Files.exists(Paths.get(caminhoArquivoLog))) {
            Files.createFile(Paths.get(caminhoArquivoLog));
        }

        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(caminhoArquivoLog), StandardCharsets.ISO_8859_1,
                StandardOpenOption.APPEND)) {

            bw.append(tempoLeitura + ";" + tempoParse + ";" + tempoGravacao + "\n");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static List<LogInformacoes> LerTodoLog() {
        if (!Files.exists(Paths.get(caminhoArquivoLog))) {
            return new ArrayList<LogInformacoes>();
        }

        List<LogInformacoes> logs = new ArrayList<LogInformacoes>();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(caminhoArquivoLog), StandardCharsets.ISO_8859_1)) {

            while (br.ready()) {

                String[] informacoes = br.readLine().split(";");

                LogInformacoes log = new LogInformacoes();
                log.setGravacao(Long.parseLong(informacoes[2]));
                log.setLeitura(Long.parseLong(informacoes[0]));
                log.setParse(Long.parseLong(informacoes[1]));

                logs.add(log);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return logs;
    }
}