package com.example.canondashboardapi.Converter.converters;

import com.example.canondashboardapi.Converter.interfaces.IGraphConverter;
import com.example.canondashboardapi.DTO.PrintSquareMeterPerMediaTypeDTO;
import com.example.canondashboardapi.Model.interfaces.IPrintSquareMeterPerMediaType;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class PrintSquareMeterPerMediaTypeConverter implements IGraphConverter<IPrintSquareMeterPerMediaType, PrintSquareMeterPerMediaTypeDTO> {


    @Override
    public PrintSquareMeterPerMediaTypeDTO modelToDTO(IPrintSquareMeterPerMediaType iPrintSquareMeterPerMediaType) {
        return new ModelMapper().map(iPrintSquareMeterPerMediaType, PrintSquareMeterPerMediaTypeDTO.class);
    }

    @Override
    public List<PrintSquareMeterPerMediaTypeDTO> ListModelToDTO(List<IPrintSquareMeterPerMediaType> iPrintSquareMeterPerMediaTypes) {
        List<PrintSquareMeterPerMediaTypeDTO> result = new ArrayList<>();
        for (IPrintSquareMeterPerMediaType iPrintSquareMeterPerMediaType : iPrintSquareMeterPerMediaTypes) {
            result.add(this.modelToDTO(iPrintSquareMeterPerMediaType));
        }
        return result;
    }
}
