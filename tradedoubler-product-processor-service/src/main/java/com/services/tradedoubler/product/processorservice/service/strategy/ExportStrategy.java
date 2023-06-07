package com.services.tradedoubler.product.processorservice.service.strategy;

import com.services.tradedoubler.product.processorservice.api.bo.ExportFileType;
import com.services.tradedoubler.product.processorservice.api.bo.Result;


public interface ExportStrategy {

    byte[] exportData(Result result);

    ExportFileType getExportType();
}
