package com.services.tradedoubler.product.processorservice.service.strategy;

import com.services.tradedoubler.product.processorservice.api.bo.ExportFileType;
import com.services.tradedoubler.product.processorservice.api.bo.ProductDto;

import java.io.File;
import java.util.Set;

public interface ExportStrategy {

    File exportData(Set<ProductDto> products);

    ExportFileType getExportType();
}
