package com.services.tradedoubler.product.processorservice.service.strategy;

import com.services.tradedoubler.product.processorservice.api.bo.ExportFileType;
import com.services.tradedoubler.product.processorservice.api.bo.Result;
import org.springframework.stereotype.Service;


@Service
public class CSVExportStrategy implements ExportStrategy{
    @Override
    public byte[] exportData(Result result) {
        return null;
    }

    @Override
    public ExportFileType getExportType() {
        return ExportFileType.CSV;
    }
}
