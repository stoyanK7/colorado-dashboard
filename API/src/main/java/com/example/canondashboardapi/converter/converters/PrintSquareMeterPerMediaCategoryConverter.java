package com.example.canondashboardapi.converter.converters;

import com.example.canondashboardapi.converter.interfaces.IGraphConverter;
import com.example.canondashboardapi.dto.PrintSquareMeterPerMediaCategoryDTO;
import com.example.canondashboardapi.model.interfaces.IPrintSquareMeterPerMediaCategory;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

// Delete later
public class PrintSquareMeterPerMediaCategoryConverter implements IGraphConverter<IPrintSquareMeterPerMediaCategory, PrintSquareMeterPerMediaCategoryDTO> {


    @Override
    public PrintSquareMeterPerMediaCategoryDTO modelToDTO(IPrintSquareMeterPerMediaCategory iPrintSquareMeterPerMediaCategory) {
        return new ModelMapper().map(iPrintSquareMeterPerMediaCategory, PrintSquareMeterPerMediaCategoryDTO.class);
    }

    @Override
    public List<PrintSquareMeterPerMediaCategoryDTO> ListModelToDTO(List<IPrintSquareMeterPerMediaCategory> iPrintSquareMeterPerMediaCategories) {
        List<PrintSquareMeterPerMediaCategoryDTO> result = new ArrayList<>();
        for (IPrintSquareMeterPerMediaCategory iPrintSquareMeterPerMediaCategory : iPrintSquareMeterPerMediaCategories) {
            result.add(this.modelToDTO(iPrintSquareMeterPerMediaCategory));
        }
        return result;
    }
}
