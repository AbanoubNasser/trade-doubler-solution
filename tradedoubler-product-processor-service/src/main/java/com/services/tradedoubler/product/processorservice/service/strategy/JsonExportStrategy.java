package com.services.tradedoubler.product.processorservice.service.strategy;

import com.services.tradedoubler.product.processorservice.api.bo.ExportFileType;
import com.services.tradedoubler.product.processorservice.api.bo.ProductDto;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Set;

@Service
public class JsonExportStrategy implements ExportStrategy{
    @Override
    public File exportData(Set<ProductDto> products) {
        return null;
    }

    @Override
    public ExportFileType getExportType() {
        return ExportFileType.JSON;
    }
}
