
package com.eleicao.ptep.relatorio;

import com.eleicao.ptep.relatorio.dto.RelatorioFinaldto;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

/**
 *
 * @author hallef
 */
@Component
public class RelatorioService {
    
    @Autowired
    private RelatorioImpl relatorioService;
    
    public void gerarFinal(HttpServletResponse response) throws IOException, IOException {
        
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=relatorio_final_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);
         
        List<RelatorioFinaldto> listRelatorioFinal = relatorioService.listRelatorioFinal();
 
        try (ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.EXCEL_PREFERENCE)) {
            String[] csvHeader = {"Candidato","Cargo","Quantidade votos"};
            String[] nameMapping = {"candidato","cargo","quantidadeVoto"};
            csvWriter.writeHeader(csvHeader);
            for (RelatorioFinaldto relatorioFinaldto : listRelatorioFinal) {
                csvWriter.write(relatorioFinaldto, nameMapping);
            }
            csvWriter.writeComment("Total de votantes: "+relatorioService.relatorioParcial().getQuantidadeVotantes());
        }
    }
    
    public void gerarParcial(HttpServletResponse response) throws IOException, IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=relatorio_parcial_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        try (ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.EXCEL_PREFERENCE)) {
            String[] csvHeader = {"Quantidade de votantes"};
            String[] nameMapping = {"quantidadeVotantes"};
            csvWriter.writeHeader(csvHeader);
            csvWriter.write(relatorioService.relatorioParcial(), nameMapping);
        }
    }
}
