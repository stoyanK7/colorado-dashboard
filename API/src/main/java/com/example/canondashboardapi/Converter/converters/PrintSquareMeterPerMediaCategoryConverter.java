package com.example.canondashboardapi.Converter.converters;

import com.example.canondashboardapi.Converter.interfaces.IGraphConverter;
import com.example.canondashboardapi.DTO.PrintSquareMeterPerMediaCategoryDTO;
import com.example.canondashboardapi.Model.interfaces.IPrintSquareMeterPerMediaCategory;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

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
