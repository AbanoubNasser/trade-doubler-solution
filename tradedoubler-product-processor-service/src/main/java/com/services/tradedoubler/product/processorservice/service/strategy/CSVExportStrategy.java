package com.services.tradedoubler.product.processorservice.service.strategy;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.services.tradedoubler.product.processorservice.api.bo.ExportFileType;
import com.services.tradedoubler.product.processorservice.api.bo.ProductDto;
import com.services.tradedoubler.product.processorservice.api.bo.Result;
import com.services.tradedoubler.product.processorservice.exception.ServiceError;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;


@Service
public class CSVExportStrategy implements ExportStrategy{

    private final CsvMapper csvMapper;

    public CSVExportStrategy() {
        this.csvMapper = new CsvMapper();
    }

    @Override
    public byte[] exportData(Result result) {

        try {
            CsvSchema schema = csvMapper.schemaFor(ProductDto.class);
            schema = schema.withColumnSeparator(',');
            schema = schema.withUseHeader(true);
            ObjectWriter myObjectWriter = csvMapper.writer(schema);
            File tempFile = new File("products.csv");
            FileOutputStream tempFileOutputStream = new FileOutputStream(tempFile);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(tempFileOutputStream, 1024);
            OutputStreamWriter writerOutputStream = new OutputStreamWriter(bufferedOutputStream, "UTF-8");
            myObjectWriter.writeValue(writerOutputStream, result.getProducts());
            byte[] data = Files.readAllBytes(tempFile.toPath());
            Files.delete(tempFile.toPath());
            return data;
        }catch (Exception ex){
            throw ServiceError.ERROR_WHILE_PARSING_DOWNLOAD_RESULT.buildException(ex.getMessage());
        }
    }

    @Override
    public ExportFileType getExportType() {
        return ExportFileType.CSV;
    }
}
