package com.services.tradedoubler.product.processorservice.service.strategy;

import com.services.tradedoubler.product.processorservice.api.bo.ExportFileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class ExportStrategyFactory {
    private Map<ExportFileType, ExportStrategy> strategies;

    @Autowired
    public ExportStrategyFactory(Set<ExportStrategy> strategySet) {
        createStrategy(strategySet);
    }

    public ExportStrategy findExportStrategyByExportFileType(ExportFileType exportFileType) {
        return strategies.get(exportFileType);
    }
    private void createStrategy(Set<ExportStrategy> strategySet) {
        strategies = new HashMap<>();
        strategySet.forEach(
                strategy ->strategies.put(strategy.getExportType(), strategy));
    }
}
