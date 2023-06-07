package com.services.tradedoubler.product.processorservice.service.strategy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.services.tradedoubler.product.processorservice.api.bo.ExportFileType;
import com.services.tradedoubler.product.processorservice.api.bo.Result;
import com.services.tradedoubler.product.processorservice.exception.ServiceError;
import org.springframework.stereotype.Service;

@Service
public class XmlExportStrategy implements ExportStrategy{

    private final XmlMapper xmlMapper;

    public XmlExportStrategy() {
        this.xmlMapper = new XmlMapper();
    }

    @Override
    public byte[] exportData(Result result) {
        try {
            return xmlMapper.writeValueAsBytes(result);
        } catch (JsonProcessingException e) {
            throw ServiceError.ERROR_WHILE_PARSING_DOWNLOAD_RESULT.buildException(e.getMessage());
        }
    }

    @Override
    public ExportFileType getExportType() {
        return ExportFileType.XML;
    }
}
