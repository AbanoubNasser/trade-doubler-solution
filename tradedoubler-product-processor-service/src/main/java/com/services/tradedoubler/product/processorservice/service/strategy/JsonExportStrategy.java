package com.services.tradedoubler.product.processorservice.service.strategy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.tradedoubler.product.processorservice.api.bo.ExportFileType;
import com.services.tradedoubler.product.processorservice.api.bo.Result;
import com.services.tradedoubler.product.processorservice.exception.ServiceError;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JsonExportStrategy implements ExportStrategy{
    private final ObjectMapper objectMapper;
    public JsonExportStrategy(){
        this.objectMapper =new ObjectMapper();
    }
    @Override
    public byte[] exportData(Result result) {
        try {
            return  objectMapper.writeValueAsBytes(result);
        } catch (IOException e) {
           throw ServiceError.ERROR_WHILE_PARSING_DOWNLOAD_RESULT.buildException(e.getMessage());
        }
    }

    @Override
    public ExportFileType getExportType() {
        return ExportFileType.JSON;
    }
}
